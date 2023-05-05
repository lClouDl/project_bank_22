package com.bank.account.services;

import com.bank.account.exceptions.AccountDetailsNotFoundException;
import com.bank.account.models.AccountDetails;
import com.bank.account.models.AccountDetailsTest;
import com.bank.account.repositories.AccountDetailsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountDetailsServiceImplTest {

    public static final int ID = 10;

    @Mock
    private AccountDetailsRepository accountDetailsRepository;

    @InjectMocks
    private AccountDetailsServiceImpl accountDetailsServiceImpl;

    @Test
    void findAll() {
        doReturn(AccountDetailsTest.getListAccountDetails()).when(accountDetailsRepository).findAll();
        List<AccountDetails> accountDetailsList = accountDetailsServiceImpl.findAll();
        assertThat(accountDetailsList).isNotEmpty();
        assertEquals(accountDetailsList.size(), 2);
    }


    @Test
    void findOneSuccess() {
        Optional<AccountDetails> accountDetailsOptional = Optional.of(AccountDetailsTest
                .getOneAccountDetailsWithParam(ID, true));
        doReturn(accountDetailsOptional).when(accountDetailsRepository).findById(ID);
        AccountDetails accountDetailsTest = accountDetailsServiceImpl.findOne(ID);
        assertThat(accountDetailsTest).isNotNull();
        assertEquals(accountDetailsTest, accountDetailsOptional.get());
    }

    @Test
    void findOneFail() {
        doReturn(Optional.empty()).when(accountDetailsRepository).findById(ID);
        assertThrows(AccountDetailsNotFoundException.class, () -> accountDetailsServiceImpl.findOne(ID));
    }

    @Test
    void saveSuccess() {
        AccountDetails accountDetailsForSave = AccountDetailsTest.getOneAccountDetailsWithParam(ID, true);
        AccountDetails accountDetailsForCheck = AccountDetailsTest.getOneAccountDetailsWithParam(ID+1, true);
        doReturn(save(accountDetailsForSave)).when(accountDetailsRepository).save(accountDetailsForCheck);
        accountDetailsServiceImpl.save(accountDetailsForCheck);
        List<AccountDetails> accountDetailsList = AccountDetailsTest.accountDetailsList;
        assertEquals(accountDetailsList.get(accountDetailsList.size()-1).getId(), ID);
    }

    private static AccountDetails save(AccountDetails accountDetails) {
        AccountDetailsTest.getListAccountDetails().add(accountDetails);
        return accountDetails;
    }

    @Test
    void updateSuccess() {
        save(AccountDetailsTest.getOneAccountDetailsWithParam(ID, false));
        AccountDetails accountDetailsForUpdate = AccountDetailsTest.getOneAccountDetailsWithParam(ID, true);
        doReturn(Optional.of(accountDetailsForUpdate)).when(accountDetailsRepository).findById(ID);
        doReturn(update(ID, accountDetailsForUpdate)).when(accountDetailsRepository).save(accountDetailsForUpdate);
        accountDetailsServiceImpl.update(ID, accountDetailsForUpdate);
        List<AccountDetails> accountDetailsList = AccountDetailsTest.accountDetailsList;
        assertTrue(accountDetailsList.get(accountDetailsList.size()-1).isNegativeBalance());
    }

    @Test
    void updateFail() {
        save(AccountDetailsTest.getOneAccountDetailsWithParam(ID, false));
        AccountDetails accountDetailsForUpdate = AccountDetailsTest.getOneAccountDetailsWithParam(ID+1, true);
        doReturn(Optional.of(accountDetailsForUpdate)).when(accountDetailsRepository).findById(ID);
        doReturn(update(ID+1, accountDetailsForUpdate)).when(accountDetailsRepository).save(accountDetailsForUpdate);
        accountDetailsServiceImpl.update(ID, accountDetailsForUpdate);
        List<AccountDetails> accountDetailsList = AccountDetailsTest.accountDetailsList;
        assertFalse(accountDetailsList.get(accountDetailsList.size()-1).isNegativeBalance());
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
        lenient().when(accountDetailsRepository.findById(ID)).thenReturn(Optional.of(AccountDetailsTest.getListAccountDetails().get(0)));
        accountDetailsServiceImpl.delete(ID);
        int sizeAfterDelete = AccountDetailsTest.accountDetailsList.size();
        assertThat(sizeAfterDelete).isLessThan(sizeBeforeDelete);
    }

    @Test
    void deleteFail() {
        doThrow(NoSuchElementException.class).when(accountDetailsRepository).deleteById(ID);
        assertThrows(NoSuchElementException.class, () -> accountDetailsServiceImpl.delete(ID));
    }
}