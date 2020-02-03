package com.maxkucher.treezproblem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryDto {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @Positive
    private BigDecimal price;
    @Positive
    private int quantityAvailable;
}

