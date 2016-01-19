package workshop.panda.birthday.core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Created by schulzst on 15.01.2016.
 */
public class BirthdayService {

    private String fileName;

    private Properties mailProperties = new Properties();

    public BirthdayService(String fileName, int smtpPort, String smtpHost) {
        this.fileName = fileName;
        this.mailProperties.put("mail.smtp.host", smtpHost);
        this.mailProperties.put("mail.smtp.port", "" + smtpPort);
    }

    public void sendGreetings(BirthDate today) throws Exception {
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
            if (customer.hasBirthday(today)) {
                String body = "Liebe {name}, Zu deinem {age}. Geburtstag alles Gute ..."
                        .replace("{name}", customer.getFirstName())
                        .replace("{age}", Long.toString(customer.age(today)));

                Session session = Session.getInstance(mailProperties, null);
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("vertrieb@company.de"));
                message.setRecipient(Message.RecipientType.TO, new InternetAddress(customer.getEmailAddress()));
                message.setSubject("Alles Gute zum Geburtstag!");
                message.setText(body);
                Transport.send(message);
            }
        }
        in.close();
    }
}
