package com.hiberus.order.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "orders")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private String id;

    String userId;
    Integer numberOfTickets;
    Double unitaryPrice;
    Double totalPrice;

    @Enumerated(EnumType.STRING)
    Transport transport;

    @Enumerated(EnumType.STRING)
    Destination destination;

}
