package com.example.BankManager.Service;

import com.example.BankManager.Class.Status;
import com.example.BankManager.Enum.StatusEnum;
import com.example.BankManager.Repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

import static com.example.BankManager.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetStatusServiceShould {

    public static final String INVALID_ID = "0000";
    @InjectMocks
    private GetStatusService statusService;
    @Mock
    private GetStatusService service;
    @Mock
    private TransactionRepository repository;

    @Test
    public void should_return_settled() {

        when(repository.findById(REFERENCE)).thenReturn(Optional.of(TRANSACTION_2));

        ResponseEntity<Status> response = statusService.getStatus(REFERENCE, INTERNAL);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(BigDecimal.valueOf(20), Objects.requireNonNull(response.getBody()).getAmount());
        assertEquals(SETTLED, response.getBody().getStatus());
    }

    @Test
    public void should_retrieve_pending_and_amount() {

        when(repository.findById(REFERENCE)).thenReturn(Optional.of(TRANSACTION));

        ResponseEntity<Status> response = statusService.getStatus(REFERENCE, INTERNAL);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(Objects.requireNonNull(response.getBody()).getAmount());
        assertEquals(PENDING, response.getBody().getStatus());
    }

    @Test
    public void should_return_pending_when_is_future_and_atm() {

        when(repository.findById(REFERENCE)).thenReturn(Optional.of(TRANSACTION_3));

        ResponseEntity<Status> responseEntity = statusService.getStatus(REFERENCE, ATM);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Objects.requireNonNull(responseEntity.getBody()).getStatus()).isEqualTo(PENDING);
        assertThat(responseEntity.getBody().getAmount()).isNull();
    }

    @Test
    public void should_return_future() {

        when(repository.findById(REFERENCE)).thenReturn(Optional.of(TRANSACTION_3));

        ResponseEntity<Status> responseEntity = statusService.getStatus(REFERENCE, CLIENT);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Objects.requireNonNull(responseEntity.getBody()).getStatus()).isEqualTo(FUTURE);
        assertThat(responseEntity.getBody().getAmount()).isNull();
    }


    @Test
    public void should_not_find_status() {

        when(repository.findById(INVALID_ID)).thenReturn(Optional.empty());

        ResponseEntity<Status> response = statusService.getStatus(INVALID_ID, INTERNAL);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(StatusEnum.INVALID.get(), Objects.requireNonNull(response.getBody()).getStatus());
    }

}