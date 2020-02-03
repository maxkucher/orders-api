package com.maxkucher.treezproblem.controllers;


import com.maxkucher.treezproblem.dto.InventoryDto;
import com.maxkucher.treezproblem.services.InventoriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/inventories")
@RequiredArgsConstructor
public class InventoriesController {

    private final InventoriesService inventoriesService;


    @GetMapping
    public ResponseEntity<?> getInventories() {
        return ResponseEntity.ok(inventoriesService.getInventories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getInventoryById(@PathVariable long id) {
        return ResponseEntity.ok(inventoriesService.getInventoryById(id));
    }

    @PostMapping
    public ResponseEntity<?> createInventory(@Valid  @RequestBody InventoryDto inventoryDto) {
        return ResponseEntity.ok(inventoriesService.createInventory(inventoryDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateInventory(@Valid @RequestBody InventoryDto inventoryDto, @PathVariable long id) {
        return ResponseEntity.ok(inventoriesService.updateInventory(inventoryDto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInventory(@PathVariable long id) {
        inventoriesService.deleteInventory(id);
        return ResponseEntity.ok().build();
    }
}
