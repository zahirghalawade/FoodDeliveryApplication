package com.tap1.daoImplementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.tap1.dao.UserDao;
import com.tap1.model.User;
import com.tap1.utility.dbConnection;

public class userDaoImplementation implements UserDao {

    private static final String INSERT_QUERY = 
        "INSERT INTO `user` (`userName`, `email`, `password`, `address`, `role`, `createdDate`, `lastlogindate`) VALUES (?, ?, ?, ?, ?, ?, ?)";

    @Override
    public void addUser(User user) {
        Connection connection = dbConnection.getConnection();
        try {
            PreparedStatement pstmt = connection.prepareStatement(INSERT_QUERY);
            pstmt.setString(1, user.getUserName());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPassword());
            pstmt.setString(4, user.getAddress());
            
            // Default role fallback to Customer if null
            String role = (user.getRole() != null && !user.getRole().trim().isEmpty()) ? user.getRole() : "Customer";
            pstmt.setString(5, role);
            
            Timestamp now = new Timestamp(System.currentTimeMillis());
            pstmt.setTimestamp(6, now);
            pstmt.setTimestamp(7, now);
            
            int i = pstmt.executeUpdate();
            System.out.println(i + " row(s) affected in user table.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User getUser(int userId) {
        String GET_QUERY = "SELECT * FROM `user` WHERE `userId` = ?";
        User user = null;

        Connection con = dbConnection.getConnection();
        try {
            PreparedStatement pstmt = con.prepareStatement(GET_QUERY);
            pstmt.setInt(1, userId);
            ResultSet res = pstmt.executeQuery();
            if (res.next()) {
                user = getUserDetails(res);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public User getUserByName(String userName) {
        String GET_QUERY = "SELECT * FROM `user` WHERE `userName` = ?";
        User user = null;

        Connection con = dbConnection.getConnection();
        try {
            PreparedStatement pstmt = con.prepareStatement(GET_QUERY);
            pstmt.setString(1, userName);
            ResultSet res = pstmt.executeQuery();
            if (res.next()) {
                user = getUserDetails(res);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        String GET_QUERY = "SELECT * FROM `user` WHERE `email` = ?";
        User user = null;

        Connection con = dbConnection.getConnection();
        try {
            PreparedStatement pstmt = con.prepareStatement(GET_QUERY);
            pstmt.setString(1, email);
            ResultSet res = pstmt.executeQuery();
            if (res.next()) {
                user = getUserDetails(res);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void updateUser(User user) {
        Connection con = dbConnection.getConnection();
        String UPDATE_QUERY = "UPDATE `user` SET `userName` = ?, `email` = ?, `password` = ?, `address` = ?, `lastlogindate` = ? WHERE `userId` = ?";
        try {
            PreparedStatement pstmt = con.prepareStatement(UPDATE_QUERY);
            pstmt.setString(1, user.getUserName());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPassword());
            pstmt.setString(4, user.getAddress());
            pstmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            pstmt.setInt(6, user.getUserId());
            
            int i = pstmt.executeUpdate();
            System.out.println(i + " row(s) updated.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteUser(int userId) {
        Connection con = dbConnection.getConnection();
        String DELETE_QUERY = "DELETE FROM `user` WHERE `userId` = ?";
        try {
            PreparedStatement pstmt = con.prepareStatement(DELETE_QUERY);
            pstmt.setInt(1, userId);
            int i = pstmt.executeUpdate();
            System.out.println(i + " row(s) deleted.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUser() {
        List<User> list = new ArrayList<>();
        String GETALL_QUERY = "SELECT * FROM `user`";
        Connection con = dbConnection.getConnection();
        try {
            Statement stmt = con.createStatement();
            ResultSet res = stmt.executeQuery(GETALL_QUERY);
            while (res.next()) {
                User user = getUserDetails(res);
                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public User getUserDetails(ResultSet res) throws SQLException {
        int id = res.getInt("userId");
        String name = res.getString("userName");
        String email = res.getString("email");
        String password = res.getString("password");
        String address = res.getString("address");
        String role = res.getString("role");
        Timestamp createdDate = res.getTimestamp("createdDate");
        Timestamp lastLoginDate = res.getTimestamp("lastlogindate");
        
        return new User(id, name, email, password, address, role, createdDate, lastLoginDate);
    }
}