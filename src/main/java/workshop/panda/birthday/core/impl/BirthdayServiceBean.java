package workshop.panda.birthday.core.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import workshop.panda.birthday.core.BirthdayService;
import workshop.panda.birthday.core.model.BirthDate;
import workshop.panda.birthday.core.model.BirthdayMessage;
import workshop.panda.birthday.core.model.Customer;
import workshop.panda.birthday.core.model.Gender;

/**
 * Created by schulzst on 15.01.2016.
 */
public class BirthdayServiceBean implements BirthdayService {

    private SmtpMessenger messenger;

    private SimpleTemplateEngine templateEngine;

    private List<Customer> allCustomers = new ArrayList<>();

    BirthdayServiceBean() {
    }

    public BirthdayServiceBean(String fileName, SmtpMessenger messenger, SimpleTemplateEngine templateEngine)
        throws Exception {
        BufferedReader in = new BufferedReader(new FileReader(fileName));
        String line = in.readLine();
        while ((line = in.readLine()) != null) {
            String[] rawData = line.split(";");
            Customer customer = new Customer(
                rawData[1],
                rawData[0],
                new BirthDate(rawData[2]),
                rawData[3],
                Gender.valueOf(rawData[4]));
            allCustomers.add(customer);
        }
        in.close();
        this.messenger = messenger;
        this.templateEngine = templateEngine;
    }

    @Override
    public void sendGreetings(BirthDate today) throws Exception {
        List<Customer> customers = allCustomers.stream()
            .filter(c -> c.hasBirthday(today))
            .collect(Collectors.toList());
        for (Customer customer : customers) {
            Map<String, Object> replacements = new HashMap<>();
            replacements.put("title", customer.getGender() == Gender.FEMALE ? "Liebe" : "Lieber");
            replacements.put("name", customer.getFirstName());
            replacements.put("age", customer.age(today));
            messenger.send(new BirthdayMessage(
                "vertrieb@company.de",
                customer.getEmailAddress(),
                "Alles Gute zum Geburtstag!",
                templateEngine.replaceInTemplate("standard", replacements)
            ));
        }
    }

}
