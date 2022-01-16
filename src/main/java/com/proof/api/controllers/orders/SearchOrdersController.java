package com.proof.api.controllers.orders;

import com.proof.api.CustomRestController;
import com.proof.api.ErrorResponse;
import com.proof.api.dtos.OrderDto;
import com.proof.services.orders.SearchOrdersService;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@CustomRestController
public class SearchOrdersController {

    SearchOrdersService searchOrdersService;

    public SearchOrdersController(SearchOrdersService searchOrdersService) {
        this.searchOrdersService = searchOrdersService;
    }

    @Operation(summary = "Search orders",
            description = "<h2>Search orders.</h2> <br>" +
                    "Search existing orders in the system. This endpoint have filtering capabilities to filter " +
                    "orders by the properties of its client. You can use any property of the ClientDto schema to " +
                    "filter the orders.",
            security = @SecurityRequirement(name = "basicAuth"),
            parameters = @Parameter(name = "filter", description = "Filter string to filter orders by it's client " +
                    "properties. You can check <a href=\"https://github.com/jirutka/rsql-parser#grammar-and-semantic\">this site</a> " +
                    "to get the full list of operations allowed: " +
                    "<ul>" +
                    "<li><b>==</b>  Equal comparator</li>" +
                    "<li><b>!=</b>  Different comparator</li>" +
                    "</ul>" +
                    "and the following logical expressions: " +
                    "<ul><li><b>and</b></li></ul>" +
                    "<ul><li><b>or</b></li></ul>" +
                    "With all this, you will be able to filter using expressions like:" +
                    "<code>firstName==John:AND:lastName!=Doe</code><br><br>" +
                    "TIP: In the database there are two clients (0001 and 0002) that will match this filter, " +
                    "so you can create orders to them to test this filter."))
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Orders found",
                    content = {@Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = OrderDto.class)))}),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(
                    responseCode = "404",
                    description = "No order found",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class))})})
    @GetMapping(value = "/orders")
    public List<OrderDto> searchOrders(@RequestParam(required = false) String filter) {
        return searchOrdersService.search(filter);
    }
}
