import {PageTitleRequest} from "../pb/fetcher/fetcher_pb"
import {FetcherServiceClient} from "../pb/fetcher/fetcher_grpc_pb";
import {credentials} from "grpc";
import {loadConfig} from "./config";

export function getPageTitle(url: string): Promise<string> {
    const config = loadConfig();
    const client = new FetcherServiceClient(config.fetcherAddr, credentials.createInsecure());

    return new Promise((resolve, reject) => {
        const request = new PageTitleRequest().setUrl(url);

        client.getPageTitle(request, ((error, response) => {
            if (error === null) {
                const title = response.getTitle();
                resolve(title);
            } else {
                reject(error);
            }
        }));
    });
}
