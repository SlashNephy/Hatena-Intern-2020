package fetcher

import io.grpc.ServerBuilder

object FetcherServer {
    private const val Port = 50051

    private val server = ServerBuilder.forPort(Port)
        .addService(FetcherService)
        .build()

    fun start() {
        server.start()
        logger.info { "Server started. Listening on $Port." }

        Runtime.getRuntime().addShutdownHook(Thread {
            logger.info { "*** shutting down gRPC server since JVM is shutting down" }
            server.shutdown()
            logger.info { "*** server shut down" }
        })

        server.awaitTermination()
    }
}
