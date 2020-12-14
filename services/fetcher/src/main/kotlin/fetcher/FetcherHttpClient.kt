package fetcher

import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import org.jsoup.Jsoup
import java.io.Closeable

private const val UserAgent = "Fetcher/0.0.1 (+https://github.com/SlashNephy/Hatena-Intern-2020)"

object Http: FetcherHttpClient({
    HttpClient {
        HttpResponseValidator {
            validateResponse { response ->
                val statusCode = response.status.value
                when (statusCode) {
                    in 400..499 -> throw ClientRequestException(response)
                    in 500..599 -> throw ServerResponseException(response)
                }

                if (statusCode >= 600) {
                    throw ResponseException(response)
                }
            }
        }
    }
})

open class FetcherHttpClient(block: () -> HttpClient): Closeable {
    private val underlying = block()

    /**
     * Extracts <title> tag from url.
     * @param url The page url.
     * @return The page title if exists, or null.
     */
    suspend fun getPageTitle(url: String): String? {
        val content = underlying.get<HttpResponse>(url) {
            userAgent(UserAgent)
        }

        val type = content.contentType() ?: ContentType.Any
        if (!type.match(ContentType.Text.Html)) {
            return null
        }

        val html = content.readText()
        val document = Jsoup.parse(html)

        val title = document.title()
        if (title.isBlank()) {
            return null
        }

        return title
    }

    /**
     * Closes the internal [HttpClient].
     */
    override fun close() {
        underlying.close()
    }
}
