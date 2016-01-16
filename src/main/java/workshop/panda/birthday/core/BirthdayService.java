package workshop.panda.birthday.core;

import java.util.List;

/**
 * Created by schulzst on 15.01.2016.
 */
public class BirthdayService {

    private CustomerRepositoryPort customerRepository;

    private MessagingPort messagingPort;

    public BirthdayService(String fileName, int smtpPort, String smtpHost) throws Exception {
        this.customerRepository = new CustomerRepositoryAdapter(fileName);
        this.messagingPort = new SmtpMessagingAdapter(smtpPort, smtpHost);
    }

    public void sendGreetings(BirthDate today) throws Exception {
        List<Customer> customers = customerRepository.findCustomersWithBirthday(today);
        for (Customer customer : customers) {
            BirthdayMessage message = new BirthdayMessage(
                "vertrieb@company.de",
                customer.getEmailAddress(),
                "Alles Gute zum Geburtstag!",
                renderBody(today, customer)
            );
            messagingPort.sendMail(message);
        }
    }

    private String renderBody(BirthDate today, Customer customer) {
        return "Liebe %NAME%, Zu deinem %AGE%. Geburtstag alles Gute ..."
                .replace("%NAME%", customer.getFirstName())
                .replace("%AGE%", Long.toString(customer.age(today)));
    }

}
