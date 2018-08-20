package client;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;

@Singleton
public class RabbitMQConnector {
    private ConnectionFactory connectionFactory;
    private Connection connection;

    @PostConstruct
    public void init() {
        try {
            connectionFactory = new ConnectionFactory();
            connection = connectionFactory.newConnection();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void subscribe(String exchangeName, Consumer consumer) throws IOException {
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(exchangeName, "fanout", true);
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, "updates", "");

        channel.basicConsume(queueName, true, consumer);
    }
}
