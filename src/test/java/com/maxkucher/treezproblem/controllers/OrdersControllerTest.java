package com.maxkucher.treezproblem.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maxkucher.treezproblem.dto.Response;
import com.maxkucher.treezproblem.entities.Order;
import com.maxkucher.treezproblem.exceptions.InventoryNotFoundException;
import com.maxkucher.treezproblem.exceptions.NotSufficientItemsException;
import com.maxkucher.treezproblem.fixtures.OrderFixtures;
import com.maxkucher.treezproblem.services.InventoriesService;
import com.maxkucher.treezproblem.services.OrdersService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = OrdersController.class)
class OrdersControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    OrdersService ordersService;


    @Test
    void whenGetOrders_success_thenStatus200() throws Exception {
        this.mockMvc
                .perform(get("/orders"))
                .andExpect(status().isOk());
    }

    @Test
    void whenGetOrders_success_thenReturnsOrders() throws Exception {
        List<Order> fakeOrders = OrderFixtures.getFakeOrders();
        when(ordersService.getOrders())
                .thenReturn(fakeOrders);

        MvcResult result = this.mockMvc
                .perform(get("/orders"))
                .andReturn();

        assertEquals(MediaType.APPLICATION_JSON.toString(), result.getResponse().getContentType());
        assertEquals(objectMapper.writeValueAsString(fakeOrders),
                result.getResponse().getContentAsString());
    }


    @Test
    void whenPostOrder_success_status200() throws Exception {
        this.mockMvc
                .perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(OrderFixtures.getCreateOrderDto())))
                .andExpect(status().isOk());
    }

    @Test
    void whenPostOrder_success_thenReturnsOrder() throws Exception {
        Order result = OrderFixtures.getFakeOrder();
        when(ordersService.createOrder(OrderFixtures.getCreateOrderDto()))
                .thenReturn(result);
        MvcResult res = this.mockMvc
                .perform(post("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(OrderFixtures.getCreateOrderDto())))
                .andReturn();
        assertEquals(MediaType.APPLICATION_JSON.toString(), res.getResponse().getContentType());
        assertEquals(objectMapper.writeValueAsString(result),
                res.getResponse().getContentAsString());

    }

    @Test
    void whenPostOrder_invalidBody_status400() throws Exception {
        this.mockMvc
                .perform(post("/orders"))
                .andExpect(status().isBadRequest());


    }

    @Test
    void whenPostOrder_insufficientException_status400() throws Exception {
        when(ordersService.createOrder(OrderFixtures.getCreateOrderDto()))
                .thenThrow(new NotSufficientItemsException(1));

        this.mockMvc
                .perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(OrderFixtures.getCreateOrderDto())))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenPostOrder_insufficientException_thenReturnsResponse() throws Exception {
        when(ordersService.createOrder(OrderFixtures.getCreateOrderDto()))
                .thenThrow(new NotSufficientItemsException(1));

        MvcResult result  = this.mockMvc
                .perform(post("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(OrderFixtures.getCreateOrderDto())))
                .andReturn();

        assertEquals(MediaType.APPLICATION_JSON.toString(), result.getResponse().getContentType());
        assertEquals(objectMapper.writeValueAsString(new Response(
                "Not sufficient items: 1"
                )),
                result.getResponse().getContentAsString());


    }

    @Test
    void whenPostOrder_inventoryNotFound_status404() throws Exception {
        when(ordersService.createOrder(OrderFixtures.getCreateOrderDto()))
                .thenThrow(new InventoryNotFoundException(1));

        this.mockMvc
                .perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(OrderFixtures.getCreateOrderDto())))
                .andExpect(status().isNotFound());
    }





}
