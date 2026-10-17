package com.tap1.servlet;

import java.io.IOException;

import com.tap1.daoImplementation.MenuDaoImplementation;
import com.tap1.model.Cart;
import com.tap1.model.CartItem;
import com.tap1.model.Menu;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/CartServlet")
public class CartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();

        // 1. Retrieve existing Cart from session or create a new one
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        // 2. Read action parameter (add, update, delete/remove)
        String action = request.getParameter("action");

        if (action != null) {
            switch (action) {
                case "add":
                    addItemToCart(request, cart);
                    break;
                case "update":
                    updateCartItem(request, cart);
                    break;
                case "delete":
                case "remove":
                    removeCartItem(request, cart);
                    break;
            }
        }

        // 3. Redirect to dynamic cart page
        response.sendRedirect("cart.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doPost(request, response);
    }

    private void addItemToCart(HttpServletRequest request, Cart cart) {
        String menuIdParam = request.getParameter("menuId");
        if (menuIdParam == null) {
            menuIdParam = request.getParameter("itemId");
        }
        
        int menuId = Integer.parseInt(menuIdParam);
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        // Fetch menu details from database using DAO
        MenuDaoImplementation menuDao = new MenuDaoImplementation();
        Menu menu = menuDao.getMenu(menuId);

        if (menu != null) {
            CartItem item = new CartItem(
                menu.getMenuId(),
                menu.getRestaurantId(),
                menu.getItemName(),
                quantity,
                menu.getPrice()
            );
            cart.addItem(item);
        }
    }

    private void updateCartItem(HttpServletRequest request, Cart cart) {
        String menuIdParam = request.getParameter("menuId");
        if (menuIdParam == null) {
            menuIdParam = request.getParameter("itemId");
        }
        
        int menuId = Integer.parseInt(menuIdParam);
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        cart.updateItem(menuId, quantity);
    }

    private void removeCartItem(HttpServletRequest request, Cart cart) {
        String menuIdParam = request.getParameter("menuId");
        if (menuIdParam == null) {
            menuIdParam = request.getParameter("itemId");
        }
        
        int menuId = Integer.parseInt(menuIdParam);
        cart.removeItem(menuId);
    }
}