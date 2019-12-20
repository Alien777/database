package pl.lasota.tool.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

public interface MailService {

    int send(MimeMessage mimeMessage) throws IOException, MessagingException;
}
