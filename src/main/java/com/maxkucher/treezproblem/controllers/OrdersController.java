package com.maxkucher.treezproblem.controllers;


import com.maxkucher.treezproblem.dto.OrderDto;
import com.maxkucher.treezproblem.dto.Response;
import com.maxkucher.treezproblem.services.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrdersController {
    private final OrdersService ordersService;


    @GetMapping
    public ResponseEntity<?> getOrders() {
        return ResponseEntity.ok(ordersService.getOrders());
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable long id) {
        return ResponseEntity.ok(ordersService.getOrderById(id));
    }


    @PostMapping
    public ResponseEntity<?> createOrder(@Valid  @RequestBody OrderDto orderDto) {
        return ResponseEntity.ok(ordersService.createOrder(orderDto));
    }



    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@Valid @RequestBody OrderDto orderDto, @PathVariable long id) {
        return ResponseEntity.ok(ordersService.updateOrder(orderDto, id));
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<?> cancelOrder(@PathVariable long id)
    {
        return ResponseEntity.ok(ordersService.cancelOrder(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable long id) {
        ordersService.deleteOrder(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
