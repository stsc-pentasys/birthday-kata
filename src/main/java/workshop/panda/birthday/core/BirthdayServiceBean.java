package workshop.panda.birthday.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import workshop.panda.birthday.core.model.BirthDate;
import workshop.panda.birthday.core.model.BirthdayMessage;
import workshop.panda.birthday.core.model.Customer;
import workshop.panda.birthday.core.model.Gender;

/**
 * Created by schulzst on 15.01.2016.
 */
public class BirthdayServiceBean implements BirthdayService {

    private final CustomerRepository customerRepository;

    private Properties mailProperties = new Properties();

    private Properties templates;

    public BirthdayServiceBean(CustomerRepository customerRepository, int smtpPort, String smtpHost, Properties templates)
        throws Exception {
        this.customerRepository = customerRepository;
        this.mailProperties.put("mail.smtp.host", smtpHost);
        this.mailProperties.put("mail.smtp.port", "" + smtpPort);
        this.templates = templates;
    }

    @Override
    public void sendGreetings(BirthDate today) throws Exception {
        List<Customer> customers = customerRepository.findCustomersByBirthday(today);
        for (Customer customer : customers) {
            String body = renderBody(today, customer);
            send(new BirthdayMessage(
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

    public void send(BirthdayMessage birthdayMessage) throws MessagingException {
        Session session = Session.getInstance(mailProperties, null);
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(birthdayMessage.getSender()));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(birthdayMessage.getRecipient()));
        message.setSubject(birthdayMessage.getSubject());
        message.setText(birthdayMessage.getBody());
        Transport.send(message);
    }

}
