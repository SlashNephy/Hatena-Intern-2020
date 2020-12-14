package fetcher

import mu.KotlinLogging

internal val logger = KotlinLogging.logger("Fetcher")

/**
 * Starts the "fetcher" service.
 */
fun main() {
    logger.info { "Starting \"fetcher\" service." }

    try {
        FetcherServer.start()
    } finally {
        Http.close()
        logger.info { "Closing \"fetcher\" service." }
    }
}
