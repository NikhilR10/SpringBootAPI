package com.mainspring.app.service;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mainspring.app.entity.Cart;
import com.mainspring.app.entity.LineItem;
import com.mainspring.app.model.InventoryEntity;
import com.mainspring.app.model.ProductEntity;
import com.mainspring.app.model.ProductRequest;
import com.mainspring.app.repository.CartRepository;
import com.mainspring.app.repository.LineItemRepository;
import com.mainspring.app.utils.HttpUtil;

@Service
public class RetailService {
	
	@Autowired
	private CartRepository cartRepo;
	
	@Autowired
	private LineItemRepository lineItemRepo;
	
	public String saveCart(int lineItemId) {
		Optional<LineItem> lineItem = lineItemRepo.findById(lineItemId);
		if(lineItem.isPresent()) {
			Cart cart = new Cart();
			List<LineItem> lineItems = new ArrayList<>();
			lineItems.add(lineItem.get());
			cart.setLineItems(lineItems);
			cartRepo.save(cart);
			return "SUCCESS";
		}
		return "FAILED";
	}
	
	public Cart getCart(int cartId) {
		Optional<Cart> findById = cartRepo.findById(cartId);
		if(findById.isPresent()) {
			return findById.get();
		} else {
			return null;
		}
	}
	
	public String updateCart(Cart cartRequest) {
		Optional<Cart> cartOpt = cartRepo.findById(cartRequest.getCartId());
		if(cartOpt.isEmpty())
			return "Cart not found";
		Cart cart = cartOpt.get();
		List<LineItem> lineItems = new ArrayList<>();
		cartRequest.getLineItems().forEach(item -> {
			Optional<LineItem> itemOpt = lineItemRepo.findById(item.getItemId());
			if(itemOpt.isPresent()) {
				lineItems.add(itemOpt.get());
			}
		});
		cart.setLineItems(lineItems);
		cartRepo.save(cart);
		return "SUCCESS";
	}
	
	public String deleteCart(int cartId) {
		Optional<Cart> cartOpt = cartRepo.findById(cartId);
		if(cartOpt.isEmpty())
			return "Cart not found";
		Cart cart = cartOpt.get();
		cartRepo.delete(cart);
		return "SUCCESS";
	}
	
	
	
	
	
	
	
	
	
	@Autowired
	private HttpUtil httpUtil;
	
	@Autowired
	private ObjectMapper mapper;
	
	public String addProduct(ProductRequest request) throws Exception {
		
		//Creating product entity for save product api request body
		ProductEntity prod = new ProductEntity();
		prod.setProductName(request.getProductName());
		prod.setProductDescription(request.getProductDescription());
		prod.setProductPrice(request.getProductPrice());
		
		
		//Inside double quotes add the product save api endpoint
		HttpRequest productSaveReq = HttpRequest.newBuilder().uri(URI.create("")).header("Content-Type", "application/json")
				.POST(BodyPublishers.ofString(mapper.writeValueAsString(prod))).build();
		
		//API is called and response is stored in this string
		String productId = httpUtil.getApiResponse(productSaveReq);
		
		//Creating inventory entity for save inventory api request body
		InventoryEntity inv = new InventoryEntity();
		inv.setProductId(Integer.valueOf(productId));
		inv.setQuantity(request.getQuantity());
		
		
		//Inside double quotes add the inventory save api endpoint
		HttpRequest inventorySaveReq = HttpRequest.newBuilder().uri(URI.create(""))
				.POST(BodyPublishers.noBody()).build();
		
		//API is called and response is stored in this string
		httpUtil.getApiResponse(inventorySaveReq);
		return "SUCCESS";
	}
	
	
	
	
	
	
	public String test() {
		HttpRequest getCart = HttpRequest.newBuilder().uri(URI.create("http://localhost:9090/get/cart?cartId=6"))
				.GET().build();
		return httpUtil.getApiResponse(getCart);
	}

}
