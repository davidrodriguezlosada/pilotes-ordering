package com.proof.api.controllers.clients.orders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proof.api.controllers.clients.orders.requests.CreateOrderRequestDto;
import com.proof.mothers.api.controllers.clients.orders.requests.CreateOrderRequestDtoMother;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CreateOrderControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void createOrderOk() throws Exception {
        //given a body
        CreateOrderRequestDto createOrderRequestDto = CreateOrderRequestDtoMother.validRequest();
        String jsonBody = new ObjectMapper().
                writer().
                withDefaultPrettyPrinter().
                writeValueAsString(createOrderRequestDto);

        //when
        mockMvc.perform(post("/api/clients/0001/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void createOrderInvalidRequestBody() throws Exception {
        //given a body
        CreateOrderRequestDto createOrderRequestDto = CreateOrderRequestDtoMother.invalidRequest();
        String jsonBody = new ObjectMapper().
                writer().
                withDefaultPrettyPrinter().
                writeValueAsString(createOrderRequestDto);

        //when
        mockMvc.perform(post("/api/clients/0001/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
