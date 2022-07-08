package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class ProductRepoTest {
    Map<String, Product> allProducts = new HashMap<>();
    ProductRepo productList = new ProductRepo(allProducts);

    @Test
    void addProduct() {
        productList.addProduct(new Product("12","blub"));
        System.out.println(productList.getAllProducts().toString());
        Assertions.assertEquals("{12=Product[id=12, Name=blub]}", productList.getAllProducts().toString());
    }

    @Test
    void removeProduct() {
        productList.addProduct(new Product("12","Golf"));
        productList.addProduct(new Product("13","Audi"));
        productList.removeProduct("13");
        Assertions.assertEquals("ProductRepo{allProducts={12=Product[id=12, Name=Golf]}}", productList.toString());

    }
}