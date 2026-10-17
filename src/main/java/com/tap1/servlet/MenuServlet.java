package com.tap1.servlet;

import java.io.IOException;
import java.util.List;

import com.tap1.daoImplementation.MenuDaoImplementation;
import com.tap1.model.Menu;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/MenuServlet")
public class MenuServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String restaurantIdParam = request.getParameter("restaurantId");

        if (restaurantIdParam != null && !restaurantIdParam.isEmpty()) {
            int restaurantId = Integer.parseInt(restaurantIdParam);

            // 1. Fetch menu list for the given restaurantId using DAO
            MenuDaoImplementation menuDao = new MenuDaoImplementation();
            List<Menu> menuList = menuDao.getMenuByRestaurantId(restaurantId);

            // 2. Attach menu list & restaurantId to the request scope
            request.setAttribute("menuList", menuList);
            request.setAttribute("restaurantId", restaurantId);

            // 3. Forward to dynamic menu.jsp view
            RequestDispatcher dispatcher = request.getRequestDispatcher("menu.jsp");
            dispatcher.forward(request, response);
        } else {
            // Redirect back to home if restaurantId is missing
            response.sendRedirect("home");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}