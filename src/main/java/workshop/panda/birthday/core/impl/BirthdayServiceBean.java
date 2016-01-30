package workshop.panda.birthday.core.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import workshop.panda.birthday.core.BirthdayService;
import workshop.panda.birthday.core.TemplateException;
import workshop.panda.birthday.core.model.BirthDate;
import workshop.panda.birthday.core.model.BirthdayMessage;
import workshop.panda.birthday.core.model.Customer;
import workshop.panda.birthday.core.model.Gender;

/**
 * Created by schulzst on 15.01.2016.
 */
public class BirthdayServiceBean implements BirthdayService {

    private List<Customer> allCustomers = new ArrayList<>();

    private Properties mailProperties = new Properties();

    private Properties templates;

    BirthdayServiceBean() {
    }

    public BirthdayServiceBean(String fileName, int smtpPort, String smtpHost, Properties templates)
        throws Exception {
        BufferedReader in = new BufferedReader(new FileReader(fileName));
        String line = in.readLine();
        while ((line = in.readLine()) != null) {
            String[] rawData = line.split(";");
            Customer customer = new Customer(
                rawData[1],
                rawData[0],
                new BirthDate(rawData[2]),
                rawData[3],
                Gender.valueOf(rawData[4]));
            allCustomers.add(customer);
        }
        in.close();
        this.mailProperties.put("mail.smtp.host", smtpHost);
        this.mailProperties.put("mail.smtp.port", "" + smtpPort);
        this.templates = templates;
    }

    @Override
    public void sendGreetings(BirthDate today) throws Exception {
        List<Customer> customers = allCustomers.stream()
            .filter(c -> c.hasBirthday(today))
            .collect(Collectors.toList());
        for (Customer customer : customers) {
            Map<String, Object> replacements = new HashMap<>();
            replacements.put("title", customer.getGender() == Gender.FEMALE ? "Liebe" : "Lieber");
            replacements.put("name", customer.getFirstName());
            replacements.put("age", customer.age(today));
            String result1;
            if (templates.containsKey("standard")) {
                String result = templates.getProperty("standard");
                for (Map.Entry<String, Object> entry : replacements.entrySet()) {
                    result = result.replace("{" + entry.getKey() + "}", entry.getValue().toString());
                }
                result1 = result;
            } else {
                throw new TemplateException("No template found with id '" + "standard" + "'");
            }
            BirthdayMessage birthdayMessage = new BirthdayMessage(
                "vertrieb@company.de",
                customer.getEmailAddress(),
                "Alles Gute zum Geburtstag!",
                result1
            );
            Session session = Session.getInstance(mailProperties, null);
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(birthdayMessage.getFrom()));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(birthdayMessage.getTo()));
            message.setSubject(birthdayMessage.getSubject());
            message.setText(birthdayMessage.getBody());
            Transport.send(message);
        }
    }

}
