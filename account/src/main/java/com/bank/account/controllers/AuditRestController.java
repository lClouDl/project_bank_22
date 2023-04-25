package com.bank.account.controllers;

import com.bank.account.AccountApplication;
import com.bank.account.dto.AuditDTO;
import com.bank.account.exceptions.*;
import com.bank.account.models.Audit;
import com.bank.account.services.AuditService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс-контроллер для аудита. Позволяет производить CRUD-операции над записями для аудирования
 */
@RestController
@Slf4j
@AllArgsConstructor
@Tag(name="Контроллер аудита", description="Позволяет производить CRUD операции над данными аудирования")
@RequestMapping("/audit")
public class AuditRestController {

    /**
     * Поле для внедрения сервиса для сущности AuditService
     * @see AuditService
     */
    private final AuditService auditService;

    /**
     * Поле для внедрения ModelMapper с помощью которого будут обрабатываться DTO
     * @see AccountApplication#modelMapper()
     */
    private final ModelMapper modelMapper;

    /**
     * Метод, который предоставляет информацию обо всех записях аудирования
     * @return возвращает список записей аудирования
     */
    @Operation(
            summary = "Список всех аудитов",
            description = "Предоставляет информацию о всех записях аудирования"
    )
    @GetMapping()
    public List<AuditDTO> getAudit() {
        return auditService.findAll().stream().map(this::convertToAuditDTO).collect(Collectors.toList());
    }

    /**
     * Метод, который предоставляет информацию об одной записи аудирования с указанным id
     * @param id - идентификационный номер записи аудирования
     * @exception AuditNotFoundException возможное исключение
     * @return возвращает запись аудирования с указанным id
     */
    @Operation(
            summary = "Аудит по id",
            description = "Предоставляет запись аудирования с переданным id"
    )
    @GetMapping("/{id}")
    public AuditDTO getAudit(@PathVariable("id") @Parameter(description = "Идентификатор аудита") int id) {

        AuditDTO auditDTO = convertToAuditDTO(auditService.findOne(id));
        log.info("Поиск записи аудирования с id: {} выполнен успешно.", id);
        return auditDTO;
    }

    /**
     * Метод, который позволяет создать новую запись аудирования и сохранить ее в базе данных
     * @param auditDTO - сущность записи аудирования
     * @param bindingResult  - объект, который может содержать в себе информацию об ошибках валидации данных аудита
     * @exception AccountDetailsNotCreatedException возможное исключение
     * @return возвращает HTTP статус "OK"
     */
    @Operation(
            summary = "Создание аудирования",
            description = "Позволяет создать и добавить в бд новую запись аудирования"
    )
    @PostMapping()
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid AuditDTO auditDTO, @Parameter(description = "Данные об ошибках валидации") BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }
            throw new AuditNotCreatedException("Не удалось создать запись аудирования по причине: " + errorMsg);
        }

        auditService.save(convertToAudit(auditDTO));
        log.info("Добавление записи аудирования с id: {} выполнен успешно.", auditDTO.getId());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /**
     * Метод, который позволяет обновить данные записи аудирования и сохранить ее в базе данных
     * @param auditDTO - сущность записи аудирования
     * @param bindingResult  - объект, который может содержать в себе информацию об ошибках валидации данных аудита
     * @exception AuditNotUpdateException возможное исключение
     * @exception AccountDetailsNotFoundForUpdateException возможное исключение
     * @return возвращает HTTP статус "OK"
     */
    @Operation(
            summary = "Обновление аудирования",
            description = "Позволяет обновить и сохранить в бд запись аудирования с переданным id"
    )
    @PatchMapping("/{id}")
    private ResponseEntity<HttpStatus> update(@RequestBody @Valid AuditDTO auditDTO, @Parameter(description = "Данные об ошибках валидации") BindingResult bindingResult, @PathVariable("id") @Parameter(description = "Идентификатор записи аудирования, в которой нужно произвести обновление") int id) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }
            throw new AuditNotUpdateException("Не удалось обновить запись аудирования по причине: " + errorMsg);
        }

        Audit audit = convertToAudit(auditDTO);
        audit.setId(id);
        auditService.update(id, audit);
        log.info("Обновление записи аудирования с id: {} выполнено успешно.", id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /**
     * Метод, который позволяет удалить запись аудирования из базы данных
     * @param id идентификационный номер записи аудирования
     * @return возвращает HTTP статус "OK"
     */
    @Operation(
            summary = "Удаление аудита",
            description = "Позволяет удалить запись аудирования с указанным id"
    )
    @DeleteMapping("/{id}")
    private ResponseEntity<HttpStatus> delete(@PathVariable("id") @Parameter(description = "Идентификатор аудита для удаления") int id) {
        auditService.delete(id);
        log.info("Удаление записи аудирования с id: {} выполнено успешно.", id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private Audit convertToAudit(AuditDTO auditDTO) {
        return modelMapper.map(auditDTO, Audit.class);
    }
    private AuditDTO convertToAuditDTO(Audit audit) {
        return modelMapper.map(audit, AuditDTO.class);
    }

}
