package workshop.panda.birthday.templating;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import workshop.panda.birthday.core.TemplateEngine;

/**
 * Created by schulzst on 19.01.2016.
 */
public class SimpleTemplateEngineIT {

    private TemplateEngine underTest = new SimpleTemplateEngine("{number} bottles of {drink}");

    @Test
    public void variablesReplaced() throws Exception {
        Map<String, Object> variables = new HashMap<>();
        variables.put("number", 99);
        variables.put("drink", "beer");

        String result = underTest.replaceInTemplate(variables);

        assertThat("Filled template", result, is("99 bottles of beer"));
    }
}