package workshop.panda.birthday.core;

import java.util.List;

/**
 * Created by schulzst on 16.01.2016.
 */
public interface CustomerRepositoryPort {

    List<Customer> findCustomersWithBirthday(BirthDate today);

}
