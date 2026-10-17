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

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        // 1. Retrieve form parameters matching your register.html input names
        String userName = req.getParameter("userName");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String address = req.getParameter("address");
        String role = req.getParameter("role");

        // Set default role if not provided in the form
        if (role == null || role.trim().isEmpty()) {
            role = "Customer";
        }

        // 2. Encrypt password using BCrypt hashing
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        // 3. Instantiate and populate the User POJO
        User user = new User(userName,email,hashedPassword,address,role);
//        user.setUserName(userName);
//        user.setEmail(email);
//        user.setPassword(hashedPassword); // Store the hashed password
//        user.setAddress(address);
//        user.setRole(role);

        // 4. Save the new user record in MySQL database via DAO
        userDaoImplementation userDao = new userDaoImplementation();
        userDao.addUser(user);

        // 5. Redirect the user to login.html to sign in
        resp.sendRedirect("login.html");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}