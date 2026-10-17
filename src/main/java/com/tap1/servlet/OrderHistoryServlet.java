package com.tap1.servlet;

import java.io.IOException;
import java.util.List;

import com.tap1.daoImplementation.OrderTableDaoImplementation;
import com.tap1.model.OrderTable;
import com.tap1.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/OrderHistoryServlet")
public class OrderHistoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        // 1. Validate logged-in user
        if (session == null || session.getAttribute("loggedInUser") == null) {
            resp.sendRedirect("login.html");
            return;
        }

        User loggedInUser = (User) session.getAttribute("loggedInUser");

        // 2. Fetch user's orders from the database
        OrderTableDaoImplementation orderDao = new OrderTableDaoImplementation();
        List<OrderTable> ordersList = orderDao.getOrdersByUserId(loggedInUser.getUserId());

        // 3. Attach order list to request and forward to JSP
        req.setAttribute("ordersList", ordersList);
        req.getRequestDispatcher("orderHistory.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}