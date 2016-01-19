package workshop.panda.birthday.persistence;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import workshop.panda.birthday.core.model.BirthDate;
import workshop.panda.birthday.core.model.Customer;
import workshop.panda.birthday.core.CustomerRepositoryPort;
import workshop.panda.birthday.core.model.Gender;

/**
 * Created by schulzst on 16.01.2016.
 */
public class CsvCustomerRepositoryAdapter implements CustomerRepositoryPort {

    private String fileName;

    private List<Customer> customers = new ArrayList<>();

    public CsvCustomerRepositoryAdapter(String fileName) throws Exception {
        this.fileName = fileName;
        initRepository(fileName);
    }

    private void initRepository(String fileName) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(fileName));
        String line = in.readLine();
        while ((line = in.readLine()) != null) {
            Customer customer = transform(line);
            customers.add(customer);
        }
        in.close();
    }

    private Customer transform(String line) {
        String[] rawData = line.split(";");
        return new Customer(
            rawData[1],
            rawData[0],
            new BirthDate(rawData[2]),
            rawData[3],
            Gender.valueOf(rawData[4]));
    }

    @Override
    public List<Customer> findCustomersWithBirthday(BirthDate today) {
        return customers.stream()
            .filter(c -> c.hasBirthday(today))
            .collect(Collectors.toList());
    }
}
