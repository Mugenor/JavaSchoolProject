package client;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeoutException;
import javaschool.controller.dtoentity.DepartureDTO;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.push.Push;
import javax.faces.push.PushContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ApplicationScoped
public class Table {
    @Inject
    private DepartureDTOService departureDTOService;
    @Inject
    @Push(channel = "departureChannel")
    private PushContext pushContext;

    private Set<DepartureDTO> departureDTOS;

    @PostConstruct
    public void init() {
        try {
            departureDTOS = departureDTOService.getDepartures();
            System.out.println("In post construct");

            ConnectionFactory connectionFactory = new ConnectionFactory();
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();

            channel.exchangeDeclare("updates", "fanout", true);
            String queueName = channel.queueDeclare().getQueue();
            channel.queueBind(queueName, "updates", "");
            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    try {
                        ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(body));
                        DepartureDTO departureDTO = (DepartureDTO) objectInputStream.readObject();
                        System.out.println("Received a message: " + departureDTO);
                        pushContext.send("departureUpdate");
                        departureDTOS.add(departureDTO);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            };
            channel.basicConsume(queueName, true, consumer);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Set<DepartureDTO> getAllDepartures() {
        return departureDTOS;
    }

    public Date getDateTime(long timestamp) {
        return new Date(timestamp);
    }
}
