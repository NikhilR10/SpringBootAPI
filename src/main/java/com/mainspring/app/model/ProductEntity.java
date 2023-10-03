package com.mainspring.app.model;

import lombok.Data;

@Data
public class ProductEntity {
	
	private String productId;
	private String productName;
	private String productDescription;
	private int productPrice;

}
