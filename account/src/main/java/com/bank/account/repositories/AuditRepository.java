package com.bank.account.repositories;

import com.bank.account.models.Audit;
import com.bank.account.services.AuditServiceImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Интерфейс-репозиторий для {@link Audit}
 * Расширяет {@link JpaRepository}
 * Используется в {@link AuditServiceImpl}
 */
@Repository
public interface AuditRepository extends JpaRepository<Audit, Integer> {
}
