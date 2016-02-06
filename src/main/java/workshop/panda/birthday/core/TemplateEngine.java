package workshop.panda.birthday.core;

import java.util.Map;

/**
 * Created by schulzst on 06.02.2016.
 */
public interface TemplateEngine {
    String render(Map<String, Object> replacements) throws TemplateException;
}
