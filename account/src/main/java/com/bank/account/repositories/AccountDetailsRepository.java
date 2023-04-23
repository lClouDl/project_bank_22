package com.bank.account.repositories;

import com.bank.account.models.AccountDetails;
import com.bank.account.services.AccountDetailsServiceImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Интерфейс-репозиторий для {@link AccountDetails}
 * Расширяет {@link JpaRepository}
 * Используется в {@link AccountDetailsServiceImpl}
 */
@Repository
public interface AccountDetailsRepository extends JpaRepository<AccountDetails, Integer> {
}
