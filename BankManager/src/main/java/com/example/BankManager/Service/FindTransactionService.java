package com.example.BankManager.Service;

import com.example.BankManager.Class.Transaction;
import com.example.BankManager.Repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class FindTransactionService {

    public static final String DESC = "desc";
    @Autowired
    TransactionRepository repository;

    public ResponseEntity<List<Transaction>> find(String id, String sortBy) {
        List<Transaction> response = repository.findAll();
        if (id != null) {
            response = findById(response, id);
        }
        if (sortBy != null) {
            response = sortResponse(response, sortBy);
        }

        if (response.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(response, HttpStatus.OK);


    }

    private List<Transaction> findById(List<Transaction> transactions, String id) {
        return transactions.stream()
                .filter(transaction -> transaction.getReference().equals(id))
                .collect(Collectors.toList());
    }

    private List<Transaction> sortResponse(List<Transaction> transactions, String sortBy) {
        Comparator<Transaction> comparator = Comparator.comparing(Transaction::getAmount);

        if (sortBy.equalsIgnoreCase(DESC)) {
            comparator = comparator.reversed();
        }

        return transactions.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

}
