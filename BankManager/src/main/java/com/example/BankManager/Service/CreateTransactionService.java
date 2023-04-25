package com.example.BankManager.Service;

import com.example.BankManager.Class.Transaction;
import com.example.BankManager.Repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Random;

@Service
public class CreateTransactionService {
    @Autowired
    TransactionRepository repository;

    @Transactional
    public ResponseEntity<Transaction> add(Transaction transaction) {
        try {
            Transaction response = null;
            if (transaction.getReference() == null)
                transaction.setReference(createRandomReference());

            if (isPossible(transaction.getAmount(), transaction.getFee())) {
                response = repository.save(transaction);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private BigDecimal getTotalAmount() {
        BigDecimal currentAmount = BigDecimal.ZERO;
        repository.findAll().forEach(current -> currentAmount.add(current.getAmount()));
        return currentAmount;
    }

    private boolean isPossible(BigDecimal amount, BigDecimal fee) {
        BigDecimal transactionAmount = BigDecimal.ZERO;
        BigDecimal totalAmount = getTotalAmount();

        if (amount != null)
            transactionAmount = transactionAmount.add(amount);

        if (fee != null)
            transactionAmount = transactionAmount.add(fee);

        return transactionAmount.compareTo(BigDecimal.ZERO) >= 0 || totalAmount.compareTo(transactionAmount.abs()) > 0;
    }

    private static String createRandomReference() {
        Random rand = new Random();
        StringBuilder reference = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            reference.append(rand.nextInt(10));
        }
        char key = (char) (rand.nextInt(26) + 'A');
        reference.append(key);
        return reference.toString();
    }

}

