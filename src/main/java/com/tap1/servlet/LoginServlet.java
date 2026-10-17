package com.tap1.servlet;

import java.io.IOException;

import org.mindrot.jbcrypt.BCrypt;

import com.tap1.daoImplementation.userDaoImplementation;
import com.tap1.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        // 1. Retrieve email and password from login form
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        // 2. Fetch user from database using email
        userDaoImplementation userDao = new userDaoImplementation();
        User user = userDao.getUserByEmail(email);

        // 3. Null safety check
        if (user != null) {
            String dbPassword = user.getPassword();

            // 4. Validate password using BCrypt with plain-text fallback
            boolean isPasswordMatch = false;
            try {
                isPasswordMatch = BCrypt.checkpw(password, dbPassword);
            } catch (IllegalArgumentException e) {
                // Fallback for unhashed legacy passwords
                isPasswordMatch = dbPassword.equals(password);
            }

            if (isPasswordMatch) {
                // 5. Store logged-in user in HTTP Session
                HttpSession session = req.getSession();
                session.setAttribute("loggedInUser", user);

                // Redirect to HomeServlet to load DB items and render index.jsp
                resp.sendRedirect("home");
            } else {
                // Redirect back to login page with error query parameter
                resp.sendRedirect("login.html?error=invalidPassword");
            }
        } else {
            // Redirect back to login page if user not found
            resp.sendRedirect("login.html?error=userNotFound");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}