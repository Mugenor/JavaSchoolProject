package javaschool.service.converter;

public interface ClassConverter<A, B> {
    B convertTo(A a);
    A convertFrom(B b);
}
