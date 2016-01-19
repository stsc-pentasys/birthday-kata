package workshop.panda.birthday.core;

import javax.mail.MessagingException;

import workshop.panda.birthday.core.model.BirthdayMessage;

/**
 * Created by schulzst on 16.01.2016.
 */
public interface MessagingPort {
    void sendMail(BirthdayMessage birthdayMessage) throws MessagingException;
}
