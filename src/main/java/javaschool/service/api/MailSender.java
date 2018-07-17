package javaschool.service.api;

import java.io.IOException;
import javax.mail.MessagingException;

public interface MailSender {
    void sendMail(String to, String pathToTemplate, String subject, String... args) throws IOException, MessagingException;
}
