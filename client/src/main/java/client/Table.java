package client;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javaschool.controller.dtoentity.DepartureDTO;
import javaschool.controller.dtoentity.TripDTO;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.push.Push;
import javax.faces.push.PushContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.joda.time.LocalDateTime;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

@Named
@ApplicationScoped
public class Table {
    @Inject
    private TripDTOService tripDTOService;
    @Inject
    @Push(channel = "departureChannel")
    private PushContext pushContext;

    private TreeNode rootNode;
    private Set<TripDTO> tripDTOSet;

    @PostConstruct
    public void init() {
        try {
            tripDTOSet = tripDTOService.getTrips();
            rootNode = new DefaultTreeNode("Today trips");
            for(TripDTO tripDTO: tripDTOSet) {
               rootNode.getChildren().add(createTripNode(tripDTO));
            }

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
                        TripDTO tripDTO = (TripDTO) objectInputStream.readObject();
                        System.out.println("Received a message: " + tripDTO);
                        if(!tripDTOSet.contains(tripDTO)){
                            rootNode.getChildren().add(createTripNode(tripDTO));
                        } else {
                            TreeNode node = createTripNode(tripDTO);
                            rootNode.getChildren().set(rootNode.getChildren().indexOf(node), node);
                        }
                        tripDTOSet.add(tripDTO);
                        pushContext.send("tripUpdate");
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

    public TreeNode getAllTrips() {
        return rootNode;
    }

    public Date getDateTime(long timestamp) {
        return new Date(timestamp);
    }

    private TreeNode createTripNode(TripDTO tripDTO) {
        List<DepartureDTO> departureDTOS = tripDTO.getDepartures();
        TreeNode tripNode = new DefaultTreeNode(new TableModel(
                departureDTOS.get(0).getStationFrom(),
                departureDTOS.get(departureDTOS.size() - 1).getStationTo(),
                departureDTOS.get(0).getDateTimeFrom(),
                departureDTOS.get(departureDTOS.size() - 1).getDateTimeTo()
        ));
        for(DepartureDTO departureDTO: departureDTOS) {
            new DefaultTreeNode(new TableModel(
                    "",
                    departureDTO.getStationFrom(),
                    departureDTO.getStationTo(),
                    departureDTO.getDateTimeFrom(),
                    departureDTO.getDateTimeTo()
            ), tripNode);
        }
        return tripNode;
    }
}
