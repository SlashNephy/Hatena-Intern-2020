package fetcher

object Config {
    enum class Mode {
        Production, Development
    }

    val mode: Mode
        get() = System.getenv("MODE")?.let {  m ->
            Mode.values().find { it.name == m }
        } ?: Mode.Production

    val grpcPort: Int
        get() = System.getProperty("GRPC_PORT")?.toIntOrNull() ?: 50052
}
