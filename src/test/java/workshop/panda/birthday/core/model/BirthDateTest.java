package workshop.panda.birthday.core.model;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Test;
import workshop.panda.birthday.core.model.BirthDate;

/**
 * Created by schulzst on 16.01.2016.
 */
public class BirthDateTest {

    private BirthDate underTest = new BirthDate("2000-01-16");

    @Test
    public void isTrueIfSameMonthAndDay() throws Exception {
        BirthDate other = new BirthDate("2011-01-16");
        assertThat("Same day", underTest.isSameDay(other));
    }

    @Test
    public void falseIfOtherDay() throws Exception {
        BirthDate other = new BirthDate("2000-01-17");
        assertThat("Other day", not(underTest.isSameDay(other)));
    }

    @Test
    public void falseIfOtherMonth() throws Exception {
        BirthDate other = new BirthDate("2000-02-16");
        assertThat("Other month", not(underTest.isSameDay(other)));
    }

    @Test
    public void ageAtOneDayBeforeBirthday() throws Exception {
        BirthDate today = new BirthDate("2016-01-15");
        long age = underTest.differenceInYears(today);
        assertThat("Age", age, is(15L));
    }

    @Test
    public void ageOnBirthday() throws Exception {
        BirthDate today = new BirthDate("2016-01-16");
        long age = underTest.differenceInYears(today);
        assertThat("Age", age, is(16L));
    }
}