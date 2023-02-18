package com.hiberus.order.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class
GetPriceResponseDto {
    private String transport;
    private String destination;
    private Double price;
}

