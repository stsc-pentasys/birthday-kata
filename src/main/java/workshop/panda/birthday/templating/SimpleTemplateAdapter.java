package workshop.panda.birthday.templating;

import workshop.panda.birthday.core.BirthDate;
import workshop.panda.birthday.core.Customer;
import workshop.panda.birthday.core.TemplatePort;

/**
 * Created by schulzst on 16.01.2016.
 */
public class SimpleTemplateAdapter implements TemplatePort {

    private String template;

    public SimpleTemplateAdapter(String template) {
        this.template = template;
    }

    public String renderBody(BirthDate today, Customer customer) {
        return template.replace("{name}", customer.getFirstName())
            .replace("{age}", Long.toString(customer.age(today)));
    }
}
