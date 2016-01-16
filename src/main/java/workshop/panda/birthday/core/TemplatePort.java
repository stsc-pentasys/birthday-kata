package workshop.panda.birthday.core;

/**
 * Created by schulzst on 16.01.2016.
 */
public interface TemplatePort {

    String renderBody(BirthDate today, Customer customer);

}
