package com.hiberus.order.service;


import com.hiberus.order.model.Destination;
import com.hiberus.order.model.Transport;
import com.hiberus.order.model.dto.GetPriceResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "ticket-price", url="http://localhost:8081/")
public interface TicketPrice {


    @GetMapping("/price")
    GetPriceResponseDto getPrice(@RequestParam Destination destination, @RequestParam Transport transport);

}
