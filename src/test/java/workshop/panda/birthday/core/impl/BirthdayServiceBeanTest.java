package workshop.panda.birthday.core.impl;

import static org.mockito.Mockito.*;
import static workshop.panda.birthday.TestData.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import workshop.panda.birthday.core.BirthdayService;
import workshop.panda.birthday.core.CustomerRepository;
import workshop.panda.birthday.core.Messenger;
import workshop.panda.birthday.core.TemplateEngine;
import workshop.panda.birthday.core.model.BirthDate;

/**
 * Created by schulzst on 19.01.2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class BirthdayServiceBeanTest {

    @InjectMocks
    private BirthdayService underTest = new BirthdayServiceBean();

    @Mock
    private CustomerRepository customerRepositoryMock;

    @Mock
    private TemplateEngine templateEngineMock;

    @Mock
    private Messenger messengerMock;

    @Test
    public void sendsOneMessage() throws Exception {
        BirthDate today = new BirthDate("2016-09-23");

        when(customerRepositoryMock.findByBirthday(today)).thenReturn(defaultCustomerList());
        when(templateEngineMock.replaceInTemplate(defaultReplacements())).thenReturn(DEFAULT_BODY);

        underTest.sendGreetings(today);

        verify(messengerMock).send(DEFAULT_MESSAGE);
    }
}