package com.skillstorm.project1.backend.dto.Warehouse;

public record AddRequest(
    int quantity,
    Integer productId
) {}
