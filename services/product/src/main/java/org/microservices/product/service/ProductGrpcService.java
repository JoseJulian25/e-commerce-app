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
            List<ProductPurchaseResponse> responses = processPurchaseRequest(request);

            PurchaseProductsResponse response = buildPurchaseResponse(responses);

            sendResponse(responseObserver, response);
        } catch (ProductPurchaseException e) {
            handleProductPurchaseException(responseObserver, e);
        } catch (Exception e) {
            handleUnknownException(responseObserver, e);
        }
    }

    private List<ProductPurchaseResponse> processPurchaseRequest(PurchaseProductsRequest request) {
        List<ProductPurchaseRequest> productRequests = request.getProductsList().stream()
                .map(this::convertToProductPurchaseRequest)
                .collect(Collectors.toList());
        return productService.purchaseProducts(productRequests);
    }

    private ProductPurchaseRequest convertToProductPurchaseRequest(products.ProductPurchaseRequest protoRequest) {
        return new ProductPurchaseRequest(
                protoRequest.getProductId(),
                protoRequest.getQuantity()
        );
    }

    private PurchaseProductsResponse buildPurchaseResponse(List<ProductPurchaseResponse> responses) {
        PurchaseProductsResponse.Builder responseBuilder = PurchaseProductsResponse.newBuilder();
        responses.forEach(response -> responseBuilder.addProducts(convertToProtoResponse(response)));
        return responseBuilder.build();
    }

    private products.ProductPurchaseResponse convertToProtoResponse(ProductPurchaseResponse response) {
        return products.ProductPurchaseResponse.newBuilder()
                .setProductId(response.productId())
                .setName(response.name())
                .setDescription(response.description())
                .setPrice(response.price())
                .setQuantity(response.quantity())
                .build();
    }

    private void sendResponse(StreamObserver<PurchaseProductsResponse> responseObserver, PurchaseProductsResponse response) {
        System.out.println("INFORMACION ENVIADA A ORDER: " + response);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    private void handleProductPurchaseException(StreamObserver<PurchaseProductsResponse> responseObserver, ProductPurchaseException e) {
        System.err.println("ERROR AL PROCESAR LA COMPRA: " + e.getMessage());
        responseObserver.onError(e);
    }

    private void handleUnknownException(StreamObserver<PurchaseProductsResponse> responseObserver, Exception e) {
        System.err.println("ERROR DESCONOCIDO: " + e.getMessage());
        responseObserver.onError(e);
    }
}


