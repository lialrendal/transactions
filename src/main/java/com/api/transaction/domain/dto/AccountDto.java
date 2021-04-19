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
public class AccountDto {

    @JsonProperty("account_id")
    private long account_id;
    @JsonProperty("client")
    private String client;
    @JsonProperty("account_type")
    private String account_type;
    @JsonProperty("account_amount")
    private double account_amount;
    @JsonProperty("interest")
    private InterestDto interest;
}
