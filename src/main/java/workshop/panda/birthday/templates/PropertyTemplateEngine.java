package workshop.panda.birthday.templates;

import java.util.Map;
import java.util.Properties;

import workshop.panda.birthday.core.TemplateEngine;
import workshop.panda.birthday.core.TemplateException;

public class PropertyTemplateEngine implements TemplateEngine {

    private Properties templates;

    public PropertyTemplateEngine(Properties templates) {
        this.templates = templates;
    }

    @Override
    public String render(Map<String, Object> replacements) throws TemplateException {
        if (templates.containsKey("standard")) {
            String body = templates.getProperty("standard");
            for (Map.Entry<String, Object> entry : replacements.entrySet()) {
                body = body.replace("{" + entry.getKey() + "}", entry.getValue().toString());
            }
            return body;
        } else {
            throw new TemplateException("No template found with id 'standard'");
        }
    }
}