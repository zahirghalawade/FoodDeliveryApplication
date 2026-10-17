package com.tap1.utility;

import com.tap1.daoImplementation.userDaoImplementation;
import com.tap1.model.User;

public class test {

	public static void main(String[] args) {
		User user=new User("Sammed","sam@gmail.com","sam@1234","Sadalaga","user");
		userDaoImplementation udi=new  userDaoImplementation();
		udi.addUser(user);
		

	}

}
