package de.iaas.skywalker.core;

import de.iaas.skywalker.MappingModules.util.ModelMappingUtils;
import de.iaas.skywalker.TransformationRepositories.repository.ServicePropertyMappingRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertTrue;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ModelMappingUtilsTests {

    @Autowired
    ServicePropertyMappingRepository servicePropertyMappingRepository;

    Map<String, List<String>> mockGenericEventSourceWithProviderSpecificProperties = new HashMap<String, List<String>>(){{
        put("stream", new ArrayList<String>(){{
            add("batchSize");
            add("consumerGroup");
            add("eventHubName");
        }});
        put("point2point", new ArrayList<String>(){{
            add("batchSize");
            add("queueName");
        }});
    }};


    @Before
    public void setup() {

    }

    @Test
    public void testGenerifyEventSourcePropertiesForExistingMappings()  {
        ModelMappingUtils utils = new ModelMappingUtils();
        Map<String, List<String>> mockGenericEventSourceWithGenerifiedProperties = new HashMap<String, List<String>>() {{
            put("stream", new ArrayList<String>(){{
                add("batchSize");
                add("consumerGroup");
                add("resourceId");
            }});
            put("point2point", new ArrayList<String>(){{
                add("batchSize");
                add("resourceId");
            }});
        }};
        Map<String, List<String>> result = utils.generifyEventSourceProperties(
                mockGenericEventSourceWithProviderSpecificProperties,
                servicePropertyMappingRepository);
        assertTrue(result.equals(mockGenericEventSourceWithGenerifiedProperties));
    }

    @Test
    public void testGenerifyEventSourcePropertiesWithNonExistingPropertiesInMappings()  {
        ModelMappingUtils utils = new ModelMappingUtils();
        Map<String, List<String>> mockGenericEventSourceWithProviderSpecificProperties = new HashMap<String, List<String>>(){{
            put("stream", new ArrayList<String>(){{
                add("batchSize");
                add("kaftaConnector");
                add("eventHubName");
            }});
            put("point2point", new ArrayList<String>(){{
                add("batchSize");
                add("queueName");
            }});
        }};
        Map<String, List<String>> mockGenericEventSourceWithGenerifiedProperties = new HashMap<String, List<String>>() {{
            put("stream", new ArrayList<String>(){{
                add("batchSize");
                add("kaftaConnector");
                add("resourceId");
            }});
            put("point2point", new ArrayList<String>(){{
                add("batchSize");
                add("resourceId");
            }});
        }};
        Map<String, List<String>> result = utils.generifyEventSourceProperties(
                mockGenericEventSourceWithProviderSpecificProperties,
                servicePropertyMappingRepository);
        assertTrue(result.equals(mockGenericEventSourceWithGenerifiedProperties));
    }

}
