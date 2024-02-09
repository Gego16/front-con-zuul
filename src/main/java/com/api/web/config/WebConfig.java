package com.api.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer{

	@Override
	public void addViewControllers(ViewControllerRegistry registro) {
		registro.addViewController("/").setViewName("lista");
		registro.addViewController("/login");
		registro.addViewController("/errores/403").setViewName("errores/403");
		registro.addViewController("/errores/500").setViewName("errores/500");
	}
}
