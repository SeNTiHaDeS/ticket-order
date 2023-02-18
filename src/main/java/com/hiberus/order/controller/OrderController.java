package com.hiberus.order.controller;


import com.hiberus.order.model.dto.PostOrderRequestDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;

public interface OrderController {
    @ApiOperation(value = "Order a ticket")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Ticket ordered successfully")
    })
    ResponseEntity<?> order(PostOrderRequestDto orderRequest);
}
