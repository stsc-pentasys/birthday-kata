package workshop.panda.birthday.core;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.icegreen.greenmail.junit.GreenMailRule;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetup;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import workshop.panda.birthday.messaging.SmtpMessagingAdapter;
import workshop.panda.birthday.persistence.CsvCustomerRepositoryAdapter;
import workshop.panda.birthday.templating.SimpleTemplateAdapter;

/**
 * Created by schulzst on 15.01.2016.
 */
public class BirthdayServiceIT {

    private static final int SMTP_PORT = 3025;
    private static final String SMTP_HOST = "127.0.0.1";

    private BirthdayService underTest;

    @Rule
    public final GreenMailRule greenMail = new GreenMailRule(new ServerSetup(SMTP_PORT, SMTP_HOST, "smtp"));

    @Before
    public void setUp() throws Exception {
        underTest = new BirthdayService(
            new CsvCustomerRepositoryAdapter("src/test/resources/birthdays.csv"),
            new SmtpMessagingAdapter(SMTP_PORT, SMTP_HOST),
            new SimpleTemplateAdapter("Liebe {name}, Zu deinem {age}. Geburtstag alles Gute ..."));
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

    private void assertMessage(MimeMessage message, BirthdayMessage expected) throws MessagingException {
        BirthdayMessage sent = new BirthdayMessage(
                GreenMailUtil.getAddressList(message.getFrom()),
                GreenMailUtil.getAddressList(message.getRecipients(Message.RecipientType.TO)),
                message.getSubject(),
                GreenMailUtil.getBody(message));

        assertThat("Message", sent, is(expected));
    }
}
