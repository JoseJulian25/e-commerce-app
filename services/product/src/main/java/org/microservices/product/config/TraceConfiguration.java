package org.microservices.product.config;

import brave.Tracing;
import brave.grpc.GrpcTracing;
import io.grpc.ServerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class TraceConfiguration {

    @Bean
    public ServerInterceptor traceServerInterceptor(Tracing tracing) {
        return GrpcTracing.create(tracing).newServerInterceptor();
    }
}
