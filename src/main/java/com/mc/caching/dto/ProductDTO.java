package com.mc.caching.dto;

import lombok.Builder;

@Builder
public record ProductDTO(Long productId,
                         String productName,
                         String productDescription,
                         Double productPrice,
                         Integer productQuantity) {
}
