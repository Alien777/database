package pl.lasota.tool.email;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

public class MailMessage implements Message {


    private final MailService mailService;
    private MimeMessage message;
    private String from;

    public MailMessage(MailService mailService, String from) {
        this.mailService = mailService;
        this.from = from;
    }


    @Override
    public Message create(String to, String subject, String body) {
        try {
            message = createMessage(to, subject, body);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return this;
    }

    @Override
    public Message create(String to, String subject, String body, List<Placeholder> placeholders) {
        String tempBody = body;
        String tempSubject = subject;

        for (Placeholder placeholder : placeholders) {
            tempSubject = tempSubject.replaceAll(Pattern.quote(placeholder.getKey()), placeholder.getValue());
            tempBody = tempBody.replaceAll(Pattern.quote(placeholder.getKey()), placeholder.getValue());
        }


        return create(to, tempSubject, tempBody);
    }

    @Override
    public int send() {
        int send = -1;
        try {
            send = mailService.send(message);
        } catch (IOException | MessagingException e) {
            e.printStackTrace();
        }

        return send;
    }

    private MimeMessage createMessage(String to, String subject, String body) throws MessagingException {
        Session session = Session.getDefaultInstance(new Properties(), null);
        MimeMessage email = new MimeMessage(session);
        email.setFrom(new InternetAddress(from));
        email.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(to));
        email.setSubject(subject, "utf-8");
        email.setText(body, "utf-8", "html");
        return email;
    }
}