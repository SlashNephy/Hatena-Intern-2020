package fetcher

import fetcher.pb.PageTitleRequest
import fetcher.pb.PageTitleResponse
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

object FetcherServiceTest {
    @Test
    fun testGetPageTitle() {
        val request = PageTitleRequest.newBuilder()
            .setUrl("https://www.hatena.ne.jp/")
            .build()
        val response = runBlocking {
            PageTitleResponse.newBuilder()
                .setCode(PageTitleResponse.StatusCode.OK)
                .setTitle(MockFetcherHttpClient.getPageTitle(request.url))
                .build()
        }

        assertEquals("はてな", response.title)
    }

    @Test
    fun testGetPageTitleIfUndefined() {
        val request = PageTitleRequest.newBuilder()
            .setUrl("https://abs.twimg.com/favicons/twitter.ico")
            .build()
        val response = runBlocking {
            PageTitleResponse.newBuilder()
                .setCode(PageTitleResponse.StatusCode.UNDEFINED_TITLE)
                .setTitle(MockFetcherHttpClient.getPageTitle(request.url))
                .build()
        }

        assertEquals(PageTitleResponse.StatusCode.UNDEFINED_TITLE, response.code)
    }
}
