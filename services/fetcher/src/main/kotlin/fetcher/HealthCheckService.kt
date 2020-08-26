package fetcher

import grpc.health.v1.HealthGrpcKt
import grpc.health.v1.HealthOuterClass

object HealthCheckService: HealthGrpcKt.HealthCoroutineImplBase() {
    override suspend fun check(request: HealthOuterClass.HealthCheckRequest): HealthOuterClass.HealthCheckResponse {
        return HealthOuterClass.HealthCheckResponse.newBuilder()
            .setStatus(HealthOuterClass.HealthCheckResponse.ServingStatus.SERVING)
            .build()
    }
}
