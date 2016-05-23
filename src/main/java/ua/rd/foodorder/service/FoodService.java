package ua.rd.foodorder.service;

import ua.rd.foodorder.domain.Food;

public interface FoodService {

	Food getById(Long Id);
	Iterable<Food> getFood();
	
	
}
