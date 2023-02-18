package com.hiberus.order.model.dto;


import com.hiberus.order.model.Destination;
import com.hiberus.order.model.Transport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class PostOrderRequestDto {
    private String userId;
    private Transport transport;
    private Destination destination;
    private Integer numberOfTickets;
}
