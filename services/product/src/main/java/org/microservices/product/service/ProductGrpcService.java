package org.microservices.product.service;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.microservices.product.DTO.ProductPurchaseRequest;
import org.microservices.product.DTO.ProductPurchaseResponse;
import org.microservices.product.exception.ProductPurchaseException;
import products.ProductServiceGrpc;
import products.PurchaseProductsRequest;
import products.PurchaseProductsResponse;

import java.util.List;
import java.util.stream.Collectors;

@GrpcService
public class ProductGrpcService extends ProductServiceGrpc.ProductServiceImplBase {

    private final ProductService productService;

    public ProductGrpcService(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public void purchaseProducts(PurchaseProductsRequest request,
                                 StreamObserver<PurchaseProductsResponse> responseObserver) {
        System.out.println("RECIBIENDO INFORMACION GRPC: " + request);
        try {
            List<ProductPurchaseRequest> productRequests = request.getProductsList().stream()
                    .map(protoRequest -> new ProductPurchaseRequest(
                            protoRequest.getProductId(),
                            protoRequest.getQuantity()
                    )).collect(Collectors.toList());

            List<ProductPurchaseResponse> responses = productService.purchaseProducts(productRequests);

            PurchaseProductsResponse.Builder responseBuilder = PurchaseProductsResponse.newBuilder();
            for (ProductPurchaseResponse response : responses) {
                responseBuilder.addProducts(products.ProductPurchaseResponse.newBuilder()
                        .setProductId(response.productId())
                        .setName(response.name())
                        .setDescription(response.description())
                        .setPrice(response.price())
                        .setQuantity(response.quantity())
                        .build());
            }

            responseObserver.onNext(responseBuilder.build());
            responseObserver.onCompleted();
        } catch (ProductPurchaseException e) {
            responseObserver.onError(e);
        }
    }
}

