package workshop.panda.birthday.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.mail.MessagingException;

import workshop.panda.birthday.core.model.BirthDate;
import workshop.panda.birthday.core.model.BirthdayMessage;
import workshop.panda.birthday.core.model.Customer;
import workshop.panda.birthday.core.model.Gender;

/**
 * Created by schulzst on 15.01.2016.
 */
public class BirthdayServiceBean implements BirthdayService {

    private final CustomerRepository customerRepository;
    private final Messenger messenger;

    private Properties templates;

    public BirthdayServiceBean(CustomerRepository customerRepository, Messenger messenger, Properties templates)
        throws Exception {
        this.customerRepository = customerRepository;
        this.messenger = messenger;
        this.templates = templates;
    }

    @Override
    public void sendGreetings(BirthDate today) throws Exception {
        List<Customer> customers = customerRepository.findCustomersByBirthday(today);
        for (Customer customer : customers) {
            String body = renderBody(today, customer);
            messenger.send(new BirthdayMessage(
                "vertrieb@company.de",
                customer.getEmailAddress(),
                "Alles Gute zum Geburtstag!",
                body
            ));
        }
    }

    public String renderBody(BirthDate today, Customer customer) throws MessagingException, TemplateException {
        Map<String, Object> replacements = new HashMap<>();
        replacements.put("title", customer.getGender() == Gender.FEMALE ? "Liebe" : "Lieber");
        replacements.put("name", customer.getFirstName());
        replacements.put("age", customer.age(today));
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
