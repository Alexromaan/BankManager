package com.example.BankManager.Class;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
public class Status {

    private String reference;
    private String status;
    private BigDecimal amount;
    private BigDecimal fee;
}
