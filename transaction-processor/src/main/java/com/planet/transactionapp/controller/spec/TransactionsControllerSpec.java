package com.planet.transactionapp.controller.spec;

import com.planet.transactionapp.model.dto.ResponseCountryAmountDto;
import com.planet.transactionapp.model.dto.ResponseTransactionDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "Transactions", description = "Operations pertaining to transactions")
public interface TransactionsControllerSpec {
    @Operation(summary = "Find a transaction by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the transaction",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseTransactionDto.class))})})
    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    ResponseEntity<?> findTransactionById(@Parameter(description = "ID of the transaction to be obtained", required = true) @PathVariable Long id);

    @Operation(summary = "Find the total amount of transactions for a specific recipient")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the transactions",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Map.class))})})
    @GetMapping(value = "/amount/recipient/{recipient-name}", produces = APPLICATION_JSON_VALUE)
    ResponseEntity<?> findAmountByRecipient(@Parameter(description = "Name of the recipient", required = true) @PathVariable("recipient-name") String recipientName);

    @Operation(summary = "Find all transactions by a specific credit card number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the transactions",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseTransactionDto.class))})})
    @GetMapping(value = "/credit-card/{number}", produces = APPLICATION_JSON_VALUE)
    ResponseEntity<?> findAllTransactionsByCreditCardNumber(
            @Parameter(description = "Credit card number, without hyphen (-)", example = "1234123412341234", required = true) @PathVariable("number") String creditNumber,
            @Parameter(description = "Page number for the returned transactions list", required = false) @RequestParam(value = "page", defaultValue = "0") Integer page,
            @Parameter(description = "Size of the returned transactions list", required = false) @RequestParam(value = "size", defaultValue = "10") Integer size,
            @Parameter(description = "Sorting order", required = false) @RequestParam(value = "sort", defaultValue = "ASC") String sort);

    @Operation(summary = "Find the total amount of transactions by country")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the transactions",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseCountryAmountDto.class))})})
    @GetMapping(value = "/amount/country", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> findAmountByCountry();
}