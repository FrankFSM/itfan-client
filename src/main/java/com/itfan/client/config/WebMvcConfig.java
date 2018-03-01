package com.itfan.client.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * WebMvcConfig
 * @author: ralap
 * @date: created at 2018/1/28 18:54
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/registPage").setViewName("regist");
        registry.addViewController("/pointChat").setViewName("point_chat");
        registry.addViewController("/groupChat").setViewName("group_chat");

    }
}
