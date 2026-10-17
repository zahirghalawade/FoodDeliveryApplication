package com.tap1.dao;

import java.util.List;

import com.tap1.model.OrderTable;

public interface OrderTableDao {
    void addOrder(OrderTable order);
    void addOrderTable(OrderTable order);
    int getLatestOrderIdByUserId(int userId);
    OrderTable getOrder(int orderId);
    void updateOrder(OrderTable order);
    void deleteOrder(int orderId);
    List<OrderTable> getAllOrders();
    
    // Domain-specific methods for food delivery app
    List<OrderTable> getOrdersByUserId(int userId);
    List<OrderTable> getOrdersByRestaurantId(int restaurantId);
}