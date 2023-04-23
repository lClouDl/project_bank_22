package com.bank.account.exceptions;

/** Класс-исключение, необходим для информирования о том, что при добавлении новой сущности Audit произошла ошибка */
public class AuditNotCreatedException extends RuntimeException {
    public AuditNotCreatedException(String msg) {
        super(msg);
    }
}
