package com.sdgtt.config;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

/**
 * 自定义测试applicationContext 初始化加载
 *
 * @author tao
 * @version 2020-09-04 11:45
 */
@Component
public class SdgApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        System.out.println("红红火火恍恍惚惚");
    }
}
