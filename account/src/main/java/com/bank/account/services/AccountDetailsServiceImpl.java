package com.bank.account.services;

import com.bank.account.exceptions.AccountDetailsNotFoundException;
import com.bank.account.exceptions.AccountDetailsNotFoundForUpdateException;
import com.bank.account.models.AccountDetails;
import com.bank.account.repositories.AccountDetailsRepository;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Класс-сервис для {@link AccountDetails}
 * Реализует интерфейс {@link AccountDetailsService}
 * Описывает CRUD-операции над сущностью {@link AccountDetails}
 */
//@AllArgsConstructor
@Service
@Transactional(readOnly = true)
public class AccountDetailsServiceImpl implements AccountDetailsService {

    private final AccountDetailsRepository accountDetailsRepository;
    private AtomicInteger counterOfNegativeBalances;

    public AccountDetailsServiceImpl(AccountDetailsRepository accountDetailsRepository, MeterRegistry meterRegistry) {
        this.accountDetailsRepository = accountDetailsRepository;
        counterOfNegativeBalances = new AtomicInteger();
        meterRegistry.gauge("counterOfNegativeBalances", counterOfNegativeBalances);
    }

    /**{@link AccountDetailsService#findAll()}*/
    public List<AccountDetails> findAll() {
        setCounterOfNegativeBalances();
        return accountDetailsRepository.findAll();
    }



    /**{@link AccountDetailsService#findOne(int id)}*/
    public AccountDetails findOne(int id) {
        return accountDetailsRepository.findById(id).orElseThrow(AccountDetailsNotFoundException::new);
    }

    /**{@link AccountDetailsService#save(AccountDetails accountDetails)}*/
    @Transactional
    public void save(AccountDetails accountDetails) {
        accountDetailsRepository.save(accountDetails);
        setCounterOfNegativeBalances();
    }

    /**{@link AccountDetailsService#update(int id, AccountDetails accountDetailsUpdate)}*/
    @Transactional
    public void update(int id, AccountDetails accountDetailsUpdate) {
        accountDetailsRepository.findById(id).orElseThrow(AccountDetailsNotFoundForUpdateException::new);
        accountDetailsUpdate.setId(id);
        accountDetailsRepository.save(accountDetailsUpdate);
        setCounterOfNegativeBalances();
    }

    /**{@link AccountDetailsService#delete(int id)}*/
    @Transactional
    public void delete(int id) {
        accountDetailsRepository.deleteById(id);
        setCounterOfNegativeBalances();
    }

    private void setCounterOfNegativeBalances() {
        List<AccountDetails> accountDetailsList = accountDetailsRepository.findAll();
        counterOfNegativeBalances.set(0);
        for (AccountDetails accountDetail : accountDetailsList) {
            counterOfNegativeBalances.set((accountDetail.isNegativeBalance()) ? (counterOfNegativeBalances.get()+1) : counterOfNegativeBalances.get());
        }
    }

}
