package client;

import java.util.Date;
import java.util.Set;
import javaschool.controller.dtoentity.TripDTO;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.push.Push;
import javax.faces.push.PushContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;


import static client.TripUpdatesConsumer.createTripNode;

@Named
@ApplicationScoped
public class Table {
    private static final String EXCHANGE_NAME = "updates";
    @Inject
    private TripDTOService tripDTOService;
    @Inject
    private RabbitMQConnector rabbitMQConnector;
    @Inject
    @Push(channel = "departureChannel")
    private PushContext pushContext;

    private TreeNode rootNode;

    @PostConstruct
    public void init() {
        try {
            Set<TripDTO> trips = tripDTOService.getTrips();
            rootNode = new DefaultTreeNode("Today trips");
            for (TripDTO tripDTO : trips) {
                rootNode.getChildren().add(createTripNode(tripDTO));
            }
            rabbitMQConnector.subscribe(EXCHANGE_NAME,
                    new TripUpdatesConsumer(rootNode, pushContext, trips));
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
}
