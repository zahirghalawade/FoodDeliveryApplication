<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.tap1.model.User" %>
<%@ page import="com.tap1.model.Cart" %>
<%@ page import="com.tap1.model.CartItem" %>
<%
    User user = (User) request.getAttribute("user");
    Double totalAmountObj = (Double) request.getAttribute("totalAmount");
    double totalAmount = (totalAmountObj != null) ? totalAmountObj : 0.0;
    Cart cart = (Cart) session.getAttribute("cart");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Checkout - foodiii</title>
    <link rel="stylesheet" href="style.css">
    <!-- FontAwesome Icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        .checkout-container {
            max-width: 1000px;
            margin: 40px auto;
            padding: 0 20px;
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 30px;
        }
        @media (max-width: 768px) {
            .checkout-container {
                grid-template-columns: 1fr;
            }
        }
        .checkout-card {
            background: #ffffff;
            padding: 25px;
            border-radius: 8px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.08);
        }
        .checkout-card h2 {
            margin-bottom: 20px;
            color: #333;
            font-size: 1.4rem;
            border-bottom: 2px solid #f4f4f4;
            padding-bottom: 10px;
        }
        .summary-item {
            display: flex;
            justify-content: space-between;
            margin-bottom: 12px;
            font-size: 0.95rem;
            color: #555;
        }
        .total-row {
            font-weight: bold;
            font-size: 1.2rem;
            color: #222;
            border-top: 2px solid #eee;
            padding-top: 15px;
            margin-top: 15px;
        }
        .form-group {
            margin-bottom: 18px;
        }
        .form-group label {
            display: block;
            margin-bottom: 6px;
            font-weight: 600;
            font-size: 0.9rem;
            color: #444;
        }
        .form-group input, .form-group select, .form-group textarea {
            width: 100%;
            padding: 10px 12px;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 0.95rem;
            outline: none;
            box-sizing: border-box;
        }
        .form-group input[readonly], .form-group textarea[readonly] {
            background-color: #f8f9fa;
        }
        .btn-submit-order {
            width: 100%;
            padding: 14px;
            background: #ff5722;
            color: #ffffff;
            border: none;
            border-radius: 5px;
            font-size: 1.1rem;
            font-weight: bold;
            cursor: pointer;
            margin-top: 10px;
            transition: background 0.3s;
        }
        .btn-submit-order:hover {
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

    <!-- Checkout Main Section -->
    <div class="checkout-container">
        
        <!-- Column 1: Order Summary -->
        <div class="checkout-card">
            <h2>Order Summary</h2>
            <% if (cart != null && !cart.getItems().isEmpty()) { 
                for (CartItem item : cart.getItems().values()) { %>
                    <div class="summary-item">
                        <span><%= item.getName() %> (x<%= item.getQuantity() %>)</span>
                        <span>₹<%= String.format("%.2f", item.getTotalPrice()) %></span>
                    </div>
            <%  } 
               } else { %>
                    <p style="color: #888;">No items in cart.</p>
            <% } %>
            
            <div class="summary-item total-row">
                <span>Total Amount</span>
                <span style="color: #ff5722;">₹<%= String.format("%.2f", totalAmount) %></span>
            </div>
        </div>

        <!-- Column 2: User Address & Payment Selection Form -->
        <div class="checkout-card">
            <h2>Delivery Details & Payment</h2>
            <form action="OrderServlet" method="POST">
                
                <div class="form-group">
                    <label for="userName">Full Name</label>
                    <input type="text" id="userName" name="userName" value="<%= (user != null && user.getUserName() != null) ? user.getUserName() : "" %>" required readonly>
                </div>

                <div class="form-group">
                    <label for="email">Email Address</label>
                    <input type="email" id="email" name="email" value="<%= (user != null && user.getEmail() != null) ? user.getEmail() : "" %>" required readonly>
                </div>

                <div class="form-group">
                    <label for="address">Delivery Address</label>
                    <textarea id="address" name="address" rows="3" placeholder="Enter delivery address" required><%= (user != null && user.getAddress() != null) ? user.getAddress() : "" %></textarea>
                </div>

                <div class="form-group">
                    <label for="paymentMethod">Payment Method</label>
                    <select id="paymentMethod" name="paymentMethod" required>
                        <option value="Cash on Delivery" selected>Cash on Delivery</option>
                        <option value="UPI / Online Payment">UPI / PhonePe / GPay</option>
                        <option value="Credit Card">Credit Card</option>
                        <option value="Debit Card">Debit Card</option>
                        <option value="Net Banking">Net Banking</option>
                    </select>
                </div>

                <button type="submit" class="btn-submit-order">
                    <i class="fa-solid fa-lock"></i> Place Order (₹<%= String.format("%.2f", totalAmount) %>)
                </button>
            </form>
        </div>

    </div>

    <!-- Footer -->
    <footer>
        <p>&copy; 2026 foodiii. All rights reserved.</p>
    </footer>

</body>
</html>