package au.edu.sydney.brawndo.erp.spfea.products;

import java.util.HashMap;
import java.util.Map;

public class FlyweightFactory {
    private static FlyweightFactory instance;
    private Map<Integer, double[]> flyweights = new HashMap<>();
    private int key = 0;

    public static FlyweightFactory getInstance() {
        if (instance == null) {
            instance = new FlyweightFactory();
        }
        return instance;
    }

    public Integer addFlyweight(double[] data){
        // if new data, we add new name to it
        if (!flyweights.containsValue(data)){
            this.key ++;
            flyweights.put(this.key, data);
        }
        return this.key;
    }

    public double[] getData(Integer key){
        return flyweights.get(key);
    }
}
