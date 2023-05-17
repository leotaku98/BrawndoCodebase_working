package au.edu.sydney.brawndo.erp.spfea.ordering;

import au.edu.sydney.brawndo.erp.ordering.Product;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * This visitor pattern is not exactly as the one in lecture.
 * The original visitor pattern will perform different action depends on the type of class.
 * But this is violating the requirements of reducing number of orders.
 * Therefore, I use manual way to decide the action of visit method.
 * In return, Subscription, business or personal have their own visit, and no need to have so many classes.
 * And if we want more types of classes, add new visit here. Easy for extension and maintain :)
 */
public class ConcreteOrderVisitor implements OrderVisitor {
    boolean isBusiness;
    int discountType;
    public ConcreteOrderVisitor(boolean isBusiness, int discountType){
        this.isBusiness = isBusiness;
        this.discountType = discountType;
    }

    @Override
    public Double visitTotalCost(ConcreteOrder order) {
        Map<Product, Integer> products = order.getProducts();
        double discountRate = order.getDiscountRate();
        int discountThreshold = order.getDiscountThreshold();
        double cost = 0.0;
        if (1 == discountType) {
            for (Product product: products.keySet()) {
                cost +=  products.get(product) * product.getCost() * discountRate;
            }
        } else if (2 == discountType) {
            for (Product product: products.keySet()) {
                System.out.println(product.getCost());
                int count = products.get(product);
                if (count >= discountThreshold) {
                    cost +=  count * product.getCost() * discountRate;
                } else {
                    cost +=  count * product.getCost();
                }
            }

        } else {return null;}

        return cost;
    }

    @Override
    public Double visitTotalCost(ConcreteSubscriptionOrder order) {
        Map<Product, Integer> products = order.getProducts();
        double discountRate = order.getDiscountRate();
        int discountThreshold = order.getDiscountThreshold();
        int numShipments = order.numberOfShipmentsOrdered();
        double cost = 0.0;
        if (1 == discountType) { // 1 is flat rate

            if (isBusiness) {
                cost = order.getTotalCost() * numShipments;
                //order = new NewOrderImplSubscription(id, date, customerID, discountRate, numShipments);
            } else {
                cost = order.getTotalCost() * numShipments;
                //order = new Order66Subscription(id, date, discountRate, customerID, numShipments);
            }
        } else if (2 == discountType) { // 2 is bulk discount
            if (isBusiness) {
                cost = order.getTotalCost() * numShipments;
                //order = new BusinessBulkDiscountSubscription(id, customerID, date, discountThreshold, discountRate, numShipments);
            } else {
                cost = order.getTotalCost() * numShipments;
                //order = new FirstOrderSubscription(id, date, discountRate, discountThreshold, customerID, numShipments);
            }
        } else {return null;}
        return cost;
    }


    public String visitInvoiceData(ConcreteOrder order){
        Map<Product, Integer> products = order.getProducts();
        double discountRate = order.getDiscountRate();
        int discountThreshold = order.getDiscountThreshold();
        if (1 == discountType) { // 1 is flat rate

            if (isBusiness) {
                return String.format("Your business account has been charged: $%,.2f" +
                        "\nPlease see your Brawndo© merchandising representative for itemised details.", order.getTotalCost());
                //order = new NewOrderImplSubscription(id, date, customerID, discountRate, numShipments);
            } else {
                StringBuilder sb = new StringBuilder();

                sb.append("Thank you for your Brawndo© order!\n");
                sb.append("Your order comes to: $");
                sb.append(String.format("%,.2f", order.getTotalCost()));
                sb.append("\nPlease see below for details:\n");
                List<Product> keyList = new ArrayList<>(products.keySet());
                keyList.sort(Comparator.comparing(Product::getProductName).thenComparing(Product::getCost));

                for (Product product: keyList) {
                    sb.append("\tProduct name: ");
                    sb.append(product.getProductName());
                    sb.append("\tQty: ");
                    sb.append(products.get(product));
                    sb.append("\tCost per unit: ");
                    sb.append(String.format("$%,.2f", product.getCost()));
                    sb.append("\tSubtotal: ");
                    sb.append(String.format("$%,.2f\n", product.getCost() * products.get(product)));
                }

                return sb.toString();
                //order = new Order66Subscription(id, date, discountRate, customerID, numShipments);
            }
        } else if (2 == discountType) { // 2 is bulk discount
            if (isBusiness) {
                return String.format("Your business account has been charged: $%,.2f" +
                        "\nPlease see your Brawndo© merchandising representative for itemised details.", order.getTotalCost());
                //order = new BusinessBulkDiscountSubscription(id, customerID, date, discountThreshold, discountRate, numShipments);
            } else {
                StringBuilder sb = new StringBuilder();

                sb.append("Thank you for your Brawndo© order!\n");
                sb.append("Your order comes to: $");
                sb.append(String.format("%,.2f", order.getTotalCost()));
                sb.append("\nPlease see below for details:\n");
                List<Product> keyList = new ArrayList<>(products.keySet());
                keyList.sort(Comparator.comparing(Product::getProductName).thenComparing(Product::getCost));

                for (Product product: keyList) {
                    sb.append("\tProduct name: ");
                    sb.append(product.getProductName());
                    sb.append("\tQty: ");
                    sb.append(products.get(product));
                    sb.append("\tCost per unit: ");
                    sb.append(String.format("$%,.2f", product.getCost()));
                    sb.append("\tSubtotal: ");
                    sb.append(String.format("$%,.2f\n", product.getCost() * products.get(product)));
                }

                return sb.toString();
                //order = new FirstOrderSubscription(id, date, discountRate, discountThreshold, customerID, numShipments);
            }
        }
        String s = "Your business account has been charged: $1,100.00\n" +
                "Please see your Brawndo© merchandising representative for itemised details.";

        return s;
    }


}
