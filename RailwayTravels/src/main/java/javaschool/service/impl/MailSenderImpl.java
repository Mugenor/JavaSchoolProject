package javaschool.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.MessageFormat;
import javaschool.service.api.MailSender;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MailSenderImpl implements MailSender {
    private static final Logger log = Logger.getLogger(MailSenderImpl.class);
    private JavaMailSender mailSender;

    @Autowired
    public MailSenderImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    @Async
    public void sendMail(String to, String pathToTemplate, String subject, String... args) throws IOException, MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message);
        messageHelper.setText(getResolvedFileContent(pathToTemplate, args), true);
        messageHelper.setTo(to);
        messageHelper.setSubject(subject);
        mailSender.send(message);
    }

    private String getResolvedFileContent(String pathToTemplate, String... args) throws IOException {
        String path = new ClassPathResource(pathToTemplate).getFile().getAbsolutePath();
        String template = new String(Files.readAllBytes(Paths.get(path)));
        if(args != null) {
            return MessageFormat.format(template, args);
        }
        return template;
    }
}
