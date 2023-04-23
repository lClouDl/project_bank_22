package com.bank.account.models;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RevisionEntity;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Класс-сущность для аккаунта
 */
@Entity
@EqualsAndHashCode
@ToString
@Table(schema = "account", name="account_details")
@Audited
@AuditTable(value = "account_aud", schema = "account")
public class AccountDetails {

    /** Поле с идентификационным номером аккаунта */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /** Поле с информацией о номере паспорта */
    @Column(name = "passport_id")
    @NotNull(message = "Номер паспорта не может быть null")
    @Min(value = 1, message = "Номер паспорта должен быть больше 0")
    private int passportId;

    /** Поле с информацией о номере аккаунта */
    @Column(name = "account_number")
    @Min(value = 1, message = "номер аккаунта должен быть больше 0")
    @NotNull(message = "Номер аккаунта не может быть null")
    private int accountNumber;

    /** Поле с информацией о банке для текущего аккаунта */
    @Column(name = "bank_details_id")
    @Min(value = 1, message = "Серийный номер банка должен быть больше 0")
    @NotNull(message = "Серийный номер банка не может быть null")
    private int bankDetailsId;

    /** Количество денежных средств на счету */
    @Column(name = "money")
    @NotNull(message = "Баланс не может быть null")
    private BigDecimal money;

    /** Метка отрицательного баланса (если true, то баланс отрицательный) */
    @Column(name = "negative_balance")
    @NotNull(message = "Метка отрицательного баланса не может быть null")
    private boolean negativeBalance;

    /** Поле с информацией о номере профиля */
    @Column(name = "profile_id")
    @Min(value = 1, message = "Номер профайла должен быть больше 0")
    @NotNull(message = "Номер профайла не может быть null")
    private int profileId;

    /**
     * Конструктор - создание объекта {@link AccountDetails}
     * @see AccountDetails#AccountDetails(int passportId, int accountNumber, int bankDetailsId, BigDecimal money, boolean negativeBalance, int profileId)
     */
    public AccountDetails(){}

    /**
     * Конструктор - создание объекта {@link AccountDetails}
     * @param passportId номер паспорта
     * @param accountNumber номер аккаунта
     * @param bankDetailsId данные банка
     * @param money количество денежных средств на счету
     * @param negativeBalance метка отрицательного баланса
     * @param profileId номер профайла
     * @see AccountDetails#AccountDetails()
     */
    public AccountDetails(int passportId, int accountNumber, int bankDetailsId, BigDecimal money, boolean negativeBalance, int profileId) {
        this.profileId = profileId;
        this.accountNumber = accountNumber;
        this.bankDetailsId = bankDetailsId;
        this.money = money;
        this.negativeBalance = negativeBalance;
        this.passportId = passportId;
    }

    /**
     * Метод, который возвращает идентификационный номер аккаунта
     * @return идентификационный номер аккаунта
     */
    public int getId() {
        return id;
    }

    /**
     * Метод, который позволяет установить идентификационный номер аккаунта
     * @param id идентификационный номер аккаунта
     */
    public void setId(int id) { this.id = id; }

    /**
     * Метод, который возвращает номер паспорта
     * @return номер паспорта
     */
    public int getPassport() {
        return passportId;
    }

    /**
     * Метод, который позволяет установить номер паспорта
     * @param passportId  номер паспорта, который будет присвоен текущему аккаунту
     */
    public void setPassport(int passportId) {
        this.passportId = passportId;
    }

    /**
     * Метод, который возвращает номер аккаунта
     * @return номер аккаунта
     */
    public int getAccountNumber() {
        return accountNumber;
    }

    /**
     * Метод, который позволяет установить номер аккаунта
     * @param accountNumber  номер аккаунта, который будет присвоен текущему аккаунту
     */
    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    /**
     * Метод, который возвращает данные банка, актуальные для текущего аккаунта
     * @return возвращает данные банка в виде объекта класса BankDetails
     */
    public int getBankDetails() {
        return bankDetailsId;
    }

    /**
     * Метод, который позволяет установить данные банка
     * @param bankDetailsId  данные банка, которые будут присвоены текущему аккаунту
     */
    public void setBankDetails(int bankDetailsId) {
        this.bankDetailsId = bankDetailsId;
    }

    /**
     * Метод, который возвращает остаток денежных средств на счете текущего аккаунта
     * @return возвращает остаток
     */
    public BigDecimal getMoney() {
        return money;
    }

    /**
     * Метод, который позволяет установить остаток денежных средств на счете текущего аккаунта
     * @param money количество денежных средств
     */
    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    /**
     * Метод, который возвращает метку отрицательного баланса (если true, то баланс отрицательный)
     * @return возвращает метку отрицательного баланса
     */
    public boolean isNegativeBalance() {
        return negativeBalance;
    }

    /**
     * Метод, который позволяет установить метку отрицательного баланса (если true, то баланс отрицательный)
     * @param negativeBalance метка отрицательного баланса (если true, то баланс отрицательный)
     */
    public void setNegativeBalance(boolean negativeBalance) {
        this.negativeBalance = negativeBalance;
    }

    /**
     * Метод, который возвращает номер профиля, актуальный для текущего аккаунта
     * @return возвращает номер текущего профиля
     */
    public int getProfile() {
        return profileId;
    }

    /**
     * Метод, который позволяет установить номер профиля, актуального для текущего аккаунта
     * @param profileId номер профиля
     */
    public void setProfile(int profileId) {
        this.profileId = profileId;
    }
}
