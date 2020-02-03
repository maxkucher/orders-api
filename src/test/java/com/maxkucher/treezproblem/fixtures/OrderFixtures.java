package com.maxkucher.treezproblem.fixtures;

import com.maxkucher.treezproblem.dto.OrderDto;
import com.maxkucher.treezproblem.dto.OrderItemDto;
import com.maxkucher.treezproblem.entities.Order;
import com.maxkucher.treezproblem.entities.OrderItem;
import com.maxkucher.treezproblem.entities.OrderStatus;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class OrderFixtures {
    public static Order getFakeOrder() {
        return Order
                .builder()
                .datePlaced(Instant.now())
                .email("test@test.com")
                .id(1L)
                .status(OrderStatus.PLACED)
                .inventory(OrderItem
                        .builder()
                        .id(2L)
                        .inventory(InventoriesFixtures.getFakeInventory())
                        .quantity(2)
                        .build())
                .build();
    }

    public static OrderDto getCreateOrderDto() {
        return OrderDto.builder()
                .email("test@test.com")
                .orderItem(OrderItemDto
                        .builder()
                        .productId(1)
                        .quantity(1)
                        .build())
                .orderItem(OrderItemDto
                        .builder()
                        .productId(2)
                        .quantity(1)
                        .build())
                .build();

    }


    public static List<Order> getFakeOrders() {
        return new ArrayList<Order>() {{
            add(Order
                    .builder()
                    .datePlaced(Instant.now())
                    .email("test@test.com")
                    .id(1L)
                    .status(OrderStatus.PLACED)
                    .inventory(OrderItem
                            .builder()
                            .id(2L)
                            .inventory(InventoriesFixtures.getFakeInventory())
                            .quantity(2)
                            .build())
                    .build());
            add(Order
                    .builder()
                    .datePlaced(Instant.now())
                    .email("test2@test.com")
                    .id(2L)
                    .status(OrderStatus.CANCELLED)
                    .inventory(OrderItem
                            .builder()
                            .id(3L)
                            .inventory(InventoriesFixtures.getFakeInventory())
                            .quantity(3)
                            .build())
                    .build());
        }};
    }
}
