package de.iaas.skywalker;

import de.iaas.skywalker.repository.MappingModuleRepository;
import de.iaas.skywalker.repository.TemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.IOException;

@SpringBootApplication
public class SkywalkerApplication {

	@Autowired
	TemplateRepository templateRepository;

	@Autowired
	MappingModuleRepository mappingModuleRepository;

	public static void main(String[] args) throws IOException {
		SpringApplication.run(SkywalkerApplication.class, args);
	}
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("http://localhost:4200")
						.allowedMethods("*");
			}
		};
	}
}
