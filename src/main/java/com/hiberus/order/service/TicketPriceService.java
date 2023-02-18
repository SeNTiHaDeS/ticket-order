package com.hiberus.order.service;



import com.hiberus.order.model.Destination;
import com.hiberus.order.model.Transport;
import com.hiberus.order.model.dto.GetPriceResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TicketPriceService {

    RestTemplate restTemplate = new RestTemplate();

    @Value("${app.environment.ticket-price-host}")
    String ticketPriceHost;

    public Double askForTicketPrice(Destination destination, Transport transport) throws Exception {
        String URI = "http://" + ticketPriceHost + ":8081/price?destination=" + destination + "&transport=" + transport;
        GetPriceResponseDto response = restTemplate.getForObject(URI, GetPriceResponseDto.class);

        if (response == null) {
            throw new Exception();
        }

        return response.getPrice();
    }
}
