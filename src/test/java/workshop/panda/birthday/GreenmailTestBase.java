package workshop.panda.birthday;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.icegreen.greenmail.junit.GreenMailRule;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetup;
import org.junit.Rule;
import workshop.panda.birthday.core.model.BirthdayMessage;

/**
 * Created by schulzst on 19.01.2016.
 */
public class GreenmailTestBase {

    protected static final int SMTP_PORT = 3025;
    protected static final String SMTP_HOST = "127.0.0.1";

    @Rule
    public final GreenMailRule greenMail = new GreenMailRule(new ServerSetup(SMTP_PORT, SMTP_HOST, "smtp"));

    protected final void assertMessage(MimeMessage message, BirthdayMessage expected) throws MessagingException {
        BirthdayMessage sent = new BirthdayMessage(
            GreenMailUtil.getAddressList(message.getFrom()),
            GreenMailUtil.getAddressList(message.getRecipients(Message.RecipientType.TO)),
            message.getSubject(),
            GreenMailUtil.getBody(message));
        assertThat("Message", sent, is(expected));
    }
}
