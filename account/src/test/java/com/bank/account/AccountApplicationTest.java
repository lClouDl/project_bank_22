package com.bank.account;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountApplicationTest {

    @Test
    void modelMapperTest() {
        AccountApplication accountApplication = new AccountApplication();
        assertNotNull(accountApplication.modelMapper());
    }
}