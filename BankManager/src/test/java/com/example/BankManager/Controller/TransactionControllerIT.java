package com.example.BankManager.Controller;

import com.example.BankManager.Class.Status;
import com.example.BankManager.Class.Transaction;
import com.example.BankManager.Service.CreateTransactionService;
import com.example.BankManager.Service.FindTransactionService;
import com.example.BankManager.Service.GetStatusService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.example.BankManager.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TransactionControllerIT {
    @Mock
    private CreateTransactionService createTransactionService;
    @Mock
    FindTransactionService findTransactionService;
    @Mock
    GetStatusService statusService;

    private TransactionController controller;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new TransactionController();
        controller.createTransactionService = createTransactionService;
        controller.findTransactionService = findTransactionService;
        controller.statusService = statusService;

    }

    @Test
    public void create_transaction() {

        when(createTransactionService.add(TRANSACTION)).thenReturn(new ResponseEntity<>(TRANSACTION, HttpStatus.CREATED));

        ResponseEntity<Transaction> response = controller.createTransaction(TRANSACTION);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(TRANSACTION, response.getBody());
        verify(createTransactionService, times(1)).add(TRANSACTION);
    }

    @Test
    public void find_all_transactions() {
        when(findTransactionService.find(null, null)).thenReturn(new ResponseEntity<>(TRANSACTION_LIST, HttpStatus.OK));

        ResponseEntity<List<Transaction>> response = controller.findTransaction(null, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(TRANSACTION_LIST, response.getBody());
        verify(findTransactionService, times(1)).find(null, null);
    }

    @Test
    public void retrieve_status_with_reference() {

        when(statusService.getStatus(REFERENCE, null)).thenReturn(new ResponseEntity<>(STATUS, HttpStatus.OK));

        ResponseEntity<Status> response = controller.getStatus(REFERENCE, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(STATUS, response.getBody());
        verify(statusService, times(1)).getStatus(REFERENCE, null);
    }
}
