package workshop.panda.birthday.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private final TemplateEngine templateEngine;

    public BirthdayServiceBean(CustomerRepository customerRepository, Messenger messenger, TemplateEngine templateEngine) {
        this.customerRepository = customerRepository;
        this.messenger = messenger;
        this.templateEngine = templateEngine;
    }

    @Override
    public void sendGreetings(BirthDate today) throws Exception {
        List<Customer> customers = customerRepository.findCustomersByBirthday(today);
        for (Customer customer : customers) {
            Map<String, Object> replacements = createReplacements(today, customer);
            String body = templateEngine.render(replacements);
            messenger.send(createBirthdayMessage(customer, body));
        }
    }

    private Map<String, Object> createReplacements(BirthDate today, Customer customer) {
        Map<String, Object> replacements = new HashMap<>();
        replacements.put("title", customer.getGender() == Gender.FEMALE ? "Liebe" : "Lieber");
        replacements.put("name", customer.getFirstName());
        replacements.put("age", customer.age(today));
        return replacements;
    }

    private BirthdayMessage createBirthdayMessage(Customer customer, String body) {
        return new BirthdayMessage(
            "vertrieb@company.de",
            customer.getEmailAddress(),
            "Alles Gute zum Geburtstag!",
            body
        );
    }

}
