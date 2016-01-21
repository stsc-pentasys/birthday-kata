package workshop.panda.birthday.templating;

import java.util.Map;
import java.util.Objects;

import workshop.panda.birthday.core.TemplateEngine;

/**
 * Created by schulzst on 16.01.2016.
 */
public class SimpleTemplateEngine implements TemplateEngine {

    private String template;

    public SimpleTemplateEngine(String template) {
        this.template = template;
    }

    @Override
    public String replaceInTemplate(Map<String, Object> variables) {
        String result = template;
        for (Map.Entry<String, Object> entry : variables.entrySet()) {
            result = result.replace("{" + entry.getKey() + "}", entry.getValue().toString());
        }
        return result;
    }
}
