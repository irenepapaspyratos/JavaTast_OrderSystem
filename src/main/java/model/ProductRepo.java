package model;

import java.util.Map;

public class ProductRepo {
    private Map<String, Product> allProducts;

    public ProductRepo(Map<String, Product> allProducts) {
        this.allProducts = allProducts;
    }

    public Map<String, Product> getAllProducts() {
        return allProducts;
    }

    public void setAllProducts(Map<String, Product> allProducts) {
        this.allProducts = allProducts;
    }

    public Product addProduct(Product newProduct) {
        if (allProducts.containsKey(newProduct.id())) {
            System.out.println("Product-ID is already existing!");
            return null;
        }
        Product addedProduct = allProducts.put(newProduct.id(), newProduct);
        System.out.println("Successfully added: " + newProduct);
        return addedProduct == null ? newProduct : null;
    }

    public Product removeProduct(String id) {
        if (!allProducts.containsKey(id)) {
            System.out.println("Product-ID not found!");
            return null;
        }
        Product removedProduct = allProducts.remove(id);
        System.out.println("Successfully removed: " + removedProduct);
        return removedProduct;
    }

    @Override
    public String toString() {
        return "ProductRepo{" +
                "allProducts=" + allProducts +
                '}';
    }
}
