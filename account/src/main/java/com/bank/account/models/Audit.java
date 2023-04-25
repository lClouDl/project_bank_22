package com.bank.account.models;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.envers.*;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.ZonedDateTime;

/**
 * Класс-сущность для записей аудирования
 */
@Data
@EqualsAndHashCode(exclude = "revInfo")
@ToString(exclude = "revInfo")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "audit", schema = "account")
public class Audit {

    /** Идентификационный номер записи аудирования */
    @Id
    @RevisionNumber
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /** Тип сущности, для которой создается запись аудирования */
    @Column(name = "entity_type")
    @NotNull(message = "Не указан тип измененной сущности")
    @NotBlank(message = "Не указан тип измененной сущности")
    @Size(max = 40, message = "Название типа сущности не должно превышать 40 символов")
    private String entityType;

    /** Тип операции над сущностью, которая инициировала запись аудирования */
    @Column(name = "operation_type")
    @NotNull(message = "Не указан тип операции произведенный над сущностью")
    @NotBlank(message = "Не указан тип операции произведенный над сущностью")
    @Size(max = 255, message = "Название типа операции не должно превышать 255 символов")
    private String operationType;

    /** Данные о том, кто совершил добавление новой сущности в бд, которое инициировало запись аудирования */
    @Column(name = "created_by")
    @NotNull(message = "Не указан инициатор изменения")
    @NotBlank(message = "Не указан инициатор изменения")
    @Size(max = 255, message = "Имя инициатора изменения не должно превышать 255 символов")
    private String createdBy;

    /** Данные о том, кто совершил изменение сущности, которое инициировало запись аудирования */
    @Column(name = "modified_by")
    @Size(max = 255, message = "Имя инициатора изменения не должно превышать 255 символов")
    private String modifiedBy;

    /** Данные о том, когда совершили добавление новой сущности в бд, которое инициировало запись аудирования */
    @Column(name = "created_at")
    @NotNull(message = "Не указана дата создания")
    private ZonedDateTime createdAt;

    /** Данные о том, когда совершили изменение сущности, которое инициировало запись аудирования */
    @Column(name = "modified_at")
    private ZonedDateTime modifiedAt;

    /** Данные о том, как теперь выглядит добавленная/измененная сущность */
    @Column(name = "new_entity_json")
    private String newEntityJson;

    /** Данные о том, как выглядела сущность до изменения */
    @Column(name = "entity_json")
    @NotNull(message = "Не указана созданная сущность")
    @NotBlank(message = "Не указана созданная сущность")
    private String entityJson;

    /**
     * Ссылка на вспомогательную таблицу аудирования
     * @see RevInfo
     */
    @OneToOne(mappedBy = "audit")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private RevInfo revInfo;

}
