package com.bank.account.models;

import com.bank.account.util.AuditListener;
import lombok.*;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import javax.persistence.*;

/**
 * Класс-сущность для расширения вспомогательной таблицы, которая задействуется при аудировании
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@RevisionEntity(AuditListener.class)
@Table(name = "REVINFO", schema = "account")
public class RevInfo {

        /** Идентификационный номер записи аудирования */
        @Id
        @RevisionNumber
        @GeneratedValue(generator = "CustomerAuditRevisionSeq")
        @SequenceGenerator(name = "CustomerAuditRevisionSeq", sequenceName = "customer_audit_revision_seq", schema = "account", allocationSize = 1)
        private int id;

        /** Время инициации записи аудирования */
        @RevisionTimestamp
        private long timestamp;

        /** Данные о том, кто произвел изменения, которые инициировали запись аудирования */
        private String username;

        /**
         * Ссылка на таблицу аудирования
         * @see Audit
         */
        @OneToOne
        @JoinColumn(name = "audit_id", referencedColumnName = "id")
        private Audit audit;
}
