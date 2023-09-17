package pl.piwowarski.facebookly.service.mapper;

public interface Mapper<T, U> {

    T map(U u);
    U unmap(T t);
}
