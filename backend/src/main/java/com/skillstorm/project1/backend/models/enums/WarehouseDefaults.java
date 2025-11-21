package com.skillstorm.project1.backend.models.enums;

import java.util.List;

public class WarehouseDefaults {

    public static List<ProductType> getSectionTypesFor(WarehouseType type) {
        return switch (type) {
            case INSTRUMENTS -> List.of(
                ProductType.WOODWIND,
                ProductType.BRASS,
                ProductType.STRING,
                ProductType.PERCUSSION
            );
            case BOOKS -> List.of(
                ProductType.DRAMA,
                ProductType.ROMANCE,
                ProductType.NONFICTION,
                ProductType.HORROR,
                ProductType.MYSTERY
            );
            case ELECTRONICS -> List.of(
                ProductType.LAPTOPS,
                ProductType.SMARTPHONES,
                ProductType.GAME_CONSOLES
            );
        };
    }
}
