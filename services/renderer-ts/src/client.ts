import {PageTitleRequest, PageTitleResponse} from "../pb/fetcher/fetcher_pb"
import {FetcherServiceClient} from "../pb/fetcher/fetcher_grpc_pb";
import {credentials} from "grpc";
import {loadConfig} from "./config";
import StatusCode = PageTitleResponse.StatusCode;

export function getPageTitle(url: string): Promise<string | undefined> {
    const config = loadConfig();
    const client = new FetcherServiceClient(config.fetcherAddr, credentials.createInsecure());

    return new Promise((resolve, reject) => {
        const request = new PageTitleRequest().setUrl(url);

        client.getPageTitle(request, ((error, response) => {
            if (error === null) {
                const code = response.getCode();
                switch (code) {
                    case PageTitleResponse.StatusCode.OK:
                        const title = response.getTitle();
                        resolve(title);

                        break;
                    case PageTitleResponse.StatusCode.UNDEFINED_TITLE:
                        resolve(undefined);

                        break;
                    default:
                        reject();

                        break;
                }
            } else {
                reject(error);
            }
        }));
    });
}
