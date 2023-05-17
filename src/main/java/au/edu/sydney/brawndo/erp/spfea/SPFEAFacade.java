package au.edu.sydney.brawndo.erp.spfea;

import au.edu.sydney.brawndo.erp.auth.AuthModule;
import au.edu.sydney.brawndo.erp.auth.AuthToken;
import au.edu.sydney.brawndo.erp.database.TestDatabase;
import au.edu.sydney.brawndo.erp.ordering.Customer;
import au.edu.sydney.brawndo.erp.ordering.Order;
import au.edu.sydney.brawndo.erp.ordering.Product;
import au.edu.sydney.brawndo.erp.spfea.contactsenders.*;
import au.edu.sydney.brawndo.erp.spfea.ordering.*;
import au.edu.sydney.brawndo.erp.spfea.products.ProductDatabase;

import java.time.LocalDateTime;
import java.util.*;

@SuppressWarnings("Duplicates")
public class SPFEAFacade {
    private AuthToken token;

    private UnitOfWorkOrder uowOrder;

    public boolean login(String userName, String password) {
        token = AuthModule.login(userName, password);
        uowOrder = UnitOfWorkOrder.getInstance();

        return null != token;
    }

    public List<Integer> getAllOrders() {
        if (null == token) {
            throw new SecurityException();
        }

        TestDatabase database = TestDatabase.getInstance();

        List<Order> orders = database.getOrders(token);

        List<Integer> result = new ArrayList<>();

        for (Order order: orders) {
            result.add(order.getOrderID());
        }

        return result;
    }

    public Integer createOrder(int customerID, LocalDateTime date, boolean isBusiness, boolean isSubscription, int discountType, int discountThreshold, int discountRateRaw, int numShipments) {
        if (null == token) {
            throw new SecurityException();
        }

        if (discountRateRaw < 0 || discountRateRaw > 100) {
            throw new IllegalArgumentException("Discount rate not a percentage");
        }

        double discountRate = 1.0 - (discountRateRaw / 100.0);


        if (!TestDatabase.getInstance().getCustomerIDs(token).contains(customerID)) {
            throw new IllegalArgumentException("Invalid customer ID");
        }

        int id = TestDatabase.getInstance().getNextOrderID();
        ConcreteOrder concreteOrder = new ConcreteOrder(id, date, discountRate, customerID);;
        if (isSubscription) {
            if (1 == discountType) { // 1 is flat rate
                concreteOrder = new ConcreteSubscriptionOrder(id, date, discountRate, customerID, numShipments);
                concreteOrder.accept(new ConcreteOrderVisitor(isBusiness, discountType));
            } else if (2 == discountType) { // 2 is bulk discount
                concreteOrder = new ConcreteSubscriptionOrder(id, date, discountRate, discountThreshold, customerID, numShipments);
                concreteOrder.accept(new ConcreteOrderVisitor(isBusiness, discountType));
            } else {return null;}
        } else {
            if (1 == discountType) {
                if (isBusiness) {
                    concreteOrder = new ConcreteOrder(id, date, discountRate, customerID);
                    concreteOrder.accept(new ConcreteOrderVisitor(isBusiness, discountType));

                } else {
                    concreteOrder = new ConcreteOrder(id, date, discountRate, customerID);
                    concreteOrder.accept(new ConcreteOrderVisitor(isBusiness, discountType));
                }
            } else if (2 == discountType) {
                if (isBusiness) {
                    concreteOrder = new ConcreteOrder(id, date, discountRate, discountThreshold, customerID);
                    concreteOrder.accept(new ConcreteOrderVisitor(isBusiness, discountType));
                } else {
                    concreteOrder = new ConcreteOrder(id, date, discountRate, discountThreshold, customerID);
                    concreteOrder.accept(new ConcreteOrderVisitor(isBusiness, discountType));
                }
            } else {return null;}
        }

        TestDatabase.getInstance().saveOrder(token, concreteOrder);
        this.uowOrder.saveOrder(concreteOrder);
        return concreteOrder.getOrderID();
    }

