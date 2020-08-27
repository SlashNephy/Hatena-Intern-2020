import {getPageTitle} from "./client";
import {FetcherServiceClient} from "../pb/fetcher/fetcher_grpc_pb";
import {PageTitleRequest, PageTitleResponse} from "../pb/fetcher/fetcher_pb";
import {ServiceError} from "grpc";
import StatusCode = PageTitleResponse.StatusCode;

jest.mock("../pb/fetcher/fetcher_grpc_pb");
const FetcherServiceClientMock = FetcherServiceClient as jest.Mock;

describe("client", () => {
    it("URL のタイトルを取得できる", async () => {
        FetcherServiceClientMock.mockImplementation(() => {
            getPageTitle: (request: PageTitleRequest, callback: (error: ServiceError | null, response: PageTitleResponse) => void) => {
                const response = new PageTitleResponse().setCode(StatusCode.OK).setTitle("Google");

                callback(null, response);
            }
        });

        const title = await getPageTitle("");

        expect(title).toBe("Google");
    });
});
