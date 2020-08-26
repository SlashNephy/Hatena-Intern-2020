package fetcher

import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

object PageTitle {
    @Test
    fun testHtml() {
        val actual = runBlocking {
            Http.getPageTitle("https://www.google.co.jp")
        }

        assertEquals("Google", actual)
    }

    @Test
    fun testNonHtml() {
        val actual = runBlocking {
            Http.getPageTitle("https://www.hatena.ne.jp/images/portal/logo-portal-top2@2x.png")
        }

        assertNull(actual)
    }
}
