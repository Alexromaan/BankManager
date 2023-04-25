package com.example.BankManager;

import com.example.BankManager.Class.Status;
import com.example.BankManager.Class.Transaction;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Constants {
    public static final String REFERENCE = "1234";
    public static final String REFERENCE_2 = "5678";
    public static final String SETTLED = "SETTLED";
    public static final String FUTURE = "FUTURE";
    public static final String PENDING = "PENDING";
    public static final String INTERNAL = "INTERNAL";
    public static final String CLIENT = "CLIENT";
    public static final String ATM = "ATM";

    public static final BigDecimal AMOUNT = BigDecimal.TEN;
    public static final String DESC = "desc";

    public static final Transaction TRANSACTION = Transaction.builder()
            .reference(REFERENCE)
            .amount(BigDecimal.TEN)
            .fee(BigDecimal.ONE)
            .date(Date.from(Instant.now()))
            .description("First transaction")
            .build();

    public static final Transaction TRANSACTION_2 = Transaction.builder()
            .reference(REFERENCE_2)
            .amount(BigDecimal.valueOf(20))
            .fee(BigDecimal.ZERO)
            .date(getYesterdayDate())
            .description("Second transaction")
            .build();

    public static final BigDecimal NEGATIVE_AMOUNT = BigDecimal.valueOf(-200);
    public static final Transaction TRANSACTION_3 = Transaction.builder()
            .amount(NEGATIVE_AMOUNT)
            .date(getTomorrowDate())
            .build();

    public static final List<Transaction> TRANSACTION_LIST = List.of(TRANSACTION, TRANSACTION_2);

    public static final Status STATUS = new Status(REFERENCE, SETTLED, new BigDecimal(BigInteger.TEN), null);
    public static final Status STATUS_PENDING = new Status(REFERENCE, PENDING, new BigDecimal(BigInteger.TEN), null);
    public static final Status STATUS_FUTURE = new Status(REFERENCE, FUTURE, new BigDecimal(BigInteger.TEN), null);

    private static Date getTomorrowDate() {
        Calendar tomorrow = Calendar.getInstance();
        tomorrow.add(Calendar.DAY_OF_YEAR, 1);
        return tomorrow.getTime();
    }

    private static Date getYesterdayDate() {
        Calendar tomorrow = Calendar.getInstance();
        tomorrow.add(Calendar.DAY_OF_YEAR, -1);
        return tomorrow.getTime();
    }

}
