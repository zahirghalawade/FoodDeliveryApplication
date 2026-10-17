package com.tap1.daoImplementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.tap1.dao.MenuDao;
import com.tap1.model.Menu;
import com.tap1.utility.dbConnection;

public class MenuDaoImplementation implements MenuDao {

    private static final String INSERT_QUERY = 
        "INSERT INTO `Menu` (`RestaurantID`, `ItemName`, `Description`, `Price`, `IsAvailable`, `Category`, `ImagePath`) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String GET_QUERY = 
        "SELECT * FROM `Menu` WHERE `MenuID` = ?";
    private static final String UPDATE_QUERY = 
        "UPDATE `Menu` SET `RestaurantID` = ?, `ItemName` = ?, `Description` = ?, `Price` = ?, `IsAvailable` = ?, `Category` = ?, `ImagePath` = ? WHERE `MenuID` = ?";
    private static final String DELETE_QUERY = 
        "DELETE FROM `Menu` WHERE `MenuID` = ?";
    private static final String GETALL_QUERY = 
        "SELECT * FROM `Menu`";
    private static final String GET_BY_RESTAURANT_QUERY = 
        "SELECT * FROM `Menu` WHERE `RestaurantID` = ?";

    @Override
    public void addMenu(Menu menu) {
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(INSERT_QUERY)) {

            pstmt.setInt(1, menu.getRestaurantId());
            pstmt.setString(2, menu.getItemName());
            pstmt.setString(3, menu.getDescription());
            pstmt.setDouble(4, menu.getPrice());
            pstmt.setInt(5, menu.getIsAvailable());
            pstmt.setString(6, menu.getCategory());
            pstmt.setString(7, menu.getImagePath());

            int rows = pstmt.executeUpdate();
            System.out.println(rows + " row(s) inserted.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Menu getMenu(int menuId) {
        Menu menu = null;
        try (Connection con = dbConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(GET_QUERY)) {

            pstmt.setInt(1, menuId);
            try (ResultSet res = pstmt.executeQuery()) {
                if (res.next()) {
                    menu = getMenuDetails(res);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return menu;
    }

    @Override
    public void updateMenu(Menu menu) {
        try (Connection con = dbConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(UPDATE_QUERY)) {

            pstmt.setInt(1, menu.getRestaurantId());
            pstmt.setString(2, menu.getItemName());
            pstmt.setString(3, menu.getDescription());
            pstmt.setDouble(4, menu.getPrice());
            pstmt.setInt(5, menu.getIsAvailable());
            pstmt.setString(6, menu.getCategory());
            pstmt.setString(7, menu.getImagePath());
            pstmt.setInt(8, menu.getMenuId());

            int rows = pstmt.executeUpdate();
            System.out.println(rows + " row(s) updated.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteMenu(int menuId) {
        try (Connection con = dbConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(DELETE_QUERY)) {

            pstmt.setInt(1, menuId);
            int rows = pstmt.executeUpdate();
            System.out.println(rows + " row(s) deleted.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Menu> getAllMenu() {
        List<Menu> list = new ArrayList<>();
        try (Connection con = dbConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet res = stmt.executeQuery(GETALL_QUERY)) {

            while (res.next()) {
                list.add(getMenuDetails(res));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Menu> getMenuByRestaurantId(int restaurantId) {
        List<Menu> list = new ArrayList<>();
        try (Connection con = dbConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(GET_BY_RESTAURANT_QUERY)) {

            pstmt.setInt(1, restaurantId);
            try (ResultSet res = pstmt.executeQuery()) {
                while (res.next()) {
                    list.add(getMenuDetails(res));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Menu getMenuDetails(ResultSet res) throws SQLException {
        int id = res.getInt("MenuID");
        int restaurantId = res.getInt("RestaurantID");
        String itemName = res.getString("ItemName");
        String description = res.getString("Description");
        double price = res.getDouble("Price");
        int isAvailable = res.getInt("IsAvailable");
        String category = res.getString("Category");
        String imagePath = res.getString("ImagePath");
        Timestamp createdAt = res.getTimestamp("CreatedAt");
        Timestamp updatedAt = res.getTimestamp("UpdatedAt");
        Timestamp deletedAt = res.getTimestamp("DeletedAt");

        return new Menu(id, restaurantId, itemName, description, price, isAvailable, category, imagePath, createdAt, updatedAt, deletedAt);
    }
}