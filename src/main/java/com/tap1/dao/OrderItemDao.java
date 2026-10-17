package com.tap1.dao;

import java.util.List;
import com.tap1.model.OrderItem;

public interface OrderItemDao {
    void addOrderItem(OrderItem orderItem);
    OrderItem getOrderItem(int orderItemId);
    void updateOrderItem(OrderItem orderItem);
    void deleteOrderItem(int orderItemId);
    List<OrderItem> getAllOrderItems();
    
    // Domain-specific method to fetch all line items associated with a specific order
    List<OrderItem> getOrderItemsByOrderId(int orderId);
}