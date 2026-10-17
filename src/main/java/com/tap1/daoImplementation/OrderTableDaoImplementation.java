package com.tap1.daoImplementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.tap1.dao.OrderTableDao;
import com.tap1.model.OrderTable;
import com.tap1.utility.dbConnection;

public class OrderTableDaoImplementation implements OrderTableDao {

    private static final String INSERT_QUERY = 
        "INSERT INTO `OrderTable` (`UserID`, `RestaurantID`, `TotalAmount`, `Status`, `PaymentMethod`) VALUES (?, ?, ?, ?, ?)";
    private static final String GET_QUERY = 
        "SELECT * FROM `OrderTable` WHERE `OrderID` = ?";
    private static final String UPDATE_QUERY = 
        "UPDATE `OrderTable` SET `UserID` = ?, `RestaurantID` = ?, `TotalAmount` = ?, `Status` = ?, `PaymentMethod` = ? WHERE `OrderID` = ?";
    private static final String DELETE_QUERY = 
        "DELETE FROM `OrderTable` WHERE `OrderID` = ?";
    private static final String GETALL_QUERY = 
        "SELECT * FROM `OrderTable`";
    private static final String GET_BY_USER_QUERY = 
        "SELECT * FROM `OrderTable` WHERE `UserID` = ?";
    private static final String GET_BY_RESTAURANT_QUERY = 
        "SELECT * FROM `OrderTable` WHERE `RestaurantID` = ?";
    private static final String LATEST_ORDER_QUERY = 
        "SELECT `OrderID` FROM `OrderTable` WHERE `UserID` = ? ORDER BY `OrderID` DESC LIMIT 1";

    @Override
    public void addOrder(OrderTable order) {
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(INSERT_QUERY)) {

            pstmt.setInt(1, order.getUserId());
            pstmt.setInt(2, order.getRestaurantId());
            pstmt.setDouble(3, order.getTotalAmount());
            pstmt.setString(4, order.getStatus());
            pstmt.setString(5, order.getPaymentMethod());

            int rows = pstmt.executeUpdate();
            System.out.println(rows + " row(s) inserted.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Alias method to support OrderServlet invocation
    @Override
    public void addOrderTable(OrderTable order) {
        addOrder(order);
    }

    // Fetches the most recent OrderID created by a specific user
    @Override
    public int getLatestOrderIdByUserId(int userId) {
        int orderId = 0;
        try (Connection con = dbConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(LATEST_ORDER_QUERY)) {

            pstmt.setInt(1, userId);
            try (ResultSet res = pstmt.executeQuery()) {
                if (res.next()) {
                    orderId = res.getInt("OrderID");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderId;
    }

    @Override
    public OrderTable getOrder(int orderId) {
        OrderTable order = null;
        try (Connection con = dbConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(GET_QUERY)) {

            pstmt.setInt(1, orderId);
            try (ResultSet res = pstmt.executeQuery()) {
                if (res.next()) {
                    order = getOrderDetails(res);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }

    @Override
    public void updateOrder(OrderTable order) {
        try (Connection con = dbConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(UPDATE_QUERY)) {

            pstmt.setInt(1, order.getUserId());
            pstmt.setInt(2, order.getRestaurantId());
            pstmt.setDouble(3, order.getTotalAmount());
            pstmt.setString(4, order.getStatus());
            pstmt.setString(5, order.getPaymentMethod());
            pstmt.setInt(6, order.getOrderId());

            int rows = pstmt.executeUpdate();
            System.out.println(rows + " row(s) updated.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteOrder(int orderId) {
        try (Connection con = dbConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(DELETE_QUERY)) {

            pstmt.setInt(1, orderId);
            int rows = pstmt.executeUpdate();
            System.out.println(rows + " row(s) deleted.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<OrderTable> getAllOrders() {
        List<OrderTable> list = new ArrayList<>();
        try (Connection con = dbConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet res = stmt.executeQuery(GETALL_QUERY)) {

            while (res.next()) {
                list.add(getOrderDetails(res));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<OrderTable> getOrdersByUserId(int userId) {
        List<OrderTable> list = new ArrayList<>();
        try (Connection con = dbConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(GET_BY_USER_QUERY)) {

            pstmt.setInt(1, userId);
            try (ResultSet res = pstmt.executeQuery()) {
                while (res.next()) {
                    list.add(getOrderDetails(res));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<OrderTable> getOrdersByRestaurantId(int restaurantId) {
        List<OrderTable> list = new ArrayList<>();
        try (Connection con = dbConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(GET_BY_RESTAURANT_QUERY)) {

            pstmt.setInt(1, restaurantId);
            try (ResultSet res = pstmt.executeQuery()) {
                while (res.next()) {
                    list.add(getOrderDetails(res));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public OrderTable getOrderDetails(ResultSet res) throws SQLException {
        int orderId = res.getInt("OrderID");
        int userId = res.getInt("UserID");
        int restaurantId = res.getInt("RestaurantID");
        Timestamp orderDate = res.getTimestamp("OrderDate");
        double totalAmount = res.getDouble("TotalAmount");
        String status = res.getString("Status");
        String paymentMethod = res.getString("PaymentMethod");

        return new OrderTable(orderId, userId, restaurantId, orderDate, totalAmount, status, paymentMethod);
    }
}