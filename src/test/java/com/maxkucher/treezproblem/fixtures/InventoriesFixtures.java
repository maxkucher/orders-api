package com.maxkucher.treezproblem.fixtures;

import com.maxkucher.treezproblem.dto.InventoryDto;
import com.maxkucher.treezproblem.entities.Inventory;
import com.maxkucher.treezproblem.entities.Order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class InventoriesFixtures {
    public static Inventory getFakeInventory() {
        return Inventory
                .builder()
                .description("test")
                .name("test")
                .price(new BigDecimal("10.0"))
                .quantityAvailable(10)
                .build();
    }


    public static List<Inventory> getFakeInventories() {
        return new ArrayList<Inventory>() {{
            add(Inventory.builder()
                    .id(1)
                    .description("test")
                    .name("test")
                    .price(new BigDecimal("30.0"))
                    .build());
            add(Inventory.builder()
                    .id(2)
                    .description("test 2")
                    .name("test 2")
                    .price(new BigDecimal("30.0"))
                    .build());
            add(Inventory.builder()
                    .id(3)
                    .description("test 3")
                    .name("test 3")
                    .price(new BigDecimal("31.0"))
                    .build());

        }};
    }

    public static InventoryDto getFakeInventoryDto() {
        return InventoryDto.builder()
                .quantityAvailable(1)
                .price(new BigDecimal("20.0"))
                .name("test")
                .description("test")
                .build();
    }


}
