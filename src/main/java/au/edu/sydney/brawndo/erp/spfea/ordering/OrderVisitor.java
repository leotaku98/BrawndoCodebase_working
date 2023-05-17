package au.edu.sydney.brawndo.erp.spfea.ordering;

import au.edu.sydney.brawndo.erp.ordering.Order;
/**
 * This visitor pattern is not exactly as the one in lecture.
 * The original visitor pattern will perform different action depends on the type of class.
 * But this is violating the requirements of reducing number of orders.
 * Therefore, I use manual way to decide the action of visit method.
 * In return, Subscription, business or personal have their own visit, and no need to have so many classes.
 * And if we want more types of classes, add new visit here. Easy for extension and maintain.
 * I still keep Product separate from SubscriptionProduct, as they are two different interfaces.
 */
public interface OrderVisitor {

    Double visitTotalCost(ConcreteOrder order);
    Double visitTotalCost(ConcreteSubscriptionOrder order);

    String visitInvoiceData(ConcreteOrder order);

}

