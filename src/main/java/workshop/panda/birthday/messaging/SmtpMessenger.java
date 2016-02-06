package workshop.panda.birthday.messaging;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import workshop.panda.birthday.core.Messenger;
import workshop.panda.birthday.core.model.BirthdayMessage;

public class SmtpMessenger implements Messenger {

    private Properties mailProperties = new Properties();

    public SmtpMessenger(int smtpPort, String smtpHost) {
        this.mailProperties.put("mail.smtp.host", smtpHost);
        this.mailProperties.put("mail.smtp.port", "" + smtpPort);
    }

    @Override
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