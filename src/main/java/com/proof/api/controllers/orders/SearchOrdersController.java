package com.proof.api.controllers.orders;

import com.proof.api.CustomRestController;
import com.proof.api.ErrorResponse;
import com.proof.api.dtos.ClientDto;
import com.proof.api.dtos.OrderDto;
import com.proof.api.filtering.expressions.Expression;
import com.proof.api.filtering.parser.ApiFilterParser;
import com.proof.services.orders.SearchOrdersService;

import org.apache.commons.lang3.StringUtils;
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
    ApiFilterParser apiFilterParser;

    public SearchOrdersController(SearchOrdersService searchOrdersService,
                                  ApiFilterParser apiFilterParser) {
        this.searchOrdersService = searchOrdersService;
        this.apiFilterParser = apiFilterParser;
    }

    @Operation(summary = "Search orders",
            description = "<h2>Search orders.</h2> <br>" +
                    "Search existing orders in the system. This endpoint have filtering capabilities to filter " +
                    "orders by the properties of its client. You can use any property of the ClientDto schema to " +
                    "filter the orders.",
            security = @SecurityRequirement(name = "basicAuth"),
            parameters = @Parameter(name = "filter", description = "Filter string to filter orders by it's client " +
                    "properties. You can use the following comparators in your filters:" +
                    "<ul>" +
                    "<li><b>=</b>  Equal comparator</li>" +
                    "<li><b><></b>  Different comparator</li>" +
                    "<li><b>:LIKE:</b> Likeness comparator. You can use % to represent any string of zero or more characters." +
                    "</ul>" +
                    "and the following logical expressions: " +
                    "<ul><li><b>:AND:</b></li></ul>" +
                    "With all this, you will be able to filter using expressions like:" +
                    "<code>firstName:LIKE:Jo%:AND:lastName=Doe</code><br><br>" +
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
        if (StringUtils.isBlank(filter)) {
            return searchOrdersService.search();
        } else {
            Expression<Object, Object> expression = apiFilterParser.parseExpression(filter, ClientDto.class);
            return searchOrdersService.search(expression);
        }
    }
}
