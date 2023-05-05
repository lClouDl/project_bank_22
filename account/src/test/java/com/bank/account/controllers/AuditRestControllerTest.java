package com.bank.account.controllers;

import com.bank.account.dto.AuditDTO;
import com.bank.account.exceptions.*;
import com.bank.account.models.Audit;
import com.bank.account.models.AuditTest;
import com.bank.account.services.AuditService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuditRestControllerTest {

    public static final int ID = 10;
    public static final String USERNAME = "Admin1";

    @Mock
    private AuditService auditService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private  AuditRestController auditRestController;

    @Test
    void getAuditAllSuccess() {
        doReturn(AuditTest.getListAudit()).when(auditService).findAll();
        assertEquals(auditRestController.getAudit().size(), 2);
        assertNotNull(auditRestController.getAudit());
        assertThat(auditRestController.getAudit()).isNotEmpty();
    }

    @Test
    void getAuditAllFail() {
        doReturn(null).when(auditService).findAll();
        assertThrows(NullPointerException.class, () -> auditRestController.getAudit());
    }


    @Test
    void getAuditByIdSuccess() {
        Audit audit = AuditTest.getOneAuditWithParam(ID, USERNAME);
        AuditDTO auditDTO = AuditTest.getOneAuditDTOWithParam(ID, USERNAME);
        doReturn(audit).when(auditService).findOne(ID);
        doReturn(auditDTO).when(modelMapper).map(audit, AuditDTO.class);
        assertEquals(auditRestController.getAudit(ID).getCreatedBy(), USERNAME);
        assertEquals(auditRestController.getAudit(ID).getId(), ID);
    }

    @Test
    void getAuditByIdFail() {
        doThrow(AuditNotFoundException.class).when(auditService).findOne(ID);
        assertThrows(AuditNotFoundException.class, () -> auditRestController.getAudit(ID));
    }

    @Test
    void createSuccess() {
        Audit audit = AuditTest.getOneAuditWithParam(ID, USERNAME);
        AuditDTO auditDTO = AuditTest.getOneAuditDTOWithParam(ID, USERNAME);
        assertDoesNotThrow(() -> auditService.save(audit));
        doReturn(save(audit)).when(modelMapper).map(auditDTO, Audit.class);
        assertEquals(auditRestController.create(auditDTO, bindingResult).getBody(), HttpStatus.OK);
    }

    @Test
    void createFail() {
        Audit audit = AuditTest.getOneAuditWithParam(ID, USERNAME);
        AuditDTO auditDTO = AuditTest.getOneAuditDTOWithParam(ID, USERNAME);
        doReturn(save(audit)).when(modelMapper).map(auditDTO, Audit.class);
        doThrow(AuditNotCreatedException.class).when(auditService).save(audit);
        assertThrows(AuditNotCreatedException.class, () -> auditRestController.create(auditDTO, bindingResult));
    }

    @Test
    void createFailBindingResult() {
        AuditDTO auditDTO = AuditTest.getOneAuditDTOWithParam(ID, USERNAME);
        lenient().when(bindingResult.hasErrors()).thenReturn(true);
        assertThrows(AuditNotCreatedException.class, () -> auditRestController.create(auditDTO, bindingResult));
    }

    private static Audit save(Audit audit) {
        AuditTest.getListAudit().add(audit);
        return audit;
    }

    @Test
    void updateSuccess() {
        save(AuditTest.getOneAuditWithParam(ID, USERNAME));
        Audit auditForUpdate = AuditTest.getOneAuditWithParam(ID, "Admin5");
        AuditDTO auditForUpdateDTO = AuditTest.getOneAuditDTOWithParam(ID, "Admin5");
        doReturn(update(ID, auditForUpdate)).when(modelMapper).map(auditForUpdateDTO, Audit.class);
        doThrow(AuditNotUpdateException.class).when(auditService).update(ID, auditForUpdate);
        assertThrows(AuditNotUpdateException.class, () -> auditRestController.update(auditForUpdateDTO, bindingResult, ID));
    }

    @Test
    void updateFail() {
        save(AuditTest.getOneAuditWithParam(ID, "Admin5"));
        Audit auditForUpdate = AuditTest.getOneAuditWithParam(ID + 1, USERNAME);
        AuditDTO auditForUpdateDTO = AuditTest.getOneAuditDTOWithParam(ID + 1, USERNAME);
        doReturn(update(ID+1, auditForUpdate)).when(modelMapper).map(auditForUpdateDTO, Audit.class);
        auditRestController.update(auditForUpdateDTO, bindingResult, ID+1);
        List<Audit> auditList = AuditTest.auditList;
        assertNotEquals(auditList.get(auditList.size()-1).getCreatedBy(), USERNAME);
    }

    @Test
    void updateFailBindingResult() {
        AuditDTO auditDTO = AuditTest.getOneAuditDTOWithParam(ID, USERNAME);
        lenient().when(bindingResult.hasErrors()).thenReturn(true);
        assertThrows(AuditNotUpdateException.class, () -> auditRestController.update(auditDTO, bindingResult, ID));
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
        lenient().when(auditService.findOne(ID)).thenReturn(AuditTest.getListAudit().get(0));
        auditRestController.delete(ID);
        int sizeAfterDelete = AuditTest.auditList.size();
        assertThat(sizeAfterDelete).isLessThan(sizeBeforeDelete);
    }

    @Test
    void deleteFail() {
        doThrow(NoSuchElementException.class).when(auditService).delete(ID);
        assertThrows(NoSuchElementException.class, () -> auditRestController.delete(ID));
    }

}