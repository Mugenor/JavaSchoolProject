package client;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;

@Singleton
public class RabbitMQConnector {
    private Connection connection;
    private List<Channel> channels;

    @PostConstruct
    public void init() {
        try {
            connection = new ConnectionFactory().newConnection();
            channels = new ArrayList<>();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void subscribe(String exchangeName, Consumer consumer) throws IOException {
        Channel channel = connection.createChannel();
        channels.add(channel);
        channel.exchangeDeclare(exchangeName, "fanout", true);
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, "updates", "");

        channel.basicConsume(queueName, true, consumer);
    }

    @PreDestroy
    public void destroy() {
        try {
            for (Channel channel : channels) {
                channel.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
