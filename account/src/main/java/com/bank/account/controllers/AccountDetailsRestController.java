package com.bank.account.controllers;

import com.bank.account.AccountApplication;
import com.bank.account.dto.AccountDetailsDTO;
import com.bank.account.exceptions.AccountDetailsNotCreatedException;
import com.bank.account.exceptions.AccountDetailsNotFoundException;
import com.bank.account.exceptions.AccountDetailsNotFoundForUpdateException;
import com.bank.account.exceptions.AccountDetailsNotUpdateException;
import com.bank.account.models.AccountDetails;
import com.bank.account.services.AccountDetailsService;
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
 * Класс-контроллер для аккаунта. Позволяет производить CRUD-операции над аккаунтом
 */
@RestController
@Slf4j
@AllArgsConstructor
@Tag(name="Контроллер аккаунта", description="Позволяет производить CRUD операции над аккаунтами")
public class AccountDetailsRestController {

    /**
     * Поле для внедрения сервиса для сущности AccountDetails
     * @see AccountDetailsService
     */
    private final AccountDetailsService accountDetailsService;

    /**
     * Поле для внедрения ModelMapper с помощью которого будут обрабатываться DTO
     * @see AccountApplication#modelMapper()
     */
    private final ModelMapper modelMapper;

    /**
     * Метод, который предоставляет информацию обо всех аккаунтах
     * @return возвращает список аккаунтов
     */
    @Operation(
            summary = "Список всех аккаунтов",
            description = "Предоставляет информацию о всех аккаунтах"
    )
    @GetMapping()
    public List<AccountDetailsDTO> getAccountDetails() {
        return accountDetailsService.findAll().stream().map(this::convertToAccountDetailsDTO).collect(Collectors.toList());
    }

    /**
     * Метод, который предоставляет информацию об одном аккаунте с указанным id
     * @param id - идентификационный номер аккаунта
     * @exception AccountDetailsNotFoundException возможное исключение
     * @return возвращает аккаунт с указанным id
     */
    @Operation(
            summary = "Аккаунт по id",
            description = "Предоставляет информацию об аккаунте с переданным id"
    )
    @GetMapping("/{id}")
    public AccountDetailsDTO getAccountDetails(@PathVariable("id") @Parameter(description = "Идентификатор аккаунта") int id) {
        AccountDetailsDTO accountDetailsDTO = convertToAccountDetailsDTO(accountDetailsService.findOne(id));
        log.info("Поиск аккаунта с id: {} выполнен успешно.", id);
        return accountDetailsDTO;
    }

    /**
     * Метод, который позволяет создать новый аккаунт и сохранить его в базе данных
     * @param accountDetailsDTO - сущность аккаунта
     * @param bindingResult  - объект, который может содержать в себе информацию об ошибках валидации данных аккаунта
     * @exception AccountDetailsNotCreatedException возможное исключение
     * @return возвращает HTTP статус "OK"
     */
    @Operation(
            summary = "Создание аккаунта",
            description = "Позволяет создать и добавить в бд новый аккаунт"
    )
    @PostMapping()
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid AccountDetailsDTO accountDetailsDTO, @Parameter(description = "Данные об ошибках валидации") BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }
            throw new AccountDetailsNotCreatedException("Не удалось создать аккаунт по причине: " + errorMsg);
        }

        accountDetailsService.save(convertToAccountDetails(accountDetailsDTO));
        log.info("Добавление аккаунта с номером: {} выполнен успешно.", accountDetailsDTO.getAccountNumber());

        return ResponseEntity.ok(HttpStatus.OK);
    }

    /**
     * Метод, который позволяет обновить данные аккаунта и сохранить его в базе данных
     * @param accountDetailsDTO - сущность аккаунта
     * @param bindingResult  - объект, который может содержать в себе информацию об ошибках валидации данных аккаунта
     * @exception AccountDetailsNotUpdateException возможное исключение
     * @exception AccountDetailsNotFoundForUpdateException возможное исключение
     * @return возвращает HTTP статус "OK"
     */
    @Operation(
            summary = "Обновление аккаунта",
            description = "Позволяет обновить и сохранить в бд данные аккаунта с переданным id"
    )
    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@RequestBody @Valid AccountDetailsDTO accountDetailsDTO, @Parameter(description = "Данные об ошибках валидации") BindingResult bindingResult, @PathVariable("id") @Parameter(description = "Идентификатор аккаунта, в котором нужно произвести обновление") int id) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }
            throw new AccountDetailsNotUpdateException("Не удалось создать аккаунт по причине: " + errorMsg);
        }

        AccountDetails accountDetails = convertToAccountDetails(accountDetailsDTO);
        accountDetailsService.update(id, accountDetails);
        log.info("Обновление аккаунта с номером: {} выполнено успешно.", accountDetailsDTO.getAccountNumber());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /**
     * Метод, который позволяет удалить аккаунт из базы данных
     * @param id идентификационный номер аккаунта
     * @return возвращает HTTP статус "OK"
     */
    @Operation(
            summary = "Удаление аккаунта",
            description = "Позволяет удалить аккаунт с указанным id"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") @Parameter(description = "Идентификатор аккаунта для удаления") int id) {
        accountDetailsService.delete(id);
        log.info("Удаление аккаунта с id: {} выполнено успешно.", id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private AccountDetails convertToAccountDetails(AccountDetailsDTO accountDetailsDTO) {
        return modelMapper.map(accountDetailsDTO, AccountDetails.class);
    }

    private AccountDetailsDTO convertToAccountDetailsDTO(AccountDetails accountDetails) {
        return modelMapper.map(accountDetails, AccountDetailsDTO.class);
    }
}
