package model;

import java.util.Map;

public record Product(String id, String Name, Map<String, Integer> productDetails) {
}