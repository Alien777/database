package pl.lasota.tool.email;

import javax.mail.MessagingException;
import java.util.List;

public interface MailSenderExecutor {

    boolean send(String to, String subject, String body) throws MessagingException;

    boolean send(String to, String subject, String body, List<Placeholder> placeholders);
}