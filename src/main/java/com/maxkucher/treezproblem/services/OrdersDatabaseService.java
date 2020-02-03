package com.maxkucher.treezproblem.services;


import com.maxkucher.treezproblem.dto.OrderDto;
import com.maxkucher.treezproblem.dto.OrderItemDto;
import com.maxkucher.treezproblem.entities.Inventory;
import com.maxkucher.treezproblem.entities.Order;
import com.maxkucher.treezproblem.entities.OrderItem;
import com.maxkucher.treezproblem.entities.OrderStatus;
import com.maxkucher.treezproblem.exceptions.NotSufficientItemsException;
import com.maxkucher.treezproblem.exceptions.OrderAlreadyCanceledException;
import com.maxkucher.treezproblem.exceptions.OrderNotFoundException;
import com.maxkucher.treezproblem.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrdersDatabaseService implements OrdersService {

    private final OrdersRepository ordersRepository;
    private final InventoriesDatabaseService inventoriesDatabaseService;

    public List<Order> getOrders() {
        return ordersRepository.findAll();
    }

    public Order getOrderById(long id) {
        return ordersRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }


    @Transactional
    public Order createOrder(OrderDto orderDto) {
        List<OrderItem> orderItems = orderDto
                .getOrderItems()
                .stream()
                .map(orderItemDto -> {
                    Inventory inventory = inventoriesDatabaseService.getInventoryById(orderItemDto.getProductId());
                    if (inventory.getQuantityAvailable() < orderItemDto.getQuantity())
                        throw new NotSufficientItemsException(inventory.getId());
                    OrderItem orderItem = new OrderItem(
                            orderItemDto.getQuantity(),
                            inventory.getPrice(),
                            inventory
                    );
                    inventory.setQuantityAvailable(inventory.getQuantityAvailable() - orderItemDto.getQuantity());
                    return orderItem;
                })
                .collect(Collectors.toList());
        Order order = new Order(
                orderDto.getEmail(),
                Instant.now(),
                OrderStatus.PLACED,
                orderItems
        );

        return ordersRepository.save(order);
    }


    private void returnItems(Order order) {
        order.getInventories()
                .forEach(orderItem -> {
                    Inventory inventory = orderItem.getInventory();
                    inventory.setQuantityAvailable(inventory.getQuantityAvailable() + orderItem.getQuantity());
                });

    }

    @Transactional
    public Order updateOrder(OrderDto orderDto, long id) {
        Order order = getOrderById(id);

        // checking if order exists and not cancelled

        if (order.getStatus().equals(OrderStatus.CANCELLED))
            throw new OrderAlreadyCanceledException(id);

        else {

            List<OrderItem> removedOrderItems = new ArrayList<>();


            order.getInventories()
                    .forEach(orderItem -> {
                        Inventory inventory = orderItem.getInventory();
                        long inventoryId = inventory.getId();
                        Optional<OrderItemDto> updatedItem = orderDto.getOrderItems()
                                .stream()
                                .filter(orderItemDto -> inventoryId == orderItemDto.getProductId())
                                .findFirst();
                        if (updatedItem.isPresent()) {
                            OrderItemDto orderItemDto = updatedItem.get();
                            if (orderItemDto.getQuantity() != orderItem.getQuantity()) {
                                // difference between quantities
                                int diff = orderItem.getQuantity() - orderItemDto.getQuantity();
                                if (diff > 0) {
                                    //  increase quantity in inventory
                                    orderItem.setQuantity(orderItemDto.getQuantity());
                                    inventory.setQuantityAvailable(inventory.getQuantityAvailable() + diff);
                                } else {
                                    //  check availability

                                    if (inventory.getQuantityAvailable() >= -diff) {
                                        inventory.setQuantityAvailable(inventory.getQuantityAvailable() + diff);
                                        orderItem.setQuantity(orderItemDto.getQuantity());
                                    } else
                                        //  if not sufficient throw exception else decrement number of items
                                        throw new NotSufficientItemsException(inventory.getId());
                                }
                            }
                        } else {
                            removedOrderItems.add(orderItem);
                            inventory.setQuantityAvailable(inventory.getQuantityAvailable() + orderItem.getQuantity());
                        }
                    });

            order.getInventories().removeAll(removedOrderItems);


            order.getInventories().addAll(findAddedOrderItems(orderDto, order));

        }


        return ordersRepository.save(order);
    }

    private List<OrderItem> findAddedOrderItems(OrderDto orderDto, Order order) {
        return orderDto.getOrderItems()
                .stream()
                .filter(orderItemDto -> order.getInventories()
                        .stream()
                        .noneMatch(orderItem -> orderItem.getInventory().getId() == orderItemDto.getProductId()))
                .map(orderItemDto -> {
                    Inventory inventory = inventoriesDatabaseService.getInventoryById(orderItemDto.getProductId());
                    if (inventory.getQuantityAvailable() < orderItemDto.getQuantity())
                        throw new NotSufficientItemsException(orderItemDto.getProductId());
                    else
                        inventory.setQuantityAvailable(inventory.getQuantityAvailable() - orderItemDto.getQuantity());
                    return new OrderItem(
                            orderItemDto.getQuantity(),
                            inventory.getPrice(),
                            inventory
                    );
                }).collect(Collectors.toList());
    }

    @Transactional
    public void deleteOrder(long id) {
        Order order = getOrderById(id);

        if (order.getStatus().equals(OrderStatus.PLACED))
            returnItems(order);

        ordersRepository.delete(order);


    }

    @Override
    public Order cancelOrder(long id) {
        Order order = getOrderById(id);
        if (order.getStatus() == OrderStatus.CANCELLED)
            throw new OrderAlreadyCanceledException(id);
        else {
            order.setStatus(OrderStatus.CANCELLED);
            return ordersRepository.save(order);
        }
    }
}
