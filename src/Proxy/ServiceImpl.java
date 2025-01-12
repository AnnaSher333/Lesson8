package Proxy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ServiceImpl implements Service, Serializable {
    @Override
    public double doHardWork(String name, int number) {
        return (name.length() + number) * 0.1;
    }

    public List<Integer> listReturn(List<Integer> list){
        List<Integer> listWithHardWork = new ArrayList<>();
        for (Integer i: list){
            listWithHardWork.add(i+1);
        }
        return listWithHardWork;
    }
}
