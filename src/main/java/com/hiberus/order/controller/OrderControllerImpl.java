package com.hiberus.order.controller;


import com.hiberus.order.model.Destination;
import com.hiberus.order.model.Order;
import com.hiberus.order.model.Transport;
import com.hiberus.order.model.dto.PostOrderRequestDto;
import com.hiberus.order.service.OrderService;
import com.hiberus.order.service.TicketPriceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Slf4j
@Controller
public class OrderControllerImpl implements OrderController {

    OrderService orderService;
    TicketPriceService ticketPriceService;

    @Autowired
    public OrderControllerImpl(OrderService orderService, TicketPriceService ticketPriceService) {
        this.orderService = orderService;
        this.ticketPriceService = ticketPriceService;
    }

    @Override
    @PostMapping(path = "/order", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> order(@RequestBody PostOrderRequestDto orderRequest) {
        String userId = orderRequest.getUserId();
        Transport transport = orderRequest.getTransport();
        Integer numberOfTickets = orderRequest.getNumberOfTickets();
        Destination destination = orderRequest.getDestination();

        try {
            Double unitaryPrice = ticketPriceService.askForTicketPrice(destination, transport);

            Order order= Order.builder()
                    .userId(userId)
                    .transport(transport)
                    .numberOfTickets(numberOfTickets)
                    .unitaryPrice(unitaryPrice)
                    .destination(destination)
                    .totalPrice(unitaryPrice * numberOfTickets)
                    .build();

            orderService.order(order);

            return new ResponseEntity<>("", HttpStatus.CREATED);
        }
        catch (Exception e) {
            return new ResponseEntity<>("", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
