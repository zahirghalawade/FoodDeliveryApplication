<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.tap1.model.Menu" %>
<%@ page import="com.tap1.model.User" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Menu - foodiii</title>
    <link rel="stylesheet" href="style.css">
    <!-- FontAwesome for Icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    
    <style>
        /* Card Hover, Layout & Button Animation Styles */
        .menu-container {
            max-width: 1200px;
            margin: 40px auto;
            padding: 0 20px;
        }

        .menu-container h2 {
            font-size: 1.8rem;
            color: #282c3f;
            margin-bottom: 20px;
            font-weight: 700;
        }

        .menu-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
            gap: 25px;
        }

        .menu-card {
            background: #ffffff;
            border-radius: 16px;
            overflow: hidden;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.08);
            transition: transform 0.35s cubic-bezier(0.25, 0.8, 0.25, 1), box-shadow 0.35s ease;
            display: flex;
            flex-direction: column;
            position: relative;
        }

        .menu-card:hover {
            transform: translateY(-8px) scale(1.02);
            box-shadow: 0 12px 30px rgba(0, 0, 0, 0.18);
        }

        .menu-img-container {
            width: 100%;
            height: 190px;
            overflow: hidden;
            background-color: #f4f4f4;
            position: relative;
        }

        .menu-card img {
            width: 100%;
            height: 100%;
            object-fit: cover;
            transition: transform 0.5s ease;
        }

        .menu-card:hover img {
            transform: scale(1.08);
        }

        .status-badge {
            position: absolute;
            top: 12px;
            right: 12px;
            background: rgba(255, 255, 255, 0.92);
            padding: 4px 10px;
            border-radius: 20px;
            font-size: 0.75rem;
            font-weight: 700;
            box-shadow: 0 2px 6px rgba(0,0,0,0.12);
        }

        .menu-info {
            padding: 18px;
            display: flex;
            flex-direction: column;
            flex-grow: 1;
        }

        .menu-item-name {
            font-size: 1.2rem;
            font-weight: 700;
            color: #282c3f;
            margin-bottom: 6px;
        }

        .menu-category {
            font-size: 0.82rem;
            color: #686b78;
            margin-bottom: 8px;
        }

        .menu-description {
            font-size: 0.85rem;
            color: #93959f;
            margin-bottom: 18px;
            line-height: 1.4;
            flex-grow: 1;
        }

        .menu-bottom {
            display: flex;
            align-items: center;
            justify-content: space-between;
            margin-top: auto;
            padding-top: 10px;
            border-top: 1px solid #f0f0f0;
        }

        .menu-price {
            font-size: 1.15rem;
            font-weight: 800;
            color: #282c3f;
        }

        /* Upgraded Hover Animation for Add to Cart Button */
        .add-cart-btn {
            background: linear-gradient(135deg, #ff5252, #e63946);
            color: #ffffff;
            border: none;
            padding: 10px 18px;
            border-radius: 10px;
            font-weight: 700;
            font-size: 0.88rem;
            cursor: pointer;
            transition: all 0.3s ease;
            box-shadow: 0 4px 10px rgba(230, 57, 70, 0.3);
            display: inline-flex;
            align-items: center;
            gap: 6px;
        }

        .add-cart-btn:hover {
            background: linear-gradient(135deg, #e63946, #d62828);
            transform: translateY(-3px);
            box-shadow: 0 6px 16px rgba(230, 57, 70, 0.5);
            color: #ffffff;
        }

        .add-cart-btn:active {
            transform: translateY(0);
        }

        .out-of-stock-btn {
            background: #e0e0e0;
            color: #888888;
            border: none;
            padding: 10px 18px;
            border-radius: 10px;
            font-weight: 700;
            font-size: 0.88rem;
            cursor: not-allowed;
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
            <% 
                User loggedInUser = (User) session.getAttribute("loggedInUser");
                if (loggedInUser != null) { 
            %>
                <a href="OrderHistoryServlet"><i class="fa-solid fa-clock-rotate-left"></i> My Orders</a>
                <a href="LogoutServlet" class="btn-login"><i class="fa-solid fa-right-from-bracket"></i> Sign Out</a>
            <% } else { %>
                <a href="login.html" class="btn-login"><i class="fa-solid fa-user"></i> Sign In</a>
                <a href="register.html" class="btn-register">Sign Up</a>
            <% } %>
        </nav>
    </header>

    <!-- Menu Container -->
    <div class="menu-container">
        <h2>Select Your Dishes</h2>
        
        <div class="menu-grid">
            <%
                List<Menu> menuList = (List<Menu>) request.getAttribute("menuList");

                if (menuList != null && !menuList.isEmpty()) {
                    for (Menu item : menuList) {
                        String imgPath = item.getImagePath();
                        String formattedPath = "";

                        if (imgPath != null && !imgPath.trim().isEmpty()) {
                            imgPath = imgPath.trim();

                            if (imgPath.startsWith("http://") || imgPath.startsWith("https://")) {
                                formattedPath = imgPath;
                            } else {
                                imgPath = imgPath.replace("FoodDeliveryApplication/", "");
                                imgPath = imgPath.replace("src/main/webapp/", "");

                                if (imgPath.startsWith("/")) {
                                    imgPath = imgPath.substring(1);
                                }

                                formattedPath = request.getContextPath() + "/" + imgPath;
                            }
                        } else {
                            formattedPath = "https://via.placeholder.com/280x180?text=No+Image";
                        }
            %>
                        <div class="menu-card">
                            <div class="menu-img-container">
                                <img src="<%= formattedPath %>" alt="<%= item.getItemName() %>">
                                <div class="status-badge">
                                    <% if (item.getIsAvailable() == 1) { %>
                                        <span style="color: #48c479;"><i class="fa-solid fa-circle" style="font-size: 8px;"></i> Available</span>
                                    <% } else { %>
                                        <span style="color: #ff5252;"><i class="fa-solid fa-circle" style="font-size: 8px;"></i> Out of Stock</span>
                                    <% } %>
                                </div>
                            </div>
                            
                            <div class="menu-info">
                                <div class="menu-item-name"><%= item.getItemName() %></div>
                                <div class="menu-category">Category: <strong><%= item.getCategory() %></strong></div>
                                <div class="menu-description"><%= item.getDescription() %></div>
                                
                                <div class="menu-bottom">
                                    <span class="menu-price">&#8377;<%= item.getPrice() %></span>
                                    
                                    <% if (item.getIsAvailable() == 1) { %>
                                        <form action="CartServlet" method="POST" style="margin: 0;">
                                            <input type="hidden" name="action" value="add">
                                            <input type="hidden" name="menuId" value="<%= item.getMenuId() %>">
                                            <input type="hidden" name="restaurantId" value="<%= item.getRestaurantId() %>">
                                            <input type="hidden" name="quantity" value="1">
                                            <button type="submit" class="add-cart-btn">
                                                <i class="fa-solid fa-plus"></i> ADD
                                            </button>
                                        </form>
                                    <% } else { %>
                                        <button type="button" class="out-of-stock-btn" disabled>Unavailable</button>
                                    <% } %>
                                </div>
                            </div>
                        </div>
            <%
                    }
                } else {
            %>
                    <div style="grid-column: 1 / -1; text-align: center; padding: 3rem;">
                        <h3>No menu items available for this restaurant.</h3>
                        <a href="home" style="color: #ff5252; text-decoration: underline;">Back to Restaurants</a>
                    </div>
            <%
                }
            %>
        </div>
    </div>

    <!-- Footer -->
    <footer>
        <p>&copy; 2026 foodiii. All rights reserved.</p>
    </footer>

</body>
</html>