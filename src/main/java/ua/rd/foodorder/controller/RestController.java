package ua.rd.foodorder.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RestController {
	
	@RequestMapping("/rest/food")
	public String restFood(){
		return "food_rest";
	}
	
}
