package pl.lasota.tool.email;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

public class MailSenderExecutorImpl implements MailSenderExecutor {

    private final MailService mailService;
    private final String from;
    private final Session session;

    public MailSenderExecutorImpl(MailService mailService, String from) {
        this.mailService = mailService;
        this.from = from;
        session = Session.getDefaultInstance(new Properties(), null);
    }

    public MailSenderExecutorImpl(MailService mailService, String from, String host) {
        this.mailService = mailService;
        this.from = from;
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", false);
        prop.put("mail.smtp.starttls.enable", "false");
        prop.put("mail.smtp.host", host);
        session = Session.getDefaultInstance(prop, null);
    }

    public MailSenderExecutorImpl(MailService mailService, String from,
                                  String host,
                                  int port,
                                  String username,
                                  String password) {
        this.mailService = mailService;
        this.from = from;

        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", host);
        prop.put("mail.smtp.port", port);
        prop.put("mail.smtp.ssl.trust", host);

        session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
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
        MimeMessage email = new MimeMessage(session);
        email.setFrom(new InternetAddress(from));
        email.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(to));
        email.setSubject(subject, "utf-8");
        email.setText(body, "utf-8", "html");
        return email;
    }
}