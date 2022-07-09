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

    public Order addOrder(Order newOrder) {
        if (allOrders.containsKey(newOrder.id())) {
            System.out.println("Order-ID is already existing!");
            return null;
        }
        Order addedOrder = allOrders.put(newOrder.id(), newOrder);
        System.out.println("Successfully added: " + newOrder);
        return addedOrder == null ? newOrder : null;
    }

    public Order removeOrder(String id) {
        if (!allOrders.containsKey(id)) {
            System.out.println("Order-ID not found!");
            return null;
        }
        Order removedOrder = allOrders.remove(id);
        System.out.println("Successfully removed: " + removedOrder);
        return removedOrder;
    }

    @Override
    public String toString() {
        return "OrderRepo{" +
                "allOrders=" + allOrders +
                '}';
    }
}
