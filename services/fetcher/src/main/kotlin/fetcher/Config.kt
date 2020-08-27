package fetcher

object Config {
    enum class Mode {
        Production, Development
    }

    val mode: Mode
        get() = System.getenv("MODE")?.let {  m ->
            Mode.values().find { it.name.equals(m, true) }
        } ?: Mode.Production

    val grpcPort: Int
        get() = System.getenv("GRPC_PORT")?.toIntOrNull() ?: 50052
}
