package workshop.panda.birthday.core;

import java.util.Objects;

/**
 * Created by schulzst on 16.01.2016.
 */
public class Customer {

    private String firstName;
    private String lastName;
    private BirthDate birthday;
    private String emailAddress;
    private Gender gender;

    public Customer(String firstName, String lastName, BirthDate birthday, String emailAddress, Gender gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.emailAddress = emailAddress;
        this.gender = gender;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public Gender getGender() {
        return gender;
    }

    public boolean hasBirthday(BirthDate today) {
        return birthday.isSameDay(today);
    }

    public long age(BirthDate today) {
        return birthday.differenceInYears(today);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(firstName, customer.firstName) &&
                Objects.equals(lastName, customer.lastName) &&
                Objects.equals(birthday, customer.birthday) &&
                Objects.equals(emailAddress, customer.emailAddress) &&
                Objects.equals(gender, customer.gender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, birthday, emailAddress, gender);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Customer{");
        sb.append("firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", birthday=").append(birthday);
        sb.append(", emailAddress='").append(emailAddress).append('\'');
        sb.append(", gender=").append(gender).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
