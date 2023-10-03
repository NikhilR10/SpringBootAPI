package com.mainspring.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mainspring.app.entity.Cart;
import com.mainspring.app.model.ProductRequest;
import com.mainspring.app.service.RetailService;

@RestController
public class RetailController {
	
	@Autowired
	private RetailService retailService;
	
	@PostMapping("/save/cart")
	public String saveCart(@RequestParam("lineItemId") int lineItemId) {
		return retailService.saveCart(lineItemId);
	}
	
	@GetMapping("/get/cart")
	public Cart getCart(@RequestParam("cartId") int cartId) {
		return retailService.getCart(cartId);
	}
	
	@PutMapping("/update/cart")
	public String updateCart(@RequestBody Cart cart) {
		return retailService.updateCart(cart);
	}
	
	@DeleteMapping("/delete/cart")
	public String deleteCart(@RequestParam("cartId") int cartId) {
		return retailService.deleteCart(cartId);
	}
	
	
	
	
	
	
	
	
	
	
	@PostMapping("/add/product")
	public String addProduct(@RequestBody ProductRequest productRequest) throws Exception {
		return retailService.addProduct(productRequest);
	}

}
