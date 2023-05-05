package com.bank.account.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountDetailsErrorResponseTest {

    @Test
    void lombokDataTest() {
        AccountDetailsErrorResponse accountDetailsErrorResponse =
                new AccountDetailsErrorResponse("test", System.currentTimeMillis());
        AccountDetailsErrorResponse accountDetailsErrorResponseForTest =
                new AccountDetailsErrorResponse("test2", System.currentTimeMillis());
        accountDetailsErrorResponseForTest.setMessage(accountDetailsErrorResponse.getMessage());
        accountDetailsErrorResponseForTest.setTimestamp(accountDetailsErrorResponse.getTimestamp());
        assertEquals(accountDetailsErrorResponse, accountDetailsErrorResponseForTest);

    }

}