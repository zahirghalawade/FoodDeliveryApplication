package com.tap1.model;

import java.sql.Timestamp;

public class User {

	
		public User() {
		super();
	}
		private int userId;
		private String userName;
		private String email;
		private String password;
		private String address;
		private String role;
		private Timestamp created_Date;
		private Timestamp last_Login_Date;
		@Override
		public String toString() {
			return "User [userId=" + userId + ", userName=" + userName + ", email=" + email + ", password=" + password
					+ ", address=" + address + ", role=" + role + ", created_Date=" + created_Date
					+ ", last_Login_Date=" + last_Login_Date + "]";
		}
		public int getUserId() {
			return userId;
		}
		public void setUserId(int userId) {
			this.userId = userId;
		}
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public String getRole() {
			return role;
		}
		public void setRole(String role) {
			this.role = role;
		}
		public Timestamp getCreated_Date() {
			return created_Date;
		}
		public void setCreated_Date(Timestamp created_Date) {
			this.created_Date = created_Date;
		}
		public Timestamp getLast_Login_Date() {
			return last_Login_Date;
		}
		public void setLast_Login_Date(Timestamp last_Login_Date) {
			this.last_Login_Date = last_Login_Date;
		}
		public User(String userName, String email, String password, String address, String role) {
			super();
			this.userName = userName;
			this.email = email;
			this.password = password;
			this.address = address;
			this.role = role;
		}
		public User(String userName, String email, String password, String address, String role, Timestamp created_Date,
				Timestamp last_Login_Date) {
			super();
			this.userName = userName;
			this.email = email;
			this.password = password;
			this.address = address;
			this.role = role;
			this.created_Date = created_Date;
			this.last_Login_Date = last_Login_Date;
		}
		public User(int userId, String userName, String email, String password, String address, String role,
				Timestamp created_Date, Timestamp last_Login_Date) {
			super();
			this.userId = userId;
			this.userName = userName;
			this.email = email;
			this.password = password;
			this.address = address;
			this.role = role;
			this.created_Date = created_Date;
			this.last_Login_Date = last_Login_Date;
		}
		
		
	

}
