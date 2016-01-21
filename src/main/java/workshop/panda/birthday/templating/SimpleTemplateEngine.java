package workshop.panda.birthday.templating;

import java.util.Map;
import java.util.Properties;

import workshop.panda.birthday.core.TemplateEngine;
import workshop.panda.birthday.core.TemplateException;

/**
 * Created by schulzst on 16.01.2016.
 */
public class SimpleTemplateEngine implements TemplateEngine {

    private Properties templates;

    public SimpleTemplateEngine(Properties templates) {
        this.templates = templates;
    }

    @Override
    public String replaceInTemplate(String templateId, Map<String, Object> variables) throws TemplateException {
        if (templates.containsKey(templateId)) {
            String result = templates.getProperty(templateId);
            for (Map.Entry<String, Object> entry : variables.entrySet()) {
                result = result.replace("{" + entry.getKey() + "}", entry.getValue().toString());
            }
            return result;
        } else {
            throw new TemplateException("No template found with id '" + templateId + "'");
        }
    }
}
