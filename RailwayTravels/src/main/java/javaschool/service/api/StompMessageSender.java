package javaschool.service.api;

/**
 * The interface Stomp message sender.
 */
public interface StompMessageSender {
    /**
     * Send to.
     *
     * @param dest    the dest
     * @param payload the payload
     */
    void sendTo(String dest, Object payload);

    /**
     * Send to default prefix.
     *
     * @param dest    the dest
     * @param payload the payload
     */
    void sendToDefaultPrefix(String dest, Object payload);
}
