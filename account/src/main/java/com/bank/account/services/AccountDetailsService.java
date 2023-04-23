package com.bank.account.services;

import com.bank.account.models.AccountDetails;
import java.util.List;

/**
 * Интерфейс для {@link AccountDetails}
 * Реализуется в {@link AccountDetailsServiceImpl}
 */
public interface AccountDetailsService {

    /**
     * Метод, который предоставляет данные обо всех аккаунтах
     * @return возвращает список объектов класса {@link AccountDetails}
     */
    List<AccountDetails> findAll();

    /**
     * Метод, который предоставляет данные об одном аккаунте, с указанным id
     * @param id идентификационный номер аккаунта, который требуется найти
     * @return возвращает объект класса {@link AccountDetails}
     */
    AccountDetails findOne(int id);

    /**
     * Метод, который сохраняет в базе данных новый аккаунт
     * @param accountDetails аккаунт({@link AccountDetails}), который требуется сохранить в базе данных
     */
    void save(AccountDetails accountDetails);

    /**
     * Метод, который изменяет и сохраняет в базе данных измененный аккаунт
     * @param id идентификационный номер аккаунта, который следует заменить на измененный аккаунт
     * @param accountDetailsUpdate измененный аккаунт({@link AccountDetails}), который требуется сохранить в базе данных
     */
    void update(int id, AccountDetails accountDetailsUpdate);

    /**
     * Метод, который удаляет аккаунт, с указанным id
     * @param id идентификационный номер аккаунта, который требуется удалить
     */
    void delete(int id);
}
