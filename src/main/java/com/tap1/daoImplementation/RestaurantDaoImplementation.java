package com.tap1.daoImplementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.tap1.dao.RestaurantDao;
import com.tap1.model.Restaurant;
import com.tap1.utility.dbConnection;

public class RestaurantDaoImplementation implements RestaurantDao {

	String INSERT_QUERY = "INSERT INTO `Restaurant` (`Name`, `CuisineType`, `DeliveryTime`, `Address`, `AdminUserID`, `Rating`, `IsActive`, `ImagePath`) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

	@Override
	public void addRestaurant(Restaurant restaurant) {
		Connection connection = dbConnection.getConnection();
		try {
			PreparedStatement pstmt = connection.prepareStatement(INSERT_QUERY);
			pstmt.setString(1, restaurant.getRestaurantName());
			pstmt.setString(2, restaurant.getCuisineType());
			pstmt.setInt(3, restaurant.getDeliveryTime());
			pstmt.setString(4, restaurant.getAddress());
			pstmt.setInt(5, restaurant.getAdminUserId());
			pstmt.setDouble(6, restaurant.getRating());
			pstmt.setInt(7, restaurant.getIsActive());
			pstmt.setString(8, restaurant.getImagePath());

			int i = pstmt.executeUpdate();
			System.out.println(i + " row affected");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Restaurant getRestaurant(int restaurantId) {
		String GET_QUERY = "SELECT * FROM `Restaurant` WHERE `RestaurantID` = ?";
		Restaurant restaurant = null;

		Connection con = dbConnection.getConnection();
		try {
			PreparedStatement pstmt = con.prepareStatement(GET_QUERY);
			pstmt.setInt(1, restaurantId);
			ResultSet res = pstmt.executeQuery();
			while (res.next()) {
				restaurant = getRestaurantDetails(res);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return restaurant;
	}

	@Override
	public void updateRestaurant(Restaurant restaurant) {
		Connection con = dbConnection.getConnection();
		String UPDATE_QUERY = "UPDATE `Restaurant` SET `Name` = ?, `CuisineType` = ?, `DeliveryTime` = ?, `Address` = ?, `AdminUserID` = ?, `Rating` = ?, `IsActive` = ?, `ImagePath` = ? WHERE `RestaurantID` = ?";
		try {
			PreparedStatement pstmt = con.prepareStatement(UPDATE_QUERY);
			pstmt.setString(1, restaurant.getRestaurantName());
			pstmt.setString(2, restaurant.getCuisineType());
			pstmt.setInt(3, restaurant.getDeliveryTime());
			pstmt.setString(4, restaurant.getAddress());
			pstmt.setInt(5, restaurant.getAdminUserId());
			pstmt.setDouble(6, restaurant.getRating());
			pstmt.setInt(7, restaurant.getIsActive());
			pstmt.setString(8, restaurant.getImagePath());
			pstmt.setInt(9, restaurant.getRestaurantId());

			int i = pstmt.executeUpdate();
			System.out.print(i + " row updated");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteRestaurant(int restaurantId) {
		Connection con = dbConnection.getConnection();
		String DELETE_QUERY = "DELETE FROM `Restaurant` WHERE `RestaurantID` = ?";
		try {
			PreparedStatement pstmt = con.prepareStatement(DELETE_QUERY);
			pstmt.setInt(1, restaurantId);
			int i = pstmt.executeUpdate();
			System.out.print(i + " row deleted");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Restaurant> getAllRestaurant() {
		ArrayList<Restaurant> list = new ArrayList<Restaurant>();
		String GETALL_QUERY = "SELECT * FROM `Restaurant`";
		Connection con = dbConnection.getConnection();
		try {
			Statement stmt = con.createStatement();
			ResultSet res = stmt.executeQuery(GETALL_QUERY);
			while (res.next()) {
				Restaurant restaurant = getRestaurantDetails(res);
				list.add(restaurant);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public Restaurant getRestaurantDetails(ResultSet res) throws SQLException {
		int id = res.getInt("RestaurantID");
		String name = res.getString("Name");
		String cuisine = res.getString("CuisineType");
		int deliveryTime = res.getInt("DeliveryTime");
		String address = res.getString("Address");
		int adminId = res.getInt("AdminUserID");
		double rating = res.getDouble("Rating");
		int isActive = res.getInt("IsActive");
		String imagePath = res.getString("ImagePath");

		Restaurant restaurant = new Restaurant(id, name, cuisine, deliveryTime, address, adminId, rating, isActive, imagePath);
		return restaurant;
	}
}