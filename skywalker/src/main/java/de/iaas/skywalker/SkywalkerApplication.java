package de.iaas.skywalker;

import de.iaas.skywalker.models.MappingModule;
import de.iaas.skywalker.models.Template;
import de.iaas.skywalker.repository.MappingModuleRepository;
import de.iaas.skywalker.repository.TemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.*;
import java.nio.file.Paths;
import java.util.stream.Stream;

@SpringBootApplication
public class SkywalkerApplication {

	@Autowired
	TemplateRepository templateRepository;

	@Autowired
	MappingModuleRepository mappingModuleRepository;

	public static void main(String[] args) {

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

	@Bean
	ApplicationRunner init(){
		return args -> {
			try {
				this.initRepos();
			} catch ( IOException e ) {
				e.printStackTrace();
			}
		};
	}

	private void initRepos() throws IOException {
		Template template = new Template();
		template.setName("serverless.yml");
		template.setBody(readFileToString(Paths.get("").toAbsolutePath().toString() + "\\src\\main\\resources\\templates\\serverless.yml"));
		templateRepository.save(template);

		MappingModule mappingModule = new MappingModule();
		mappingModule.setName("rule_serverless_v2.yaml");
		mappingModule.setBody(readFileToString(Paths.get("").toAbsolutePath().toString() + "\\src\\main\\resources\\mapping.configurations\\rule_serverless_v2.yaml"));
		mappingModuleRepository.save(mappingModule);
	}

	private String readFileToString(String filePath) throws IOException {
		InputStream is = new FileInputStream(filePath);
		BufferedReader buf = new BufferedReader(new InputStreamReader(is));

		String line = buf.readLine();
		StringBuilder sb = new StringBuilder();

		while(line != null){ sb.append(line).append("\n"); line = buf.readLine(); }
		return sb.toString();

	}

}
