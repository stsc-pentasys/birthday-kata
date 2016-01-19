package workshop.panda.birthday.persistence;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import workshop.panda.birthday.core.model.BirthDate;
import workshop.panda.birthday.core.model.Customer;
import workshop.panda.birthday.core.model.Gender;

/**
 * Created by schulzst on 19.01.2016.
 */
public class CsvCustomerRepositoryIT {

    private CsvCustomerRepository underTest;

    @Before
    public void setUp() throws Exception {
        underTest = new CsvCustomerRepository("src/test/resources/birthdays.csv");
    }

    @Test
    public void singleCustomerWithMatchingBirthday() throws Exception {
        BirthDate today = new BirthDate("2016-08-19");
        List<Customer> result = underTest.findByBirthday(today);
        assertThat("Number of customers", result.size(), is(1));
        Customer single = result.get(0);
        assertThat("First name", single.getFirstName(), is("Anastasia"));
        assertThat("Last name", single.getLastName(), is("Baum"));
        assertThat("Birthday", single.age(today), is(38L));
        assertThat("Email", single.getEmailAddress(), is("anastasia.baum@mnet-mail.de"));
        assertThat("Gender", single.getGender(), is(Gender.FEMALE));
    }

    @Test
    public void moreThanOneCustomerWithMatchingBirthday() throws Exception {
        BirthDate today = new BirthDate("2016-09-23");
        List<Customer> result = underTest.findByBirthday(today);
        assertThat("Number of customers", result.size(), is(2));
    }

    @Test
    public void emptyResultIfNoMatchingBirthday() throws Exception {
        BirthDate today = new BirthDate("2016-12-12");
        List<Customer> result = underTest.findByBirthday(today);
        assertThat("No customers", result.isEmpty());
    }
}