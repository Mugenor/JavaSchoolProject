package javaschool.service.api;

import java.io.IOException;
import java.util.Map;
import javax.activation.DataSource;
import javax.mail.MessagingException;

public interface MailSender {
    /**
     * Sending an email with specified template for message.
     *
     * @param to             consumer's email address
     * @param pathToTemplate path to file with html template for message. File must be in classpath
     * @param subject        subject of message
     * @param args           arguments for template
     * @throws IOException
     * @throws MessagingException
     */
    void sendMail(String to, String pathToTemplate, String subject, String... args) throws IOException, MessagingException;
    void sendMail(String to, String pathToTemplate, String subject,
                  Map<String, ? extends DataSource> attachments, String... args) throws IOException, MessagingException;
}
