package com.hiberus.order;

import com.google.gson.Gson;

import com.hiberus.order.model.Destination;
import com.hiberus.order.model.Transport;
import com.hiberus.order.model.dto.GetPriceResponseDto;
import com.hiberus.order.model.dto.PostOrderRequestDto;
import com.hiberus.order.service.TicketPrice;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class OrderControllerTests {

    @Autowired
    private MockMvc mockMvc;

    Gson gson = new Gson();

    @MockBean
    private TicketPrice ticketPriceService;

    @Test
    public void createAnOrder() throws Exception {
        when(ticketPriceService.getPrice(any(), any())).thenReturn(GetPriceResponseDto.builder().build());

        PostOrderRequestDto postOrderRequestDto = PostOrderRequestDto.builder()
                .userId("Pepito Grillo")
                .destination(Destination.MADRID)
                .transport(Transport.TRAIN)
                .numberOfTickets(12)
                .build();

        mockMvc.perform(post("/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(postOrderRequestDto)))
                .andDo(print())
                .andExpect(status().isCreated());
    }
}
