package Proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

public class CacheProxy implements InvocationHandler {
    private Service service;
    private Loader loader;
    private final Map<Object, Object> cacheMap = new HashMap<>();

    public CacheProxy(){}

    public CacheProxy(Service service) {
        this.service = service;
    }

    public Service cache(Service service){
        this.service = service;
        return service;
    }
    public Loader cache(Loader loader){
        this.loader = loader;
        return loader;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (!method.isAnnotationPresent(Cache.class)) {
            return invoke(method, args);
        }else {
            Cache cache = method.getAnnotation(Cache.class);
            Object result = method.invoke(service, args); //проверим возвращаемый методом результат
            if (cache.listSize() > 0 && result instanceof List) {
                List<?> newList = subList((List<?>) result, cache.listSize());
                result = (Object) newList;
            }
            Saver saver = new Saver(cache);
            if (cache.cacheType() == CacheType.FILE ) { //запишем в файл
                saver.chackAndSave(result);
                return result;
            } else { //запишем в память
                if (!cacheMap.containsKey(key(method, args))) {
                    Object invoke = invoke(method, args);
                    cacheMap.put(key(method, args), invoke);
                    return result;
                } else {
                    System.out.println("Результат из памяти");
                    return cacheMap.get(key(method, args));
                }
            }

        }
    }
    private Object invoke(Method method, Object[] args) throws Throwable {
        try {
            return method.invoke(service, args);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Невозможно выполнить", e);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }

    private Object key(Method method, Object[] args) {
        List<Object> key = new ArrayList<>();
        key.add(method);
        key.addAll(Arrays.asList(args));
        return key;
    }

    //генератор ключа
    private String generateKey(Method method, Cache cache, Object[] args){
        Class[] param = cache.identetyBy();
        StringBuilder keyBuilder = new StringBuilder(method.getName()); //ключ
        for (Object arg : args) {
            for (Class par : param) {
                if (arg.getClass().equals(par)) { //если класс параметра равен классу аргумента
                    System.out.println(arg);
                    keyBuilder.append(arg); //добавляем пареметр к ключу
                }
            }
        }
        return keyBuilder.toString();
    }

    public List<?> subList(List<?> list, int maxNumOfElement){
        List<?> listResult = (List<?>) list; //создаем новый лист
        if (listResult.size() > maxNumOfElement) {// Уменьшаем количество элементов
            listResult = list.subList(0, maxNumOfElement);
        }
        return listResult;
    }

}

