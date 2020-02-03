package com.maxkucher.treezproblem.services;

import com.maxkucher.treezproblem.dto.InventoryDto;
import com.maxkucher.treezproblem.entities.Inventory;

import java.util.List;

public interface InventoriesService {

    List<Inventory> getInventories();

    Inventory getInventoryById(long id);

    Inventory createInventory(InventoryDto inventoryDto);

    Inventory updateInventory(InventoryDto inventoryDto, long id);

    void deleteInventory(long id);
}
