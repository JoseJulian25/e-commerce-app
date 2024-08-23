package org.microservices.product.service;

import lombok.RequiredArgsConstructor;
import org.microservices.product.DTO.ProductResponse;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@CacheConfig(cacheNames = "products")
@RequiredArgsConstructor
public class CacheService {

    private final CacheManager cacheManager;


    public void updateProductCache(Integer id, ProductResponse productResponse){
        Cache cache = cacheManager.getCache("products");
        assert cache != null;
        cache.put(id, productResponse);
    }

    public void evictAllProductsCache(){
        Cache cache = cacheManager.getCache("products");
        assert cache != null;
        cache.evict("all");
    }

    public void refreshAllProductsCache(List<ProductResponse> allProducts) {
        Cache cache = cacheManager.getCache("products");
        if (cache != null) {
            cache.put("all", allProducts);
        }
    }
}
