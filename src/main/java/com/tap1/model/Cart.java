package com.tap1.model;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    private Map<Integer, CartItem> items;

    public Cart() {
        this.items = new HashMap<>();
    }

    // Add item or increment quantity if already present
    public void addItem(CartItem item) {
        int itemId = item.getItemId();
        if (items.containsKey(itemId)) {
            CartItem existingItem = items.get(itemId);
            existingItem.setQuantity(existingItem.getQuantity() + item.getQuantity());
        } else {
            items.put(itemId, item);
        }
    }

    // Update item quantity directly or remove if quantity <= 0
    public void updateItem(int itemId, int quantity) {
        if (items.containsKey(itemId)) {
            if (quantity <= 0) {
                items.remove(itemId);
            } else {
                items.get(itemId).setQuantity(quantity);
            }
        }
    }

    // Remove item completely
    public void removeItem(int itemId) {
        items.remove(itemId);
    }

    // Get map of items
    public Map<Integer, CartItem> getItems() {
        return items;
    }

    // Calculate subtotal for all items in the cart
    public double getTotalAmount() {
        double total = 0.0;
        for (CartItem item : items.values()) {
            total += item.getTotalPrice();
        }
        return total;
    }

    // Clear cart contents after checkout
    public void clear() {
        items.clear();
    }
}