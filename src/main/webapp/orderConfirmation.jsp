<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    Integer orderIdObj = (Integer) request.getAttribute("orderId");
    int orderId = (orderIdObj != null) ? orderIdObj : 0;
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Order Confirmed - foodiii</title>
    <link rel="stylesheet" href="style.css">
    <!-- FontAwesome Icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        .confirmation-container {
            max-width: 600px;
            margin: 60px auto;
            background: #ffffff;
            padding: 40px 30px;
            text-align: center;
            border-radius: 12px;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
        }
        .success-icon {
            font-size: 4.5rem;
            color: #2ed573;
            margin-bottom: 20px;
        }
        .confirmation-container h1 {
            font-size: 1.8rem;
            color: #2c3e50;
            margin-bottom: 10px;
        }
        .confirmation-container p {
            font-size: 1rem;
            color: #666;
            margin-bottom: 20px;
            line-height: 1.5;
        }
        .order-details-card {
            background-color: #f8f9fa;
            border: 1px dashed #e0e0e0;
            border-radius: 8px;
            padding: 20px;
            margin: 20px 0 30px;
        }
        .order-id-label {
            font-size: 0.9rem;
            color: #777;
            text-transform: uppercase;
            letter-spacing: 1px;
        }
        .order-id-value {
            font-size: 1.6rem;
            font-weight: 700;
            color: #ff5722;
            margin-top: 5px;
        }
        .btn-action-group {
            display: flex;
            justify-content: center;
            gap: 15px;
            flex-wrap: wrap;
        }
        .btn-home {
            display: inline-block;
            padding: 12px 28px;
            background: #ff5722;
            color: #ffffff;
            text-decoration: none;
            border-radius: 6px;
            font-weight: 600;
            font-size: 1rem;
            transition: background 0.3s ease;
        }
        .btn-home:hover {
            background: #e64a19;
        }
        .btn-orders {
            display: inline-block;
            padding: 12px 28px;
            background: #2c3e50;
            color: #ffffff;
            text-decoration: none;
            border-radius: 6px;
            font-weight: 600;
            font-size: 1rem;
            transition: background 0.3s ease;
        }
        .btn-orders:hover {
            background: #1a252f;
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

    <!-- Confirmation Main Section -->
    <div class="confirmation-container">
        <i class="fa-solid fa-circle-check success-icon"></i>
        <h1>Thank You for Your Order!</h1>
        <p>Your order has been received and sent to the restaurant. It will arrive shortly!</p>

        <% if (orderId > 0) { %>
            <div class="order-details-card">
                <div class="order-id-label">Order Reference Number</div>
                <div class="order-id-value">#<%= orderId %></div>
            </div>
        <% } %>

        <div class="btn-action-group">
            <a href="home" class="btn-home"><i class="fa-solid fa-house"></i> Back to Home</a>
            <a href="OrderHistoryServlet" class="btn-orders"><i class="fa-solid fa-clock-rotate-left"></i> View Order History</a>
        </div>
    </div>

    <!-- Footer -->
    <footer>
        <p>&copy; 2026 foodiii. All rights reserved.</p>
    </footer>

</body>
</html>