package javaschool.service.impl;

import javaschool.service.api.StompMessageSender;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class StompMessageSenderImpl implements StompMessageSender {
    private static final Logger log = Logger.getLogger(StompMessageSenderImpl.class);
    @Value("${stomp.endpoint.ticket.prefix}")
    private String TICKET_ENDPOINT_PREFIX;
    private SimpMessageSendingOperations sendingOperations;

    @Autowired
    public StompMessageSenderImpl(SimpMessageSendingOperations sendingOperations) {
        this.sendingOperations = sendingOperations;
    }

    @Override
    @Async
    public void sendTo(String dest, Object payload) {
        sendingOperations.convertAndSend(dest, payload);
    }


    @Override
    @Async
    public void sendToDefaultPrefix(String dest, Object payload) {
        String resolvedDestination = TICKET_ENDPOINT_PREFIX + dest;
        log.info("Sending to: \'" + resolvedDestination + "\', object: " + payload);
        sendingOperations.convertAndSend(resolvedDestination, payload);
    }
}
