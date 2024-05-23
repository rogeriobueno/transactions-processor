package com.planet.transactionapp.controller;

import com.planet.transactionapp.controller.spec.TransactionsControllerSpec;
import com.planet.transactionapp.model.dto.ResponseCountryAmountDto;
import com.planet.transactionapp.model.dto.ResponseTransactionDto;
import com.planet.transactionapp.services.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController implements TransactionsControllerSpec {

    private final TransactionService transactionService;

    @Override
    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseTransactionDto> findTransactionById(@PathVariable Long id) {
        ResponseTransactionDto responseDto = transactionService.findById(id);
        responseDto.add(linkTo(methodOn(TransactionController.class)
                .findTransactionById(responseDto.getId())).withSelfRel());
        return ResponseEntity.ok(responseDto);
    }

    @Override
    @GetMapping(value = "/amount/recipient/{recipient-name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ResponseCountryAmountDto>> findAmountByRecipient(@PathVariable("recipient-name") String recipientName) {
        // 404 only in Internal API
        return ResponseEntity.ok(transactionService.findAmountByRecipient(recipientName));
    }

    @Override
    @GetMapping(value = "/amount/country", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAmountByCountry() {
        return ResponseEntity.ok(transactionService.findSumAmountTransactionGroupByCurrency());
    }

    @Override
    @GetMapping(value = "/credit-card/{number}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAllTransactionsByCreditCardNumber(
            @PathVariable("number") String creditNumber,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "sort", defaultValue = "ASC") String sort) {
        Sort.Direction order = sort.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable Pageable = PageRequest.of(page, size, Sort.by(order, "id"));
        Page<ResponseTransactionDto> dtos = transactionService.findAllByCreditNumberPageable(creditNumber, Pageable);
        dtos.forEach(trans -> trans.add(linkTo(methodOn(TransactionController.class)
                .findTransactionById(trans.getId())).withSelfRel()));
        return ResponseEntity.ok(dtos);
    }


}
