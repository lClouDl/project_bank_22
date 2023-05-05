package com.bank.account.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuditErrorResponseTest {

    @Test
    void lombokDataTest() {
        AuditErrorResponse auditErrorResponse =
                new AuditErrorResponse("test", System.currentTimeMillis());
        AuditErrorResponse auditErrorResponseForTest =
                new AuditErrorResponse("test2", System.currentTimeMillis());
        auditErrorResponseForTest.setMessage(auditErrorResponse.getMessage());
        auditErrorResponseForTest.setTimestamp(auditErrorResponse.getTimestamp());
        assertEquals(auditErrorResponse, auditErrorResponseForTest);

    }

}