package de.iaas.skywalker.core;

import de.iaas.skywalker.ApplicationModels.util.EvaluationHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;


@RunWith(SpringJUnit4ClassRunner.class)
public class EvaluationHandlerTests {

    Map<String, List<String>> mockEventSources = new HashMap<String, List<String>>() {{
        put("http", new ArrayList<String>(){{
            add("path");
            add("methods");
        }});
        put("storage", new ArrayList<String>(){{
            add("resourceId");
            add("events");
        }});
    }};

    Map<String, List<String>> mockEventSourcesAlt = new HashMap<String, List<String>>() {{
        put("stream", new ArrayList<String>(){{
            add("batchSize");
            add("resourceId");
        }});
    }};

    Map<String, List<String>> mockTargetDistinctEvents = new HashMap<String, List<String>>() {{
        put("stream", new ArrayList<String>(){{
            add("batchSize");
            add("consumerGroup");
            add("startingPosition");
        }});
        put("point2point", new ArrayList<String>(){{
            add("batchSize");
            add("resourceId");
        }});
    }};

    Map<String, List<String>> mockTargetDistinctProperties = new HashMap<String, List<String>>() {{
        put("http", new ArrayList<String>(){{
            add("endpointConfig");
            add("rules");
        }});
        put("storage", new ArrayList<String>(){{
            add("rules");
        }});
    }};

    Map<String, List<String>> mockTargetDistinctEventSameProperties = new HashMap<String, List<String>>() {{
        put("point2point", new ArrayList<String>(){{
            add("batchSize");
            add("resourceId");
        }});
    }};

    Map<String, List<String>> halfEventsMatch = new HashMap<String, List<String>>() {{
        put("http", new ArrayList<String>(){{
            add("path");
            add("methods");
        }});
        put("stream", new ArrayList<String>(){{
            add("batchSize");
            add("resourceId");
        }});
    }};

    Map<String, List<String>> halfEventsMatchWithHalfOfProperties = new HashMap<String, List<String>>() {{
        put("http", new ArrayList<String>(){{
            add("path");
            add("rules");
        }});
        put("stream", new ArrayList<String>(){{
            add("batchSize");
            add("resourceId");
        }});
    }};

    @Before
    public void setup() {

    }

    @Test
    public void testEvaluateTargetPlatformCoverageScoreValidWhenSame()  {
        EvaluationHelper evaluationHelper = new EvaluationHelper(mockEventSources);
        double result = evaluationHelper.evaluateTargetPlatformCoverageScore(mockEventSources);
        assertEquals(result, 1.0, 0.000001);
    }

    @Test
    public void testEvaluateTargetPlatformCoverageScoreValidWhenDistinctEvents()  {
        EvaluationHelper evaluationHelper = new EvaluationHelper(mockEventSources);
        double result = evaluationHelper.evaluateTargetPlatformCoverageScore(mockTargetDistinctEvents);
        assertEquals(result, 0.0, 0.000001);
    }

    @Test
    public void testEvaluateTargetPlatformCoverageScoreValidWhenSameEventsAndDistinctProperties()  {
        EvaluationHelper evaluationHelper = new EvaluationHelper(mockEventSources);
        double result = evaluationHelper.evaluateTargetPlatformCoverageScore(mockTargetDistinctProperties);
        assertEquals(result, 0.0, 0.000001);
    }

    @Test
    public void testEvaluateTargetPlatformCoverageScoreValidWhenDistinctEventAndSameProperties()  {
        EvaluationHelper evaluationHelper = new EvaluationHelper(mockEventSourcesAlt);
        double result = evaluationHelper.evaluateTargetPlatformCoverageScore(mockTargetDistinctEventSameProperties);
        assertEquals(result, 0.0, 0.000001);
    }

    @Test
    public void testEvaluateTargetPlatformCoverageScoreValidHalfOfEventsMatchWithAllProperties()  {
        EvaluationHelper evaluationHelper = new EvaluationHelper(mockEventSources);
        double result = evaluationHelper.evaluateTargetPlatformCoverageScore(halfEventsMatch);
        assertEquals(result, 0.5, 0.000001);
    }

    @Test
    public void testEvaluateTargetPlatformCoverageScoreValidHalfOfEventsMatchWithHalfOfProperties()  {
        EvaluationHelper evaluationHelper = new EvaluationHelper(mockEventSources);
        double result = evaluationHelper.evaluateTargetPlatformCoverageScore(halfEventsMatchWithHalfOfProperties);
        assertEquals(result, 0.25, 0.000001);
    }

    @Test
    public void testGetPlatformCandidateEventCoverageModelWhenEqual()  {
        Map<String, List<Map<String, String>>> mockCoverageModelWhenValid = new HashMap<String, List<Map<String, String>>>() {{
            put("http", new ArrayList<Map<String, String>>(){{
                add(new HashMap<String, String>(){{put("path", "path");}});
                add(new HashMap<String, String>(){{put("methods", "methods");}});
            }});
            put("storage", new ArrayList<Map<String, String>>(){{
                add(new HashMap<String, String>(){{put("resourceId", "resourceId");}});
                add(new HashMap<String, String>(){{put("events", "events");}});
            }});
        }};
        EvaluationHelper evaluationHelper = new EvaluationHelper(mockEventSources);
        Map<String, List<Map<String, String>>> result = evaluationHelper.getPlatformCandidateEventCoverageModel(mockEventSources);
        assertTrue(result.equals(mockCoverageModelWhenValid));
    }

