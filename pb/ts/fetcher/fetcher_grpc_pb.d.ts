// package: fetcher
// file: fetcher.proto

/* tslint:disable */
/* eslint-disable */

import * as grpc from "grpc";
import * as fetcher_pb from "./fetcher_pb";

interface IFetcherServiceService extends grpc.ServiceDefinition<grpc.UntypedServiceImplementation> {
    getPageTitle: IFetcherServiceService_IGetPageTitle;
}

interface IFetcherServiceService_IGetPageTitle extends grpc.MethodDefinition<fetcher_pb.PageTitleRequest, fetcher_pb.PageTitleResponse> {
    path: string; // "/fetcher.FetcherService/GetPageTitle"
    requestStream: false;
    responseStream: false;
    requestSerialize: grpc.serialize<fetcher_pb.PageTitleRequest>;
    requestDeserialize: grpc.deserialize<fetcher_pb.PageTitleRequest>;
    responseSerialize: grpc.serialize<fetcher_pb.PageTitleResponse>;
    responseDeserialize: grpc.deserialize<fetcher_pb.PageTitleResponse>;
}

export const FetcherServiceService: IFetcherServiceService;

export interface IFetcherServiceServer {
    getPageTitle: grpc.handleUnaryCall<fetcher_pb.PageTitleRequest, fetcher_pb.PageTitleResponse>;
}

export interface IFetcherServiceClient {
    getPageTitle(request: fetcher_pb.PageTitleRequest, callback: (error: grpc.ServiceError | null, response: fetcher_pb.PageTitleResponse) => void): grpc.ClientUnaryCall;
    getPageTitle(request: fetcher_pb.PageTitleRequest, metadata: grpc.Metadata, callback: (error: grpc.ServiceError | null, response: fetcher_pb.PageTitleResponse) => void): grpc.ClientUnaryCall;
    getPageTitle(request: fetcher_pb.PageTitleRequest, metadata: grpc.Metadata, options: Partial<grpc.CallOptions>, callback: (error: grpc.ServiceError | null, response: fetcher_pb.PageTitleResponse) => void): grpc.ClientUnaryCall;
}

export class FetcherServiceClient extends grpc.Client implements IFetcherServiceClient {
    constructor(address: string, credentials: grpc.ChannelCredentials, options?: object);
    public getPageTitle(request: fetcher_pb.PageTitleRequest, callback: (error: grpc.ServiceError | null, response: fetcher_pb.PageTitleResponse) => void): grpc.ClientUnaryCall;
    public getPageTitle(request: fetcher_pb.PageTitleRequest, metadata: grpc.Metadata, callback: (error: grpc.ServiceError | null, response: fetcher_pb.PageTitleResponse) => void): grpc.ClientUnaryCall;
    public getPageTitle(request: fetcher_pb.PageTitleRequest, metadata: grpc.Metadata, options: Partial<grpc.CallOptions>, callback: (error: grpc.ServiceError | null, response: fetcher_pb.PageTitleResponse) => void): grpc.ClientUnaryCall;
}
