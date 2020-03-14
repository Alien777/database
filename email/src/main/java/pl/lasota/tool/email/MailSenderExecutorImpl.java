package pl.lasota.tool.email;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

public class MailMailSenderExecutor implements MailSenderExecutor {

    private final MailService mailService;
    private final String from;
    private final Session session;

    public MailMailSenderExecutor(MailService mailService, String from) {
        this.mailService = mailService;
        this.from = from;
        session = Session.getDefaultInstance(new Properties(), null);
    }

    public MailMailSenderExecutor(MailService mailService, String from, Properties properties) {
        this.mailService = mailService;
        this.from = from;
        session = Session.getDefaultInstance(new Properties(), null);
    }

    @Override
    public boolean send(String to, String subject, String body) {
        try {
            return send(createMessage(to, subject, body)) != -1;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean send(String to, String subject, String body, List<Placeholder> placeholders) {
        String tempBody = body;
        String tempSubject = subject;

        for (Placeholder placeholder : placeholders) {
            tempSubject = tempSubject.replaceAll(Pattern.quote(placeholder.getKey()), placeholder.getValue());
            tempBody = tempBody.replaceAll(Pattern.quote(placeholder.getKey()), placeholder.getValue());
        }

        return send(to, tempSubject, tempBody);
    }


    private int send(MimeMessage mimeMessage) {
        int send = -1;
        try {
            send = mailService.send(mimeMessage);
        } catch (IOException | MessagingException e) {
            e.printStackTrace();
        }
        return send;
    }

    private MimeMessage createMessage(String to, String subject, String body) throws MessagingException {

        MimeMessage email = new MimeMessage();
        email.setFrom(new InternetAddress(from));
        email.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(to));
        email.setSubject(subject, "utf-8");
        email.setText(body, "utf-8", "html");
        return email;
    }
}