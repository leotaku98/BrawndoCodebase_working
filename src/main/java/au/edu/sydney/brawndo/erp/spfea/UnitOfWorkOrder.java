package au.edu.sydney.brawndo.erp.spfea;

import au.edu.sydney.brawndo.erp.auth.AuthModule;
import au.edu.sydney.brawndo.erp.database.TestDatabase;
import au.edu.sydney.brawndo.erp.ordering.Order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UnitOfWorkOrder {
    private List<Order> orders = new ArrayList<>();

    private static final UnitOfWorkOrder instance = new UnitOfWorkOrder();

    public static UnitOfWorkOrder getInstance(){
        return instance;
    }

    public void saveOrder(Order order){
        System.out.println("saving..."+order.getOrderID());
//        System.out.println("savingClass..."+order.getClass());
        for (Order iter : orders) {
            if (iter.getOrderID() == order.getOrderID()) {
                orders.remove(iter);
                break;
            }
        }

        orders.add(order);
    }

    public Order getOrder(int id){
        for (Order iter : orders) {
            if (iter.getOrderID() == id) {
                return iter.copy();
            }
        }

        return null;
    }
}
