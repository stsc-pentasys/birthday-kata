package workshop.panda.birthday.messaging;

import javax.mail.MessagingException;

import workshop.panda.birthday.core.BirthdayMessage;

/**
 * Created by schulzst on 16.01.2016.
 */
public interface MessagingPort {
    void sendMail(BirthdayMessage birthdayMessage) throws MessagingException;
}
