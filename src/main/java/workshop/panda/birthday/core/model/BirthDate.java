package workshop.panda.birthday.core.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * Created by schulzst on 16.01.2016.
 */
public class BirthDate {

    private LocalDate date;

    public BirthDate(LocalDate date) {
        this.date = date;
    }

    public BirthDate(String formatted) {
        this(LocalDate.parse(formatted));
    }

    public BirthDate() {
        this(LocalDate.now());
    }

    public boolean isSameDay(BirthDate birthday) {
        boolean sameDayOfMonth = this.date.getDayOfMonth() == birthday.date.getDayOfMonth();
        boolean sameMonth = this.date.getMonthValue() == birthday.date.getMonthValue();
        return sameDayOfMonth && sameMonth;
    }

    public long differenceInYears(BirthDate today) {
        return date.until(today.date, ChronoUnit.YEARS);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BirthDate birthday = (BirthDate) o;
        return Objects.equals(date, birthday.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BirthDate{");
        sb.append("date=").append(date);
        sb.append('}');
        return sb.toString();
    }
}
