package javaschool.service.api;

public interface RabbitService {
    /**
     * Sending serialized object to RabbitMQ's default configured exchange
     *
     * @param obj object to send
     */
    void convertAndSend(Object obj);
}
