import model.Order;
import model.OrderRepo;
import model.Product;
import model.ProductRepo;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ShopService {

    public static void main(String[] args) {

        Map<String, Product> allProducts = new HashMap<>();
        ProductRepo productMap = new ProductRepo(allProducts);

        Map<String, Order> allOrders = new HashMap<>();
        OrderRepo orderMap = new OrderRepo(allOrders);

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
                int productChoice = chosenProductAction();
                if (productChoice == 3) {
                    moreActionChoice = true;
                } else if (editProductMap(productChoice, productMap) == 3) {
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

        while (!actionChoice.matches("[1-8]")) {
            System.out.println("Your choice does not match one of the given options.");
            chosenMainAction();
        }

        System.out.println("You selected option " + actionChoice + " - Let's go!");
        return Integer.parseInt(actionChoice);
    }

    public static int chosenProductAction() {
        Scanner terminalScanner = new Scanner(System.in);
        System.out.println("What would you like to do? \n" +
                "1 = Add a product \n" +
                "2 = Delete a product \n" +
                "3 = Back to Start \n" +
                "Your Choice: ");
        String productActionChoice = terminalScanner.nextLine();

        while (!productActionChoice.matches("[1-3]")) {
            System.out.println("Your choice does not match one of the given options.");
            chosenProductAction();
        }

        System.out.println("You selected option " + productActionChoice + " - Let's go!");
        return Integer.parseInt(productActionChoice);
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
            if (!tryAgain.matches("(?i)(y|yes)")) showProduct(allProducts);
        }
    }

    private static int editProductMap(int productChoice, ProductRepo productMap) {

        Product returnValue = null;
        switch (productChoice) {
            case 1:
                returnValue = createProduct(productMap);
                break;
            case 2:
                returnValue = deleteProduct(productMap);
                break;
        }

        return returnValue == null ? 3 : 0;
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
            System.out.println("The id of your new product must be: \n" +
                    "not empty, not 0, not already existing. \n" +
                    "Enter your id: ");
            String productId = terminalScanner.nextLine();

            Product newProduct = new Product(productId, productName);
            Product addedProduct = productMap.addProduct(newProduct);
            while (addedProduct == null || productId.equals("0") || productId.equals(" ") || productId.equals("")) {
                System.out.println("Product id does not match the criteria - try again: ");
                productId = terminalScanner.nextLine();
                newProduct = new Product(productId, productName);
                addedProduct = productMap.addProduct(newProduct);
            }

            return addedProduct;
        } else if (productIdChoice == 2) {
            String productId = generateProductId(productMap);
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
                return deletedProduct;
            }
        }
        return deletedProduct;
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
        Scanner terminalScanner = new Scanner(System.in);
        System.out.println("Enter the id of the product to display: ");
        String oderId = terminalScanner.nextLine();
        Order order = allOrders.get(oderId);
    }

}
