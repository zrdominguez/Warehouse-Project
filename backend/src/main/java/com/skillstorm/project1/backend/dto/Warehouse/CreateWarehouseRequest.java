package com.skillstorm.project1.backend.dto.Warehouse;

import com.skillstorm.project1.backend.models.enums.WarehouseType;

public record CreateWarehouseRequest (
    Integer userId,
    String name,
    WarehouseType warehouseType,
    int capacity,
    String location
) {}
