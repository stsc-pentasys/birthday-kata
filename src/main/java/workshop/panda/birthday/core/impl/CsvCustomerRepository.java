package workshop.panda.birthday.core.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import workshop.panda.birthday.core.model.BirthDate;
import workshop.panda.birthday.core.model.Customer;
import workshop.panda.birthday.core.model.Gender;

/**
 * Created by schulzst on 16.01.2016.
 */
public class CsvCustomerRepository {

    private String fileName;

    private List<Customer> customers = new ArrayList<>();

    public CsvCustomerRepository(String fileName) throws Exception {
        this.fileName = fileName;
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
            customers.add(customer);
        }
        in.close();
    }

    public List<Customer> findByBirthday(BirthDate today) {
        return customers.stream()
            .filter(c -> c.hasBirthday(today))
            .collect(Collectors.toList());
    }
}
