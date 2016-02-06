package workshop.panda.birthday.core;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.Properties;
import javax.mail.internet.MimeMessage;

import com.icegreen.greenmail.junit.GreenMailRule;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetup;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import workshop.panda.birthday.core.model.BirthDate;

/**
 * Created by schulzst on 15.01.2016.
 */
public class BirthdayServiceIT {

    protected static final int SMTP_PORT = 3025;
    protected static final String SMTP_HOST = "127.0.0.1";

    private BirthdayService underTest;

    @Rule
    public final GreenMailRule greenMail = new GreenMailRule(new ServerSetup(BirthdayServiceIT.SMTP_PORT, BirthdayServiceIT.SMTP_HOST, "smtp"));

    @Before
    public void setUp() throws Exception {
        Properties templates = new Properties();
        templates.setProperty("standard", "{title} {name}, Zu deinem {age}. Geburtstag alles Gute ...");

        underTest = new BirthdayServiceBean(
            new CsvCustomerRepository("src/test/resources/birthdays.csv"),
            new SmtpMessenger(SMTP_PORT, SMTP_HOST),
            templates);
    }

    @Test
    public void emailSentToOneCustomer() throws Exception {
        underTest.sendGreetings(new BirthDate("2016-08-19"));
        MimeMessage[] emails = greenMail.getReceivedMessages();
        assertThat("Number of messages", emails.length, is(1));
        MimeMessage message = emails[0];
        assertThat("From", message.getFrom()[0].toString(), is("vertrieb@company.de"));
        assertThat("To", GreenMailUtil.getAddressList(message.getAllRecipients()), is("anastasia.baum@mnet-mail.de"));
        assertThat("Subject", message.getSubject(), is("Alles Gute zum Geburtstag!"));
        assertThat("Body", GreenMailUtil.getBody(message), is("Liebe Anastasia, Zu deinem 38. Geburtstag alles Gute ..."));
    }

    @Test
    public void emailsForCustomersWithSameBirthday() throws Exception {
        underTest.sendGreetings(new BirthDate("2016-09-23"));
        MimeMessage[] emails = greenMail.getReceivedMessages();
        assertThat("Number of messages", emails.length, is(2));
    }

}
