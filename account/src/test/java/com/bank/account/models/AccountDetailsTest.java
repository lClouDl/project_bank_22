package com.bank.account.models;

import com.bank.account.dto.AccountDetailsDTO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountDetailsTest {

    public static List<AccountDetails> accountDetailsList;

    public static List<AccountDetails> getListAccountDetails() {
        accountDetailsList = new ArrayList<>();
        accountDetailsList.add(getOneAccountDetailsWithParam(1, true));
        accountDetailsList.add(getOneAccountDetailsWithParam(2, false));
        return accountDetailsList;
    }

    public static AccountDetails getOneAccountDetailsWithParam(int valueInt, boolean valueBoolean) {
        return new AccountDetails(valueInt, valueInt, valueInt, valueInt, BigDecimal.valueOf(valueInt), valueBoolean, valueInt);
    }

    public static AccountDetailsDTO getOneAccountDetailsDTOWithParam(int valueInt, boolean valueBoolean) {
        AccountDetailsDTO accountDetailsDTO = new AccountDetailsDTO(valueInt, valueInt, valueInt, BigDecimal.valueOf(valueInt), valueBoolean, valueInt);
        AccountDetailsDTO accountDetailsDTOForTest = new AccountDetailsDTO();
        accountDetailsDTOForTest.setAccountNumber(accountDetailsDTO.getAccountNumber());
        accountDetailsDTOForTest.setBankDetailsId(accountDetailsDTO.getBankDetailsId());
        accountDetailsDTOForTest.setMoney(accountDetailsDTO.getMoney());
        accountDetailsDTOForTest.setProfileId(accountDetailsDTO.getProfileId());
        accountDetailsDTOForTest.setPassportId(accountDetailsDTO.getPassportId());
        accountDetailsDTOForTest.setNegativeBalance(accountDetailsDTO.isNegativeBalance());
        return accountDetailsDTOForTest;
    }

    @Test
    void noArgsConstructorTest () {
       AccountDetails accountDetails = new AccountDetails();
       List<AccountDetails> accountDetailsTest = getListAccountDetails();
       accountDetailsTest.add(accountDetails);
       assertEquals(accountDetailsTest.size(), 3);
    }

}