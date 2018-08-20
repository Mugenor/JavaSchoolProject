package client;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import javaschool.controller.dtoentity.DepartureDTO;
import javaschool.controller.dtoentity.TripDTO;
import javaschool.controller.dtoentity.TripUpdate;
import javax.faces.push.PushContext;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

public class TripUpdatesConsumer implements Consumer {
    private static final String PUSH_EVENT = "tripUpdate";

    private final TreeNode root;
    private final PushContext pushContext;
    private final Set<TripDTO> tripDTOSet;

    public TripUpdatesConsumer(TreeNode rootNode, PushContext pushContext) {
        this(rootNode, pushContext, new HashSet<>());
    }

    public TripUpdatesConsumer(TreeNode rootNode, PushContext pushContext, Set<TripDTO> initialTrips) {
        this.root = rootNode;
        this.pushContext = pushContext;
        this.tripDTOSet = initialTrips;
    }

    @Override
    public void handleConsumeOk(String consumerTag) {
        //unnecessary
    }

    @Override
    public void handleCancelOk(String consumerTag) {
        //unnecessary
    }

    @Override
    public void handleCancel(String consumerTag) throws IOException {
        //unnecessary
    }

    @Override
    public void handleShutdownSignal(String consumerTag, ShutdownSignalException sig) {
        //unnecessary
    }

    @Override
    public void handleRecoverOk(String consumerTag) {
        //unnecessary
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(body));
            TripUpdate tripUpdate = (TripUpdate) objectInputStream.readObject();
            switch (tripUpdate.getAction()) {
                case TripUpdate.CREATE:
                    doCreateTrip(tripUpdate.getTrip());
                    break;
                case TripUpdate.UPDATE:
                    doUpdateTrip(tripUpdate.getTrip());
                    break;
                case TripUpdate.DELETE:
                    doDeleteTrip(tripUpdate.getTrip());
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private synchronized void doUpdateTrip(TripDTO trip) {
        if (containsTodayDepartures(trip.getDepartures())) {
            TreeNode updateNode = createTripNode(trip);
            if (tripDTOSet.contains(trip)) {
                root.getChildren().
                        set(indexOfChild((TableModel) updateNode.getData(), root.getChildren()), updateNode);
            } else {
                root.getChildren().add(updateNode);
            }
            tripDTOSet.add(trip);
            pushContext.send(PUSH_EVENT);
        } else {
            if (tripDTOSet.contains(trip)) {
                doDeleteTrip(trip);
            }
        }
    }

    private synchronized void doDeleteTrip(TripDTO trip) {
        if (tripDTOSet.remove(trip)) {
            TreeNode deleteNode = createTripNode(trip);
            deleteFromChildren((TableModel) deleteNode.getData(), root.getChildren());
            pushContext.send(PUSH_EVENT);
        }
    }

    private synchronized void doCreateTrip(TripDTO trip) {
        if (containsTodayDepartures(trip.getDepartures())) {
            root.getChildren().add(createTripNode(trip));
            tripDTOSet.add(trip);
            pushContext.send(PUSH_EVENT);
        }
    }

    private boolean containsTodayDepartures(List<DepartureDTO> departures) {
        LocalDateTime todayAsDate = LocalDate.now().toLocalDateTime(LocalTime.MIDNIGHT);
        long today = todayAsDate.toDateTime().getMillis();
        long tomorrow = todayAsDate.plusDays(1).toDateTime().getMillis();
        for (DepartureDTO departure : departures) {
            if ((departure.getDateTimeFrom() >= today && departure.getDateTimeFrom() < tomorrow)
                    || (departure.getDateTimeTo() >= today && departure.getDateTimeTo() < tomorrow)) {
                return true;
            }
        }
        return false;
    }

    public static TreeNode createTripNode(TripDTO tripDTO) {
        List<DepartureDTO> departureDTOS = tripDTO.getDepartures();
        TreeNode tripNode = new DefaultTreeNode(new TableModel(
                tripDTO.getId(),
                departureDTOS.get(0).getStationFrom(),
                departureDTOS.get(departureDTOS.size() - 1).getStationTo(),
                departureDTOS.get(0).getDateTimeFrom(),
                departureDTOS.get(departureDTOS.size() - 1).getDateTimeTo()
        ));
        for (DepartureDTO departureDTO : departureDTOS) {
            new DefaultTreeNode(new TableModel(
                    tripDTO.getId(),
                    "",
                    departureDTO.getStationFrom(),
                    departureDTO.getStationTo(),
                    departureDTO.getDateTimeFrom(),
                    departureDTO.getDateTimeTo()
            ), tripNode);
        }
        return tripNode;
    }

    private void deleteFromChildren(TableModel model, List<TreeNode> children) {
        ListIterator<TreeNode> childrenIt = children.listIterator();
        while (childrenIt.hasNext()) {
            TreeNode node = childrenIt.next();
            if (node.getData().equals(model)) {
                childrenIt.remove();
                break;
            }
        }
    }

    private int indexOfChild(TableModel model, List<TreeNode> children) {
        int index = 0;
        for (TreeNode node : children) {
            if (node.getData().equals(model)) {
                return index;
            }
            ++index;
        }
        return -1;
    }

}
