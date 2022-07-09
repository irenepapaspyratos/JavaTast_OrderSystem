import model.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ShopService {
    static Map<String, Product> allProducts = new HashMap<>();
    static ProductRepo productMap = new ProductRepo(allProducts);

    static Map<String, Order> allOrders = new HashMap<>();
    static OrderRepo orderMap = new OrderRepo(allOrders);


    public static void main(String[] args) {

        boolean moreActionChoice = false;

        int mainChoice = chosenMainAction();


        switch (mainChoice) {
            case 1:
                if (!allProducts.isEmpty()) {
                    System.out.println(productMap);
                } else {
                    System.out.println("Your product list is empty.");
                }

                moreActionChoice = moreAction();
                break;

            case 2:
                int choiceProduct = chosenAction("product");
                if (choiceProduct == 3) {
                    moreActionChoice = true;
                } else if (editMap(choiceProduct, productMap, null, "product") == 3) {
                    moreActionChoice = true;
                } else {
                    moreActionChoice = moreAction();
                }

                break;

            case 3:
                if (!allProducts.isEmpty()) {
                    showProduct(allProducts);
                } else {
                    System.out.println("Your product list is empty.");
                }

                moreActionChoice = moreAction();
                break;

            case 4:
                if (!allOrders.isEmpty()) {
                    System.out.println(orderMap);
                } else {
                    System.out.println("Your order list is empty.");
                }

                moreActionChoice = moreAction();
                break;

            case 5:
                int choiceOrder = chosenAction("order");
                if (choiceOrder == 3) {
                    moreActionChoice = true;
                } else if (editMap(choiceOrder, null, orderMap, "order") == 3) {
                    moreActionChoice = true;
                } else {
                    moreActionChoice = moreAction();
                }

                break;

            case 6:
                if (!allOrders.isEmpty()) {
                    showOrder(allOrders);
                } else {
                    System.out.println("Your order list is empty.");
                }

                moreActionChoice = moreAction();
                break;
                
            case 8:
                System.out.println("You left the program.");

                moreActionChoice = false;
                break;
            default:
                System.out.println("Your choice does not match one of the given options.");
                chosenMainAction();
                break;
        }


        if (moreActionChoice) main(null);

    }

    public static boolean moreAction() {
        Scanner terminalScanner = new Scanner(System.in);
        System.out.println("Would you like to do anything else: \n" +
                "1 = Yes, back to Start! \n" +
                "2 = No, quit now. \n" +
                "Your choice: ");
        String moreAction = terminalScanner.nextLine();

        while (!moreAction.matches("[1-2]")) {
            System.out.println("Your choice did not match the options - try again.");
            moreAction();
        }

        System.out.println("You selected option " + moreAction + " - Let's go!");
        if (moreAction.equals("1")) return true;

        System.out.println("You left the program.");
        return false;
    }

    public static int chosenMainAction() {
        Scanner terminalScanner = new Scanner(System.in);
        System.out.println("What would you like to do? \n" +
                "1 = Show all products \n" +
                "2 = Edit the product list (add or delete products) \n" +
                "3 = Show details of a single product \n" +
                "4 = Show all orders \n" +
                "5 = Edit the order list (add or delete orders) \n" +
                "6 = Show details of a single order \n" +
                "7 = Edit a specific order (add or delete products) \n" +
                "8 = Quit \n" +
                "Your choice: ");
        String actionChoice = terminalScanner.nextLine();

        if (actionChoice.equals("0")) actionChoice = "666";
        if (actionChoice.matches("[1-8]")) System.out.println("You selected option " + actionChoice + " - Let's go!");
        return Integer.parseInt(actionChoice);
    }

    public static int chosenAction(String typeOf) {
        Scanner terminalScanner = new Scanner(System.in);
        System.out.println("What would you like to do? \n" +
                "1 = Add " + typeOf + " \n" +
                "2 = Delete " + typeOf + " \n" +
                "3 = Back to Start \n" +
                "Your Choice: ");
        String actionChoice = terminalScanner.nextLine();

        while (!actionChoice.matches("[1-3]")) {
            System.out.println("Your choice does not match one of the given options.");
            chosenAction(typeOf);
        }

        System.out.println("You selected option " + actionChoice + " - Let's go!");
        return Integer.parseInt(actionChoice);
    }

    private static int editMap(int chosenAction, ProductRepo mapP, OrderRepo mapO, String typeOf) {

        Product returnValueP = null;
        Order returnValueO = null;
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

        int addition = getAll.size();
        id = "" + addition;

        while (id.equals("0") || getAll.containsKey(id)) {
            id = "" + (Integer.parseInt(id) + 1);
        }

        return id;
    }

    public static String manualId() {
        Scanner terminalScanner = new Scanner(System.in);
        System.out.println("The id of your new item must be: \n" +
                "not empty, not 0, not already existing. \n" +
                "Enter your id or quit with 0: ");
        String id = terminalScanner.nextLine();

        while (id.equals(" ") || id.equals("")) {
            System.out.println("Given id does not match the criteria - try again: ");
            id = terminalScanner.nextLine();
        }

        return id;
    }


    public static void showProduct(Map<String, Product> allProducts) {
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
    }

    public static Product createProduct(ProductRepo productMap) {
        Scanner terminalScanner = new Scanner(System.in);
        System.out.println("Enter the Name of your new product: ");
        String productName = terminalScanner.nextLine();
        System.out.println("What would you like to do? \n" +
                "1 = Enter your product id manually \n" +
                "2 = Let a product id be generated automatically \n" +
                "3 = Back to Start \n" +
                "Your Choice: ");
        String productCreateChoice = terminalScanner.nextLine();

        while (!productCreateChoice.matches("[1-3]")) {
            System.out.println("Your choice does not match one of the given options.");
            System.out.println("What would you like to do? \n" +
                    "1 = Enter your product id manually \n" +
                    "2 = Let a product id be generated automatically \n" +
                    "3 = Back to Start \n" +
                    "Your Choice: ");
            productCreateChoice = terminalScanner.nextLine();
        }
        int productIdChoice = Integer.parseInt(productCreateChoice);

        if (productIdChoice == 1) {

            String productId = manualId();
            if (productId.equals("0")) return null;

            Product newProduct = new Product(productId, productName);
            Product addedProduct = productMap.addProduct(newProduct);

            while (addedProduct == null) {
                productId = manualId();
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


    public static void showOrder(Map<String, Order> allOrders) {
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
    }

    public static Order createOrder(OrderRepo orderMap) {
        Scanner terminalScanner = new Scanner(System.in);
        System.out.println("What would you like to do? \n" +
                "1 = Enter your order id manually \n" +
                "2 = Let a order id be generated automatically \n" +
                "3 = Back to Start \n" +
                "Your Choice: ");
        String orderCreateChoice = terminalScanner.nextLine();

        while (!orderCreateChoice.matches("[1-3]")) {
            System.out.println("Your choice does not match one of the given options.");
            System.out.println("What would you like to do? \n" +
                    "1 = Enter your order id manually \n" +
                    "2 = Let a order id be generated automatically \n" +
                    "3 = Back to Start \n" +
                    "Your Choice: ");
            orderCreateChoice = terminalScanner.nextLine();
        }
        int orderIdChoice = Integer.parseInt(orderCreateChoice);

        Map<String, Integer> newOrderList = new HashMap<>();
        if (orderIdChoice == 1) {

            String orderId = manualId();
            if (orderId.equals("0")) return null;

            Order newOrder = new Order(orderId, newOrderList);
            Order addedOrder = orderMap.addOrder(newOrder);

            while (addedOrder == null) {
                orderId = manualId();
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


}
