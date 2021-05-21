package com.dolphinmovie.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({
	@PropertySource(value="properties/url.properties"),
	@PropertySource(value="properties/apiKeys.properties"),
	@PropertySource(value="properties/util.properties")
})
public class ServiceConfig {
}
