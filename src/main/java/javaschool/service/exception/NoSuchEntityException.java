package javaschool.service.exception;

public class NoSuchEntityException extends RuntimeException {
    private Class entityClass;

    public NoSuchEntityException(Class entityClass) {
        this.entityClass = entityClass;
    }

    public NoSuchEntityException(String message, Class entityClass) {
        super(message);
        this.entityClass = entityClass;
    }

    public NoSuchEntityException(String message, Class entityClass, Throwable cause) {
        super(message, cause);
        this.entityClass = entityClass;
    }

    public Class getEntityClass() {
        return entityClass;
    }
}
