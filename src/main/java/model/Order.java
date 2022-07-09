package model;

import java.util.Map;


// Order: orderId => [{productId1 => ordered number}, {productId2 => ordered number},...]
public record Order(String id, Map<String, Integer> itemsToOrderList) {
}
