package workshop.panda.birthday.core;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.Properties;
import javax.mail.internet.MimeMessage;

import org.junit.Before;
import org.junit.Test;
import workshop.panda.birthday.GreenmailTestBase;
import workshop.panda.birthday.core.impl.BirthdayServiceBean;
import workshop.panda.birthday.core.model.BirthDate;
import workshop.panda.birthday.core.model.BirthdayMessage;

/**
 * Created by schulzst on 15.01.2016.
 */
public class BirthdayServiceIT extends GreenmailTestBase {

    private BirthdayService underTest;

    @Before
    public void setUp() throws Exception {
        Properties templates = new Properties();
        templates.setProperty("standard", "{title} {name}, Zu deinem {age}. Geburtstag alles Gute ...");

        underTest = new BirthdayServiceBean(
            "src/test/resources/birthdays.csv",
            SMTP_PORT, SMTP_HOST,
            templates);
    }

    @Test
    public void emailSentToOneCustomer() throws Exception {
        underTest.sendGreetings(new BirthDate("2016-08-19"));
        MimeMessage[] emails = greenMail.getReceivedMessages();
        assertThat("Number of messages", emails.length, is(1));
        assertMessage(emails[0], new BirthdayMessage(
                "vertrieb@company.de",
                "anastasia.baum@mnet-mail.de",
                "Alles Gute zum Geburtstag!",
                "Liebe Anastasia, Zu deinem 38. Geburtstag alles Gute ..."));
    }

    @Test
    public void emailsForCustomersWithSameBirthday() throws Exception {
        underTest.sendGreetings(new BirthDate("2016-09-23"));
        MimeMessage[] emails = greenMail.getReceivedMessages();
        assertThat("Number of messages", emails.length, is(2));
    }
}
