package com.api.transaction.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InterestDto {

    @JsonProperty("interest_id")
    private long interest_id;
    @JsonProperty("percentage")
    private double percentage;
    @JsonProperty("interest_init_date")
    private Date interest_init_date;
    @JsonProperty("interest_final_date")
    private Date interest_final_date;
}
