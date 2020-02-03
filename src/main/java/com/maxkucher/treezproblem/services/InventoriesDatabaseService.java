package com.maxkucher.treezproblem.services;


import com.maxkucher.treezproblem.dto.InventoryDto;
import com.maxkucher.treezproblem.entities.Inventory;
import com.maxkucher.treezproblem.exceptions.InventoryNotFoundException;
import com.maxkucher.treezproblem.repository.InventoriesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoriesDatabaseService implements InventoriesService {

    private final InventoriesRepository inventoriesRepository;

    public List<Inventory> getInventories() {
        return inventoriesRepository.findAll();
    }

    public Inventory getInventoryById(long id) {
        return inventoriesRepository.findById(id)
                .orElseThrow(() -> new InventoryNotFoundException(id));
    }

    public Inventory createInventory(InventoryDto inventoryDto) {
        Inventory inventory = new Inventory(
                inventoryDto.getName(),
                inventoryDto.getDescription(),
                inventoryDto.getPrice(),
                inventoryDto.getQuantityAvailable()
        );
        return inventoriesRepository.save(inventory);
    }

    public Inventory updateInventory(InventoryDto inventoryDto, long id) {
        Inventory inventory = getInventoryById(id);
        inventory.setDescription(inventoryDto.getDescription());
        inventory.setName(inventoryDto.getName());
        inventory.setPrice(inventoryDto.getPrice());
        inventory.setQuantityAvailable(inventoryDto.getQuantityAvailable());

        return inventoriesRepository.save(inventory);
    }


    public void deleteInventory(long id) {
        try {
            inventoriesRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new InventoryNotFoundException(id);
        }
    }
}
