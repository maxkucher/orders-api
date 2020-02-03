package com.maxkucher.treezproblem.repository;

import com.maxkucher.treezproblem.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrdersRepository extends JpaRepository<Order, Long> {
}
