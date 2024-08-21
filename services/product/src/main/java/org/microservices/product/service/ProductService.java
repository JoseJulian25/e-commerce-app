package org.microservices.product.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.microservices.product.DTO.ProductPurchaseRequest;
import org.microservices.product.DTO.ProductPurchaseResponse;
import org.microservices.product.DTO.ProductRequest;
import org.microservices.product.DTO.ProductResponse;
import org.microservices.product.entities.Product;
import org.microservices.product.exception.ProductPurchaseException;
import org.microservices.product.mapper.ProductMapper;
import org.microservices.product.repository.ProductRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    public Integer createProduct(ProductRequest productRequest) {
        var product = mapper.toProduct(productRequest);
        return repository.save(product).getId();
    }

    public List<ProductPurchaseResponse> purchaseProducts(List<ProductPurchaseRequest> requests) throws ProductPurchaseException {
        List<Product> storedProducts = fetchStoredProducts(requests);
        validateProductExistence(requests, storedProducts);
        return processPurchaseRequests(requests, storedProducts);
    }

    public ProductResponse findById(Integer productId) {
        return repository.findById(productId)
                .map(mapper::toProductResponse)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with the ID: " + productId));
    }


    public List<ProductResponse> findAll() {
        return repository.findAll().stream()
                .map(mapper::toProductResponse)
                .toList();
    }

    private List<Product> fetchStoredProducts(List<ProductPurchaseRequest> requests) {
        List<Integer> productIds = requests.stream()
                .map(ProductPurchaseRequest::productId)
                .toList();
        return repository.findAllByIdInOrderById(productIds);
    }

    private void validateProductExistence(List<ProductPurchaseRequest> requests, List<Product> storedProducts) throws ProductPurchaseException {
        if (requests.size() != storedProducts.size()) {
            throw new ProductPurchaseException("One or more products do not exist");
        }
    }

    private List<ProductPurchaseResponse> processPurchaseRequests(List<ProductPurchaseRequest> requests, List<Product> storedProducts) throws ProductPurchaseException {
        List<ProductPurchaseRequest> sortedRequests = requests.stream()
                .sorted(Comparator.comparing(ProductPurchaseRequest::productId))
                .toList();

        List<ProductPurchaseResponse> purchasedProducts = new ArrayList<>();

        for (int i = 0; i < storedProducts.size(); i++) {
            Product product = storedProducts.get(i);

            ProductPurchaseRequest productRequest = sortedRequests.get(i);

            validateStockAvailability(product, productRequest);

            updateProductStock(product, productRequest);

            purchasedProducts.add(mapper.toProductPurchaseResponse(product, productRequest.quantity()));
        }

        return purchasedProducts;
    }

    private void validateStockAvailability(Product product, ProductPurchaseRequest request) throws ProductPurchaseException {
        if (product.getAvailableQuantity() < request.quantity()) {
            throw new ProductPurchaseException("Insufficient stock quantity with ID: " + request.productId());
        }
    }

    private void updateProductStock(Product product, ProductPurchaseRequest request) {
        int newAvailableQuantity = product.getAvailableQuantity() - request.quantity();
        product.setAvailableQuantity(newAvailableQuantity);
        repository.save(product);
    }
}
