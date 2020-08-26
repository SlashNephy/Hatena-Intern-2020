package fetcher

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import org.jsoup.Jsoup
import java.io.Closeable

object Http: Closeable {
    private const val UserAgent = "Fetcher/0.0.1 (+https://github.com/SlashNephy/Hatena-Intern-2020)"
    private val client = HttpClient()

    /**
     * Extracts <title> tag from url.
     * @param url The page url.
     * @return The page title if exists, or null.
     */
    suspend fun getPageTitle(url: String): String? {
        val content = client.get<HttpResponse>(url) {
            userAgent(UserAgent)
        }

        val type = content.contentType() ?: ContentType.Any
        if (!type.match(ContentType.Text.Html)) {
            return null
        }

        val html = content.readText()
        val document = Jsoup.parse(html)

        return document.title()
    }

    /**
     * Closes the internal [HttpClient].
     */
    override fun close() {
        client.close()
    }
}
