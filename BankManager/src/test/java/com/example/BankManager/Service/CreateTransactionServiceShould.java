package com.example.BankManager.Service;

import com.example.BankManager.Class.Transaction;
import com.example.BankManager.Repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.example.BankManager.Constants.TRANSACTION;
import static com.example.BankManager.Constants.TRANSACTION_3;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateTransactionServiceShould {

    @InjectMocks
    private CreateTransactionService createTransactionService;
    @Mock
    private TransactionRepository repository;

    @BeforeEach
    public void setUp() {
        repository.deleteAll();
    }

    @Test
    void should_add_transaction() {

        when(repository.save(TRANSACTION)).thenReturn(TRANSACTION);

        ResponseEntity<Transaction> response = createTransactionService.add(TRANSACTION);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(TRANSACTION, response.getBody());
    }

    @Test
    void should_not_save_transaction_if_no_enough_money() {

        ResponseEntity<Transaction> response = createTransactionService.add(TRANSACTION_3);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void should_retrieve_exception() {

        ResponseEntity<Transaction> response = createTransactionService.add(null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

}