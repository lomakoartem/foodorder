package ua.rd.foodorder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.rd.foodorder.domain.Food;
import ua.rd.foodorder.repository.FoodRepository;

@Service
public class SimpleFoodService implements FoodService {

	@Autowired
	private FoodRepository foodRepository;
	
	@Override
	public Food getById(Long Id) {
		return null;
	}

	@Override
	public Iterable<Food> getFood() {
		return foodRepository.findAll();
	}

}
