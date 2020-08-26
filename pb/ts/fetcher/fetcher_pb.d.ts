// package: fetcher
// file: fetcher.proto

/* tslint:disable */
/* eslint-disable */

import * as jspb from "google-protobuf";

export class PageTitleRequest extends jspb.Message { 
    getUrl(): string;
    setUrl(value: string): PageTitleRequest;


    serializeBinary(): Uint8Array;
    toObject(includeInstance?: boolean): PageTitleRequest.AsObject;
    static toObject(includeInstance: boolean, msg: PageTitleRequest): PageTitleRequest.AsObject;
    static extensions: {[key: number]: jspb.ExtensionFieldInfo<jspb.Message>};
    static extensionsBinary: {[key: number]: jspb.ExtensionFieldBinaryInfo<jspb.Message>};
    static serializeBinaryToWriter(message: PageTitleRequest, writer: jspb.BinaryWriter): void;
    static deserializeBinary(bytes: Uint8Array): PageTitleRequest;
    static deserializeBinaryFromReader(message: PageTitleRequest, reader: jspb.BinaryReader): PageTitleRequest;
}

export namespace PageTitleRequest {
    export type AsObject = {
        url: string,
    }
}

export class PageTitleResponse extends jspb.Message { 
    getTitle(): string;
    setTitle(value: string): PageTitleResponse;


    serializeBinary(): Uint8Array;
    toObject(includeInstance?: boolean): PageTitleResponse.AsObject;
    static toObject(includeInstance: boolean, msg: PageTitleResponse): PageTitleResponse.AsObject;
    static extensions: {[key: number]: jspb.ExtensionFieldInfo<jspb.Message>};
    static extensionsBinary: {[key: number]: jspb.ExtensionFieldBinaryInfo<jspb.Message>};
    static serializeBinaryToWriter(message: PageTitleResponse, writer: jspb.BinaryWriter): void;
    static deserializeBinary(bytes: Uint8Array): PageTitleResponse;
    static deserializeBinaryFromReader(message: PageTitleResponse, reader: jspb.BinaryReader): PageTitleResponse;
}

export namespace PageTitleResponse {
    export type AsObject = {
        title: string,
    }
}
