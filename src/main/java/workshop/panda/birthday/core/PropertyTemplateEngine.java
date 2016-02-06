package workshop.panda.birthday.core;

import java.util.Map;
import java.util.Properties;

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