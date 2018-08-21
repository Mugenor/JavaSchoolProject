package javaschool.service.exception;

/**
 * The type No such entity exception.
 */
public class NoSuchEntityException extends RuntimeException {
    private final Class entityClass;

    /**
     * Instantiates a new No such entity exception.
     *
     * @param entityClass the entity class
     */
    public NoSuchEntityException(Class entityClass) {
        this.entityClass = entityClass;
    }

    /**
     * Instantiates a new No such entity exception.
     *
     * @param message     the message
     * @param entityClass the entity class
     */
    public NoSuchEntityException(String message, Class entityClass) {
        super(message);
        this.entityClass = entityClass;
    }

    /**
     * Instantiates a new No such entity exception.
     *
     * @param message     the message
     * @param entityClass the entity class
     * @param cause       the cause
     */
    public NoSuchEntityException(String message, Class entityClass, Throwable cause) {
        super(message, cause);
        this.entityClass = entityClass;
    }

    /**
     * Gets entity class.
     *
     * @return the entity class
     */
    public Class getEntityClass() {
        return entityClass;
    }
}
