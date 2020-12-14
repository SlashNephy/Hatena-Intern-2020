package fetcher

import fetcher.pb.FetcherServiceGrpcKt
import fetcher.pb.PageTitleRequest
import fetcher.pb.PageTitleResponse
import io.ktor.http.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.time.Instant
import kotlin.time.hours
import kotlin.time.toJavaDuration

object FetcherService: FetcherServiceGrpcKt.FetcherServiceCoroutineImplBase() {
    override suspend fun getPageTitle(request: PageTitleRequest): PageTitleResponse {
        val result = runCatching {
            when (val old = getPageTitleFromCache(request.url)) {
                is PageTitleCache.Value -> {
                    logger.info { "Found url in cache: ${request.url}" }

                    old.value
                }
                is PageTitleCache.NotFound -> {
                    logger.info { "Not found url in cache: ${request.url}" }

                    val new = Http.getPageTitle(request.url)
                    setPageTitleToCache(request.url, new)

                    new
                }
            }
        }.onSuccess {
            logger.info { "gRPC response success: ${request.url} => $it" }
        }.onFailure {
            logger.error(it) { "gRPC response failure: ${request.url}" }
        }

        return PageTitleResponse.newBuilder()
            .apply {
                if (result.isSuccess) {
                    if (result.getOrNull() != null) {
                        code = PageTitleResponse.StatusCode.OK
                        title = result.getOrNull()
                    } else {
                        code = PageTitleResponse.StatusCode.UNDEFINED_TITLE
                    }
                } else {
                    code = PageTitleResponse.StatusCode.UNAVAILABLE
                }
            }
            .build()
    }

    private val cache = mutableMapOf<String, PageTitleCache.Value>()
    private val cacheLock = Mutex()
    private val cacheValidDuration = 3.hours.toJavaDuration()

    private sealed class PageTitleCache {
        data class Value(val value: String?, val updated: Instant): PageTitleCache()
        object NotFound: PageTitleCache()
    }

    private suspend fun getPageTitleFromCache(url: String): PageTitleCache {
        val value = cacheLock.withLock {
            cache[url] ?: return PageTitleCache.NotFound
        }

        // クエリパラメータがあるときはキャッシュを無視
        val parsedUrl = Url(url)
        if (!parsedUrl.parameters.isEmpty()) {
            return PageTitleCache.NotFound
        }

        // キャッシュ期限が切れたものは無視
        val delta = java.time.Duration.between(Instant.now(), value.updated)
        return if (delta < cacheValidDuration) {
            value
        } else {
            PageTitleCache.NotFound
        }
    }

    private suspend fun setPageTitleToCache(url: String, title: String?) {
        cacheLock.withLock {
            cache[url] = PageTitleCache.Value(title, Instant.now())
        }
    }
}
