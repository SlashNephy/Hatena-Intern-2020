package fetcher

import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.http.*
import kotlin.random.Random

object MockFetcherHttpClient: FetcherHttpClient({
    HttpClient(MockEngine) {
        engine {
            addHandler { request ->
                when (request.url.toString()) {
                    "https://www.google.co.jp/" -> {
                        val headers = headersOf("Content-Type" to listOf(ContentType.Text.Html.toString()))
                        respond("<head><title>Google</title></head><body></body>", headers = headers)
                    }
                    "https://www.hatena.ne.jp/images/portal/logo-portal-top2@2x.png" -> {
                        val headers = headersOf("Content-Type" to listOf(ContentType.Image.PNG.toString()))
                        respond(Random.nextBytes(10), headers = headers)
                    }
                    else -> {
                        error("Unhandled ${request.url}")
                    }
                }
            }
        }
    }
})
