package pl.lasota.tool.email;

import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;


public class SimpleProvider implements MailService {

    @Override
    public int send(MimeMessage mimeMessage) throws MessagingException {
        Transport.send(mimeMessage);
        return 1;
    }

}