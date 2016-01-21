package workshop.panda.birthday.core;

import java.util.Map;

/**
 * Created by schulzst on 16.01.2016.
 */
public interface TemplateEngine {

    String replaceInTemplate(String templateId, Map<String, Object> variables) throws TemplateException;

}
