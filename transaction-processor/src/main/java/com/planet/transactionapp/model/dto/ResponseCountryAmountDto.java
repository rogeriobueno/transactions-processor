package com.planet.transactionapp.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Schema(name = "CountryAmountResponse",
        description = "Schema to hold response amount for countries")
@JsonPropertyOrder(value = {"countryName", "totalSum"})
public class ResponseCountryAmountDto {

    @Schema(description = "Country name")
    @JsonProperty("countryName")
    private String country;

    @Schema(description = "Total sum")
    @JsonProperty("totalSum")
    private BigDecimal sum;
}
