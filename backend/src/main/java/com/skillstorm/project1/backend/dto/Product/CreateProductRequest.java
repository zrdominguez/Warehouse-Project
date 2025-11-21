package com.skillstorm.project1.backend.dto.Product;

import com.skillstorm.project1.backend.models.enums.ProductType;

public record CreateProductRequest(
    String name,
    ProductType productType,
    String description
) {}
