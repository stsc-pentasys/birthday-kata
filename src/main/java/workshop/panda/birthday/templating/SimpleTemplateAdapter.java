package workshop.panda.birthday.templating;

import workshop.panda.birthday.core.BirthDate;
import workshop.panda.birthday.core.Customer;

/**
 * Created by schulzst on 16.01.2016.
 */
public class SimpleTemplateAdapter implements TemplatePort {

    private String template;

    public SimpleTemplateAdapter(String template) {
        this.template = template;
    }

    public String renderBody(BirthDate today, Customer customer) {
        return template.replace("%NAME%", customer.getFirstName())
            .replace("%AGE%", Long.toString(customer.age(today)));
    }
}
