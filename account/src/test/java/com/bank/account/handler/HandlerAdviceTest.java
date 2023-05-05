package com.bank.account.handler;

import com.bank.account.exceptions.*;
import com.bank.account.util.AccountDetailsErrorResponse;
import com.bank.account.util.AuditErrorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import static org.assertj.core.api.Assertions.assertThat;

class HandlerAdviceTest {


    HandlerAdvice handlerAdvice;

    @BeforeEach
    void initHandler() {
        handlerAdvice = new HandlerAdvice();
    }

    @Test
    void handlerExceptionForAccountDetailsNotFoundException() {
        final AccountDetailsNotFoundException notFoundException = new AccountDetailsNotFoundException();
        ResponseEntity<AccountDetailsErrorResponse> response =
                handlerAdvice.handleException(notFoundException);
        assertThat(response).isNotNull();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).isEqualTo("Аккаунт с таким id не найден!");
    }

    @Test
    void handlerExceptionForAccountDetailsNotCreatedException() {
        final AccountDetailsNotCreatedException notCreatedException = new AccountDetailsNotCreatedException("test");
        ResponseEntity<AccountDetailsErrorResponse> response =
                handlerAdvice.handleException(notCreatedException);
        assertThat(response).isNotNull();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).isEqualTo("test");
    }

    @Test
    void handlerExceptionForAccountDetailsNotUpdateException() {
        final AccountDetailsNotUpdateException notUpdatedException = new AccountDetailsNotUpdateException("test");
        ResponseEntity<AccountDetailsErrorResponse> response =
                handlerAdvice.handleException(notUpdatedException);
        assertThat(response).isNotNull();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).isEqualTo("test");
    }

    @Test
    void handlerExceptionForAccountDetailsNotFoundForUpdateException() {
        final AccountDetailsNotFoundForUpdateException notFoundForUpdateException = new AccountDetailsNotFoundForUpdateException();
        ResponseEntity<AccountDetailsErrorResponse> response =
                handlerAdvice.handleException(notFoundForUpdateException);
        assertThat(response).isNotNull();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).isEqualTo("При обновлении, аккаунт с таким id не найден!");
    }

    @Test
    void handlerExceptionForAuditNotFoundException() {
        final AuditNotFoundException auditNotFoundException = new AuditNotFoundException();
        ResponseEntity<AuditErrorResponse> response =
                handlerAdvice.handleException(auditNotFoundException);
        assertThat(response).isNotNull();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).isEqualTo("Запись аудирования с таким id не найдена!");
    }

    @Test
    void handlerExceptionForAuditNotCreatedException() {
        final AuditNotCreatedException auditNotCreatedException = new AuditNotCreatedException("test");
        ResponseEntity<AuditErrorResponse> response =
                handlerAdvice.handleException(auditNotCreatedException);
        assertThat(response).isNotNull();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).isEqualTo("test");
    }

    @Test
    void handlerExceptionForAuditNotUpdateException() {
        final AuditNotUpdateException auditNotUpdateException = new AuditNotUpdateException("test");
        ResponseEntity<AuditErrorResponse> response =
                handlerAdvice.handleException(auditNotUpdateException);
        assertThat(response).isNotNull();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).isEqualTo("test");
    }
}