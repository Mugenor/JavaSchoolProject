package javaschool.config;

import java.util.Properties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@Import({JPAConfig.class, SecurityConfig.class, RabbitConfig.class, SecurityWebSocketConfig.class})
@ComponentScan({"javaschool.service"})
@PropertySource("classpath:mail.properties")
@EnableScheduling
@EnableAsync
public class RootConfig {
    private static final String protocolPropName = "mail.transport.protocol";
    private static final String authPropName = "mail.smtp.auth";
    private static final String starttlsEnablePropName = "mail.smtp.starttls.enable";
    private static final String debugPropName = "mail.debug";
    @Value("${mail.host}")
    private String mailHost;
    @Value("${mail.username}")
    private String username;
    @Value("${mail.password}")
    private String password;
    @Value("${mail.transport.protocol}")
    private String protocol;
    @Value("${mail.smtp.auth}")
    private String auth;
    @Value("${mail.smtp.starttls.enable}")
    private String starttls;
    @Value("${mail.debug}")
    private String debug;

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(mailHost);
        mailSender.setPort(587);

        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties properties = mailSender.getJavaMailProperties();
        properties.put(protocolPropName, protocol);
        properties.put(authPropName, auth);
        properties.put(starttlsEnablePropName, starttls);
        properties.put(debugPropName, debug);

        return mailSender;
    }
}
