package com.hiberus.order.controller;


import com.hiberus.order.model.Destination;
import com.hiberus.order.model.Order;
import com.hiberus.order.model.Transport;
import com.hiberus.order.model.dto.GetPriceResponseDto;
import com.hiberus.order.model.dto.PostOrderRequestDto;
import com.hiberus.order.service.OrderService;
import com.hiberus.order.service.TicketPrice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
public class OrderControllerImpl  {

    OrderService orderService;
    TicketPrice ticketPriceService;

    @Autowired
    public OrderControllerImpl(OrderService orderService, TicketPrice ticketPriceService) {
        this.orderService = orderService;
        this.ticketPriceService = ticketPriceService;
    }


    @PostMapping(path = "/order", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> order(@RequestBody PostOrderRequestDto orderRequest) {
        String userId = orderRequest.getUserId();
        Transport transport = orderRequest.getTransport();
        Integer numberOfTickets = orderRequest.getNumberOfTickets();
        Destination destination = orderRequest.getDestination();

        try {
            GetPriceResponseDto unitaryPrice = ticketPriceService.getPrice(destination, transport);

            Order order= Order.builder()
                    .userId(userId)
                    .transport(transport)
                    .numberOfTickets(numberOfTickets)
                    .unitaryPrice(unitaryPrice.getPrice())
                    .destination(destination)
                    .totalPrice(unitaryPrice.getPrice() * numberOfTickets)
                    .build();

            orderService.order(order);

            return new ResponseEntity<>("", HttpStatus.CREATED);
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
