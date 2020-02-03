package com.maxkucher.treezproblem.dto;


import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {
    @Email
    private String email;




    @Singular
    @Size(min = 1)
    private List<OrderItemDto> orderItems = new ArrayList<>();


}
