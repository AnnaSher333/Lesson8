package Proxy;

import java.io.IOException;
import java.lang.reflect.Proxy;

public class Main {
    public static void main(String[] args) {
        CacheProxy cacheProxy = new CacheProxy();
        Service service = cacheProxy.cache(new ServiceImpl());
        Loader loader = cacheProxy.cache(new LoaderImpl());

       run(service);

    }
    public static void run(Service service){
        double d1 = service.doHardWork("work1", 10);
        System.out.println(d1);
        double d2 = service.doHardWork("work2", 5);
        System.out.println(d2);
        double d3 = service.doHardWork("work1", 10);
        System.out.println(d3);
    }


}
