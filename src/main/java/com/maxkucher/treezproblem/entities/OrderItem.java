package com.maxkucher.treezproblem.entities;


import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class OrderItem {
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private BigDecimal salePrice;

    @ManyToOne(cascade = CascadeType.PERSIST,
            optional = false)
    @JoinColumn(name = "INVENTORY_ID")
    private Inventory inventory;


    public OrderItem(int quantity, BigDecimal salePrice, Inventory inventory) {
        this.quantity = quantity;
        this.salePrice = salePrice;
        this.inventory = inventory;
    }


}
