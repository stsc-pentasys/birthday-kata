package workshop.panda.birthday.core;

import java.util.List;

/**
 * Created by schulzst on 15.01.2016.
 */
public class BirthdayServiceBean implements BirthdayService {

    private CustomerRepositoryPort customerRepository;

    private MessagingPort messagingPort;

    private TemplatePort templatePort;

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
            BirthdayMessage message = createMessage(today, customer);
            messagingPort.sendMail(message);
        }
    }

    private BirthdayMessage createMessage(BirthDate today, Customer customer) {
        return new BirthdayMessage(
            "vertrieb@company.de",
            customer.getEmailAddress(),
            "Alles Gute zum Geburtstag!",
            templatePort.renderBody(today, customer)
        );
    }

}
