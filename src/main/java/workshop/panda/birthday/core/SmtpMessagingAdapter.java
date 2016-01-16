package workshop.panda.birthday.core;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Created by schulzst on 16.01.2016.
 */
public class SmtpMessagingAdapter implements MessagingPort {

    private Properties mailProperties = new Properties();

    public SmtpMessagingAdapter(int smtpPort, String smtpHost) {
        this.mailProperties.put("mail.smtp.host", smtpHost);
        this.mailProperties.put("mail.smtp.port", "" + smtpPort);
    }

    @Override
    public void sendMail(BirthdayMessage birthdayMessage) throws MessagingException {
        Session session = Session.getInstance(mailProperties, null);
        Message message = transform(birthdayMessage, session);
        Transport.send(message);
    }

    private Message transform(BirthdayMessage birthdayMessage, Session session) throws MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(birthdayMessage.getFrom()));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(birthdayMessage.getTo()));
        message.setSubject(birthdayMessage.getSubject());
        message.setText(birthdayMessage.getBody());
        return message;
    }
}
