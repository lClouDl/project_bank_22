package com.bank.account;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;


/**
 * Класс-конфигурации. Позволяет настроить Spring Boot App
 */
@SpringBootApplication
@Slf4j
@EnableDiscoveryClient
public class AccountApplication {

    /** Точка входа в приложение*/
    public static void main(String[] args) {
        SpringApplication.run(AccountApplication.class, args);
        log.debug("Приложение запустилось с кодом: {}", args.length);
        log.info("Приложение успешно запустилось с кодом {}", args.length);
    }

    /**
     * Метод создания бина для {@link ModelMapper}
     * Используется в контроллерах {@link com.bank.account.controllers.AccountDetailsRestController}
     * и {@link com.bank.account.controllers.AuditRestController}
     */
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        return modelMapper;
    }

}
