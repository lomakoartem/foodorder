package ua.rd.foodorder.repository;

import org.springframework.data.repository.CrudRepository;

import ua.rd.foodorder.domain.Food;

public interface FoodRepository extends CrudRepository<Food, Long> {

}
