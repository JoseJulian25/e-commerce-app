package org.microservices.order.grpc;

import brave.Tracing;
import brave.grpc.GrpcTracing;
import io.grpc.ClientInterceptor;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import products.ProductServiceGrpc;

@Configuration(proxyBeanMethods = false)
public class GrpcClientConfiguration {

    @Bean
    @Primary
    public ClientInterceptor traceClientInterceptor(Tracing tracing){
        return GrpcTracing.create(tracing).newClientInterceptor();
    }

    @Bean
    public ManagedChannel productServiceChannel(ClientInterceptor traceClientInterceptor){
        return ManagedChannelBuilder.forAddress("localhost", 9090)
                .intercept(traceClientInterceptor)
                .usePlaintext()
                .build();
    }

    @Bean
    public ProductServiceGrpc.ProductServiceBlockingStub productServiceStub(ManagedChannel channel) {
        return ProductServiceGrpc.newBlockingStub(channel);
    }
}
