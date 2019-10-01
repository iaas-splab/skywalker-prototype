package de.iaas.skywalker;

import de.iaas.skywalker.models.DeploymentModel;
import de.iaas.skywalker.models.MappingModule;
import de.iaas.skywalker.models.ServiceMapping;
import de.iaas.skywalker.repository.MappingModuleRepository;
import de.iaas.skywalker.repository.ServiceMappingRepository;
import de.iaas.skywalker.repository.DeploymentModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


import java.io.IOException;
import java.io.*;
import java.nio.file.Paths;
import java.util.Arrays;

@SpringBootApplication
public class SkywalkerApplication {

	@Autowired
	DeploymentModelRepository deploymentModelRepository;

	@Autowired
	MappingModuleRepository mappingModuleRepository;

	@Autowired
	ServiceMappingRepository serviceMappingRepository;

	private static final String MACOS_MAPPINGS = "/src/main/resources/mapping.configurations/rule_serverless_v2.yaml";
	private static final String MACOS_TEMPLATES = "/src/main/resources/templates/serverless.yml";
	private static final String WIN_MAPPINGS = "\\src\\main\\resources\\mapping.configurations\\rule_serverless_v2.yaml";
	private static final String WIN_TEMPLATES = "\\src\\main\\resources\\templates\\serverless.yml";

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

		DeploymentModel dm = new DeploymentModel();
		dm.setName("serverless.yml");
		dm.setBody(readFileToString(Paths.get("").toAbsolutePath().toString() + MACOS_TEMPLATES));
		deploymentModelRepository.save(dm);

		MappingModule mappingModule = new MappingModule();
		mappingModule.setName("rule_serverless_v2.yaml");
		mappingModule.setBody(readFileToString(Paths.get("").toAbsolutePath().toString() + MACOS_MAPPINGS));
		mappingModuleRepository.save(mappingModule);

		serviceMappingRepository.save(
				new ServiceMapping(
						"http",
						"aws",
						"http",
						Arrays.asList("path", "authorizer", "method")
				)
		);
		serviceMappingRepository.save(
				new ServiceMapping(
						"http",
						"azure",
						"http",
						Arrays.asList("route", "authLevel", "methods")
				)
		);
		serviceMappingRepository.save(
				new ServiceMapping(
						"storage",
						"aws",
						"s3",
						Arrays.asList("bucket", "event", "rules")
				)
		);
		serviceMappingRepository.save(
				new ServiceMapping(
						"storage",
						"azure",
						"blob",
						Arrays.asList("path")
				)
		);
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
