package javaschool.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Import({JPAConfig.class, SecurityConfig.class})
@ComponentScan({"javaschool.service"})
public class RootConfig {
}
