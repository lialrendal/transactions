package com.api.transaction.domain.entity;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="operations")
public class Operation {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long operation_id;
    private String operation_type;
    private double amount;
    private long source_account_id;
    private long destination_account_id;

    public Operation(String operation_type, double amount, long source_account_id,
                     long destination_account_id) {
        super();
        this.operation_type = operation_type;
        this.amount = amount;
        this.source_account_id = source_account_id;
        this.destination_account_id = destination_account_id;
    }
}

