package com.dolphinmovie.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@Import({PropertyConfig.class, ServiceConfig.class, DbConfig.class})
public class AppConfig {

}
