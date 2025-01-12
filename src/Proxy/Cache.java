package Proxy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static Proxy.CacheType.FILE;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Cache {
    CacheType cacheType() default FILE; // Тип кэширования
    String fileNamePrefix() default "file"; // Имя файла
    boolean zip() default false; // Сжать ли файл
    Class[] identetyBy() default {}; // Аргументы для ключа
    int listSize() default 10; // Максимальный размер листа
}

