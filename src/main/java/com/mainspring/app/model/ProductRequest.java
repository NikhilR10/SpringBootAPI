package com.mainspring.app.model;

import lombok.Data;

@Data
public class ProductRequest {
	
	private String productName;
	private String productDescription;
	private int productPrice;
	private int quantity;
	
}
