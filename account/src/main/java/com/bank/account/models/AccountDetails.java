package com.bank.account.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Класс-сущность для аккаунта
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
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

//    /**
//     * Конструктор - создание объекта {@link AccountDetails}
//     * @see AccountDetails#AccountDetails(int passportId, int accountNumber, int bankDetailsId, BigDecimal money, boolean negativeBalance, int profileId)
//     */
//    public AccountDetails(){}

//    /**
//     * Конструктор - создание объекта {@link AccountDetails}
//     * @param passportId номер паспорта
//     * @param accountNumber номер аккаунта
//     * @param bankDetailsId данные банка
//     * @param money количество денежных средств на счету
//     * @param negativeBalance метка отрицательного баланса
//     * @param profileId номер профайла
//     * @see AccountDetails#AccountDetails()
//     */
//    public AccountDetails(int passportId, int accountNumber, int bankDetailsId, BigDecimal money, boolean negativeBalance, int profileId) {
//        this.profileId = profileId;
//        this.accountNumber = accountNumber;
//        this.bankDetailsId = bankDetailsId;
//        this.money = money;
//        this.negativeBalance = negativeBalance;
//        this.passportId = passportId;
//    }
}
