package com.maxkucher.treezproblem.repository;

import com.maxkucher.treezproblem.entities.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface InventoriesRepository extends JpaRepository<Inventory, Long> {
}
