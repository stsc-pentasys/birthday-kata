package workshop.panda.birthday.persistence;

import java.util.List;

import workshop.panda.birthday.core.BirthDate;
import workshop.panda.birthday.core.Customer;

/**
 * Created by schulzst on 16.01.2016.
 */
public interface CustomerRepositoryPort {

    List<Customer> findCustomersWithBirthday(BirthDate today);

}
