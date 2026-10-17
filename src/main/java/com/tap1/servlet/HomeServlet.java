package com.tap1.servlet;

import java.io.IOException;
import java.util.List;

import com.tap1.daoImplementation.RestaurantDaoImplementation;
import com.tap1.model.Restaurant;
import com.tap1.model.User;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        
        // 1. Get or create the HTTP Session
        HttpSession session = request.getSession();

        // 2. Fetch restaurant data dynamically from DB using DAO
        RestaurantDaoImplementation restaurantDao = new RestaurantDaoImplementation();
        List<Restaurant> restaurantList = restaurantDao.getAllRestaurant();

        // 3. Store restaurant list into HTTP Session
        session.setAttribute("restaurantList", restaurantList);

        // 4. (Optional) Retrieve logged-in user details from session if available
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser != null) {
            // Access user email, username, or role stored during login
            String userEmail = loggedInUser.getEmail();
            String userName = loggedInUser.getUserName();
            
            // Store specific user fields or metadata into session if needed
            session.setAttribute("userEmail", userEmail);
            session.setAttribute("userName", userName);
        }

        // 5. Also attach to request scope for current render cycle in index.jsp
        request.setAttribute("restaurantList", restaurantList);

        // 6. Forward to index.jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}