package pl.piwowarski.facebookly.service.mapper.reverseMap;

public interface ReverseMapper <T, U>{
    U map(T t);
}
