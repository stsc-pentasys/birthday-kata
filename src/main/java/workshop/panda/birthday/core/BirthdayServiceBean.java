package workshop.panda.birthday.core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
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
import workshop.panda.birthday.core.model.Customer;
import workshop.panda.birthday.core.model.Gender;

/**
 * Created by schulzst on 15.01.2016.
 */
public class BirthdayServiceBean implements BirthdayService {

    private String fileName;

    private Properties mailProperties = new Properties();

    private Properties templates;

    public BirthdayServiceBean(String fileName, int smtpPort, String smtpHost, Properties templates)
        throws Exception {
        this.fileName = fileName;
        this.mailProperties.put("mail.smtp.host", smtpHost);
        this.mailProperties.put("mail.smtp.port", "" + smtpPort);
        this.templates = templates;
    }

    @Override
    public void sendGreetings(BirthDate today) throws Exception {
        List<Customer> customers = findCustomersByBirthday(today);
        for (Customer customer : customers) {
            render(today, customer);
        }
    }

    private List<Customer> findCustomersByBirthday(BirthDate today) throws IOException {
        List<Customer> customers = new LinkedList<>();
        BufferedReader in = new BufferedReader(new FileReader(fileName));
        try {
            String line = in.readLine();
            while ((line = in.readLine()) != null) {
                Customer customer = parseCustomer(line);
                if (customer.hasBirthday(today)) {
                    customers.add(customer);
                }
            }
        } finally {
            in.close();
        }
        return customers;
    }

    private Customer parseCustomer(String line) {
        String[] rawData = line.split(";");
        return new Customer(
            rawData[1],
            rawData[0],
            new BirthDate(rawData[2]),
            rawData[3],
            Gender.valueOf(rawData[4]));
    }

    private void render(BirthDate today, Customer customer) throws MessagingException, TemplateException {
        Map<String, Object> replacements = new HashMap<>();
        replacements.put("title", customer.getGender() == Gender.FEMALE ? "Liebe" : "Lieber");
        replacements.put("name", customer.getFirstName());
        replacements.put("age", customer.age(today));
        if (templates.containsKey("standard")) {
            String body = templates.getProperty("standard");
            for (Map.Entry<String, Object> entry : replacements.entrySet()) {
                body = body.replace("{" + entry.getKey() + "}", entry.getValue().toString());
            }
            send(customer, body);
        } else {
            throw new TemplateException("No template found with id 'standard'");
        }
    }

    private void send(Customer customer, String body) throws MessagingException {
        Session session = Session.getInstance(mailProperties, null);
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("vertrieb@company.de"));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(customer.getEmailAddress()));
        message.setSubject("Alles Gute zum Geburtstag!");
        message.setText(body);
        Transport.send(message);
    }

}
