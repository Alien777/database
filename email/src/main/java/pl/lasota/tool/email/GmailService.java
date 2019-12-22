package pl.lasota.tool.email;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Base64;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.GmailScopes;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;


public final class GmailService implements MailService {

    private final String applicationName;
    private final String tokenPath;
    private final String credentialPath;
    private final String userId;
    private final String mail;
    private final String accessType;
    private final int port;

    private final JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
    private final List<String> lists = new ArrayList<>(GmailScopes.all());

    private final NetHttpTransport netHttpTransport;
    private final Credential credential;
    private final String host;

    public GmailService(String accessType, String host, int port, String applicationName, String tokenPath, String credentialPath, String userId, String mail)
            throws GeneralSecurityException, IOException {

        this.applicationName = applicationName;
        this.tokenPath = tokenPath;
        this.credentialPath = credentialPath;
        this.userId = userId;
        this.mail = mail;
        this.accessType = accessType;
        this.port = port;
        this.host = host;

        netHttpTransport = GoogleNetHttpTransport.newTrustedTransport();
        credential = getCredentials(netHttpTransport);
    }

    @Override
    public int send(MimeMessage mimeMessage) throws IOException, MessagingException {

        com.google.api.services.gmail.Gmail service = new com.google.api.services.gmail.Gmail.Builder(netHttpTransport, jsonFactory, credential)
                .setApplicationName(applicationName)
                .build();

        com.google.api.services.gmail.model.Message message = createMessageWithEmail(mimeMessage);
        com.google.api.services.gmail.Gmail.Users.Messages.Send send = service.users().messages().send(mail, message);
        send.execute();
        return send.getLastStatusCode();
    }

    private Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        File initialFile = new File(credentialPath);
        GoogleClientSecrets clientSecrets;
        try (InputStream targetStream = new FileInputStream(initialFile)) {
            clientSecrets = GoogleClientSecrets.load(jsonFactory, new InputStreamReader(targetStream));
        }

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, jsonFactory, clientSecrets, lists)
                .setDataStoreFactory(new FileDataStoreFactory(new File(tokenPath)))
                .setAccessType(accessType)
                .build();

        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setHost(host).setPort(port).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize(userId);
    }

    private com.google.api.services.gmail.model.Message createMessageWithEmail(MimeMessage emailContent) throws IOException, MessagingException {
        String encodedEmail;
        try (ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
            emailContent.writeTo(buffer);
            byte[] bytes = buffer.toByteArray();
            encodedEmail = Base64.encodeBase64URLSafeString(bytes);
        }
        return new com.google.api.services.gmail.model.Message().setRaw(encodedEmail);
    }
}