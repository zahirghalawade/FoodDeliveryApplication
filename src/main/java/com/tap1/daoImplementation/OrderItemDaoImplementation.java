package com.tap1.daoImplementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.tap1.dao.OrderItemDao;
import com.tap1.model.OrderItem;
import com.tap1.utility.dbConnection;

public class OrderItemDaoImplementation implements OrderItemDao {

	String INSERT_QUERY = "INSERT INTO `OrderItem` (`OrderID`, `MenuID`, `Quantity`, `ItemTotal`) VALUES (?, ?, ?, ?)";

	@Override
	public void addOrderItem(OrderItem orderItem) {
		Connection connection = dbConnection.getConnection();
		try {
			PreparedStatement pstmt = connection.prepareStatement(INSERT_QUERY);
			pstmt.setInt(1, orderItem.getOrderId());
			pstmt.setInt(2, orderItem.getMenuId());
			pstmt.setInt(3, orderItem.getQuantity());
			pstmt.setDouble(4, orderItem.getItemTotal());

			int i = pstmt.executeUpdate();
			System.out.println(i + " row affected");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public OrderItem getOrderItem(int orderItemId) {
		String GET_QUERY = "SELECT * FROM `OrderItem` WHERE `OrderItemID` = ?";
		OrderItem orderItem = null;

		Connection con = dbConnection.getConnection();
		try {
			PreparedStatement pstmt = con.prepareStatement(GET_QUERY);
			pstmt.setInt(1, orderItemId);
			ResultSet res = pstmt.executeQuery();
			while (res.next()) {
				orderItem = getOrderItemDetails(res);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orderItem;
	}

	@Override
	public void updateOrderItem(OrderItem orderItem) {
		Connection con = dbConnection.getConnection();
		String UPDATE_QUERY = "UPDATE `OrderItem` SET `OrderID` = ?, `MenuID` = ?, `Quantity` = ?, `ItemTotal` = ? WHERE `OrderItemID` = ?";
		try {
			PreparedStatement pstmt = con.prepareStatement(UPDATE_QUERY);
			pstmt.setInt(1, orderItem.getOrderId());
			pstmt.setInt(2, orderItem.getMenuId());
			pstmt.setInt(3, orderItem.getQuantity());
			pstmt.setDouble(4, orderItem.getItemTotal());
			pstmt.setInt(5, orderItem.getOrderItemId());

			int i = pstmt.executeUpdate();
			System.out.print(i + " row updated");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteOrderItem(int orderItemId) {
		Connection con = dbConnection.getConnection();
		String DELETE_QUERY = "DELETE FROM `OrderItem` WHERE `OrderItemID` = ?";
		try {
			PreparedStatement pstmt = con.prepareStatement(DELETE_QUERY);
			pstmt.setInt(1, orderItemId);
			int i = pstmt.executeUpdate();
			System.out.print(i + " row deleted");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<OrderItem> getAllOrderItems() {
		ArrayList<OrderItem> list = new ArrayList<OrderItem>();
		String GETALL_QUERY = "SELECT * FROM `OrderItem`";
		Connection con = dbConnection.getConnection();
		try {
			Statement stmt = con.createStatement();
			ResultSet res = stmt.executeQuery(GETALL_QUERY);
			while (res.next()) {
				OrderItem orderItem = getOrderItemDetails(res);
				list.add(orderItem);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<OrderItem> getOrderItemsByOrderId(int orderId) {
		ArrayList<OrderItem> list = new ArrayList<OrderItem>();
		String GET_BY_ORDER_QUERY = "SELECT * FROM `OrderItem` WHERE `OrderID` = ?";
		Connection con = dbConnection.getConnection();
		try {
			PreparedStatement pstmt = con.prepareStatement(GET_BY_ORDER_QUERY);
			pstmt.setInt(1, orderId);
			ResultSet res = pstmt.executeQuery();
			while (res.next()) {
				OrderItem orderItem = getOrderItemDetails(res);
				list.add(orderItem);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public OrderItem getOrderItemDetails(ResultSet res) throws SQLException {
		int orderItemId = res.getInt("OrderItemID");
		int orderId = res.getInt("OrderID");
		int menuId = res.getInt("MenuID");
		int quantity = res.getInt("Quantity");
		double itemTotal = res.getDouble("ItemTotal");

		return new OrderItem(orderItemId, orderId, menuId, quantity, itemTotal);
	}
}