package org.microservices.order.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import products.ProductServiceGrpc;

@Configuration
public class GrpcClientConfiguration {

    @Bean
    public ManagedChannel productServiceChannel(){
        return ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext().build();
    }

    @Bean
    public ProductServiceGrpc.ProductServiceBlockingStub productServiceStub(ManagedChannel channel) {
        return ProductServiceGrpc.newBlockingStub(channel);
    }
}
