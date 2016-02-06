package workshop.panda.birthday.persistence;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import workshop.panda.birthday.core.CustomerRepository;
import workshop.panda.birthday.core.model.BirthDate;
import workshop.panda.birthday.core.model.Customer;
import workshop.panda.birthday.core.model.Gender;

public class CsvCustomerRepository implements CustomerRepository {

    private String fileName;

    public CsvCustomerRepository(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Customer> findCustomersByBirthday(BirthDate today) throws IOException {
        List<Customer> customers = new LinkedList<Customer>();
        BufferedReader in = new BufferedReader(new FileReader(fileName));
        try {
            String line = in.readLine();
            while ((line = in.readLine()) != null) {
                Customer customer = parseCustomer(line);
                if (customer.hasBirthday(today)) {
                    customers.add(customer);
                }
            }
        } finally {
            in.close();
        }
        return customers;
    }

    private Customer parseCustomer(String line) {
        String[] rawData = line.split(";");
        return new Customer(
            rawData[1],
            rawData[0],
            new BirthDate(rawData[2]),
            rawData[3],
            Gender.valueOf(rawData[4]));
    }
}