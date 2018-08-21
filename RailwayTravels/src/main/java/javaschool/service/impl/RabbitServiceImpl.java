package javaschool.service.impl;

import javaschool.service.api.RabbitService;
import org.apache.log4j.Logger;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * The type Rabbit service.
 */
@Service
public class RabbitServiceImpl implements RabbitService {
    private Logger log = Logger.getLogger(RabbitServiceImpl.class);
    private RabbitTemplate rabbitTemplate;

    /**
     * Instantiates a new Rabbit service.
     *
     * @param rabbitTemplate the rabbit template
     */
    @Autowired
    public RabbitServiceImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    @Async
    public void convertAndSend(Object obj) {
        try {
            log.debug("Sending an object: " + obj);
            rabbitTemplate.convertAndSend(obj);
        } catch (AmqpException e) {
            log.error("Can't send an object", e);
        }
    }
}
