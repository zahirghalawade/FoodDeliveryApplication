<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.tap1.model.OrderTable" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Orders - foodiii</title>
    <link rel="stylesheet" href="style.css">
    <!-- FontAwesome Icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        .history-container {
            max-width: 950px;
            margin: 40px auto;
            padding: 25px;
            background: #ffffff;
            border-radius: 10px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.08);
        }
        .history-header {
            display: flex;
            align-items: center;
            justify-content: space-between;
            border-bottom: 2px solid #f4f4f4;
            padding-bottom: 15px;
            margin-bottom: 25px;
        }
        .history-header h2 {
            margin: 0;
            color: #2c3e50;
            font-size: 1.5rem;
        }
        .order-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 10px;
        }
        .order-table th {
            background-color: #f8f9fa;
            color: #444;
            font-weight: 600;
            padding: 12px 15px;
            text-align: left;
            border-bottom: 2px solid #e9ecef;
        }
        .order-table td {
            padding: 14px 15px;
            border-bottom: 1px solid #f1f1f1;
            color: #555;
            font-size: 0.95rem;
        }
        .order-id {
            font-weight: bold;
            color: #ff5722;
        }
        .order-amount {
            font-weight: bold;
            color: #2c3e50;
        }
        .status-badge {
            display: inline-block;
            padding: 5px 12px;
            border-radius: 20px;
            font-size: 0.8rem;
            font-weight: 600;
            text-transform: capitalize;
        }
        .status-delivered {
            background-color: #e6f4ea;
            color: #137333;
        }
        .status-pending {
            background-color: #fef7e0;
            color: #b06000;
        }
        .no-orders {
            text-align: center;
            padding: 40px 20px;
            color: #777;
        }
        .btn-browse {
            display: inline-block;
            margin-top: 15px;
            padding: 10px 24px;
            background: #ff5722;
            color: #fff;
            border-radius: 5px;
            text-decoration: none;
            font-weight: 600;
        }
        .btn-browse:hover {
            background: #e64a19;
        }
    </style>
</head>
<body>

    <!-- Header / Navbar Section -->
    <header class="navbar">
        <div class="logo">
            <a href="home"><i class="fa-solid fa-utensils"></i> foodiii</a>
        </div>
        
        <div class="location-container">
            <i class="fa-solid fa-location-dot"></i>
            <span><strong>Other</strong> Bangalore, Karnataka, India</span>
        </div>

        <nav class="nav-links">
            <a href="home"><i class="fa-solid fa-house"></i> Home</a>
            <a href="cart.jsp" class="cart-btn"><i class="fa-solid fa-cart-shopping"></i> Cart</a>
            
            <%-- Dynamic Authentication Navigation --%>
            <% if (session.getAttribute("loggedInUser") != null) { %>
                <a href="OrderHistoryServlet"><i class="fa-solid fa-clock-rotate-left"></i> My Orders</a>
                <a href="LogoutServlet" class="btn-login"><i class="fa-solid fa-right-from-bracket"></i> Sign Out</a>
            <% } else { %>
                <a href="login.html" class="btn-login"><i class="fa-solid fa-user"></i> Sign In</a>
                <a href="register.html" class="btn-register">Sign Up</a>
            <% } %>
        </nav>
    </header>

    <!-- Order History Main Section -->
    <div class="history-container">
        <div class="history-header">
            <h2><i class="fa-solid fa-clock-rotate-left"></i> My Order History</h2>
        </div>

        <%
            List<OrderTable> ordersList = (List<OrderTable>) request.getAttribute("ordersList");
            if (ordersList != null && !ordersList.isEmpty()) {
        %>
        <table class="order-table">
            <thead>
                <tr>
                    <th>Order ID</th>
                    <th>Restaurant</th>
                    <th>Date & Time</th>
                    <th>Total Amount</th>
                    <th>Payment Method</th>
                    <th>Status</th>
                </tr>
            </thead>
            <tbody>
                <% for (OrderTable order : ordersList) { 
                    boolean isDelivered = "Delivered".equalsIgnoreCase(order.getStatus());
                %>
                <tr>
                    <td class="order-id">#<%= order.getOrderId() %></td>
                    <td>Restaurant #<%= order.getRestaurantId() %></td>
                    <td><%= order.getOrderDate() != null ? order.getOrderDate() : "N/A" %></td>
                    <td class="order-amount">₹<%= String.format("%.2f", order.getTotalAmount()) %></td>
                    <td><%= order.getPaymentMethod() %></td>
                    <td>
                        <span class="status-badge <%= isDelivered ? "status-delivered" : "status-pending" %>">
                            <%= order.getStatus() %>
                        </span>
                    </td>
                </tr>
                <% } %>
            </tbody>
        </table>
        <% } else { %>
        <div class="no-orders">
            <p>You haven't placed any orders yet.</p>
            <a href="home" class="btn-browse"><i class="fa-solid fa-utensils"></i> Explore Restaurants</a>
        </div>
        <% } %>
    </div>

    <!-- Footer -->
    <footer>
        <p>&copy; 2026 foodiii. All rights reserved.</p>
    </footer>

</body>
</html>