package Proxy;

public interface Service {
    @Cache (cacheType = CacheType.IN_MEMORY, fileNamePrefix = "test", zip = false, identetyBy = {String.class})
    double doHardWork(String name, int number);

}
