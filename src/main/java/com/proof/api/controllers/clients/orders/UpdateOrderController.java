package com.proof.api.controllers.clients.orders;

import com.proof.api.CustomRestController;
import com.proof.api.ErrorResponse;
import com.proof.api.controllers.clients.orders.requests.UpdateOrderRequestDto;
import com.proof.api.dtos.OrderDto;
import com.proof.services.clients.orders.UpdateOrderService;
import com.proof.services.notifications.NotificationService;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@CustomRestController
public class UpdateOrderController {

    private final UpdateOrderService updateOrderService;
    private final NotificationService notificationService;

    public UpdateOrderController(UpdateOrderService updateOrderService,
                                 NotificationService notificationService) {
        this.updateOrderService = updateOrderService;
        this.notificationService = notificationService;
    }

    @Operation(summary = "Updates an existing order",
            description = "<h2>Updates an existing order.</h2> <br> Orders can't be updated after 5 minutes from it's creation")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Order updated",
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
    @PutMapping(value = "/clients/{client-code}/orders/{order-code}",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public OrderDto updateOrder(
            @PathVariable("client-code") String clientCode,
            @PathVariable("order-code") String orderCode,
            @Valid @RequestBody UpdateOrderRequestDto updateOrderRequestDto) {

        OrderDto orderDto = updateOrderService.updateOrder(clientCode, orderCode, updateOrderRequestDto);

        notificationService.notifyOrderUpdate(clientCode, orderCode, updateOrderRequestDto);

        return orderDto;
    }
}
