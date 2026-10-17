package com.tap1.dao;

import java.util.List;

import com.tap1.model.Restaurant;
import com.tap1.model.User;

public interface RestaurantDao {
	void addRestaurant(Restaurant restaurant);
	Restaurant getRestaurant(int restaurantId);
	void updateRestaurant(Restaurant restaurant);
	void deleteRestaurant(int restaurantId);
	List<Restaurant> getAllRestaurant();

}
