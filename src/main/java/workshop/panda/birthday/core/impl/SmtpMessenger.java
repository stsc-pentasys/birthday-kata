package workshop.panda.birthday.core.impl;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import workshop.panda.birthday.core.model.BirthdayMessage;

/**
 * Created by schulzst on 16.01.2016.
 */
public class SmtpMessenger {

    private Properties mailProperties = new Properties();

    public SmtpMessenger(int smtpPort, String smtpHost) {
        this.mailProperties.put("mail.smtp.host", smtpHost);
        this.mailProperties.put("mail.smtp.port", "" + smtpPort);
    }

    public void send(BirthdayMessage birthdayMessage) throws MessagingException {
        Session session = Session.getInstance(mailProperties, null);
        Message message1 = new MimeMessage(session);
        message1.setFrom(new InternetAddress(birthdayMessage.getFrom()));
        message1.setRecipient(Message.RecipientType.TO, new InternetAddress(birthdayMessage.getTo()));
        message1.setSubject(birthdayMessage.getSubject());
        message1.setText(birthdayMessage.getBody());
        Message message = message1;
        Transport.send(message);
    }

}
