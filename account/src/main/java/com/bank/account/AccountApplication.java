package com.bank.account;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Класс-конфигурации. Позволяет настроить Spring Boot App
 */
@SpringBootApplication
public class AccountApplication {

    /** Поле с логгером */
    static final Logger LOGGER =
            LoggerFactory.getLogger(AccountApplication.class);

    /** Точка входа в приложение*/
    public static void main(String[] args) {
        LOGGER.info("Сейчас будет производиться запуск приложения.");
        SpringApplication.run(AccountApplication.class, args);
        LOGGER.debug("Приложение запустилось с кодом: {}", args.length);
        LOGGER.info("Приложение успешно запустилось с кодом {}", args.length);
    }

    /**
     * Метод создания бина для {@link ModelMapper}
     * Используется в контроллерах {@link com.bank.account.controllers.AccountDetailsRestController}
     * и {@link com.bank.account.controllers.AuditRestController}
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
