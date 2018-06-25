package javaschool.service.exception;

public class PassengerRegisteredException extends RuntimeException {
    public PassengerRegisteredException(String message) {
        super(message);
    }

    public PassengerRegisteredException() {
    }
}
