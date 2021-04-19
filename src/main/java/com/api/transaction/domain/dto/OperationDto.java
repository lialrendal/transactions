package com.api.transaction.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OperationDto {

    @JsonProperty("operation_id")
    private long operation_id;
    @JsonProperty("operation_type")
    private String operation_type;
    @JsonProperty("amount")
    private double amount;
    @JsonProperty("source_account_id")
    private long source_account_id;
    @JsonProperty("destination_account_id")
    private long destination_account_id;
}
