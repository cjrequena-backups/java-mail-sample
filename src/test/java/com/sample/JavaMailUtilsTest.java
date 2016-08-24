package com.sample;

import lombok.extern.log4j.Log4j2;
import org.junit.Test;

import javax.mail.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * Created by cjrequena on 16/08/16.
 */
@Log4j2
public class JavaMailUtilsTest {

    Folder inbox;
    Folder processed;
    static final String EMAIL = "sshubtest1@tuitravel-ad.net";
    static final String PASSWORD = "Hola1234";
    static final String MAIL_SMTP_USER = EMAIL;
    static final String MAIL_SMTP_HOST = "smtp-mail.office365.com";
    static final String MAIL_SMTP_PORT = "587";
    static final String MAIL_SMTP_STARTTLS_ENABLE = "true";
    static final String MAIL_SMTP_AUTH = "true";
    static final String MAIL_SMTP_SOCKETFACTORY_PORT = "587";
    static final String MAIL_SMTP_SOCKETFACTORY_CLASS = "javax.net.ssl.SSLSocketFactory";
    static final String MAIL_SMTP_SOCKETFACTORY_FALLBACK = "true";

    static final String MAIL_IMAPS_SOCKETFACTORY_CLASS = "javax.net.ssl.SSLSocketFactory";
    static final String MAIL_IMAPS_HOST = "outlook.office365.com";
    static final String MAIL_IMAPS_PORT = "993";
    static final String MAIL_IMAPS_SOCKETFACTORY_PORT = "993";
    static final String MAIL_IMAPS_SOCKETFACTORY_FALLBACK = "false";



    @Test
    public void JavaMailUtilsTest() {
        try {
            Session session;
            Properties properties = System.getProperties();

            // Set manual Properties SMTP
            properties.setProperty("mail.smtp.user", MAIL_SMTP_USER);
            properties.setProperty("mail.smtp.host", MAIL_SMTP_HOST);
            properties.setProperty("mail.smtp.port", MAIL_SMTP_PORT);
            properties.setProperty("mail.smtp.starttls.enable", MAIL_SMTP_STARTTLS_ENABLE);
            properties.setProperty("mail.smtp.auth", MAIL_SMTP_AUTH);
            properties.setProperty("mail.smtp.socketFactory.port", MAIL_SMTP_SOCKETFACTORY_PORT);
            properties.setProperty("mail.smtp.socketFactory.class", MAIL_SMTP_SOCKETFACTORY_CLASS);
            properties.setProperty("mail.smtp.socketFactory.fallback", MAIL_SMTP_SOCKETFACTORY_FALLBACK);
            // Set manual Properties IMAP
            properties.setProperty("mail.imaps.socketFactory.class", MAIL_IMAPS_SOCKETFACTORY_CLASS);
            properties.setProperty("mail.imaps.host", MAIL_IMAPS_HOST);
            properties.setProperty("mail.imaps.port", MAIL_IMAPS_PORT);
            properties.setProperty("mail.imaps.socketFactory.port", MAIL_IMAPS_SOCKETFACTORY_PORT);
            properties.setProperty("mail.imaps.socketFactory.fallback", MAIL_IMAPS_SOCKETFACTORY_FALLBACK);

            /* Create the session  */
            session = JavaMailUtils.getSession(EMAIL, PASSWORD, properties);
            Store store = session.getStore("imaps");
            store.connect();

            //JavaMailUtils.sendEmail(session, "no-replay@gmail.com", EMAIL, "no-replay@gmail.com", "TEST", "UN BODY TEST");

            /* Open the inbox using store. */
            inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_WRITE);

            Message messages[] = JavaMailUtils
                .getAllMessagesFromFolder(inbox, FetchProfile.Item.ENVELOPE, FetchProfile.Item.CONTENT_INFO, FetchProfile.Item.FLAGS,
                    FetchProfile.Item.SIZE);
            //Message messages[] = JavaMailUtils.getSeenMessagesFromFolder(inbox, FetchProfile.Item.ENVELOPE, FetchProfile.Item.CONTENT_INFO, FetchProfile.Item.FLAGS, FetchProfile.Item.SIZE);
            log.debug("No. of Unread Messages : " + inbox.getUnreadMessageCount());

            /* Open the processed using store. */
            processed = store.getFolder("PROCESSED");
            processed.open(Folder.READ_WRITE);

            /*Move messges from a folder to another folder*/
            JavaMailUtils.moveMessages(inbox, processed, messages);

            try {

                printAllMessages(messages);

                inbox.close(true);
                store.close();

            } catch (Exception ex) {
                log.debug("Exception arise at the time of read mail");
                ex.printStackTrace();
            }

        } catch (MessagingException e) {
            log.debug("Exception while connecting to server: " + e.getLocalizedMessage());
            e.printStackTrace();
            System.exit(2);
        }


    }

    public void printAllMessages(Message[] msgs) throws Exception {
        for (int i = 0; i < msgs.length; i++) {
            log.debug("MESSAGE #" + (i + 1) + ":");
            printEnvelope(msgs[i]);
        }
    }

    public void printEnvelope(Message message) throws Exception {

        Address[] a;

        // FROM
        if ((a = message.getFrom()) != null) {
            for (int j = 0; j < a.length; j++) {
                log.debug("De : " + a[j].toString());
            }
        }

        String subject = message.getSubject();

        Date receivedDate = message.getReceivedDate();
        Date sentDate = message.getSentDate(); // receivedDate is returning
        // null. So used getSentDate()

        //Dar Formato a la fecha
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        log.debug("Asunto : " + subject);

        if (receivedDate != null) {
            log.debug("Recibido: " + df.format(receivedDate));
        }

        log.debug("Enviado : " + df.format(sentDate));
    }

}
