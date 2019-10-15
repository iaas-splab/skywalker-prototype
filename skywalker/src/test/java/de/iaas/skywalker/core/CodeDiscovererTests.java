package de.iaas.skywalker.core;

import de.iaas.skywalker.CodeAnalysis.lambda.javalang.CodeDiscoverer;
import de.iaas.skywalker.CodeAnalysis.lambda.javalang.utils.DiscoveryHelper;
import de.iaas.skywalker.utils.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
public class CodeDiscovererTests {

    TestUtils utils = new TestUtils();

    @Before
    public void setup() {

    }

    @Test
    public void testCodeDiscoveryWithStringOutputForProviderSpecificApiUsage() throws IOException {
        String function = utils.load_string_from_file("src/test/resources/aws_test_handler.java");
        String expected = utils.load_string_from_file("src/test/resources/aws_test_handler_annotated.java");
        CodeDiscoverer codeDiscoverer = new CodeDiscoverer(function, new ArrayList<String>(){{add("ThumbnailGenerationHandler");}});
        codeDiscoverer.discoverImports();
        codeDiscoverer.discoverClassMethods();
        DiscoveryHelper discoveryHelper = new DiscoveryHelper();
        String result = discoveryHelper.annotateHandlerBody(function, codeDiscoverer.getCriticalTerms());
        assertTrue(result.equals(expected));
    }

    @Test
    public void testCodeDiscoveryWithStringOutputForProviderAgnosticFunctions() throws IOException {
        String function = utils.load_string_from_file("src/test/resources/provider_agnostic_test_handler.java");
        CodeDiscoverer codeDiscoverer = new CodeDiscoverer(function, new ArrayList<String>(){{add("ProviderAgnosticCodeThatDoesSomething");}});
        codeDiscoverer.discoverImports();
        codeDiscoverer.discoverClassMethods();
        DiscoveryHelper discoveryHelper = new DiscoveryHelper();
        String result = discoveryHelper.annotateHandlerBody(function, codeDiscoverer.getCriticalTerms());
        assertTrue(result.equals(function));
    }
}
