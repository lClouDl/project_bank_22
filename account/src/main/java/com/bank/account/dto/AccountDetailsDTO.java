package com.bank.account.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Класс слоя DTO для сущности аккаунта
 */
@Schema(description = "Сущность аккаунта")
public class AccountDetailsDTO {

    /** Поле с информацией о номере паспорта */
    @Schema(description = "Номер паспорта аккаунта")
    @NotNull(message = "Номер паспорта не может быть null")
    @Min(value = 1, message = "Номер паспорта должен быть больше 0")
    private int passport;

    /** Поле с информацией о номере аккаунта */
    @Schema(description = "Номер аккаунта")
    @Min(value = 1, message = "номер аккаунта должен быть больше 0")
    @NotNull(message = "Номер аккаунта не может быть null")
    private int accountNumber;

    /** Поле с информацией о банке для текущего аккаунта */
    @Schema(description = "Банковские данные для этого аккаунта")
    @Min(value = 1, message = "Серийный номер банка должен быть больше 0")
    @NotNull(message = "Серийный номер банка не может быть null")
    private int bankDetails;

    /** Колличество денежных средств на счету */
    @Schema(description = "Колличество денег на счету")
    @NotNull(message = "Баланс не может быть null")
    private BigDecimal money;

    /** Метка отрицательного баланса (если true, то баланс отрицательный) */
    @Schema(description = "Метка отрицательного баланса")
    @NotNull(message = "Метка отрицательного баланса не может быть null")
    private boolean negativeBalance;

    /** Поле с информацией о номере профиля */
    @Schema(description = "Номер профайла, где задействован этот аккаунт")
    @Min(value = 1, message = "Номер профайла должен быть больше 0")
    @NotNull(message = "Номер профайла не может быть null")
    private int profile;

    /**
     * Метод, который возвращает номер паспорта
     * @return номер паспорта
     */
    public int getPassport() {
        return passport;
    }

    /**
     * Метод, который позволяет установить номер паспорта
     * @param passportId  номер паспорта, который будет присвоен текущему аккаунту
     */
    public void setPassport(int passportId) {
        this.passport = passportId;
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
        return bankDetails;
    }

    /**
     * Метод, который позволяет установить данные банка
     * @param bankDetailsId  данные банка, которые будут присвоены текущему аккаунту
     */
    public void setBankDetails(int bankDetailsId) {
        this.bankDetails = bankDetailsId;
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
     * @param money колличество денежных средств
     */
    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    /**
     * Метод, который возвращает метку отрицательного баланца (если true, то баланс отрицательный)
     * @return возвращает метку отрицательного баланса
     */
    public boolean isNegativeBalance() {
        return negativeBalance;
    }

    /**
     * Метод, который позволяет установить метку отрицательного баланца (если true, то баланс отрицательный)
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
        return profile;
    }

    /**
     * Метод, который позволяет установить номер профиля, актуального для текущего аккаунта
     * @param profileId номер профиля
     */
    public void setProfile(int profileId) {
        this.profile = profileId;
    }
}
