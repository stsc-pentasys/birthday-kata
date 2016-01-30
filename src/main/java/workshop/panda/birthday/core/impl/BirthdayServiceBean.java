package workshop.panda.birthday.core.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import workshop.panda.birthday.core.TemplateException;
import workshop.panda.birthday.core.model.BirthDate;
import workshop.panda.birthday.core.model.BirthdayMessage;
import workshop.panda.birthday.core.BirthdayService;
import workshop.panda.birthday.core.model.Customer;
import workshop.panda.birthday.core.model.Gender;

/**
 * Created by schulzst on 15.01.2016.
 */
public class BirthdayServiceBean implements BirthdayService {

    private CsvCustomerRepository customerRepository;

    private SmtpMessenger messenger;

    private SimpleTemplateEngine templateEngine;

    BirthdayServiceBean() {}

    public BirthdayServiceBean(CsvCustomerRepository customerRepository, SmtpMessenger messenger, SimpleTemplateEngine templateEngine)
        throws Exception {
        this.customerRepository = customerRepository;
        this.messenger = messenger;
        this.templateEngine = templateEngine;
    }

    @Override
    public void sendGreetings(BirthDate today) throws Exception {
        List<Customer> customers = customerRepository.findByBirthday(today);
        for (Customer customer : customers) {
            messenger.send(new BirthdayMessage(
                    "vertrieb@company.de",
                    customer.getEmailAddress(),
                    "Alles Gute zum Geburtstag!",
                    createMessageBody(today, customer)
                ));
        }
    }

    private String createMessageBody(BirthDate today, Customer customer) throws TemplateException {
        Map<String, Object> replacements = new HashMap<>();
        replacements.put("title", customer.getGender() == Gender.FEMALE ? "Liebe" : "Lieber");
        replacements.put("name", customer.getFirstName());
        replacements.put("age", customer.age(today));
        return templateEngine.replaceInTemplate("standard", replacements);
    }

}
