package workshop.panda.birthday.core;

import javax.mail.MessagingException;

import workshop.panda.birthday.core.model.BirthdayMessage;

/**
 * Created by schulzst on 16.01.2016.
 */
public interface Messenger {

    void send(BirthdayMessage birthdayMessage) throws MessagingException;

}
