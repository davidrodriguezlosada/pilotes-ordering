package com.proof.api.controllers.clients.orders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proof.api.controllers.clients.orders.requests.UpdateOrderRequestDto;
import com.proof.mothers.api.controllers.clients.orders.requests.UpdateOrderRequestDtoMother;
import com.proof.services.clients.orders.UpdateOrderService;
import com.proof.services.notifications.NotificationService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UpdateOrderControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    UpdateOrderController updateOrderController;
    @Mock
    UpdateOrderService updateOrderService;
    @Mock
    NotificationService notificationService;

    @BeforeEach
    public void setup() {
        this.updateOrderController = new UpdateOrderController(updateOrderService, notificationService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(updateOrderController).build();
    }

    @Test
    void updateOrderOk() throws Exception {
        // given a body
        UpdateOrderRequestDto updateOrderRequestDto = UpdateOrderRequestDtoMother.validRequest();
        String jsonBody = new ObjectMapper().
                writer().
                withDefaultPrettyPrinter().
                writeValueAsString(updateOrderRequestDto);

        // when
        mockMvc.perform(put("/api/clients/0001/orders/O-0001")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void updateOrderInvalidRequestBody() throws Exception {
        //given a body
        UpdateOrderRequestDto updateOrderRequestDto = UpdateOrderRequestDtoMother.invalidRequest();
        String jsonBody = new ObjectMapper().
                writer().
                withDefaultPrettyPrinter().
                writeValueAsString(updateOrderRequestDto);

        //when
        mockMvc.perform(put("/api/clients/0001/orders/O-0001")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
