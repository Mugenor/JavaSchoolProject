package javaschool.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The type Rabbit config.
 */
@Configuration
@EnableRabbit
public class RabbitConfig {
    /**
     * Hello queue.
     *
     * @return the queue
     */
    @Bean
    public Queue hello() {
        return new Queue("hello");
    }

    /**
     * Connection factory connection factory.
     *
     * @return the connection factory
     */
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPassword("guest");
        connectionFactory.setUsername("guest");
        return connectionFactory;
    }

    /**
     * Rabbit template rabbit template.
     *
     * @return the rabbit template
     */
    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setExchange("updates");
        return rabbitTemplate;
    }

}
