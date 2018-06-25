package javaschool.config;

import org.springframework.context.annotation.*;

@Configuration
@Import({JPAConfig.class})
@ComponentScan({"javaschool.service"})
public class RootConfig {
}
