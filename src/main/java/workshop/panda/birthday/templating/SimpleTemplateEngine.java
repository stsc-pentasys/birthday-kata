package workshop.panda.birthday.templating;

import java.util.Map;

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
    public String replaceInTemplate(Map<String, String> variables) {
        String result = template;
        for (Map.Entry<String, String> entry : variables.entrySet()) {
            result = result.replace("{" + entry.getKey() + "}", entry.getValue());
        }
        return result;
    }
}
