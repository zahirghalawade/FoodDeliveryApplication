<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.tap1.model.Restaurant" %>
<%@ page import="com.tap1.model.User" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>foodiii - Order Food Online</title>
    <link rel="stylesheet" href="style.css">
    <!-- FontAwesome Icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    
    <style>
        /* Card Hover & Animation Styles */
        .restaurant-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(320px, 1fr));
            gap: 25px;
            padding: 20px 0;
        }

        .restaurant-card {
            background: #ffffff;
            border-radius: 16px;
            overflow: hidden;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.08);
            transition: transform 0.35s cubic-bezier(0.25, 0.8, 0.25, 1), box-shadow 0.35s ease;
            position: relative;
            display: flex;
            flex-direction: column;
        }

        .restaurant-card:hover {
            transform: translateY(-8px) scale(1.02);
            box-shadow: 0 12px 30px rgba(0, 0, 0, 0.18);
        }

        .img-container {
            width: 100%;
            height: 200px;
            overflow: hidden;
            position: relative;
            background-color: #f4f4f4;
        }

        .restaurant-card img {
            width: 100%;
            height: 100%;
            object-fit: cover;
            transition: transform 0.5s ease;
        }

        .restaurant-card:hover img {
            transform: scale(1.08);
        }

        .restaurant-info {
            padding: 18px;
            display: flex;
            flex-direction: column;
            flex-grow: 1;
        }

        .restaurant-info h2 {
            font-size: 1.25rem;
            margin: 0 0 8px 0;
            color: #282c3f;
            font-weight: 700;
        }

        .details {
            display: flex;
            align-items: center;
            gap: 12px;
            margin-bottom: 10px;
            font-size: 0.9rem;
        }

        .rating {
            background-color: #48c479;
            color: white;
            padding: 3px 8px;
            border-radius: 6px;
            font-weight: 700;
            display: flex;
            align-items: center;
            gap: 4px;
        }

        .time {
            color: #686b78;
            font-weight: 600;
        }

        .cuisine {
            color: #686b78;
            font-size: 0.9rem;
            margin-bottom: 4px;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
        }

        .address {
            color: #93959f;
            font-size: 0.82rem;
            margin-bottom: 18px;
        }

        /* Upgraded View Menu Button with Hover Effect */
        .order-btn {
            display: inline-block;
            text-align: center;
            background: linear-gradient(135deg, #ff5252, #e63946);
            color: #ffffff;
            padding: 10px 0;
            border-radius: 10px;
            text-decoration: none;
            font-weight: 700;
            letter-spacing: 0.5px;
            transition: all 0.3s ease;
            box-shadow: 0 4px 10px rgba(230, 57, 70, 0.3);
            margin-top: auto;
        }

        .order-btn:hover {
            background: linear-gradient(135deg, #e63946, #d62828);
            transform: translateY(-2px);
            box-shadow: 0 6px 15px rgba(230, 57, 70, 0.5);
            color: #ffffff;
        }

        .order-btn:active {
            transform: translateY(0);
        }
    </style>
</head>
<body>

    <!-- Header Navigation -->
    <header class="navbar">
        <div class="logo">
            <a href="home"><i class="fa-solid fa-utensils"></i> foodiii</a>
        </div>
        
        <div class="location-container">
            <i class="fa-solid fa-location-dot"></i>
            <span><strong>Other</strong> Bangalore, Karnataka, India</span>
        </div>

        <nav class="nav-links">
            <a href="home" class="active"><i class="fa-solid fa-house"></i> Home</a>
            <a href="#search"><i class="fa-solid fa-magnifying-glass"></i> Search</a>
            <a href="#offers"><i class="fa-solid fa-percent"></i> Offers</a>
            <a href="cart.jsp" class="cart-btn"><i class="fa-solid fa-cart-shopping"></i> Cart</a>
            
            <% 
                User loggedInUser = (User) session.getAttribute("loggedInUser");
                if (loggedInUser != null) { 
            %>
                <span class="user-name" style="color: #333; font-weight: 600; margin: 0 8px;">
                    <i class="fa-solid fa-user-check"></i> Hi, <%= loggedInUser.getUserName() %>
                </span>
                <a href="OrderHistoryServlet"><i class="fa-solid fa-clock-rotate-left"></i> My Orders</a>
                <a href="LogoutServlet" class="btn-login"><i class="fa-solid fa-right-from-bracket"></i> Sign Out</a>
            <% } else { %>
                <a href="login.html" class="btn-login"><i class="fa-solid fa-user"></i> Sign In</a>
                <a href="register.html" class="btn-register">Sign Up</a>
            <% } %>
        </nav>
    </header>

    <!-- Hero Section -->
    <section class="hero-section">
        <div class="hero-content">
            <h1>Order Food From Your Favorite Restaurants</h1>
            <p>Freshly cooked meals delivered right to your doorstep in minutes.</p>
            <div class="search-box">
                <i class="fa-solid fa-location-dot"></i>
                <input type="text" placeholder="Search for restaurant or cuisine...">
                <button type="submit">Search</button>
            </div>
        </div>
    </section>

    <!-- Dynamic Restaurant List Section -->
    <section class="restaurants-section" id="restaurants">
        <h2>Top Restaurant Chains</h2>
        
        <div class="restaurant-grid">
            <%
                List<Restaurant> allRestaurants = (List<Restaurant>) request.getAttribute("allRestaurants");
                if (allRestaurants == null) {
                    allRestaurants = (List<Restaurant>) request.getAttribute("restaurantList");
                }

                if (allRestaurants != null && !allRestaurants.isEmpty()) {
                    for (Restaurant restaurant : allRestaurants) {
                        String imgPath = restaurant.getImagePath();
                        String formattedPath = "";

                        if (imgPath != null && !imgPath.trim().isEmpty()) {
                            imgPath = imgPath.trim();

                            if (imgPath.startsWith("http://") || imgPath.startsWith("https://")) {
                                formattedPath = imgPath;
                            } else {
                                // Strip project prefix & local IDE source folder paths
                                imgPath = imgPath.replace("FoodDeliveryApplication/", "");
                                imgPath = imgPath.replace("src/main/webapp/", "");

                                if (imgPath.startsWith("/")) {
                                    imgPath = imgPath.substring(1);
                                }

                                formattedPath = request.getContextPath() + "/" + imgPath;
                            }
                        } else {
                            formattedPath = "https://via.placeholder.com/300x200?text=No+Image";
                        }
            %>
                        <div class="restaurant-card">
                            <div class="img-container">
                                <img src="<%= formattedPath %>" alt="<%= restaurant.getName() %>">
                            </div>
                            
                            <div class="restaurant-info">
                                <h2><%= restaurant.getName() %></h2>
                                
                                <div class="details">
                                    <span class="rating"><i class="fa-solid fa-star"></i> <%= restaurant.getRating() %></span>
                                    <span class="time"><%= restaurant.getDeliveryTime() %> mins</span>
                                </div>
                                
                                <p class="cuisine"><%= restaurant.getCuisineType() %></p>
                                <p class="address"><%= restaurant.getAddress() %></p>
                                
                                <a href="MenuServlet?restaurantId=<%= restaurant.getRestaurantId() %>" class="order-btn">
                                    View Menu <i class="fa-solid fa-arrow-right" style="margin-left: 5px;"></i>
                                </a>
                            </div>
                        </div>
            <%
                    }
                } else {
            %>
                    <p style="grid-column: 1 / -1; text-align: center; padding: 2rem;">No restaurants available directly from the database.</p>
            <%
                }
            %>
        </div>
    </section>

    <!-- Footer -->
    <footer>
        <p>&copy; 2026 foodiii. All rights reserved.</p>
    </footer>

</body>
</html>