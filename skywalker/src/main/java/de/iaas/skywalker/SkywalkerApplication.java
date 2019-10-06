package de.iaas.skywalker;

import de.iaas.skywalker.ApplicationModels.repository.GenericApplicationModelRepository;
import de.iaas.skywalker.DeploymentModels.repository.DeploymentModelRepository;
import de.iaas.skywalker.MappingModules.repository.MappingModuleRepository;
import de.iaas.skywalker.MappingModules.model.DeploymentModel;
import de.iaas.skywalker.Processes.Database.MongoHelper;
import de.iaas.skywalker.TransformationRepositories.repository.ServiceMappingRepository;
import de.iaas.skywalker.TransformationRepositories.repository.ServicePropertyMappingRepository;
import de.iaas.skywalker.TransformationRepositories.model.GenericServiceProperty;
import de.iaas.skywalker.MappingModules.model.MappingModule;
import de.iaas.skywalker.TransformationRepositories.model.EventSourceMapping;
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
import java.util.HashMap;
import java.util.List;

@SpringBootApplication
public class SkywalkerApplication {

	@Autowired
    DeploymentModelRepository deploymentModelRepository;

	@Autowired
    MappingModuleRepository mappingModuleRepository;

	@Autowired
    ServiceMappingRepository serviceMappingRepository;

	@Autowired
    ServicePropertyMappingRepository servicePropertyMappingRepository;

	@Autowired
    GenericApplicationModelRepository genericApplicationModelRepository;

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
//				MongoHelper mh = new MongoHelper();
//				mh.startMongoServer();
				//mongod --dbpath=.
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
				new EventSourceMapping(
						"http",
						"aws",
						"http",
						Arrays.asList("path", "authorizer", "method", "cors", "private")
				)
		);
		serviceMappingRepository.save(
				new EventSourceMapping(
						"http",
						"azure",
						"http",
						Arrays.asList("route", "authLevel", "methods")
				)
		);
		serviceMappingRepository.save(
				new EventSourceMapping(
						"storage",
						"aws",
						"s3",
						Arrays.asList("bucket", "event", "rules")
				)
		);
		serviceMappingRepository.save(
				new EventSourceMapping(
						"storage",
						"azure",
						"blob",
						Arrays.asList("path", "connection")
				)
		);
		serviceMappingRepository.save(
				new EventSourceMapping(
						"schedule",
						"aws",
						"schedule",
						Arrays.asList("name", "description", "rate")
				)
		);
		serviceMappingRepository.save(
				new EventSourceMapping(
						"schedule",
						"azure",
						"timer",
						Arrays.asList("schedule", "runOnStartup", "useMonitor")
				)
		);
		serviceMappingRepository.save(
				new EventSourceMapping(
						"stream",
						"aws",
						"stream",
						Arrays.asList("arn", "batchSize", "startingPosition")
				)
		);
		serviceMappingRepository.save(
				new EventSourceMapping(
						"stream",
						"azure",
						"eventHub",
						Arrays.asList("path", "eventHubName", "consumerGroup", "connection")
				)
		);
		serviceMappingRepository.save(
				new EventSourceMapping(
						"point2point",
						"aws",
						"sqs",
						Arrays.asList("arn", "batchSize")
				)
		);
		serviceMappingRepository.save(
				new EventSourceMapping(
						"point2point",
						"azure",
						"queue",
						Arrays.asList("queueName", "consumerGroup", "connection")
				)
		);
		serviceMappingRepository.save(
				new EventSourceMapping(
						"point2point",
						"azure",
						"serviceBus",
						Arrays.asList("queueName", "accessRights", "connection")
				)
		);

		servicePropertyMappingRepository.save(
				new GenericServiceProperty(
						"http",
						new HashMap<String, List<String>>() {{
							put("path", Arrays.asList("path", "route"));
							put("methods", Arrays.asList("method", "methods"));
							put("rules", Arrays.asList("rules"));
							put("auth", Arrays.asList("authorizer", "authLevel"));
							put("cors", Arrays.asList("cors"));
							put("endpointConfig", Arrays.asList("private"));
						}}
				)
		);
		servicePropertyMappingRepository.save(
				new GenericServiceProperty(
						"schedule",
						new HashMap<String, List<String>>() {{
							put("schedule", Arrays.asList("rate", "schedule"));
							put("onBootUp", Arrays.asList("runOnStartup"));
							put("monitoring", Arrays.asList("useMonitor"));
						}}
				)
		);
		servicePropertyMappingRepository.save(
				new GenericServiceProperty(
						"stream",
						new HashMap<String, List<String>>() {{
							put("resourceId", Arrays.asList("arn", "path", "eventHubName"));
							put("batchSize", Arrays.asList("batchSize"));
							put("startingPosition", Arrays.asList("startingPosition"));
							put("consumerGroup", Arrays.asList("consumerGroup"));
						}}
				)
		);
		servicePropertyMappingRepository.save(
				new GenericServiceProperty(
						"point2point",
						new HashMap<String, List<String>>() {{
							put("resourceId", Arrays.asList("arn", "queueName", "queueName"));
							put("batchSize", Arrays.asList("batchSize"));
						}}
				)
		);
		servicePropertyMappingRepository.save(
				new GenericServiceProperty(
						"storage",
						new HashMap<String, List<String>>() {{
							put("resourceId", Arrays.asList("bucket", "path"));
							put("events", Arrays.asList("event"));
							put("rules", Arrays.asList("rules"));
						}}
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
