package com.maxkucher.treezproblem.services;

import com.maxkucher.treezproblem.dto.InventoryDto;
import com.maxkucher.treezproblem.entities.Inventory;
import com.maxkucher.treezproblem.exceptions.InventoryNotFoundException;
import com.maxkucher.treezproblem.repository.InventoriesRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.maxkucher.treezproblem.fixtures.InventoriesFixtures.getFakeInventories;
import static com.maxkucher.treezproblem.fixtures.InventoriesFixtures.getFakeInventory;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class InventoriesDatabaseServiceTest {

    @Mock
    InventoriesRepository inventoriesRepository;


    @InjectMocks
    InventoriesDatabaseService inventoriesDatabaseService;


    @Test
    void whenGetInventories_thenReturnAllInventories() {
        when(inventoriesRepository.findAll())
                .thenReturn(getFakeInventories());

        List<Inventory> inventories = inventoriesDatabaseService.getInventories();

        assertEquals(inventories, getFakeInventories());
        verify(inventoriesRepository, times(1))
                .findAll();
    }

    @Test
    void whenGetInventoryByIdExists_thenReturnInventory() {
        long testId = 0;
        when(inventoriesRepository.findById(anyLong()))
                .thenReturn(Optional.of(getFakeInventory()));
        Inventory inventory = inventoriesDatabaseService.getInventoryById(testId);

        assertEquals(inventory, getFakeInventory());

        verify(inventoriesRepository, times(1))
                .findById(testId);
    }


    @Test
    void whenGetInventoryByIdDoesNotExist_thenThrowException() {
        long testId = 1;
        when(inventoriesRepository.findById(anyLong()))
                .thenReturn(Optional.empty());
        assertThrows(InventoryNotFoundException.class, () -> inventoriesDatabaseService.getInventoryById(testId));

    }

    @Test
    void whenCreateInventory_thenReturnsCreatedObject() {
        when(inventoriesRepository.save(getFakeInventory()))
                .thenReturn(getFakeInventory());

        Inventory inventory = inventoriesDatabaseService.createInventory(getFakeInventoryDto());

        assertEquals(inventory, getFakeInventory());

        verify(inventoriesRepository, times(1))
                .save(getFakeInventory());
    }

    @Test
    void whenUpdateInventoryExists_thenReturnsUpdatedObject() {
        long testId = 1;
        when(inventoriesRepository.findById(testId))
                .thenReturn(Optional.of(getFakeInventory()));
        when(inventoriesRepository.save(getFakeInventory()))
                .thenReturn(getFakeInventory());

        Inventory inventory = inventoriesDatabaseService.updateInventory(getFakeInventoryDto(), testId);

        assertEquals(inventory, getFakeInventory());


        verify(inventoriesRepository, times(1))
                .save(getFakeInventory());

        verify(inventoriesRepository, times(1))
                .findById(testId);
    }

    @Test
    void whenUpdateInventoryExists_thenThrowsException() {
        long testId = 1;
        when(inventoriesRepository.findById(testId))
                .thenThrow(new InventoryNotFoundException(testId));

        assertThrows(InventoryNotFoundException.class, () -> inventoriesDatabaseService.updateInventory(getFakeInventoryDto(), testId));
    }

    @Test
    void whenDeleteInventory_thenCallsDeleteById() {
        long testId = 1;
        inventoriesDatabaseService.deleteInventory(testId);
        verify(inventoriesRepository, times(1))
                .deleteById(testId);
    }

    @Test
    void whenDeleteInventoryDoesNotExist_thenThrowInventoryNotFoundException() {

        long testId = 1;
        doThrow(new EmptyResultDataAccessException(1))
                .when(inventoriesRepository)
                .deleteById(testId);

        assertThrows(InventoryNotFoundException.class, () -> inventoriesDatabaseService.deleteInventory(testId));
    }


    static InventoryDto getFakeInventoryDto() {
        return InventoryDto
                .builder()
                .description("test")
                .name("test")
                .price(new BigDecimal("10.0"))
                .quantityAvailable(10)
                .build();
    }


}
