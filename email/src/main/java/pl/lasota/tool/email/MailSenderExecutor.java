package pl.lasota.tool.email;

import javax.mail.MessagingException;
import java.util.List;

public interface Message {

    Message create(String to, String subject, String body) throws MessagingException;

    Message create(String to, String subject, String body, List<Placeholder> placeholders);

    int send();

}