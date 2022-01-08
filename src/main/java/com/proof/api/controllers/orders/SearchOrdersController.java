package com.proof.api.controllers.orders;

import com.proof.api.CustomRestController;
import com.proof.api.dtos.OrderDto;
import com.proof.services.orders.SearchOrdersService;

import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@CustomRestController
public class SearchOrdersController {

    SearchOrdersService searchOrdersService;

    public SearchOrdersController(SearchOrdersService searchOrdersService) {
        this.searchOrdersService = searchOrdersService;
    }

    @Operation(summary = "Search orders",
            description = "<h2>Search orders.</h2>",
            security = @SecurityRequirement(name = "basicAuth"))
    @GetMapping(value = "/orders")
    public List<OrderDto> searchOrders() {
        return searchOrdersService.search();
    }
}
