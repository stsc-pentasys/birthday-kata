package workshop.panda.birthday.messaging;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import javax.mail.internet.MimeMessage;

import org.junit.Test;
import workshop.panda.birthday.GreenmailTestBase;
import workshop.panda.birthday.TestData;
import workshop.panda.birthday.core.Messenger;

/**
 * Created by schulzst on 19.01.2016.
 */
public class SmtpMessengerIT extends GreenmailTestBase {

    private Messenger underTest = new SmtpMessenger(SMTP_PORT, SMTP_HOST);

    @Test
    public void singleMailSent() throws Exception {
        underTest.send(TestData.DEFAULT_MESSAGE);
        MimeMessage[] emails = greenMail.getReceivedMessages();
        assertThat("Number of messages", emails.length, is(1));
        assertMessage(emails[0], TestData.DEFAULT_MESSAGE);
    }
}