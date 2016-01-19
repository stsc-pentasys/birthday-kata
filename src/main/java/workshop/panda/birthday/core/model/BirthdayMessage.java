package workshop.panda.birthday.core.model;

import java.util.Objects;

public class BirthdayMessage {
    private final String from;
    private final String to;
    private final String subject;
    private final String body;

    public BirthdayMessage(String from, String to, String subject, String body) {
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.body = body;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BirthdayMessage that = (BirthdayMessage) o;
        return Objects.equals(from, that.from) &&
                Objects.equals(to, that.to) &&
                Objects.equals(subject, that.subject) &&
                Objects.equals(body, that.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, subject, body);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BirthdayMessage{");
        sb.append("from='").append(from).append('\'');
        sb.append(", to='").append(to).append('\'');
        sb.append(", subject='").append(subject).append('\'');
        sb.append(", body='").append(body).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
