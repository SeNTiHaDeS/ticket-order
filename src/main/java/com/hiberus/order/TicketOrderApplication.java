package com.hiberus.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.hiberus.order.model")
public class TicketOrderApplication {
    public static void main(final String[] args) {
        SpringApplication.run(TicketOrderApplication.class, args);
    }
}
