package com.api.transaction.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="accounts")
public class Account {

    @Id
    private long account_id;
    private String client;
    private String account_type;
    private double account_amount;
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name="interest_id")
    private Interest interest;

}

