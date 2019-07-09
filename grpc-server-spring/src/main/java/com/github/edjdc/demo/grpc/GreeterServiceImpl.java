package com.github.edjdc.demo.grpc;

import org.lognet.springboot.grpc.GRpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.grpc.examples.helloworld.GreeterGrpc;
import io.grpc.examples.helloworld.HelloReply;
import io.grpc.examples.helloworld.HelloRequest;
import io.grpc.stub.StreamObserver;

@GRpcService
public class GreeterServiceImpl extends GreeterGrpc.GreeterImplBase {

	private static final Logger LOGGER = LoggerFactory.getLogger(GreeterServiceImpl.class);

	@Override
	public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
		LOGGER.info("server received {}", request);
		
		String message = "Hello " + request.getName();
		HelloReply reply = HelloReply.newBuilder().setMessage(message).build();
		
		LOGGER.info("server responded {}", reply);
		
		responseObserver.onNext(reply);
		responseObserver.onCompleted();
	}

}
