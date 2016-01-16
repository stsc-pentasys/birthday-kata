package workshop.panda.birthday.core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
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

    public void sendBirthdayGreetings(BirthDate today) throws Exception {
        BufferedReader in = new BufferedReader(new FileReader(fileName));
        String str = "";
        str = in.readLine();
        while ((str = in.readLine()) != null) {
            String[] rawData = str.split(";");
            Customer customer = new Customer(
                    rawData[1],
                    rawData[0],
                    new BirthDate(rawData[2]),
                    rawData[3],
                    Gender.valueOf(rawData[4]));
            if (customer.getBirthday().isSameDay(today)) {
                String recipient = customer.getEmailAddress();
                String subject = "Alles Gute zum Geburtstag!";
                String body = "Liebe %NAME%, Zu deinem %AGE%. Geburtstag alles Gute ..."
                        .replace("%NAME%", customer.getFirstName())
                        .replace("%AGE%", Long.toString(customer.getBirthday().ageAt(today)));

                Session session = Session.getInstance(mailProperties, null);
                Message msg = new MimeMessage(session);
                msg.setFrom(new InternetAddress("vertrieb@company.de"));
                msg.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
                msg.setSubject(subject);
                msg.setText(body);
                Transport.send(msg);
            }
        }
        in.close();
    }
}
