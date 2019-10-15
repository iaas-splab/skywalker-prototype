package de.iaas.skywalker;

import de.iaas.skywalker.ApplicationModels.repository.GenericApplicationModelRepository;
import de.iaas.skywalker.DeploymentModels.repository.DeploymentModelRepository;
import de.iaas.skywalker.DeploymentPackages.repository.DeploymentPackageRepository;
import de.iaas.skywalker.MappingModules.repository.MappingModuleRepository;
import de.iaas.skywalker.TransformationRepositories.repository.ServiceMappingRepository;
import de.iaas.skywalker.TransformationRepositories.repository.ServicePropertyMappingRepository;
import de.iaas.skywalker.Utils.RepositoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
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
    DeploymentModelRepository deploymentModelRepository;

	@Autowired
	DeploymentPackageRepository deploymentPackageRepository;

	@Autowired
    MappingModuleRepository mappingModuleRepository;

	@Autowired
    ServiceMappingRepository serviceMappingRepository;

	@Autowired
    ServicePropertyMappingRepository servicePropertyMappingRepository;

	@Autowired
    GenericApplicationModelRepository genericApplicationModelRepository;

	RepositoryUtils repoUtils = new RepositoryUtils();

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
				this.repoUtils.initDeploymentPackageRepository(deploymentPackageRepository);
				this.repoUtils.initDeploymentModelRepository(deploymentModelRepository);
				this.repoUtils.initMappingModuleRepository(mappingModuleRepository);
				this.repoUtils.initServiceMappingRepository(serviceMappingRepository);
				this.repoUtils.initServicePropertyMappingRepository(servicePropertyMappingRepository);
			}
			catch ( IOException e ) { e.printStackTrace(); }
		};
	}
}
