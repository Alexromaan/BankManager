package com.example.BankManager.Class;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;


@Entity
@Table(name = "transactions")
@Data
@AllArgsConstructor
@Builder
public class Transaction {
    @Id
    @Column
    private String reference;
    @Column
    private String account_iban;
    @Column
    private Date date;
    @Column
    private BigDecimal amount;
    @Column
    private BigDecimal fee;
    @Column
    private String description;

}