package javaschool.service.api;

public interface StompMessageSender {
    void sendTo(String dest, Object payload);
    void sendToDefaultPrefix(String dest, Object payload);
}
