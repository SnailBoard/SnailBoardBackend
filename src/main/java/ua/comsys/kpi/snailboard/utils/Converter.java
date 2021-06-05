package ua.comsys.kpi.snailboard.utils;

public interface Converter<T, R> {
    R convert(T source);
}
