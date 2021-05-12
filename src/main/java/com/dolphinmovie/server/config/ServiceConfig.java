package com.dolphinmovie.server.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages="com.dolphinmovie.server.service")
public class ServiceConfig {
}
