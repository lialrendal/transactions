package com.api.transaction.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="interest")
public class Interest {

    @Id
    private long interest_id;
    private double percentage;
    private Date interest_init_date;
    private Date interest_final_date;

}