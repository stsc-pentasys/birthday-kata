package workshop.panda.birthday.templating;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import workshop.panda.birthday.core.TemplateEngine;
import workshop.panda.birthday.core.TemplateException;

/**
 * Created by schulzst on 19.01.2016.
 */
public class SimpleTemplateEngineIT {

    private TemplateEngine underTest;

    private Properties templates = new Properties();

    @Before
    public void setUp() throws Exception {
        templates.setProperty("standard", "{number} bottles of {drink}");
        templates.setProperty("special", "the {number}th flagon of {drink}");

        underTest = new SimpleTemplateEngine(templates);
    }

    @Test
    public void variablesReplaced() throws Exception {
        Map<String, Object> variables = new HashMap<>();
        variables.put("number", 99);
        variables.put("drink", "beer");

        String result = underTest.replaceInTemplate("standard", variables);

        assertThat("Filled template", result, is("99 bottles of beer"));
    }

    @Test
    public void resultDependsOnTemplate() throws Exception {
        Map<String, Object> variables = new HashMap<>();
        variables.put("number", 10);
        variables.put("drink", "wine");

        String result = underTest.replaceInTemplate("special", variables);

        assertThat("Filled template", result, is("the 10th flagon of wine"));
    }

    @Test(expected = TemplateException.class)
    public void failsForUnknownTemplate() throws Exception {
        Map<String, Object> variables = new HashMap<>();
        underTest.replaceInTemplate("unknown", variables);
    }
}