    @Test
    public void testGetPlatformCandidateEventCoverageModelWhenDistinctEvents()  {
        Map<String, List<Map<String, String>>> mockCoverageModelWhenValid = new HashMap<String, List<Map<String, String>>>() {{
            put("http", new ArrayList<Map<String, String>>(){{
                add(new HashMap<String, String>(){{put("path", "-");}});
                add(new HashMap<String, String>(){{put("methods", "-");}});
            }});
            put("storage", new ArrayList<Map<String, String>>(){{
                add(new HashMap<String, String>(){{put("resourceId", "-");}});
                add(new HashMap<String, String>(){{put("events", "-");}});
            }});
        }};
        EvaluationHelper evaluationHelper = new EvaluationHelper(mockEventSources);
        Map<String, List<Map<String, String>>> result = evaluationHelper.getPlatformCandidateEventCoverageModel(mockTargetDistinctEvents);
        assertTrue(result.equals(mockCoverageModelWhenValid));
    }

    @Test
    public void testGetPlatformCandidateEventCoverageModelWhenSameEventsAndDistinctProperties()  {
        Map<String, List<Map<String, String>>> mockCoverageModelWhenValid = new HashMap<String, List<Map<String, String>>>() {{
            put("http", new ArrayList<Map<String, String>>(){{
                add(new HashMap<String, String>(){{put("path", "-");}});
                add(new HashMap<String, String>(){{put("methods", "-");}});
            }});
            put("storage", new ArrayList<Map<String, String>>(){{
                add(new HashMap<String, String>(){{put("resourceId", "-");}});
                add(new HashMap<String, String>(){{put("events", "-");}});
            }});
        }};
        EvaluationHelper evaluationHelper = new EvaluationHelper(mockEventSources);
        Map<String, List<Map<String, String>>> result = evaluationHelper.getPlatformCandidateEventCoverageModel(mockTargetDistinctProperties);
        assertTrue(result.equals(mockCoverageModelWhenValid));
    }

    @Test
    public void testGetPlatformCandidateEventCoverageModelWhenDistinctEventAndSameProperties()  {
        Map<String, List<Map<String, String>>> mockCoverageModelWhenValid = new HashMap<String, List<Map<String, String>>>() {{
            put("stream", new ArrayList<Map<String, String>>(){{
                add(new HashMap<String, String>(){{put("batchSize", "-");}});
                add(new HashMap<String, String>(){{put("resourceId", "-");}});
            }});
        }};
        EvaluationHelper evaluationHelper = new EvaluationHelper(mockEventSourcesAlt);
        Map<String, List<Map<String, String>>> result = evaluationHelper.getPlatformCandidateEventCoverageModel(mockTargetDistinctEventSameProperties);
        assertTrue(result.equals(mockCoverageModelWhenValid));
    }

    @Test
    public void testGetPlatformCandidateEventCoverageModelHalfOfEventsMatchWithAllProperties()  {
        Map<String, List<Map<String, String>>> mockCoverageModelWhenValid = new HashMap<String, List<Map<String, String>>>() {{
            put("http", new ArrayList<Map<String, String>>(){{
                add(new HashMap<String, String>(){{put("path", "path");}});
                add(new HashMap<String, String>(){{put("methods", "methods");}});
            }});
            put("storage", new ArrayList<Map<String, String>>(){{
                add(new HashMap<String, String>(){{put("resourceId", "-");}});
                add(new HashMap<String, String>(){{put("events", "-");}});
            }});
        }};
        EvaluationHelper evaluationHelper = new EvaluationHelper(mockEventSources);
        Map<String, List<Map<String, String>>> result = evaluationHelper.getPlatformCandidateEventCoverageModel(halfEventsMatch);
        assertTrue(result.equals(mockCoverageModelWhenValid));
    }

    @Test
    public void testGetPlatformCandidateEventCoverageModelHalfOfEventsMatchWithHalfOfProperties()  {
        Map<String, List<Map<String, String>>> mockCoverageModelWhenValid = new HashMap<String, List<Map<String, String>>>() {{
            put("http", new ArrayList<Map<String, String>>(){{
                add(new HashMap<String, String>(){{put("path", "path");}});
                add(new HashMap<String, String>(){{put("methods", "-");}});
            }});
            put("storage", new ArrayList<Map<String, String>>(){{
                add(new HashMap<String, String>(){{put("resourceId", "-");}});
                add(new HashMap<String, String>(){{put("events", "-");}});
            }});
        }};
        EvaluationHelper evaluationHelper = new EvaluationHelper(mockEventSources);
        Map<String, List<Map<String, String>>> result = evaluationHelper.getPlatformCandidateEventCoverageModel(halfEventsMatchWithHalfOfProperties);
        assertTrue(result.equals(mockCoverageModelWhenValid));
    }

    @Test
    public void testGetPlatformCandidateEventCoverageModelWithEmptySetOfSourceAppEventProperties()  {
        Map<String, List<String>> mockEventSourcesWithEmptySetOfProperties = new HashMap<String, List<String>>() {{
            put("http", new ArrayList<>());
            put("storage", new ArrayList<>());
        }};

        Map<String, List<String>> mockTargetEventSources = new HashMap<String, List<String>>() {{
            put("http", new ArrayList<String>(){{
                add("path");
                add("methods");
            }});
            put("storage", new ArrayList<String>(){{
                add("resourceId");
                add("events");
            }});
        }};

        Map<String, List<Map<String, String>>> mockCoverageModel = new HashMap<String, List<Map<String, String>>>() {{
            put("http", new ArrayList<Map<String, String>>(){{}});
            put("storage", new ArrayList<Map<String, String>>(){{}});
        }};
        EvaluationHelper evaluationHelper = new EvaluationHelper(mockEventSourcesWithEmptySetOfProperties);
        Map<String, List<Map<String, String>>> result = evaluationHelper.getPlatformCandidateEventCoverageModel(mockTargetEventSources);
        assertTrue(result.equals(mockCoverageModel));
    }

}
