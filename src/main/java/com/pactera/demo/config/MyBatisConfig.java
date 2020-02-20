package com.pactera.demo.config;

import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: mybatis配置
 * @author: LiuZhiJun
 * @create: 2019-10-10 16:17
 **/
@Configuration
public class MyBatisConfig {
    @Bean
    public ConfigurationCustomizer configurationCustomizer(){
        return new ConfigurationCustomizer(){
            @Override
            public void customize(org.apache.ibatis.session.Configuration configuration) {
                // 设置驼峰命名
                configuration.setMapUnderscoreToCamelCase(true);
            }
        };
    }
}
