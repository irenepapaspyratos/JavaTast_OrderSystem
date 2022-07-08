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
        Product addedProduct = allProducts.put(newProduct.id(), newProduct);
        System.out.println("Successfully added: " + newProduct);
        return addedProduct;
    }

    public Product removeProduct(String id) {
        Product removedProduct = allProducts.remove(id);
        System.out.println("Successfully removed: " + ((removedProduct != null) ? removedProduct : "NONE - ID not found!"));
        return removedProduct;
    }

    @Override
    public String toString() {
        return "ProductRepo{" +
                "allProducts=" + allProducts +
                '}';
    }
}
