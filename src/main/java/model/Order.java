package model;

import java.util.Map;

public record Order(String id, Map<String, Integer> orderList) {
}
