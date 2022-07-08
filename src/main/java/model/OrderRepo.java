package model;

import java.util.Map;

public class OrderRepo {
    private Map<String, Order> allOrders;

    public OrderRepo(Map<String, Order> allOrders) {
        this.allOrders = allOrders;
    }

    public Map<String, Order> getAllOrders() {
        return allOrders;
    }

    public void setAllOrders(Map<String, Order> allOrders) {
        this.allOrders = allOrders;
    }

    public String addOrder (Order newOrder) {
        return ("Successfully added: " + allOrders.put(newOrder.id(), newOrder));
    }

     public String removeOrder (String id) {
        return ( "Successfully removed: " + allOrders.remove(id) );
    }

    @Override
    public String toString() {
        return "OrderRepo{" +
                "allOrders=" + allOrders +
                '}';
    }
}
