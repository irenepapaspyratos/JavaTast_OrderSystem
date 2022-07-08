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

    public String addProduct (Product newProduct) {
        return ("Successfully added: " + allProducts.put(newProduct.id(), newProduct));
    }

    public String removeProduct (String id) {
        return ( "Successfully removed: " + allProducts.remove(id) );
    }

    @Override
    public String toString() {
        return "ProductRepo{" +
                "allProducts=" + allProducts +
                '}';
    }
}
