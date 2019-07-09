package com.github.edjdc.demo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.examples.helloworld.GreeterGrpc;
import io.grpc.examples.helloworld.HelloReply;
import io.grpc.examples.helloworld.HelloRequest;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SpringGrpcApplicationTests {

	private static final Logger LOGGER = LoggerFactory.getLogger(SpringGrpcApplicationTests.class);
	
	private GreeterGrpc.GreeterBlockingStub greeterServiceBlockingStub;

	@Before
	public void setup() {
		ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 6565).usePlaintext().build();
		greeterServiceBlockingStub = GreeterGrpc.newBlockingStub(managedChannel);
	}

	@Test
	public void testSayHello() {
		HelloRequest request = HelloRequest.newBuilder().setName("Java Tests").build();
		LOGGER.info("client sending {}", request);

		HelloReply greeting = greeterServiceBlockingStub.sayHello(request);
		LOGGER.info("client received {}", greeting);
		assertThat(greeting.getMessage()).isEqualTo("Hello Java Tests");
	}
}
