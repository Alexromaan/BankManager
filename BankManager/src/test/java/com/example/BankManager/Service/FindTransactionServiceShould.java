package com.example.BankManager.Service;

import com.example.BankManager.Class.Transaction;
import com.example.BankManager.Repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;

import static com.example.BankManager.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindTransactionServiceShould {

    @InjectMocks
    private FindTransactionService findTransactionService;

    @Mock
    private TransactionRepository repository;

    @Test
    void should_find_all_transactions() {

        when(repository.findAll()).thenReturn(TRANSACTION_LIST);

        ResponseEntity<List<Transaction>> response = findTransactionService.find(null, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(TRANSACTION_LIST, response.getBody());
    }

    @Test
    void should_find_by_Id() {

        when(repository.findAll()).thenReturn(TRANSACTION_LIST);

        ResponseEntity<List<Transaction>> response = findTransactionService.find(REFERENCE, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, Objects.requireNonNull(response.getBody()).size());
        assertEquals(REFERENCE, response.getBody().get(0).getReference());
    }

    @Test
    void should_sort_all() {

        when(repository.findAll()).thenReturn(TRANSACTION_LIST);

        ResponseEntity<List<Transaction>> response = findTransactionService.find(null, DESC);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, Objects.requireNonNull(response.getBody()).size());
        assertEquals(REFERENCE, response.getBody().get(1).getReference());
        assertEquals(REFERENCE_2, response.getBody().get(0).getReference());
    }

    @Test
    void should_not_find_transactions() {

        ResponseEntity<List<Transaction>> response = findTransactionService.find("0987", null);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


}