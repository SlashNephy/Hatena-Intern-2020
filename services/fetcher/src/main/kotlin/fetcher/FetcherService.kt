package fetcher

import fetcher.pb.FetcherServiceGrpcKt
import fetcher.pb.PageTitleRequest
import fetcher.pb.PageTitleResponse

object FetcherService: FetcherServiceGrpcKt.FetcherServiceCoroutineImplBase() {
    override suspend fun getPageTitle(request: PageTitleRequest): PageTitleResponse {
        val result = runCatching {
            Http.getPageTitle(request.url)
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
}
