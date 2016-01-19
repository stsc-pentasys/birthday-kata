package workshop.panda.birthday.templating;

import java.util.Map;

import workshop.panda.birthday.core.model.BirthDate;
import workshop.panda.birthday.core.model.Customer;
import workshop.panda.birthday.core.TemplatePort;

/**
 * Created by schulzst on 16.01.2016.
 */
public class SimpleTemplateAdapter implements TemplatePort {

    private String template;

    public SimpleTemplateAdapter(String template) {
        this.template = template;
    }

    @Override
    public String fillTemplate(Map<String, String> replacements) {
        String result = template;
        for (Map.Entry<String, String> entry : replacements.entrySet()) {
            result = result.replace("{" + entry.getKey() + "}", entry.getValue());
        }
        return result;
    }
}
