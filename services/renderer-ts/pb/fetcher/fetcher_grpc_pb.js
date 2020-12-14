// GENERATED CODE -- DO NOT EDIT!

'use strict';
var grpc = require('grpc');
var fetcher_pb = require('./fetcher_pb.js');

function serialize_fetcher_PageTitleRequest(arg) {
  if (!(arg instanceof fetcher_pb.PageTitleRequest)) {
    throw new Error('Expected argument of type fetcher.PageTitleRequest');
  }
  return Buffer.from(arg.serializeBinary());
}

function deserialize_fetcher_PageTitleRequest(buffer_arg) {
  return fetcher_pb.PageTitleRequest.deserializeBinary(new Uint8Array(buffer_arg));
}

function serialize_fetcher_PageTitleResponse(arg) {
  if (!(arg instanceof fetcher_pb.PageTitleResponse)) {
    throw new Error('Expected argument of type fetcher.PageTitleResponse');
  }
  return Buffer.from(arg.serializeBinary());
}

function deserialize_fetcher_PageTitleResponse(buffer_arg) {
  return fetcher_pb.PageTitleResponse.deserializeBinary(new Uint8Array(buffer_arg));
}


var FetcherServiceService = exports.FetcherServiceService = {
  getPageTitle: {
    path: '/fetcher.FetcherService/GetPageTitle',
    requestStream: false,
    responseStream: false,
    requestType: fetcher_pb.PageTitleRequest,
    responseType: fetcher_pb.PageTitleResponse,
    requestSerialize: serialize_fetcher_PageTitleRequest,
    requestDeserialize: deserialize_fetcher_PageTitleRequest,
    responseSerialize: serialize_fetcher_PageTitleResponse,
    responseDeserialize: deserialize_fetcher_PageTitleResponse,
  },
};

exports.FetcherServiceClient = grpc.makeGenericClientConstructor(FetcherServiceService);
