package workshop.panda.birthday.core;

import java.util.Map;

import workshop.panda.birthday.core.model.BirthDate;
import workshop.panda.birthday.core.model.Customer;

/**
 * Created by schulzst on 16.01.2016.
 */
public interface TemplateEngine {

    String replaceInTemplate(Map<String, String> variables);

}
