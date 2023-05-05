package com.bank.account.services;

import com.bank.account.exceptions.AuditNotFoundException;
import com.bank.account.models.Audit;
import com.bank.account.models.AuditTest;
import com.bank.account.repositories.AuditRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuditServiceImplTest {

    public static final int ID = 10;
    public static final String USERNAME = "Admin1";

    @Mock
    private AuditRepository auditRepository;

    @InjectMocks
    private AuditServiceImpl auditServiceImpl;

    @Test
    void findAll() {
        doReturn(AuditTest.getListAudit()).when(auditRepository).findAll();
        List<Audit> auditList = auditServiceImpl.findAll();
        assertThat(auditList).isNotEmpty();
        assertEquals(auditList.size(), 2);
    }


    @Test
    void findOneSuccess() {
        Optional<Audit> auditOptional = Optional.of(AuditTest
                .getOneAuditWithParam(ID, USERNAME));
        doReturn(auditOptional).when(auditRepository).findById(ID);
        Audit audit = auditServiceImpl.findOne(ID);
        assertThat(audit).isNotNull();
        assertEquals(audit, auditOptional.get());
    }

    @Test
    void findOneFail() {
        doReturn(Optional.empty()).when(auditRepository).findById(ID);
        assertThrows(AuditNotFoundException.class, () -> auditServiceImpl.findOne(ID));
    }

    @Test
    void saveSuccess() {
        Audit auditForSave = AuditTest.getOneAuditWithParam(ID, USERNAME);
        Audit auditForCheck = AuditTest.getOneAuditWithParam(ID+1, USERNAME);
        doReturn(save(auditForSave)).when(auditRepository).save(auditForCheck);
        auditServiceImpl.save(auditForCheck);
        List<Audit> auditList = AuditTest.auditList;
        assertEquals(auditList.get(auditList.size()-1).getId(), ID);
    }

    private static Audit save(Audit audit) {
        AuditTest.getListAudit().add(audit);
        return audit;
    }

    @Test
    void updateSuccess() {
        save(AuditTest.getOneAuditWithParam(ID, USERNAME));
        Audit auditForUpdate = AuditTest.getOneAuditWithParam(ID, "Admin5");
        lenient().when(auditRepository.findById(ID)).thenReturn(Optional.of(auditForUpdate));
        lenient().when(auditRepository.save(auditForUpdate)).thenReturn(update(ID, auditForUpdate));
        auditServiceImpl.update(ID, auditForUpdate);
        List<Audit> auditList = AuditTest.auditList;
        assertEquals(auditList.get(auditList.size()-1).getCreatedBy(), "Admin5");
    }

    @Test
    void updateFail() {
        save(AuditTest.getOneAuditWithParam(ID, USERNAME));
        Audit auditForUpdate = AuditTest.getOneAuditWithParam(ID+1, USERNAME);
        lenient().when(auditRepository.findById(ID)).thenReturn(Optional.of(auditForUpdate));
        lenient().when(auditRepository.save(auditForUpdate)).thenReturn(update(ID+1, auditForUpdate));
        auditServiceImpl.update(ID, auditForUpdate);
        List<Audit> auditList = AuditTest.auditList;
        assertEquals(auditList.get(auditList.size()-1).getId(), ID);
    }

    private static Audit update(int id, Audit auditForUpdate) {
        for (Audit someAudit : AuditTest.auditList) {
            if (someAudit.getId() == id) {
                someAudit.setEntityType(auditForUpdate.getEntityType());
                someAudit.setOperationType(auditForUpdate.getOperationType());
                someAudit.setCreatedBy(auditForUpdate.getCreatedBy());
                someAudit.setModifiedBy(auditForUpdate.getModifiedBy());
                someAudit.setCreatedAt(auditForUpdate.getCreatedAt());
                someAudit.setModifiedAt(auditForUpdate.getModifiedAt());
                someAudit.setNewEntityJson(auditForUpdate.getNewEntityJson());
                someAudit.setEntityJson(auditForUpdate.getEntityJson());
            }
        }
        return auditForUpdate;
    }

    @Test()
    void deleteSuccess() {
        save(AuditTest.getOneAuditWithParam(ID, USERNAME));
        int sizeBeforeDelete = AuditTest.auditList.size();
        lenient().when(auditRepository.findById(ID)).thenReturn(Optional.of(AuditTest.getListAudit().get(0)));
        auditServiceImpl.delete(ID);
        int sizeAfterDelete = AuditTest.auditList.size();
        assertThat(sizeAfterDelete).isLessThan(sizeBeforeDelete);
    }

    @Test
    void deleteFail() {
        doThrow(NoSuchElementException.class).when(auditRepository).deleteById(ID);
        assertThrows(NoSuchElementException.class, () -> auditServiceImpl.delete(ID));
    }

}