package com.proof.api.controllers.clients.orders;

import com.proof.api.CustomRestController;
import com.proof.api.ErrorResponse;
import com.proof.api.controllers.clients.orders.requests.CreateOrderRequestDto;
import com.proof.api.dtos.OrderDto;
import com.proof.services.clients.orders.CreateOrderService;
import com.proof.services.notifications.NotificationService;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@CustomRestController
public class CreateOrderController {

    private final CreateOrderService createOrderService;
    private final NotificationService notificationService;

    public CreateOrderController(CreateOrderService createOrderService,
                                 NotificationService notificationService) {
        this.createOrderService = createOrderService;
        this.notificationService = notificationService;
    }

    @Operation(summary = "Creates a new order",
            description = "<h2>Creates a new order.</h2>")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Order created",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = OrderDto.class))}),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(
                    responseCode = "404",
                    description = "Client or order have not been found",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class))})})
    @PostMapping(value = "/clients/{client-code}/orders",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public OrderDto createOrder(
            @PathVariable("client-code") String clientCode,
            @Valid @RequestBody CreateOrderRequestDto createOrderRequestDto) {

        OrderDto orderDto = createOrderService.createOrder(clientCode, createOrderRequestDto);

        notificationService.notifyOrderCreate(clientCode, createOrderRequestDto);

        return orderDto;
    }
}
