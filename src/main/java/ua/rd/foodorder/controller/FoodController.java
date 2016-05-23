package ua.rd.foodorder.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ua.rd.foodorder.service.FoodService;

@Controller
@RequestMapping("/food")
public class FoodController {

	@Autowired
	private FoodService foodService;
	
	@RequestMapping("/list")
	public String getFood(Model model){
		
		model.addAttribute("foodList", foodService.getFood());
		
		return "food";
	}
	
	
}
