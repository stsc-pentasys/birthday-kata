package workshop.panda.birthday.core.model;

/**
 * Created by schulzst on 06.02.2016.
 */
public class BirthdayMessage {

    private String sender;
    private String recipient;
    private String subject;
    private String body;

    public BirthdayMessage(String sender, String recipient, String subject, String body) {
        this.sender = sender;
        this.recipient = recipient;
        this.subject = subject;
        this.body = body;
    }

    public String getSender() {
        return sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }
}
