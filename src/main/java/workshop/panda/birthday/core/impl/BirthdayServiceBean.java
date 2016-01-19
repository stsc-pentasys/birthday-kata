package workshop.panda.birthday.core.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import workshop.panda.birthday.core.model.BirthDate;
import workshop.panda.birthday.core.model.BirthdayMessage;
import workshop.panda.birthday.core.BirthdayService;
import workshop.panda.birthday.core.model.Customer;
import workshop.panda.birthday.core.CustomerRepositoryPort;
import workshop.panda.birthday.core.MessagingPort;
import workshop.panda.birthday.core.TemplatePort;
import workshop.panda.birthday.core.model.Gender;

/**
 * Created by schulzst on 15.01.2016.
 */
public class BirthdayServiceBean implements BirthdayService {

    private CustomerRepositoryPort customerRepository;

    private MessagingPort messagingPort;

    private TemplatePort templatePort;

    BirthdayServiceBean() {}

    public BirthdayServiceBean(CustomerRepositoryPort customerRepository, MessagingPort messagingPort, TemplatePort templatePort)
        throws Exception {
        this.customerRepository = customerRepository;
        this.messagingPort = messagingPort;
        this.templatePort = templatePort;
    }

    @Override
    public void sendGreetings(BirthDate today) throws Exception {
        List<Customer> customers = customerRepository.findCustomersWithBirthday(today);
        for (Customer customer : customers) {
            messagingPort.sendMail(new BirthdayMessage(
                    "vertrieb@company.de",
                    customer.getEmailAddress(),
                    "Alles Gute zum Geburtstag!",
                    createMessageBody(today, customer)
                ));
        }
    }

    private String createMessageBody(BirthDate today, Customer customer) {
        Map<String, String> replacements = new HashMap<>();
        replacements.put("title", customer.getGender() == Gender.FEMALE ? "Liebe" : "Lieber");
        replacements.put("name", customer.getFirstName());
        replacements.put("age", Long.toString(customer.age(today)));
        return templatePort.fillTemplate(replacements);
    }

}
