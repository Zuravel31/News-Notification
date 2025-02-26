package com.example.client.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import feign.Client;
import feign.Request;
import feign.Retryer;

@Configuration
public class ConfigClient  {

    @Bean
    public Client feignClient() {
        return new Client.Default(null, null);
    }

    @Bean
    public Request.Options options() {
        return new Request.Options(5000, 30000); // Таймауты на подключение и чтение в миллисекундах
    }

    @Bean
    public Retryer retryer() {
        return new Retryer.Default(1000, 2000, 3); // Настройки повторных попыток
    }
}
