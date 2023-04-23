package com.bank.account.exceptions;

/** Класс-исключение, необходим для информирования о том, что при добавлении новой сущности AccountDetails произошла ошибка */
public class AccountDetailsNotCreatedException extends RuntimeException {

    /** Конструктор - создание нового объекта
     * @see AccountDetailsNotCreatedException
     */
    public AccountDetailsNotCreatedException(String msg) {
        super(msg);
    }
}
