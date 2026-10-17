package com.tap1.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.tap1.model.Cart;
import com.tap1.model.CartItem;
import com.tap1.model.User;
import com.tap1.utility.dbConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/OrderServlet")
public class OrderServlet extends HttpServlet {

    private static final String INSERT_ORDER = 
        "INSERT INTO `OrderTable` (`UserID`, `RestaurantID`, `TotalAmount`, `Status`, `PaymentMethod`) VALUES (?, ?, ?, ?, ?)";

    private static final String INSERT_ORDER_ITEM = 
        "INSERT INTO `OrderItem` (`OrderID`, `MenuID`, `Quantity`, `ItemTotal`) VALUES (?, ?, ?, ?)";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        // 1. Session and Cart verification
        if (session == null || session.getAttribute("loggedInUser") == null) {
            resp.sendRedirect("login.html");
            return;
        }

        User loggedInUser = (User) session.getAttribute("loggedInUser");
        Cart cart = (Cart) session.getAttribute("cart");

        if (cart == null || cart.getItems().isEmpty()) {
            resp.sendRedirect("cart.jsp");
            return;
        }

        // 2. Extract order preferences
        String paymentMethod = req.getParameter("paymentMethod");
        if (paymentMethod == null || paymentMethod.trim().isEmpty()) {
            paymentMethod = "Cash on Delivery";
        }

        int restaurantId = cart.getItems().values().iterator().next().getRestaurantId();
        double totalAmount = cart.getTotalAmount();
        String status = "Pending";

        Connection connection = null;
        PreparedStatement orderPstmt = null;
        PreparedStatement itemPstmt = null;
        ResultSet generatedKeys = null;
        int generatedOrderId = -1;

        try {
            // 3. Obtain database connection and disable auto-commit to start transaction
            connection = dbConnection.getConnection();
            connection.setAutoCommit(false);

            // 4. Insert into OrderTable and request auto-generated primary key
            orderPstmt = connection.prepareStatement(INSERT_ORDER, Statement.RETURN_GENERATED_KEYS);
            orderPstmt.setInt(1, loggedInUser.getUserId());
            orderPstmt.setInt(2, restaurantId);
            orderPstmt.setDouble(3, totalAmount);
            orderPstmt.setString(4, status);
            orderPstmt.setString(5, paymentMethod);

            int affectedRows = orderPstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating order failed, no rows affected.");
            }

            // Retrieve generated Order ID
            generatedKeys = orderPstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                generatedOrderId = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Creating order failed, no ID obtained.");
            }

            // 5. Insert corresponding line items into OrderItem batch
            itemPstmt = connection.prepareStatement(INSERT_ORDER_ITEM);

            for (CartItem cartItem : cart.getItems().values()) {
                itemPstmt.setInt(1, generatedOrderId);
                itemPstmt.setInt(2, cartItem.getItemId());
                itemPstmt.setInt(3, cartItem.getQuantity());
                itemPstmt.setDouble(4, cartItem.getTotalPrice());
                itemPstmt.addBatch();
            }

            // Execute batch insertion for all order items
            itemPstmt.executeBatch();

            // 6. Commit transaction if all database operations succeeded
            connection.commit();

            // 7. Clear cart session state
            cart.clear();
            session.removeAttribute("cart");

            // 8. Forward user to order confirmation page
            req.setAttribute("orderId", generatedOrderId);
            req.getRequestDispatcher("orderConfirmation.jsp").forward(req, resp);

        } catch (Exception e) {
            // Roll back transaction on error
            if (connection != null) {
                try {
                    connection.rollback();
                    System.err.println("Transaction rolled back due to error: " + e.getMessage());
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            e.printStackTrace();
            resp.sendRedirect("cart.jsp?error=transaction_failed");

        } finally {
            // Clean up JDBC resources
            try {
                if (generatedKeys != null) generatedKeys.close();
                if (orderPstmt != null) orderPstmt.close();
                if (itemPstmt != null) itemPstmt.close();
                if (connection != null) {
                    connection.setAutoCommit(true); // Reset connection auto-commit state
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("cart.jsp");
    }
}