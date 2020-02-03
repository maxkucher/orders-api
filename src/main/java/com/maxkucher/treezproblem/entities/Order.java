package com.maxkucher.treezproblem.entities;


import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ORDERS")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Order {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private Instant datePlaced;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;


    @Singular
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(
            name = "ORDER_ORDER_ITEM",
            joinColumns = @JoinColumn(name = "ORDER_ID"),
            inverseJoinColumns = @JoinColumn(name = "ORDER_ITEM_ID")
    )
    private List<OrderItem> inventories = new ArrayList<>();


    public Order(String email, Instant datePlaced, OrderStatus status, List<OrderItem> inventories) {
        this.email = email;
        this.datePlaced = datePlaced;
        this.status = status;
        this.inventories = inventories;
    }


}
