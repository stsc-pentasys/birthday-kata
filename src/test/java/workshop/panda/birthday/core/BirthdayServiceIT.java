package workshop.panda.birthday.core;

import com.icegreen.greenmail.junit.GreenMailRule;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetup;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.mail.internet.MimeMessage;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

/**
 * Created by schulzst on 15.01.2016.
 */
public class BirthdayServiceIT {

    private BirthdayService underTest = new BirthdayService();

    private GreenMail greenMail = new GreenMail(new ServerSetup(3025, "127.0.0.1", "smtp"));

    @Before
    public void setUp() throws Exception {
        greenMail.start();
    }

    @After
    public void tearDown() throws Exception {
        greenMail.stop();
    }

    @Test
    public void underTestAvailable() throws Exception {
        assertThat("Test instance", underTest, notNullValue());
    }

    @Test
    public void smptServerAvailable() throws Exception {
        GreenMailUtil.sendTextEmailTest("john.doe@apple.com", "jane.dough@oracle.com", "Test", "This is a test!");
        MimeMessage[] emails = greenMail.getReceivedMessages();
        assertThat("Number of messages", emails.length, is(1));
    }
}
