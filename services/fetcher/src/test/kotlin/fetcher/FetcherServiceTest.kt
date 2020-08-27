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
            FetcherService.getPageTitle(request)
        }

        assertEquals("はてな", response.title)
    }

    @Test
    fun testGetPageTitleIfUndefined() {
        val request = PageTitleRequest.newBuilder()
            .setUrl("https://abs.twimg.com/favicons/twitter.ico")
            .build()
        val response = runBlocking {
            FetcherService.getPageTitle(request)
        }

        assertEquals(PageTitleResponse.StatusCode.UNDEFINED_TITLE, response.code)
    }
}
