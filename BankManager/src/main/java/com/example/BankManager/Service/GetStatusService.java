package com.example.BankManager.Service;

import com.example.BankManager.Class.Status;
import com.example.BankManager.Class.Transaction;
import com.example.BankManager.Enum.StatusEnum;
import com.example.BankManager.Repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Optional;

@Service
public class GetStatusService {
    public static final String ATM = "ATM";
    public static final String INTERNAL = "INTERNAL";
    @Autowired
    TransactionRepository repository;

    public ResponseEntity<Status> getStatus(String reference, String channel) {
        Optional<Transaction> transaction = repository.findById(reference);
        Status response = Status.builder().reference(reference).build();
        if (transaction.isPresent()) {
            response = mapStatus(transaction.get(), channel);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.setStatus(StatusEnum.INVALID.get());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    private Status mapStatus(Transaction transaction, String channel) {
        BigDecimal amount = null;

        if (channel.equalsIgnoreCase(INTERNAL)) {
            amount = transaction.getAmount();
        }

        Calendar calendar = Calendar.getInstance();
        int today = calendar.get(Calendar.DAY_OF_YEAR);

        calendar.setTime(transaction.getDate());
        int transactionDay = calendar.get(Calendar.DAY_OF_YEAR);

        String status;
        if (transactionDay < today) {
            status = StatusEnum.SETTLED.get();
        } else if (transactionDay == today) {
            status = StatusEnum.PENDING.get();
        } else if (channel.equalsIgnoreCase(ATM)) {
            status = StatusEnum.PENDING.get();
        } else {
            status = StatusEnum.FUTURE.get();
        }

        return Status.builder()
                .status(status)
                .amount(amount)
                .build();
    }
}