    public List<Integer> getAllCustomerIDs() {
        if (null == token) {
            throw new SecurityException();
        }

        TestDatabase database = TestDatabase.getInstance();
        return database.getCustomerIDs(token);
    }

    public Customer getCustomer(int id) {
        if (null == token) {
            throw new SecurityException();
        }

        return new CustomerImpl(token, id);
    }

    public boolean removeOrder(int id) {
        if (null == token) {
            throw new SecurityException();
        }

        TestDatabase database = TestDatabase.getInstance();
        return database.removeOrder(token, id);
    }

    public List<Product> getAllProducts() {
        if (null == token) {
            throw new SecurityException();
        }

        return new ArrayList<>(ProductDatabase.getTestProducts());
    }

    public boolean finaliseOrder(int orderID, List<String> contactPriority) {
        if (null == token) {
            throw new SecurityException();
        }


        List<ContactSender> contactPrioritySenders = new ArrayList<>();

        if (null != contactPriority) {
            for (String method: contactPriority) {
                switch (method.toLowerCase()) {
                    case "merchandiser":

                        contactPrioritySenders.add(new MerchandiserSender());
                        break;
                    case "email":

                        contactPrioritySenders.add(new EmailSender());
                        break;
                    case "carrier pigeon":

                        contactPrioritySenders.add(new CarrierPigeonSender());
                        break;
                    case "mail":

                        contactPrioritySenders.add(new MailSender());
                        break;
                    case "phone call":

                        contactPrioritySenders.add(new PhoneCallSender());
                        break;
                    case "sms":

                        contactPrioritySenders.add(new SmsSender());
                        break;
                    default:
                        break;
                }
            }
        }

        if (contactPrioritySenders.size() == 0) { // needs setting to default
            contactPrioritySenders = Arrays.asList(
                    new MerchandiserSender(),
                    new EmailSender(),
                    new CarrierPigeonSender(),
                    new MailSender(),
                    new PhoneCallSender(),
                    new SmsSender()
            );
        }

        Order order = TestDatabase.getInstance().getOrder(token, orderID);
        order.finalise();
//        Order order = this.uowOrder.getOrder(orderID);
//        order.finalise();
        TestDatabase.getInstance().saveOrder(token, order);

        return ContactHandler.sendInvoice(token, getCustomer(order.getCustomer()), contactPrioritySenders, order.generateInvoiceData());
        //return ContactHandler.sendInvoice(token, getCustomer(order.getCustomer()), contactPriorityAsMethods, order.generateInvoiceData());
    }

    public void logout() {
        AuthModule.logout(token);
        token = null;
    }

    public double getOrderTotalCost(int orderID) {
        if (null == token) {
            throw new SecurityException();
        }

        Order order = TestDatabase.getInstance().getOrder(token, orderID);
        if (null == order) {
            return 0.0;
        }

        return order.getTotalCost();
    }

    public void orderLineSet(int orderID, Product product, int qty) {
        if (null == token) {
            throw new SecurityException();
        }

        Order order = TestDatabase.getInstance().getOrder(token, orderID);

        if (null == order) {
            System.out.println("got here");
            return;
        }

        order.setProduct(product, qty);

        this.uowOrder.saveOrder(order);
        TestDatabase.getInstance().saveOrder(token, order);
    }

    public String getOrderLongDesc(int orderID) {
        if (null == token) {
            throw new SecurityException();
        }

        Order order = TestDatabase.getInstance().getOrder(token, orderID);
        if (null == order) {
            return null;
        }

        return order.longDesc();
    }

    public String getOrderShortDesc(int orderID) {
        if (null == token) {
            throw new SecurityException();
        }

        Order order = TestDatabase.getInstance().getOrder(token, orderID);
        if (null == order) {
            return null;
        }

        return order.shortDesc();
    }

    public List<String> getKnownContactMethods() {if (null == token) {
        throw new SecurityException();
    }

        return ContactHandler.getKnownMethods();
    }
}
