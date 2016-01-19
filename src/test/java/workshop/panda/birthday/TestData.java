package workshop.panda.birthday;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import workshop.panda.birthday.core.model.BirthDate;
import workshop.panda.birthday.core.model.BirthdayMessage;
import workshop.panda.birthday.core.model.Customer;
import workshop.panda.birthday.core.model.Gender;

/**
 * Created by schulzst on 19.01.2016.
 */
public final class TestData {

    public static final String DEFAULT_BODY = "Lieber Wolfgang ...";

    public static final Customer DEFAULT_CUSTOMER = new Customer(
        "Wolfgang",
        "Blume",
        new BirthDate("1968-09-23"),
        "blumhe@gmx.de",
        Gender.MALE);

    public static final BirthdayMessage DEFAULT_MESSAGE = new BirthdayMessage(
        "vertrieb@company.de",
        DEFAULT_CUSTOMER.getEmailAddress(),
        "Alles Gute zum Geburtstag!",
        DEFAULT_BODY
    );

    public static List<Customer> defaultCustomerList() {
        List<Customer> customers = new ArrayList<>(1);
        customers.add(DEFAULT_CUSTOMER);
        return customers;
    }

    public static Map<String, String> defaultReplacements() {
        Map<String, String> replacements = new HashMap<>();
        replacements.put("title", "Lieber");
        replacements.put("name", DEFAULT_CUSTOMER.getFirstName());
        replacements.put("age", "48");

        return replacements;
    }

    private TestData() {
    }
}
