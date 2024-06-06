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
        List<Integer> productIds = requests.stream()
                .map(ProductPurchaseRequest::productId)
                .toList();

        List<Product> storedProducts = repository.findAllByIdInOrderById(productIds);

        if (productIds.size() != storedProducts.size()) {
            throw new ProductPurchaseException("One or more products does not exit");
        }

        List<ProductPurchaseRequest> storedRequest = requests.stream().sorted(Comparator.comparing(ProductPurchaseRequest::productId)).toList();

        List<ProductPurchaseResponse> purchasedProduct = new ArrayList<>();

        for(int i = 0; i < storedProducts.size(); i++){
            Product product = storedProducts.get(i);
            ProductPurchaseRequest productRequest = storedRequest.get(i);

            if(product.getAvailableQuantity() < productRequest.quantity()){
                throw new ProductPurchaseException("Insufficient stock quantity with ID: " + productRequest.productId());
            }

            int newAvailableQuantity = product.getAvailableQuantity() - productRequest.quantity();
            product.setAvailableQuantity(newAvailableQuantity);
            repository.save(product);

            purchasedProduct.add(mapper.toProductPurchaseResponse(product, productRequest.quantity()));
        }

        return purchasedProduct;


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
}
