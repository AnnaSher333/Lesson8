package Proxy;

import java.lang.reflect.Proxy;

public class LoaderImpl implements Loader{
    @Override
    public Service loadClass(Service service) {
        ClassLoader classLoader = service.getClass().getClassLoader();
        Class[] interfaces = service.getClass().getInterfaces();
        return (Service) Proxy.newProxyInstance(classLoader, interfaces, new CacheProxy(service));
    }

}
