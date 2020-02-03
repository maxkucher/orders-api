package com.maxkucher.treezproblem.services;

import com.maxkucher.treezproblem.dto.OrderDto;
import com.maxkucher.treezproblem.entities.Order;

import java.util.List;

public interface OrdersService {


    List<Order> getOrders();

    Order getOrderById(long id);

    Order createOrder(OrderDto orderDto);

    Order updateOrder(OrderDto orderDto, long id);

    void deleteOrder(long id);

    Order cancelOrder(long id);
}
