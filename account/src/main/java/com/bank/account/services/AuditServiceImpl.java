package com.bank.account.services;

import com.bank.account.exceptions.AuditNotFoundException;
import com.bank.account.models.Audit;
import com.bank.account.repositories.AuditRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Класс-сервис для {@link Audit}
 * Реализует интерфейс {@link AuditService}
 * Описывает CRUD-операции над сущностью {@link Audit}
 */
@Service
@Transactional(readOnly = true)
public class AuditServiceImpl implements AuditService {

    private final AuditRepository auditRepository;

    /**
     * Конструктор - создание объекта {@link AuditServiceImpl}
     * Так же необходим для внедрения зависимости (репозитория)
     * @param auditRepository интерфейс репозитория для работы с {@link org.springframework.data.jpa.repository.JpaRepository}
     */
    public AuditServiceImpl(AuditRepository auditRepository) {
        this.auditRepository = auditRepository;
    }

    /**{@link AuditService#findAll()}*/
    @Override
    public List<Audit> findAll() {
        return auditRepository.findAll();
    }

    /**{@link AuditService#findOne(int id)}*/
    @Override
    public Audit findOne(int id) {
        return auditRepository.findById(id).orElseThrow(AuditNotFoundException::new);
    }

    /**{@link AuditService#save(Audit audit)}*/
    @Override
    @Transactional
    public void save(Audit audit) {
        auditRepository.save(audit);
    }

    /**{@link AuditService#update(int id, Audit auditUpdate)}*/
    @Override
    @Transactional
    public void update(int id, Audit auditUpdate) {
        auditUpdate.setId(id);
        auditRepository.save(auditUpdate);
    }

    /**{@link AuditService#delete(int id)}*/
    @Override
    @Transactional
    public void delete(int id) {
        auditRepository.deleteById(id);
    }
}
