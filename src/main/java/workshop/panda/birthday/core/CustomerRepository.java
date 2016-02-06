package workshop.panda.birthday.core;

import java.io.IOException;
import java.util.List;

import workshop.panda.birthday.core.model.BirthDate;
import workshop.panda.birthday.core.model.Customer;

/**
 * Created by schulzst on 06.02.2016.
 */
public interface CustomerRepository {
    List<Customer> findCustomersByBirthday(BirthDate today) throws IOException;
}
