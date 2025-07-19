package com.mc.caching.service;

import com.mc.caching.dto.ProductDTO;
import com.mc.caching.model.ProductEntity;
import com.mc.caching.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    @CachePut(value = "products", key = "#result.productId()")
    public ProductDTO addProduct(ProductDTO productDTO) {
        ProductEntity productEntity = ProductEntity.builder()
                .productName(productDTO.productName())
                .productPrice(productDTO.productPrice())
                .productQuantity(productDTO.productQuantity())
                .productDescription(productDTO.productDescription())
                .build();
        productEntity = productRepository.save(productEntity);
        return ProductDTO.builder()
                .productId(productEntity.getProductId())
                .productName(productEntity.getProductName())
                .productPrice(productEntity.getProductPrice())
                .productQuantity(productEntity.getProductQuantity())
                .productDescription(productEntity.getProductDescription())
                .build();
    }

    @CachePut(value = "products", key = "#result.productId()")
    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {
        ProductEntity productEntity = productRepository.findById(productId).orElseThrow(() -> new IllegalStateException("Product not found"));
        productEntity.setProductName(productDTO.productName());
        productEntity.setProductPrice(productDTO.productPrice());
        productEntity.setProductQuantity(productDTO.productQuantity());
        productEntity.setProductDescription(productDTO.productDescription());
        productEntity = productRepository.save(productEntity);
        return ProductDTO.builder()
                .productId(productEntity.getProductId())
                .productName(productEntity.getProductName())
                .productPrice(productEntity.getProductPrice())
                .productQuantity(productEntity.getProductQuantity())
                .productDescription(productEntity.getProductDescription())
                .build();
    }

    @Cacheable(value = "products", key = "#productId")
    public ProductDTO getProduct(Long productId) {
        ProductEntity productEntity = productRepository.findById(productId).orElseThrow(() -> new IllegalStateException("Product not found"));
        return ProductDTO.builder()
                .productId(productEntity.getProductId())
                .productName(productEntity.getProductName())
                .productPrice(productEntity.getProductPrice())
                .productQuantity(productEntity.getProductQuantity())
                .productDescription(productEntity.getProductDescription())
                .build();
    }

    @CacheEvict(value = "products", key = "#productId")
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }
}
