package javaschool.service.converter;

/**
 * The interface Class converter.
 *
 * @param <A> the type parameter
 * @param <B> the type parameter
 */
public interface ClassConverter<A, B> {
    /**
     * Converts parameter to B.class
     *
     * @param a Object of class A to convert
     * @return Converted to B.class object
     */
    B convertTo(A a);

    /**
     * Converts parameter to A.class
     *
     * @param b Object of class B to convert
     * @return Converted to A.class object
     */
    A convertFrom(B b);
}
