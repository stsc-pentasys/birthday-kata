package workshop.panda.birthday.core;

import workshop.panda.birthday.core.model.BirthDate;

/**
 * Created by schulzst on 19.01.2016.
 */
public interface BirthdayService {

    void sendGreetings(BirthDate today) throws Exception;

}
