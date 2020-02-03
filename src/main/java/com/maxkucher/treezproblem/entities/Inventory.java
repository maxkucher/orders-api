package com.maxkucher.treezproblem.entities;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import java.math.BigDecimal;

@Entity
@Table(name = "INVENTORIES")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Inventory {
    @Id
    @GeneratedValue
    private long id;


    private String name;

    private String description;

    @Min(0)
    private BigDecimal price;

    @Min(0)
    private int quantityAvailable;

    public Inventory(String name, String description, BigDecimal price, int quantityAvailable) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantityAvailable = quantityAvailable;
    }

}
