package com.proof.api.controllers.orders;

import com.proof.services.orders.SearchOrdersService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
class SearchOrdersControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    SearchOrdersController searchOrdersController;
    @Mock
    SearchOrdersService searchOrdersService;

    @BeforeEach
    void setup() {
        this.searchOrdersController = new SearchOrdersController(searchOrdersService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(searchOrdersController).build();
    }

    @Test
    @WithMockUser(username = "user", password = "userPass")
    void searchOrderOk() throws Exception {
        //when
        mockMvc.perform(get("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk());

        // then
        verify(searchOrdersService, times(1)).search(any());
    }


    @Test
    @WithMockUser(username = "user", password = "userPass")
    void searchOrderWithFilterOk() throws Exception {
        //when
        mockMvc.perform(get("/api/orders?filter=firstName=John")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk());

        // then
        verify(searchOrdersService, times(1)).search(any());
    }
}
