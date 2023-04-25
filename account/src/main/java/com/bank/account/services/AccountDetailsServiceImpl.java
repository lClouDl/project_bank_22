package com.bank.account.services;

import com.bank.account.exceptions.AccountDetailsNotFoundException;
import com.bank.account.exceptions.AccountDetailsNotFoundForUpdateException;
import com.bank.account.models.AccountDetails;
import com.bank.account.repositories.AccountDetailsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * Класс-сервис для {@link AccountDetails}
 * Реализует интерфейс {@link AccountDetailsService}
 * Описывает CRUD-операции над сущностью {@link AccountDetails}
 */
@AllArgsConstructor
@Service
@Transactional(readOnly = true)
public class AccountDetailsServiceImpl implements AccountDetailsService {

    private final AccountDetailsRepository accountDetailsRepository;

    /**{@link AccountDetailsService#findAll()}*/
    public List<AccountDetails> findAll() { return accountDetailsRepository.findAll(); }

    /**{@link AccountDetailsService#findOne(int id)}*/
    public AccountDetails findOne(int id) {
        return accountDetailsRepository.findById(id).orElseThrow(AccountDetailsNotFoundException::new);
    }

    /**{@link AccountDetailsService#save(AccountDetails accountDetails)}*/
    @Transactional
    public void save(AccountDetails accountDetails) { accountDetailsRepository.save(accountDetails); }

    /**{@link AccountDetailsService#update(int id, AccountDetails accountDetailsUpdate)}*/
    @Transactional
    public void update(int id, AccountDetails accountDetailsUpdate) {
        accountDetailsRepository.findById(id).orElseThrow(AccountDetailsNotFoundForUpdateException::new);
        accountDetailsUpdate.setId(id);
        accountDetailsRepository.save(accountDetailsUpdate);
    }

    /**{@link AccountDetailsService#delete(int id)}*/
    @Transactional
    public void delete(int id) {
        accountDetailsRepository.deleteById(id);
    }

}
