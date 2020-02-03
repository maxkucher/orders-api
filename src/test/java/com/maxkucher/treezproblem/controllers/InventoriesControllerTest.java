package com.maxkucher.treezproblem.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maxkucher.treezproblem.dto.Response;
import com.maxkucher.treezproblem.exceptions.InventoryNotFoundException;
import com.maxkucher.treezproblem.fixtures.InventoriesFixtures;
import com.maxkucher.treezproblem.services.InventoriesService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = InventoriesController.class)
class InventoriesControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InventoriesService inventoriesService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void whenGetInventories_thenStatus200() throws Exception {
        this.mockMvc.perform(get("/inventories"))
                .andExpect(status().isOk());
    }

    @Test
    void whenGetInventories_thenReturnsInventoriesListJSON() throws Exception {
        when(inventoriesService.getInventories())
                .thenReturn(InventoriesFixtures.getFakeInventories());
        MvcResult result = this.mockMvc.perform(get("/inventories"))
                .andReturn();
        assertEquals(MediaType.APPLICATION_JSON.toString(), result.getResponse().getContentType());
        assertEquals(objectMapper.writeValueAsString(InventoriesFixtures.getFakeInventories()),
                result.getResponse().getContentAsString());
        verify(inventoriesService, times(1)).getInventories();
    }


    @Test
    void whenGetInventoriesByIdFound_thenStatus200() throws Exception {
        this.mockMvc.perform(get("/inventories/1"))
                .andExpect(status().isOk());
    }

    @Test
    void whenGetInventoriesByIdFound_thenReturnInventoryJSON() throws Exception {
        when(inventoriesService.getInventoryById(1))
                .thenReturn(InventoriesFixtures.getFakeInventory());
        MvcResult result = this.mockMvc
                .perform(get("/inventories/1"))
                .andReturn();
        assertEquals(MediaType.APPLICATION_JSON.toString(), result.getResponse().getContentType());
        assertEquals(objectMapper.writeValueAsString(InventoriesFixtures.getFakeInventory()),
                result.getResponse().getContentAsString());

        verify(inventoriesService, times(1)).getInventoryById(1);

    }

    @Test
    void whenGetInventoriesNotFound_thenStatus404() throws Exception {
        when(inventoriesService.getInventoryById(anyLong()))
                .thenThrow(new InventoryNotFoundException(1));
        this.mockMvc.perform(get("/inventories/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenGetInventoriesNotFound_thenReturnsResponseJSON() throws Exception {
        when(inventoriesService.getInventoryById(anyLong()))
                .thenThrow(new InventoryNotFoundException(1));
        MvcResult result = this.mockMvc.perform(get("/inventories/1"))
                .andReturn();

        assertEquals(MediaType.APPLICATION_JSON.toString(), result.getResponse().getContentType());
        assertEquals(objectMapper.writeValueAsString(new Response(
                        "Inventory with id 1 not found"
                )),
                result.getResponse().getContentAsString());
    }


    @Test
    void whenPostInventoriesValidBody_thenStatus200() throws Exception {
        this.mockMvc.perform(post("/inventories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper
                        .writeValueAsString(InventoriesFixtures.getFakeInventoryDto())))
                .andExpect(status().isOk());
    }

    @Test
    void whenPostInventoriesSuccess_thenReturnCreatedInventory() throws Exception {
        when(inventoriesService.createInventory(InventoriesFixtures.getFakeInventoryDto()))
                .thenReturn(InventoriesFixtures.getFakeInventory());

        MvcResult result = this.mockMvc
                .perform(post("/inventories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(InventoriesFixtures.getFakeInventoryDto())))
                .andReturn();

        assertEquals(MediaType.APPLICATION_JSON.toString(), result.getResponse().getContentType());
        assertEquals(objectMapper.writeValueAsString(InventoriesFixtures.getFakeInventory()),
                result.getResponse().getContentAsString());


    }


    @Test
    void whenPostInventories_invalidBody_thenStatus400() throws Exception {
        this.mockMvc.perform(post("/inventories"))
                .andExpect(status().isBadRequest());
    }


    @Test
    void whenPutInventories_invalidBody_thenStatus400() throws Exception {
        this.mockMvc.perform(put("/inventories/1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenPutInventories_inventoryNotFound_thenStatus404() throws Exception {
        when(inventoriesService.updateInventory(any(), anyLong()))
                .thenThrow(new InventoryNotFoundException(1));
        this.mockMvc.perform(put("/inventories/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(InventoriesFixtures.getFakeInventoryDto())))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenPutInventories_inventoryNotFound_thenReturnsResponse() throws Exception {
        when(inventoriesService.updateInventory(any(), anyLong()))
                .thenThrow(new InventoryNotFoundException(1));
        MvcResult result = this.mockMvc.perform(put("/inventories/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(InventoriesFixtures.getFakeInventoryDto())))
                .andReturn();

        assertEquals(MediaType.APPLICATION_JSON.toString(), result.getResponse().getContentType());
        assertEquals(objectMapper.writeValueAsString(new Response(
                        "Inventory with id 1 not found"
                )),
                result.getResponse().getContentAsString());
    }

    @Test
    void whenPutInventories_success_thenStatus200() throws Exception {
        this.mockMvc.perform(put("/inventories/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(InventoriesFixtures.getFakeInventoryDto())))
                .andExpect(status().isOk());
    }

    @Test
    void whenPutInventories_success_thenReturnsInventory() throws Exception {
        when(inventoriesService.updateInventory(InventoriesFixtures.getFakeInventoryDto(), 1))
                .thenReturn(InventoriesFixtures.getFakeInventory());
        MvcResult result = this.mockMvc.perform(put("/inventories/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(InventoriesFixtures.getFakeInventoryDto())))
                .andReturn();

        assertEquals(MediaType.APPLICATION_JSON.toString(), result.getResponse().getContentType());
        assertEquals(objectMapper.writeValueAsString(InventoriesFixtures.getFakeInventory()),
                result.getResponse().getContentAsString());
    }


    @Test
    void whenDeleteInventories_success_thenStatus200() throws Exception {
        this.mockMvc
                .perform(delete("/inventories/1"))
                .andExpect(status().isOk());
    }

    @Test
    void whenDeleteInventories_notFound_thenStatus404() throws Exception {
        doThrow(new InventoryNotFoundException(1))
                .when(inventoriesService)
                .deleteInventory(1);

        this.mockMvc
                .perform(delete("/inventories/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenDeleteInventories_notFound_thenReturnsResponse() throws Exception {
        doThrow(new InventoryNotFoundException(1))
                .when(inventoriesService)
                .deleteInventory(anyLong());
        MvcResult result = this.mockMvc.perform(delete("/inventories/1"))
                .andReturn();

        assertEquals(MediaType.APPLICATION_JSON.toString(), result.getResponse().getContentType());
        assertEquals(objectMapper.writeValueAsString(new Response(
                        "Inventory with id 1 not found"
                )),
                result.getResponse().getContentAsString());
    }


}
