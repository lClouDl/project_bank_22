package com.bank.account.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Класс слоя DTO для сущности аккаунта
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Сущность аккаунта")
public class AccountDetailsDTO {

    /** Поле с информацией о номере паспорта */
    @Schema(description = "Номер паспорта аккаунта")
    @NotNull(message = "Номер паспорта не может быть null")
    @Min(value = 1, message = "Номер паспорта должен быть больше 0")
    private int passportId;

    /** Поле с информацией о номере аккаунта */
    @Schema(description = "Номер аккаунта")
    @Min(value = 1, message = "номер аккаунта должен быть больше 0")
    @NotNull(message = "Номер аккаунта не может быть null")
    private int accountNumber;

    /** Поле с информацией о банке для текущего аккаунта */
    @Schema(description = "Банковские данные для этого аккаунта")
    @Min(value = 1, message = "Серийный номер банка должен быть больше 0")
    @NotNull(message = "Серийный номер банка не может быть null")
    private int bankDetailsId;

    /** Количество денежных средств на счету */
    @Schema(description = "Количество денег на счету")
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
    private int profileId;
}
