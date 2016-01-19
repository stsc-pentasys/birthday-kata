package workshop.panda.birthday.core.impl;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static workshop.panda.birthday.TestData.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ietf.jgss.MessageProp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import workshop.panda.birthday.TestData;
import workshop.panda.birthday.core.BirthdayService;
import workshop.panda.birthday.core.CustomerRepositoryPort;
import workshop.panda.birthday.core.MessagingPort;
import workshop.panda.birthday.core.TemplatePort;
import workshop.panda.birthday.core.model.BirthDate;
import workshop.panda.birthday.core.model.BirthdayMessage;
import workshop.panda.birthday.core.model.Customer;
import workshop.panda.birthday.core.model.Gender;

/**
 * Created by schulzst on 19.01.2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class BirthdayServiceBeanTest {

    @InjectMocks
    private BirthdayService underTest = new BirthdayServiceBean();

    @Mock
    private CustomerRepositoryPort customerRepositoryPortMock;

    @Mock
    private TemplatePort templatePortMock;

    @Mock
    private MessagingPort messagingPortMock;

    @Test
    public void sendsOneMessage() throws Exception {
        BirthDate today = new BirthDate("2016-09-23");

        when(customerRepositoryPortMock.findCustomersWithBirthday(today)).thenReturn(defaultCustomerList());
        when(templatePortMock.fillTemplate(defaultReplacements())).thenReturn(DEFAULT_BODY);

        underTest.sendGreetings(today);

        verify(messagingPortMock).sendMail(DEFAULT_MESSAGE);
    }
}