package com.bank.account.models;

import com.bank.account.dto.AuditDTO;
import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AuditTest {

    public static List<Audit> auditList;

    public static List<Audit> getListAudit() {
        auditList = new ArrayList<>();
        auditList.add(getOneAuditWithParam(1, "User1"));
        auditList.add(getOneAuditWithParam(2, "Admin2"));
        return auditList;
    }

    public static Audit getOneAuditWithParam(int id, String username) {
        return new Audit(id, "AccountDetails",
                "Mod",
                username,
                username,
                ZonedDateTime.of(id, id, id, id,id, id, id, ZoneId.of("GMT")),
                ZonedDateTime.of(id, id, id, id,id, id, id, ZoneId.of("GMT")),
                "New entity json",
                "Old entity json",
                new RevInfo());
    }

    public static AuditDTO getOneAuditDTOWithParam(int id, String username) {
        AuditDTO auditDTO = new AuditDTO(id,
                "AccountDetails",
                "Mod",
                username,
                username,
                id,
                id,
                "New entity json",
                "Old entity json");
        AuditDTO auditDTOForTest = new AuditDTO();

        auditDTOForTest.setId(auditDTO.getId());
        auditDTOForTest.setEntityType(auditDTO.getEntityType());
        auditDTOForTest.setOperationType(auditDTO.getOperationType());
        auditDTOForTest.setCreatedBy(auditDTO.getCreatedBy());
        auditDTOForTest.setModifiedBy(auditDTO.getModifiedBy());
        auditDTOForTest.setCreatedAt(auditDTO.getCreatedAt());
        auditDTOForTest.setModifiedAt(auditDTO.getModifiedAt());
        auditDTOForTest.setNewEntityJson(auditDTO.getNewEntityJson());
        auditDTOForTest.setEntityJson(auditDTO.getEntityJson());
        return auditDTOForTest;
    }

    @Test
    void noArgsConstructorTest () {
        Audit audit = new Audit();
        List<Audit> auditTest = getListAudit();
        auditTest.add(audit);
        assertEquals(auditTest.size(), 3);
    }

    @Test
    void toStringTest () {
        Audit audit = getOneAuditWithParam(1, "Admin");
        audit.setRevInfo(new RevInfo());
        assertThat(audit.toString()).isNotEmpty();
        assertThat(audit.toString()).isNotNull();
        assertNotNull(audit.getRevInfo());
    }
}