package com.bank.account.controllers;

import com.bank.account.dto.AccountDetailsDTO;
import com.bank.account.exceptions.AccountDetailsNotCreatedException;
import com.bank.account.exceptions.AccountDetailsNotFoundException;
import com.bank.account.exceptions.AccountDetailsNotUpdateException;
import com.bank.account.models.AccountDetails;
import com.bank.account.models.AccountDetailsTest;
import com.bank.account.services.AccountDetailsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import java.util.List;
import java.util.NoSuchElementException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountDetailsRestControllerTest {

    public static final int ID = 10;

    @Mock
    private AccountDetailsService accountDetailsService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private  BindingResult bindingResult;

    @InjectMocks
    private  AccountDetailsRestController accountDetailsRestController;

    @Test
    void getAccountDetailsAllSuccess() {
        doReturn(AccountDetailsTest.getListAccountDetails()).when(accountDetailsService).findAll();
        assertEquals(accountDetailsRestController.getAccountDetails().size(), 2);
        assertNotNull(accountDetailsRestController.getAccountDetails());
        assertThat(accountDetailsRestController.getAccountDetails()).isNotEmpty();
    }

    @Test
    void getAccountDetailsAllFail() {
        doReturn(null).when(accountDetailsService).findAll();
        assertThrows(NullPointerException.class, () -> accountDetailsRestController.getAccountDetails());
    }


    @Test
    void getAccountDetailsByIdSuccess() {
        AccountDetails accountDetails = AccountDetailsTest.getOneAccountDetailsWithParam(ID, false);
        AccountDetailsDTO accountDetailsDTO = AccountDetailsTest.getOneAccountDetailsDTOWithParam(ID, false);
        doReturn(accountDetails).when(accountDetailsService).findOne(ID);
        doReturn(accountDetailsDTO).when(modelMapper).map(accountDetails, AccountDetailsDTO.class);
        assertFalse(accountDetailsRestController.getAccountDetails(ID).isNegativeBalance());
        assertEquals(accountDetailsRestController.getAccountDetails(ID).getBankDetailsId(), ID);
    }

    @Test
    void getAccountDetailsByIdFail() {
        doThrow(AccountDetailsNotFoundException.class).when(accountDetailsService).findOne(ID);
        assertThrows(AccountDetailsNotFoundException.class, () -> accountDetailsRestController.getAccountDetails(ID));
    }

    @Test
    void createSuccess() {
        AccountDetails accountDetails = AccountDetailsTest.getOneAccountDetailsWithParam(ID, false);
        AccountDetailsDTO accountDetailsDTO = AccountDetailsTest.getOneAccountDetailsDTOWithParam(ID, false);
        assertDoesNotThrow(() -> accountDetailsService.save(accountDetails));
        doReturn(save(accountDetails)).when(modelMapper).map(accountDetailsDTO, AccountDetails.class);
        assertEquals(accountDetailsRestController.create(accountDetailsDTO, bindingResult).getBody(), HttpStatus.OK);
    }

    @Test
    void createFail() {
        AccountDetails accountDetails = AccountDetailsTest.getOneAccountDetailsWithParam(ID, false);
        AccountDetailsDTO accountDetailsDTO = AccountDetailsTest.getOneAccountDetailsDTOWithParam(ID, false);
        doReturn(save(accountDetails)).when(modelMapper).map(accountDetailsDTO, AccountDetails.class);
        doThrow(AccountDetailsNotCreatedException.class).when(accountDetailsService).save(accountDetails);
        assertThrows(AccountDetailsNotCreatedException.class, () -> accountDetailsRestController.create(accountDetailsDTO, bindingResult));
    }

    @Test
    void createFailBindingResult() {
        AccountDetailsDTO accountDetailsDTO = AccountDetailsTest.getOneAccountDetailsDTOWithParam(ID, false);
        lenient().when(bindingResult.hasErrors()).thenReturn(true);
        assertThrows(AccountDetailsNotCreatedException.class, () -> accountDetailsRestController.create(accountDetailsDTO, bindingResult));
    }

    private static AccountDetails save(AccountDetails accountDetails) {
        AccountDetailsTest.getListAccountDetails().add(accountDetails);
        return accountDetails;
    }


    @Test
    void updateSuccess() {
        save(AccountDetailsTest.getOneAccountDetailsWithParam(ID, false));
        AccountDetails accountDetailsForUpdate = AccountDetailsTest.getOneAccountDetailsWithParam(ID, true);
        AccountDetailsDTO accountDetailsForUpdateDTO = AccountDetailsTest.getOneAccountDetailsDTOWithParam(ID, true);
        doReturn(update(ID, accountDetailsForUpdate)).when(modelMapper).map(accountDetailsForUpdateDTO, AccountDetails.class);
        doThrow(AccountDetailsNotUpdateException.class).when(accountDetailsService).update(ID, accountDetailsForUpdate);
        assertThrows(AccountDetailsNotUpdateException.class, () -> accountDetailsRestController.update(accountDetailsForUpdateDTO, bindingResult, ID));
    }

    @Test
    void updateFail() {
        save(AccountDetailsTest.getOneAccountDetailsWithParam(ID, false));
        AccountDetails accountDetailsForUpdate = AccountDetailsTest.getOneAccountDetailsWithParam(ID+1, true);
        AccountDetailsDTO accountDetailsForUpdateDTO = AccountDetailsTest.getOneAccountDetailsDTOWithParam(ID+1, true);
        doReturn(update(ID+1, accountDetailsForUpdate)).when(modelMapper).map(accountDetailsForUpdateDTO, AccountDetails.class);
        accountDetailsRestController.update(accountDetailsForUpdateDTO, bindingResult, ID+1);
        List<AccountDetails> accountDetailsList = AccountDetailsTest.accountDetailsList;
        assertFalse(accountDetailsList.get(accountDetailsList.size()-1).isNegativeBalance());
    }

    @Test
    void updateFailBindingResult() {
        AccountDetailsDTO accountDetailsDTO = AccountDetailsTest.getOneAccountDetailsDTOWithParam(ID, false);
        lenient().when(bindingResult.hasErrors()).thenReturn(true);
        assertThrows(AccountDetailsNotUpdateException.class, () -> accountDetailsRestController.update(accountDetailsDTO, bindingResult, ID));
    }


    private static AccountDetails update(int id, AccountDetails accountDetailsForUpdate) {
        for (AccountDetails someAccDetails : AccountDetailsTest.accountDetailsList) {
            if (someAccDetails.getId() == id) {
                someAccDetails.setBankDetailsId(accountDetailsForUpdate.getBankDetailsId());
                someAccDetails.setPassportId(accountDetailsForUpdate.getPassportId());
                someAccDetails.setAccountNumber(accountDetailsForUpdate.getAccountNumber());
                someAccDetails.setMoney(accountDetailsForUpdate.getMoney());
                someAccDetails.setNegativeBalance(accountDetailsForUpdate.isNegativeBalance());
                someAccDetails.setProfileId(accountDetailsForUpdate.getProfileId());
            }
        }
        return accountDetailsForUpdate;
    }


    @Test()
    void deleteSuccess() {
        save(AccountDetailsTest.getOneAccountDetailsWithParam(ID, true));
        int sizeBeforeDelete = AccountDetailsTest.accountDetailsList.size();
        lenient().when(accountDetailsService.findOne(ID)).thenReturn(AccountDetailsTest.getListAccountDetails().get(0));
        accountDetailsRestController.delete(ID);
        int sizeAfterDelete = AccountDetailsTest.accountDetailsList.size();
        assertThat(sizeAfterDelete).isLessThan(sizeBeforeDelete);
    }

    @Test
    void deleteFail() {
        doThrow(NoSuchElementException.class).when(accountDetailsService).delete(ID);
        assertThrows(NoSuchElementException.class, () -> accountDetailsRestController.delete(ID));
    }
}