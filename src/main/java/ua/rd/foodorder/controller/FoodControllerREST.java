package ua.rd.foodorder.controller;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ua.rd.foodorder.domain.Food;
import ua.rd.foodorder.service.FoodService;

@RestController
public class FoodControllerREST {
	
	private Logger logger = LoggerFactory.getLogger(FoodControllerREST.class);
	
	@Autowired
	private FoodService foodService;
	
	@RequestMapping(value ="/rest/food/list", method = RequestMethod.GET)
	public ResponseEntity<List<Food>> listAllFood(){
		List<Food> food = new ArrayList<Food>();
		Iterator<Food> iterator = foodService.getFood().iterator();
		
		while(iterator.hasNext()){
			food.add(iterator.next());
		}
		
		if(food.isEmpty()){
			return new ResponseEntity<List<Food>>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<List<Food>>(food, HttpStatus.OK);
	}
}
