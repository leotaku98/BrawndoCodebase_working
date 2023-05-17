package au.edu.sydney.brawndo.erp.spfea.ordering;

import au.edu.sydney.brawndo.erp.ordering.SubscriptionOrder;

import java.time.LocalDateTime;

public class ConcreteSubscriptionOrder extends ConcreteOrder  {
    private int numShipments;
    public ConcreteSubscriptionOrder(int id, LocalDateTime date, double discountRate, int discountThreshold, int customerID, int numShipments) {
        super(id, date, discountRate, discountThreshold, customerID);
        this.numShipments = numShipments;
    }

    public ConcreteSubscriptionOrder(int id, LocalDateTime date, double discountRate, int customerID, int numShipments) {
        super(id, date, discountRate, customerID);
        this.numShipments = numShipments;
    }

    public double getRecurringCost(){
        return super.getTotalCost();
    }

    public int numberOfShipmentsOrdered(){
        return numShipments;
    }
    
}
