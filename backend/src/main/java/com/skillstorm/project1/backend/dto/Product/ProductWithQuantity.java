package com.skillstorm.project1.backend.dto.Product;

import com.skillstorm.project1.backend.models.enums.ProductType;

public record ProductWithQuantity(
    Integer id,
    String name,
    String sku,
    ProductType productType,
    String description,
    Integer quantity
) {}
