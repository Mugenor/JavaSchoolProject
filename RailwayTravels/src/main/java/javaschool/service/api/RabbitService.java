package javaschool.service.api;

/**
 * The interface Rabbit service.
 */
public interface RabbitService {
    /**
     * Sending serialized object to RabbitMQ's default configured exchange
     *
     * @param obj object to send
     */
    void convertAndSend(Object obj);
}
