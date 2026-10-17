package com.tap1.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter(urlPatterns = {
    "/checkout.jsp",
    "/CheckoutServlet",
    "/OrderServlet",
    "/OrderHistoryServlet",
    "/orderHistory.jsp",
    "/orderConfirmation.jsp"
})
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization logic if needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        // Prevent browser caching of protected pages (prevents back-button navigation after logout)
        resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
        resp.setHeader("Pragma", "no-cache"); // HTTP 1.0
        resp.setDateHeader("Expires", 0); // Proxies

        // Retrieve existing session without creating a new one
        HttpSession session = req.getSession(false);

        // Check if session exists and user is logged in
        boolean isLoggedIn = (session != null && session.getAttribute("loggedInUser") != null);

        if (isLoggedIn) {
            // User is authenticated; pass request along the filter chain
            chain.doFilter(request, response);
        } else {
            // User is unauthenticated; redirect to login page
            resp.sendRedirect(req.getContextPath() + "/login.html?error=unauthorized");
        }
    }

    @Override
    public void destroy() {
        // Cleanup resources if needed
    }
}