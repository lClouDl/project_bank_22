package com.bank.account.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурационный класс для настройки swagger
 */
@Configuration
public class SwaggerConfig {

    /**
     * Метод настройки данных, указанных в заголовке Swagger Api
     * @see <a href="Документация Swagger">http://localhost:8085/api/account/swagger-ui/index.html#/</a>
     * @return возвращает данные о названии проекта, его версии и контакты разработчика
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Account")
                                .version("1.0.0")
                                .contact(
                                        new Contact()
                                                .email("account@mail.ru")
                                                .url("http://localhost:8085/api/account/")
                                                .name("Киреев Константин")
                                )
                );
    }
}
