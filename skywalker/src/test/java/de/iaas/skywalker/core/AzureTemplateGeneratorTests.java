package de.iaas.skywalker.core;

import de.iaas.skywalker.MappingModules.model.DeploymentModel;
import de.iaas.skywalker.TransformationRepositories.repository.ServiceMappingRepository;
import de.iaas.skywalker.TransformationRepositories.repository.ServicePropertyMappingRepository;
import de.iaas.skywalker.Translator.ServerlessFramework.Azure.AzureTemplateGenerator;
import de.iaas.skywalker.utils.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.*;

import static org.junit.Assert.assertTrue;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class AzureTemplateGeneratorTests {

    @Autowired
    ServiceMappingRepository serviceMappingRepository;

    @Autowired
    ServicePropertyMappingRepository servicePropertyMappingRepository;

    DeploymentModel model;
    TestUtils utils = new TestUtils();

    @Before
    public void setup() throws IOException {
        model = utils.load_deployment_model_from_file("src/test/resources/aws_model.yaml");
    }

    @Test
    public void testTranslateSourceDeploymentModelToTargetProviderTemplate() throws IOException {
        AzureTemplateGenerator generator =
                new AzureTemplateGenerator(model, serviceMappingRepository, servicePropertyMappingRepository);
        String result = generator.translateSourceDeploymentModelToTargetProviderTemplate();
        String expected = utils
                .load_deployment_model_from_file("src/test/resources/azure_translated_model.yaml").getBody();
        assertTrue(result.equals(expected));
    }
}
