package com.sample;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.FlagTerm;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

/**
 * Created by cjrequena on 16/08/16.
 */


/**
 * See {@linktourl https://javamail.java.net/nonav/docs/api/}
 * See {@linktourl https://javamail.java.net/nonav/docs/api/com/sun/mail/imap/package-summary.html}
 * See {@linktourl https://javamail.java.net/nonav/docs/api/com/sun/mail/smtp/package-summary.html}
 */
@Data
@Log4j2
public class JavaMailUtils {

    private JavaMailUtils() {

    }

    /**
     * @param email
     * @param password
     * @param properties
     * @return
     */
    public static Session getSession(final String email, final String password, Properties properties) {
        Authenticator authentication = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }
        };
        return Session.getInstance(properties, authentication);
    }

    /**
     * @param folder
     * @param fetchProfilesItems
     * @return
     * @throws MessagingException
     */
    public static Message[] getAllMessagesFromFolder(Folder folder, FetchProfile.Item... fetchProfilesItems) throws MessagingException {
        try {
            Message messages[] = folder.getMessages();
            FetchProfile fp = new FetchProfile();
            for (FetchProfile.Item fetchProfilesItem : fetchProfilesItems) {
                fp.add(fetchProfilesItem);
            }
            folder.fetch(messages, fp);
            return messages;
        } catch (MessagingException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    /**
     * @param folder
     * @param fetchProfilesItems
     * @return
     * @throws MessagingException
     */
    public static Message[] getSeenMessagesFromFolder(Folder folder, FetchProfile.Item... fetchProfilesItems) throws MessagingException {
        try {
            Message messages[] = folder.search(new FlagTerm(new Flags(Flags.Flag.SEEN), Boolean.TRUE));
            FetchProfile fp = new FetchProfile();
            for (FetchProfile.Item fetchProfilesItem : fetchProfilesItems) {
                fp.add(fetchProfilesItem);
            }
            folder.fetch(messages, fp);
            return messages;
        } catch (MessagingException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }


    /**
     * @param folder
     * @param fetchProfilesItems
     * @return
     * @throws MessagingException
     */
    public static Message[] getNotSeenMessagesFromFolder(Folder folder, FetchProfile.Item... fetchProfilesItems) throws MessagingException {
        try {
            Message messages[] = folder.search(new FlagTerm(new Flags(Flags.Flag.SEEN), Boolean.FALSE));
            FetchProfile fp = new FetchProfile();
            for (FetchProfile.Item fetchProfilesItem : fetchProfilesItems) {
                fp.add(fetchProfilesItem);
            }
            folder.fetch(messages, fp);
            return messages;
        } catch (MessagingException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    /**
     * @param folder
     * @param fetchProfilesItems
     * @return
     * @throws MessagingException
     */
    public static Message[] getDraftMessagesFromFolder(Folder folder, FetchProfile.Item... fetchProfilesItems) throws MessagingException {
        try {
            Message messages[] = folder.search(new FlagTerm(new Flags(Flags.Flag.DRAFT), Boolean.TRUE));
            FetchProfile fp = new FetchProfile();
            for (FetchProfile.Item fetchProfilesItem : fetchProfilesItems) {
                fp.add(fetchProfilesItem);
            }
            folder.fetch(messages, fp);
            return messages;
        } catch (MessagingException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    /**
     * @param folder
     * @param fetchProfilesItems
     * @return
     * @throws MessagingException
     */
    public static Message[] getNotDraftMessagesFromFolder(Folder folder, FetchProfile.Item... fetchProfilesItems) throws MessagingException {
        try {
            Message messages[] = folder.search(new FlagTerm(new Flags(Flags.Flag.DRAFT), Boolean.FALSE));
            FetchProfile fp = new FetchProfile();
            for (FetchProfile.Item fetchProfilesItem : fetchProfilesItems) {
                fp.add(fetchProfilesItem);
            }
            folder.fetch(messages, fp);
            return messages;
        } catch (MessagingException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    /**
     * @param folder
     * @param fetchProfilesItems
     * @return
     * @throws MessagingException
     */
    public static Message[] getAnsweredMessagesFromFolder(Folder folder, FetchProfile.Item... fetchProfilesItems) throws MessagingException {
        try {
            Message messages[] = folder.search(new FlagTerm(new Flags(Flags.Flag.ANSWERED), Boolean.TRUE));
            FetchProfile fp = new FetchProfile();
            for (FetchProfile.Item fetchProfilesItem : fetchProfilesItems) {
                fp.add(fetchProfilesItem);
            }
            folder.fetch(messages, fp);
            return messages;
        } catch (MessagingException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    /**
     * @param folder
     * @param fetchProfilesItems
     * @return
     * @throws MessagingException
     */
    public static Message[] getNotAnsweredMessagesFromFolder(Folder folder, FetchProfile.Item... fetchProfilesItems) throws MessagingException {
        try {
            Message messages[] = folder.search(new FlagTerm(new Flags(Flags.Flag.ANSWERED), Boolean.FALSE));
            FetchProfile fp = new FetchProfile();
            for (FetchProfile.Item fetchProfilesItem : fetchProfilesItems) {
                fp.add(fetchProfilesItem);
            }
            folder.fetch(messages, fp);
            return messages;
        } catch (MessagingException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    /**
     * @param folder
     * @param fetchProfilesItems
     * @return
     * @throws MessagingException
     */
    public static Message[] getFlaggedMessagesFromFolder(Folder folder, FetchProfile.Item... fetchProfilesItems) throws MessagingException {
        try {
            Message messages[] = folder.search(new FlagTerm(new Flags(Flags.Flag.FLAGGED), Boolean.TRUE));
            FetchProfile fp = new FetchProfile();
            for (FetchProfile.Item fetchProfilesItem : fetchProfilesItems) {
                fp.add(fetchProfilesItem);
            }
            folder.fetch(messages, fp);
            return messages;
        } catch (MessagingException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    /**
     * @param folder
     * @param fetchProfilesItems
     * @return
     * @throws MessagingException
     */
    public static Message[] getNotFlaggedMessagesFromFolder(Folder folder, FetchProfile.Item... fetchProfilesItems) throws MessagingException {
        try {
            Message messages[] = folder.search(new FlagTerm(new Flags(Flags.Flag.FLAGGED), Boolean.FALSE));
            FetchProfile fp = new FetchProfile();
            for (FetchProfile.Item fetchProfilesItem : fetchProfilesItems) {
                fp.add(fetchProfilesItem);
            }
            folder.fetch(messages, fp);
            return messages;
        } catch (MessagingException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    /**
     * @param folder
     * @param fetchProfilesItems
     * @return
     * @throws MessagingException
     */
    public static Message[] getRecentMessagesFromFolder(Folder folder, FetchProfile.Item... fetchProfilesItems) throws MessagingException {
        try {
            Message messages[] = folder.search(new FlagTerm(new Flags(Flags.Flag.RECENT), Boolean.TRUE));
            FetchProfile fp = new FetchProfile();
            for (FetchProfile.Item fetchProfilesItem : fetchProfilesItems) {
                fp.add(fetchProfilesItem);
            }
            folder.fetch(messages, fp);
            return messages;
        } catch (MessagingException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    /**
     * @param folder
     * @param fetchProfilesItems
     * @return
     * @throws MessagingException
     */
    public static Message[] getNotRecentFlaggedMessagesFromFolder(Folder folder, FetchProfile.Item... fetchProfilesItems) throws MessagingException {
        try {
            Message messages[] = folder.search(new FlagTerm(new Flags(Flags.Flag.RECENT), Boolean.FALSE));
            FetchProfile fp = new FetchProfile();
            for (FetchProfile.Item fetchProfilesItem : fetchProfilesItems) {
                fp.add(fetchProfilesItem);
            }
            folder.fetch(messages, fp);
            return messages;
        } catch (MessagingException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    /**
     * @param folder
     * @param fetchProfilesItems
     * @return
     * @throws MessagingException
     */
    public static Message[] getDeletedMessagesFromFolder(Folder folder, FetchProfile.Item... fetchProfilesItems) throws MessagingException {
        try {
            Message messages[] = folder.search(new FlagTerm(new Flags(Flags.Flag.DELETED), Boolean.TRUE));
            FetchProfile fp = new FetchProfile();
            for (FetchProfile.Item fetchProfilesItem : fetchProfilesItems) {
                fp.add(fetchProfilesItem);
            }
            folder.fetch(messages, fp);
            return messages;
        } catch (MessagingException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    /**
     * @param folder
     * @param fetchProfilesItems
     * @return
     * @throws MessagingException
     */
    public static Message[] getNotDeletedFlaggedMessagesFromFolder(Folder folder, FetchProfile.Item... fetchProfilesItems) throws MessagingException {
        try {
            Message messages[] = folder.search(new FlagTerm(new Flags(Flags.Flag.DELETED), Boolean.FALSE));
            FetchProfile fp = new FetchProfile();
            for (FetchProfile.Item fetchProfilesItem : fetchProfilesItems) {
                fp.add(fetchProfilesItem);
            }
            folder.fetch(messages, fp);
            return messages;
        } catch (MessagingException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }


    /**
     * @param fromFolder
     * @param destinationFolder
     * @param messages
     * @throws MessagingException
     */
    public static void copyMessages(Folder fromFolder, Folder destinationFolder, Message... messages) throws MessagingException {
        try {
            fromFolder.copyMessages(messages, destinationFolder);
        } catch (MessagingException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    /**
     * @param fromFolder
     * @param destinationFolder
     * @param messages
     * @throws MessagingException
     */
    public static void moveMessages(Folder fromFolder, Folder destinationFolder, Message... messages) throws MessagingException {
        try {
            copyMessages(fromFolder, destinationFolder, messages);
            for (Message message : messages) {
                message.setFlag(Flags.Flag.DELETED, Boolean.TRUE);
            }
        } catch (MessagingException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }


    /**
     * Utility method to send simple HTML email
     *
     * @param session
     * @param toEmail
     * @param subject
     * @param body
     */
    public static void sendEmail(Session session, String fromEmail, String toEmail, String replayToEmail, String subject, String body) {
        try {
            MimeMessage msg = new MimeMessage(session);
            //set message headers
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");

            msg.setFrom(new InternetAddress(fromEmail, "NoReply-JD"));

            msg.setReplyTo(InternetAddress.parse(replayToEmail, false));

            msg.setSubject(subject, "UTF-8");

            msg.setText(body, "UTF-8");

            msg.setSentDate(new Date());

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
            System.out.println("Message is ready");
            Transport.send(msg);

            log.debug("EMail Sent Successfully!!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Utility method to send email with attachment
     *
     * @param session
     * @param toEmail
     * @param subject
     * @param body
     */
    public static void sendAttachmentEmail(
        Session session,
        String fromEmail,
        String toEmail,
        String replayToEmail,
        String subject,
        String body,
        FileDataSource fileDataSource) {
        try {
            MimeMessage msg = new MimeMessage(session);
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");

            msg.setFrom(new InternetAddress(fromEmail, "NoReply-JD"));

            msg.setReplyTo(InternetAddress.parse(replayToEmail, false));

            msg.setSubject(subject, "UTF-8");

            msg.setSentDate(new Date());

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));

            // Create the message body part
            BodyPart messageBodyPart = new MimeBodyPart();

            // Fill the message
            messageBodyPart.setText(body);

            // Create a multipart message for attachment
            Multipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            // Second part is attachment
            messageBodyPart = new MimeBodyPart();

            messageBodyPart.setDataHandler(new DataHandler(fileDataSource));
            messageBodyPart.setFileName(fileDataSource.getName());
            multipart.addBodyPart(messageBodyPart);

            // Send the complete message parts
            msg.setContent(multipart);

            // Send message
            Transport.send(msg);
            log.debug("EMail Sent Successfully with attachment!!");
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Utility method to send image in email body
     *
     * @param session
     * @param toEmail
     * @param subject
     * @param body
     */
    public static void sendImageEmail(
        Session session,
        String fromEmail,
        String toEmail,
        String replayToEmail,
        String subject,
        String body,
        FileDataSource fileDataSource) {
        try {
            MimeMessage msg = new MimeMessage(session);
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");

            msg.setFrom(new InternetAddress(fromEmail, "NoReply-JD"));

            msg.setReplyTo(InternetAddress.parse(replayToEmail, false));

            msg.setSubject(subject, "UTF-8");

            msg.setSentDate(new Date());

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));

            // Create the message body part
            BodyPart messageBodyPart = new MimeBodyPart();

            messageBodyPart.setText(body);

            // Create a multipart message for attachment
            Multipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            // Second part is image attachment
            messageBodyPart = new MimeBodyPart();
            messageBodyPart.setDataHandler(new DataHandler(fileDataSource));
            messageBodyPart.setFileName(fileDataSource.getName());
            //Trick is to add the content-id header here
            messageBodyPart.setHeader("Content-ID", "image_id");
            multipart.addBodyPart(messageBodyPart);

            //third part for displaying image in the email body
            messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent("<h1>Attached Image</h1>" + "<img src='cid:image_id'>", "text/html");
            multipart.addBodyPart(messageBodyPart);

            //Set the multipart message to the email message
            msg.setContent(multipart);

            // Send message
            Transport.send(msg);
            log.debug("EMail Sent Successfully with image!!");
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }







}


