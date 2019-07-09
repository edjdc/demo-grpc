package com.github.edjdc.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.google.common.util.concurrent.ListenableFuture;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.examples.helloworld.GreeterGrpc;
import io.grpc.examples.helloworld.HelloReply;
import io.grpc.examples.helloworld.HelloRequest;

@SpringBootApplication
public class SpringGrpcApplication implements CommandLineRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(SpringGrpcApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringGrpcApplication.class, args);
	}

	@Autowired
	private EurekaClient client;

	@Override
	public void run(String... args) throws Exception {
		InstanceInfo instanceInfo = client.getNextServerFromEureka("grpc-server", false);
		ManagedChannel channel = ManagedChannelBuilder.forAddress(instanceInfo.getIPAddr(), instanceInfo.getPort()).usePlaintext().build();

		GreeterGrpc.GreeterFutureStub stub = GreeterGrpc.newFutureStub(channel);

		HelloRequest request = HelloRequest.newBuilder().setName("Java App").build();
		LOGGER.info("client sending {}", request);
		ListenableFuture<HelloReply> sayHello = stub.sayHello(request);

		HelloReply helloReply = sayHello.get();
		LOGGER.info("client received {}", helloReply);

	}
}
