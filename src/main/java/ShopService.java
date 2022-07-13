import model.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class ShopService {

    static Map<String, Product> allProducts = new HashMap<>();
    static ProductRepo productMap = new ProductRepo(allProducts);

    static Map<String, Order> allOrders = new HashMap<>();
    static OrderRepo orderMap = new OrderRepo(allOrders);

    static Map<String, Integer> allProductsToOrder = new HashMap<>();
    static ProductsToOrderList productsToOrderMap = new ProductsToOrderList(allProductsToOrder);

    static Questionary question = new Questionary();

    public static void main(String[] args) {

        boolean moreActionChoice = false;

        int mainChoice = question.askingType("mainAction");
        switch (mainChoice) {

            case 1: // show all products
                if (!allProducts.isEmpty()) {
                    System.out.println(productMap);
                } else {
                    question.printText("product list", "empty");
                }

                moreActionChoice = question.askingType("moreAction") == 1;
                break;

            case 2: // edit list of products
                int choiceProduct = question.askingType("editAction");
                if (choiceProduct == 3) {
                    moreActionChoice = true;
                } else if (editMap(choiceProduct, productMap, null, null, null, "product") == 3) {
                    moreActionChoice = true;
                } else {
                    moreActionChoice = question.askingType("moreAction") == 1;
                }
                break;

            case 3: // show single product
                if (!allProducts.isEmpty()) {
                    showProduct(allProducts);
                } else {
                    question.printText("product list", "empty");
                }

                moreActionChoice = question.askingType("moreAction") == 1;
                break;

            case 4: // show all orders
                if (!allOrders.isEmpty()) {
                    System.out.println(orderMap);
                } else {
                    question.printText("order list", "empty");
                }

                moreActionChoice = question.askingType("moreAction") == 1;
                break;

            case 5: // edit list of orders
                int choiceOrder = question.askingType("editAction");
                if (choiceOrder == 3) {
                    moreActionChoice = true;
                } else if (editMap(choiceOrder, null, orderMap, null, null, "order") == 3) {
                    moreActionChoice = true;
                } else {
                    moreActionChoice = question.askingType("moreAction") == 1;
                }
                break;

            case 6: // show single order
                if (!allOrders.isEmpty()) {
                    showOrder(allOrders);
                } else {
                    question.printText("order list", "empty");
                }

                moreActionChoice = question.askingType("moreAction") == 1;
                break;

            case 7: // edit single order
                if (!allOrders.isEmpty()) {
                    Order orderToEdit = showOrder(allOrders);
                    if (orderToEdit != null) {
                        int choice = question.askingType("editAction");
                        ;
                        if (choice == 3) {
                            moreActionChoice = true;
                        } else if (editMap(choice, null, null, productsToOrderMap, null, "productsInOrder") == 3) {
                            moreActionChoice = true;
                        } else {
                            moreActionChoice = question.askingType("moreAction") == 1;
                        }
                    } else {
                        moreActionChoice = question.askingType("moreAction") == 1;
                    }
                } else {
                    System.out.println("Your order list is empty -> nothing to edit.");
                    moreActionChoice = question.askingType("moreAction") == 1;
                }
                break;

            case 8: // quit
                question.printText(null, "quit");

                moreActionChoice = false;
                break;

            default:
                question.printText(null, "noMatch");
                main(null);
                break;
        }


        if (moreActionChoice) main(null);

    }


    // General Methods /////////////////////////////////////////////////////

    private static int editMap(int chosenAction, ProductRepo mapP, OrderRepo mapO, ProductsToOrderList mapI, String orderToEditId, String typeOf) {

        Product returnValueP = null;
        Order returnValueO = null;
        ProductsToOrderList returnValueI = null;

        if (typeOf.equals("productsInOrder")) {
            returnValueI = editOrder(orderToEditId, chosenAction, mapI);
            return returnValueO == null ? 3 : 0;
        }

        switch (chosenAction) {
            case 1:
                if (typeOf.equals("product")) returnValueP = createProduct(mapP);
                if (typeOf.equals("order")) returnValueO = createOrder(mapO);
                break;

            case 2:
                if (typeOf.equals("product")) {
                    returnValueP = deleteProduct(mapP);
                }
                if (typeOf.equals("order")) {
                    returnValueO = deleteOrder(mapO);
                }
                break;
        }

        if (typeOf.equals("product")) return returnValueP == null ? 3 : 0;
        if (typeOf.equals("order")) return returnValueO == null ? 3 : 0;
        return 3;
    }

    public static String generateId(ProductRepo mapP, OrderRepo mapO, String typeOf) {
        // can certainly be improved
        String id;
        Map getAll = null;
        if (typeOf.equals("product")) {
            getAll = mapP.getAllProducts();
        } else if (typeOf.equals("order")) {
            getAll = mapO.getAllOrders();
        }


        int addition = getAll == null ? 0 : getAll.size();
        id = "" + addition;
        boolean containsId = getAll != null && getAll.containsKey(id);

        while (id.equals("0") || containsId) {
            id = "" + (Integer.parseInt(id) + 1);
        }

        return id;
    }
    

// Product Methods ////////////////////////////////////////////////////////

    public static Product showProduct(Map<String, Product> allProducts) {
        Scanner terminalScanner = new Scanner(System.in);
        System.out.println("Enter the id of the product to display: ");
        String productId = terminalScanner.nextLine();
        Product product = allProducts.get(productId);
        if (product != null) {
            System.out.println(product);
        } else {
            System.out.println("Given id does not exist. \nTry again? y(yes) / n(no)");
            String tryAgain = terminalScanner.nextLine();
            while (!tryAgain.matches("(?i)(y|yes|n|no)")) {
                System.out.println("Your choice does not match the options.");
                System.out.println("Given id does not exist. \nTry again? y(yes) / n(no)");
                tryAgain = terminalScanner.nextLine();
            }
            if (tryAgain.matches("(?i)(y|yes)")) showProduct(allProducts);
        }

        return product;
    }

    public static Product createProduct(ProductRepo productMap) {
        Scanner terminalScanner = new Scanner(System.in);
        System.out.println("Enter the Name of your new product: ");
        String productName = terminalScanner.nextLine();

        int productIdChoice = question.askingType("idAction");

        if (productIdChoice == 1) {

            String productId = "" + question.askingType("manualIdAction");
            if (productId.equals("0")) return null;

            Product newProduct = new Product(productId, productName);
            Product addedProduct = productMap.addProduct(newProduct);

            while (addedProduct == null) {
                productId = "" + question.askingType("manualIdAction");
                newProduct = new Product(productId, productName);
                addedProduct = productMap.addProduct(newProduct);
            }

            return addedProduct;
        } else if (productIdChoice == 2) {
            String productId = generateId(productMap, null, "product");
            Product newProduct = new Product(productId, productName);

            return productMap.addProduct(newProduct);
        }

        return null;
    }

    public static Product deleteProduct(ProductRepo productMap) {
        Scanner terminalScanner = new Scanner(System.in);
        System.out.println("Enter the id of the product to remove: ");
        String idToDelete = terminalScanner.nextLine();
        Product deletedProduct = productMap.removeProduct(idToDelete);

        while (deletedProduct == null) {
            System.out.println("Given id does not exist. \nTry again? y(yes) / n(no)");
            String tryAgain = terminalScanner.nextLine();
            if (tryAgain.matches("(?i)(y|yes)")) {
                System.out.println("Enter the id of the product to remove: ");
                idToDelete = terminalScanner.nextLine();
                deletedProduct = productMap.removeProduct(idToDelete);
            } else {
                return null;
            }
        }
        return deletedProduct;
    }

    // Order Methods //////////////////////////////////////////////////////////

    public static Order showOrder(Map<String, Order> allOrders) {
        Scanner terminalScanner = new Scanner(System.in);
        System.out.println("Enter the id of the order to display: ");
        String orderId = terminalScanner.nextLine();
        Order order = allOrders.get(orderId);
        if (order != null) {
            System.out.println(order);
        } else {
            System.out.println("Given id does not exist. \nTry again? y(yes) / n(no)");
            String tryAgain = terminalScanner.nextLine();
            while (!tryAgain.matches("(?i)(y|yes|n|no)")) {
                System.out.println("Your choice does not match the options.");
                System.out.println("Given id does not exist. \nTry again? y(yes) / n(no)");
                tryAgain = terminalScanner.nextLine();
            }
            if (tryAgain.matches("(?i)(y|yes)")) showOrder(allOrders);
        }

        return order;
    }

    public static Order createOrder(OrderRepo orderMap) {
        int orderIdChoice = question.askingType("idAction");

        Map<String, Integer> newOrderList = new HashMap<>();
        if (orderIdChoice == 1) {

            String orderId = "" + question.askingType("manualIdAction");
            if (orderId.equals("0")) return null;

            Order newOrder = new Order(orderId, newOrderList);
            Order addedOrder = orderMap.addOrder(newOrder);

            while (addedOrder == null) {
                orderId = "" + question.askingType("manualIdAction");
                newOrder = new Order(orderId, newOrderList);
                addedOrder = orderMap.addOrder(newOrder);
            }

            return addedOrder;
        } else if (orderIdChoice == 2) {
            String orderId = generateId(null, orderMap, "order");
            Order newOrder = new Order(orderId, newOrderList);

            return orderMap.addOrder(newOrder);
        }

        return null;
    }

    public static Order deleteOrder(OrderRepo orderMap) {
        Scanner terminalScanner = new Scanner(System.in);
        System.out.println("Enter the id of the order to remove: ");
        String idToDelete = terminalScanner.nextLine();
        Order deletedOrder = orderMap.removeOrder(idToDelete);

        while (deletedOrder == null) {
            System.out.println("Given id does not exist. \nTry again? y(yes) / n(no)");
            String tryAgain = terminalScanner.nextLine();
            if (tryAgain.matches("(?i)(y|yes)")) {
                System.out.println("Enter the id of the order to remove: ");
                idToDelete = terminalScanner.nextLine();
                deletedOrder = orderMap.removeOrder(idToDelete);
            } else {
                return null;
            }
        }
        return deletedOrder;
    }

    private static ProductsToOrderList editOrder(String orderToEditId, int chosenAction, ProductsToOrderList mapI) {
        Set orderKeys = allOrders.keySet();
        int orderKeysLength = orderKeys.size();
        int counter = 0;
        for (Order order : allOrders.values()) {
            //if (order.id() == orderToEditId) order.productsToOrderList(); //delete this :)
            counter++;
        }
        //System.out.println(Order.get("1"));
        switch (chosenAction) {
            case 1:
                Scanner terminalScanner = new Scanner(System.in);
                System.out.println("Enter the id of the product to add: ");
                String productId = terminalScanner.nextLine();
                Product product = allProducts.get(productId);
                if (product != null) {
                    System.out.println(product);
                    System.out.println("Enter the amount of the product to add: ");
                    String productAmount = terminalScanner.nextLine();
                    mapI.addProducts(productId, Integer.parseInt(productAmount));

                } else {
                    System.out.println("Given id does not exist. \nTry again? y(yes) / n(no)");
                    String tryAgain = terminalScanner.nextLine();
                    while (!tryAgain.matches("(?i)(y|yes|n|no)")) {
                        System.out.println("Your choice does not match the options.");
                        System.out.println("Given id does not exist. \nTry again? y(yes) / n(no)");
                        tryAgain = terminalScanner.nextLine();
                    }
                    if (tryAgain.matches("(?i)(y|yes)")) editOrder(orderToEditId, chosenAction, mapI);
                }


                return mapI;
            case 2:

        }


        return null;
    }

}
