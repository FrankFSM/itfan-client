package com.itfan.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author: ralap
 * @date: created at 2018/1/28 12:09
 */
@Configuration
public class AutoConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
