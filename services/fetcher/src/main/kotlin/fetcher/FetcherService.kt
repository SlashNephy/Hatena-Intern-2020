package fetcher

import fetcher.pb.FetcherServiceGrpcKt
import fetcher.pb.PageTitleRequest
import fetcher.pb.PageTitleResponse

object FetcherService: FetcherServiceGrpcKt.FetcherServiceCoroutineImplBase() {
    override suspend fun getPageTitle(request: PageTitleRequest): PageTitleResponse {
        return PageTitleResponse.newBuilder()
            .setTitle(Http.getPageTitle(request.url))
            .build()
    }
}
