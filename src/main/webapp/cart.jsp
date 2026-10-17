<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.tap1.model.Cart" %>
<%@ page import="com.tap1.model.CartItem" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Your Cart - foodiii</title>
    <link rel="stylesheet" href="style.css">
    <!-- FontAwesome for Icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
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
            <a href="cart.jsp" class="cart-btn active"><i class="fa-solid fa-cart-shopping"></i> Cart</a>
            
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

    <!-- Cart Content Container -->
    <div class="cart-container">
        <h2>Your Shopping Cart</h2>

        <%
            // Retrieve Cart object from HTTP Session
            Cart cart = (Cart) session.getAttribute("cart");
            
            if (cart != null && !cart.getItems().isEmpty()) {
        %>
            <table class="cart-table">
                <thead>
                    <tr>
                        <th>Item Name</th>
                        <th>Price</th>
                        <th>Quantity</th>
                        <th>Subtotal</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        for (CartItem item : cart.getItems().values()) {
                    %>
                    <tr>
                        <td><strong><%= item.getName() %></strong></td>
                        <td>₹<%= String.format("%.2f", item.getPrice()) %></td>
                        <td>
                            <!-- Form to Update Quantity -->
                            <form action="CartServlet" method="POST" style="display: flex; align-items: center; gap: 5px;">
                                <input type="hidden" name="action" value="update">
                                <input type="hidden" name="itemId" value="<%= item.getItemId() %>">
                                <input type="number" name="quantity" value="<%= item.getQuantity() %>" min="1" class="qty-input">
                                <button type="submit" style="padding: 4px 8px; background: #2d3436; color: #fff; border: none; border-radius: 4px; cursor: pointer;">
                                    Update
                                </button>
                            </form>
                        </td>
                        <td>₹<%= String.format("%.2f", item.getTotalPrice()) %></td>
                        <td>
                            <!-- Form to Remove Item -->
                            <form action="CartServlet" method="POST" style="display:inline;">
                                <input type="hidden" name="action" value="remove">
                                <input type="hidden" name="itemId" value="<%= item.getItemId() %>">
                                <button type="submit" class="btn-remove">
                                    <i class="fa-solid fa-trash"></i>
                                </button>
                            </form>
                        </td>
                    </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>

            <div class="cart-summary">
                <h3>Total Amount: <span style="color: #ff5722;">₹<%= String.format("%.2f", cart.getTotalAmount()) %></span></h3>
                
                <div style="margin-top: 20px; display: flex; justify-content: space-between;">
                    <a href="home" class="btn-login" style="text-decoration: none; padding: 10px 20px;">
                        <i class="fa-solid fa-arrow-left"></i> Add More Items
                    </a>
                    <a href="CheckoutServlet" class="submit-btn checkout-btn">
                        Proceed to Checkout <i class="fa-solid fa-arrow-right"></i>
                    </a>
                </div>
            </div>

        <%
            } else {
        %>
            <!-- Empty Cart State -->
            <div style="text-align: center; padding: 3rem 1rem;">
                <i class="fa-solid fa-basket-shopping" style="font-size: 4rem; color: #ccc; margin-bottom: 1rem;"></i>
                <h3>Your cart is empty!</h3>
                <p style="color: #666; margin-bottom: 1.5rem;">Good food is always waiting for you. Explore restaurants to add dishes.</p>
                <a href="home" class="submit-btn checkout-btn">Explore Restaurants</a>
            </div>
        <%
            }
        %>
    </div>

    <!-- Footer -->
    <footer>
        <p>&copy; 2026 foodiii. All rights reserved.</p>
    </footer>

</body>
</html>