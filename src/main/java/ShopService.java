import model.Order;
import model.OrderRepo;
import model.Product;
import model.ProductRepo;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ShopService {

    public static void main(String[] args) {

        int mainChoice = chosenMainAction();

        Map<String, Product> allProducts = new HashMap<>();
        ProductRepo productMap = new ProductRepo(allProducts);

        Map<String, Order> allOrders = new HashMap<>();
        OrderRepo orderMap = new OrderRepo(allOrders);

        String moreActionChoice = null;

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
                int productChoice = chosenProductAction();
                if (productChoice == 3) main(null);
                int editChoice = editProduct(productChoice, productMap);
                if (editChoice == 3) main(null);
                moreActionChoice = moreAction();
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
            case 6:
                if (!allOrders.isEmpty()) {
                    showOrder(allOrders);
                } else {
                    System.out.println("Your order list is empty.");
                }
                moreActionChoice = moreAction();
                break;
        }


        if (moreActionChoice != null && moreActionChoice.matches("1")) main(null);

    }

    public static String moreAction() {
        Scanner eingabeScanner = new Scanner(System.in);
        System.out.println("Would you like to do anything else: \n" +
                "1 = Yes, back to Start! \n" +
                "2 = No, quit now. \n" +
                "Your choice: ");
        String moreAction = eingabeScanner.nextLine();

        while (!moreAction.matches("[1-2]")) {
            System.out.println("Your choice did not match the options - try again.");
            moreAction();
        }
        return moreAction;
    }

    public static int chosenMainAction() {
        Scanner eingabeScanner = new Scanner(System.in);
        System.out.println("What would you like to do? \n" +
                "1 = Show all products \n" +
                "2 = Edit the product list (add or delete products) \n" +
                "3 = Show details of a single product \n" +
                "4 = Show all orders \n" +
                "5 = Edit the order list (add or delete orders) \n" +
                "6 = Show details of a single order \n" +
                "7 = Edit a specific order (add or delete products) \n" +
                "Your choice: ");
        String actionChoice = eingabeScanner.nextLine();

        while (!actionChoice.matches("[1-7]")) {
            System.out.println("Your choice does not match one of the given options.");
            chosenMainAction();
        }

        System.out.println("You selected option " + actionChoice + " - Let's go!");
        return Integer.parseInt(actionChoice);
    }

    public static int chosenProductAction() {
        Scanner eingabeScanner = new Scanner(System.in);
        System.out.println("What would you like to do? \n" +
                "1 = Add a product \n" +
                "2 = Delete a product \n" +
                "3 = Back to Start \n" +
                "Your Choice: ");
        String productActionChoice = eingabeScanner.nextLine();

        while (!productActionChoice.matches("[1-3]")) {
            System.out.println("Your choice does not match one of the given options.");
            chosenProductAction();
        }

        System.out.println("You selected option " + productActionChoice + " - Let's go!");
        return Integer.parseInt(productActionChoice);
    }

    public static void showProduct(Map<String, Product> allProducts) {
        Scanner eingabeScanner = new Scanner(System.in);
        System.out.println("Enter the id of the product to display: ");
        String productId = eingabeScanner.nextLine();
        Product product = allProducts.get(productId);
    }

    private static int editProduct(int productChoice, ProductRepo productMap) {

        int returnValue = 0;
        switch (productChoice) {
            case 1:
                returnValue = createProduct(productMap);

        }

        return returnValue;

    }

    public static int createProduct(ProductRepo productMap) {
        Scanner eingabeScanner = new Scanner(System.in);
        System.out.println("Enter the Name of your new product: ");
        String productName = eingabeScanner.nextLine();
        System.out.println("What would you like to do? \n" +
                "1 = Enter your product id manually \n" +
                "2 = Let a product id be generated automatically \n" +
                "3 = Back to Start \n" +
                "Your Choice: ");
        String productCreateChoice = eingabeScanner.nextLine();
        while (!productCreateChoice.matches("[1-3]")) {
            System.out.println("Your choice does not match one of the given options.");
            System.out.println("What would you like to do? \n" +
                    "1 = Enter your product id manually \n" +
                    "2 = Let a product id be generated automatically \n" +
                    "3 = Back to Start \n" +
                    "Your Choice: ");
            productCreateChoice = eingabeScanner.nextLine();
        }
        int productIdChoice = Integer.parseInt(productCreateChoice);
        if (productIdChoice == 1) {
            System.out.println("Enter the id of your new product: ");
            String productId = eingabeScanner.nextLine();
            if (productMap.getAllProducts().containsKey(productId)) {
                System.out.println("Product id already exists - try again: ");
                productId = eingabeScanner.nextLine();
            }
            productMap.addProduct(new Product(productId, productName));
        } else if (productIdChoice == 2) {
            String productId = generateProductId(productMap);
            productMap.addProduct(new Product(productId, productName));
        }
        return productIdChoice;
    }

    public static String generateProductId(ProductRepo productMap) {
        // can certainly be improved
        String productId = "" + productMap.getAllProducts().size();
        while (productId.matches("0") || productMap.getAllProducts().containsKey(productId)) {
            productId = "" + (Integer.parseInt(productId) + 1);
        }
        return productId;
    }

    public static void showOrder(Map<String, Order> allOrders) {
        Scanner eingabeScanner = new Scanner(System.in);
        System.out.println("Enter the id of the product to display: ");
        String oderId = eingabeScanner.nextLine();
        Order order = allOrders.get(oderId);
    }

}
