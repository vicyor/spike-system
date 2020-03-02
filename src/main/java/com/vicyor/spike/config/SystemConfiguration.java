package com.vicyor.spike.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 作者:姚克威
 * 时间:2020/3/2 14:41
 **/
@Configuration
public class SystemConfiguration {
    @Bean
    ExecutorService executorService(){
        return Executors.newCachedThreadPool();
    }
}
