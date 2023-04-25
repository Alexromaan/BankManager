package com.example.BankManager.Controller;

import com.example.BankManager.Class.Status;
import com.example.BankManager.Class.Transaction;
import com.example.BankManager.Service.CreateTransactionService;
import com.example.BankManager.Service.FindTransactionService;
import com.example.BankManager.Service.GetStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class TransactionController {
    @Autowired
    CreateTransactionService createTransactionService;
    @Autowired
    FindTransactionService findTransactionService;
    @Autowired
    GetStatusService statusService;

    @PostMapping("/create")
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
        return createTransactionService.add(transaction);
    }

    @PostMapping(value = "/find")
    public ResponseEntity<List<Transaction>> findTransaction(
            @RequestBody(required = false) String id,
            @RequestBody(required = false) String sortBy) {

        return findTransactionService.find(id, sortBy);
    }

    @PostMapping("/get-status")
    public ResponseEntity<Status> getStatus(
            @RequestBody String reference,
            @RequestBody(required = false) String channel) {

        return statusService.getStatus(reference, channel);
    }
}
