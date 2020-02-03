package com.maxkucher.treezproblem.dto;


import lombok.*;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Response {
    private boolean success = false;
    @NonNull
    private String message;
}
