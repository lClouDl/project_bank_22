package com.bank.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Класс слоя DTO для сущности аудита
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Сущность записей аудирования")
public class AuditDTO {

    /** Идентификационный номер записи аудирования */
    @Schema(description = "Идентификационный номер записи аудирования")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /** Тип сущности, для которой создается запись аудирования */
    @Schema(description = "Тип сущности для которой создана запись аудирования")
    @Column(name = "entity_type")
    @NotNull(message = "Не указан тип измененной сущности")
    @NotBlank(message = "Не указан тип измененной сущности")
    @Size(max = 40, message = "Название типа сущности не должно превышать 40 символов")
    private String entityType;

    /** Тип операции над сущностью, которая инициировала запись аудирования */
    @Schema(description = "Тип операции, спровоцировавший запись аудирования")
    @Column(name = "operation_type")
    @NotNull(message = "Не указан тип операции произведенный над сущностью")
    @NotBlank(message = "Не указан тип операции произведенный над сущностью")
    @Size(max = 255, message = "Название типа операции не должно превышать 255 символов")
    private String operationType;

    /** Данные о том, кто совершил добавление новой сущности в бд, которое инициировало запись аудирования */
    @Schema(description = "Кто добавил новые данные, вызвавшие запись аудирования")
    @Column(name = "created_by")
    @NotNull(message = "Не указан инициатор изменения")
    @NotBlank(message = "Не указан инициатор изменения")
    @Size(max = 255, message = "Имя инициатора изменения не должно превышать 255 символов")
    private String createdBy;

    /** Данные о том, кто совершил изменение сущности, которое инициировало запись аудирования */
    @Schema(description = "Кто изменил данные, вызвавшие запись аудирования")
    @Column(name = "modified_by")
    @Size(max = 255, message = "Имя инициатора изменения не должно превышать 255 символов")
    private String modifiedBy;

    /** Данные о том, когда совершили добавление новой сущности в бд, которое инициировало запись аудирования */
    @Schema(description = "Когда добавили новые данные, вызвавшие запись аудирования")
    @Column(name = "created_at")
    @NotNull(message = "Не указана дата создания")
    private long createdAt;

    /** Данные о том, когда совершили изменение сущности, которое инициировало запись аудирования */
    @Schema(description = "Когда изменили данные, вызвавшие запись аудирования")
    @Column(name = "modified_at")
    private long modifiedAt;

    /** Данные о том, как теперь выглядит добавленная/измененная сущность */
    @Schema(description = "Измененные данные, вызвавшие запись аудирования")
    @Column(name = "new_entity_json")
    private String newEntityJson;

    /** Данные о том, как выглядела сущность до изменения */
    @Schema(description = "Данные до изменения, вызвавшие запись аудирования")
    @Column(name = "entity_json")
    @NotNull(message = "Не указана созданная сущность")
    @NotBlank(message = "Не указана созданная сущность")
    private String entityJson;
}
