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
    String email="pepito_trueno@outlook.com";
    String password ="$$@2wkAwsswY#4y";

    @Test
    public void JavaMailUtilsTest() {
        try {

            // SEND AN EMAIL
            Properties properties = System.getProperties();
            properties.put("mail.smtp.user", email);
            properties.put("mail.smtp.host", "smtp-mail.outlook.com");
            properties.put("mail.smtp.port", "587");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.socketFactory.port", "587");
            properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            properties.put("mail.smtp.socketFactory.fallback", "true");
            Session session = JavaMailUtils.getSession(email, password, properties);
            JavaMailUtils.sendEmail(session, "no-replay@gmail.com", email, "no-replay@gmail.com", "TEST", "UN BODY TEST");


            final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
            // Set manual Properties
            properties.setProperty("mail.imaps.socketFactory.class", SSL_FACTORY);
            properties.setProperty("mail.imaps.socketFactory.fallback", "false");
            properties.setProperty("mail.imaps.port", "993");
            properties.setProperty("mail.imaps.socketFactory.port", "993");
            properties.put("mail.imaps.host", "imap-mail.outlook.com");
            /* Create the session  */
            session = JavaMailUtils.getSession(email, password, properties);
            Store store = session.getStore("imaps");
            store.connect();

            /* Open the inbox using store. */
            inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_WRITE);

            Message messages[] = JavaMailUtils.getAllMessagesFromFolder(inbox, FetchProfile.Item.ENVELOPE, FetchProfile.Item.CONTENT_INFO, FetchProfile.Item.FLAGS, FetchProfile.Item.SIZE);
            //Message messages[] = JavaMailUtils.getSeenMessagesFromFolder(inbox, FetchProfile.Item.ENVELOPE, FetchProfile.Item.CONTENT_INFO, FetchProfile.Item.FLAGS, FetchProfile.Item.SIZE);
            log.debug("No. of Unread Messages : " + inbox.getUnreadMessageCount());

            /* Open the inbox using store. */
            processed = store.getFolder("PROCESSED");
            processed.open(Folder.READ_WRITE);

            /*Copy messges from a folder to another folder*/
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