package org.microservices.order.grpc;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import lombok.RequiredArgsConstructor;
import org.microservices.order.DTO.PurchaseRequest;
import org.microservices.order.DTO.PurchaseResponse;
import org.microservices.order.exception.ProductPurchaseException;
import org.springframework.stereotype.Component;
import products.ProductPurchaseRequest;
import products.ProductServiceGrpc;
import products.PurchaseProductsRequest;
import products.PurchaseProductsResponse;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GrpcProductClient {

    private final ProductServiceGrpc.ProductServiceBlockingStub productServiceStub;

    public List<PurchaseResponse> purchaseProducts(List<PurchaseRequest> products) {
        List<ProductPurchaseRequest> grpcProductsRequest = convertToGrpcRequest(products);
        PurchaseProductsRequest grpcRequest = createGrpcRequest(grpcProductsRequest);
        return callGrpcService(grpcRequest);
    }

    private List<ProductPurchaseRequest> convertToGrpcRequest(List<PurchaseRequest> products) {
        return products.stream()
                .map(product -> ProductPurchaseRequest.newBuilder()
                        .setProductId(product.productId())
                        .setQuantity(product.quantity())
                        .build()).toList();
    }

    private PurchaseProductsRequest createGrpcRequest(List<ProductPurchaseRequest> grpcProductsRequest) {
        return PurchaseProductsRequest.newBuilder()
                .addAllProducts(grpcProductsRequest)
                .build();
    }

    private List<PurchaseResponse> callGrpcService(PurchaseProductsRequest grpcRequest) {
        try {
            System.out.println("Enviando informacion GRPC: " + grpcRequest);
            PurchaseProductsResponse grpcResponse = productServiceStub.purchaseProducts(grpcRequest);

            System.out.println("Recibiendo informacion GRPC:" + grpcResponse);
            return grpcResponse.getProductsList().stream()
                    .map(grpcProduct -> new PurchaseResponse(
                            grpcProduct.getProductId(),
                            grpcProduct.getName(),
                            grpcProduct.getDescription(),
                            grpcProduct.getPrice(),
                            grpcProduct.getQuantity()
                    )).toList();

        } catch (StatusRuntimeException ex) {
            if (ex.getStatus().getCode().value() == 3)
                throw new ProductPurchaseException(ex.getStatus().getDescription());
        }

        return List.of();
    }
}

