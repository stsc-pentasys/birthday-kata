package workshop.panda.birthday.core;

import java.util.List;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Created by schulzst on 15.01.2016.
 */
public class BirthdayService {

    private Properties mailProperties = new Properties();

    private CustomerRepositoryPort customerRepository;

    public BirthdayService(String fileName, int smtpPort, String smtpHost) throws Exception {
        this.customerRepository = new CustomerRepositoryAdapter(fileName);
        this.mailProperties.put("mail.smtp.host", smtpHost);
        this.mailProperties.put("mail.smtp.port", "" + smtpPort);
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
            sendMail(message);
        }
    }

    private String renderBody(BirthDate today, Customer customer) {
        return "Liebe %NAME%, Zu deinem %AGE%. Geburtstag alles Gute ..."
                .replace("%NAME%", customer.getFirstName())
                .replace("%AGE%", Long.toString(customer.age(today)));
    }

    private void sendMail(BirthdayMessage birthdayMessage) throws MessagingException {
        Session session = Session.getInstance(mailProperties, null);
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(birthdayMessage.getFrom()));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(birthdayMessage.getTo()));
        message.setSubject(birthdayMessage.getSubject());
        message.setText(birthdayMessage.getBody());
        Transport.send(message);
    }
}
