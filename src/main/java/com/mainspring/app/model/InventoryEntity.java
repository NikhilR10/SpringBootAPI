package com.mainspring.app.model;

import lombok.Data;

@Data
public class InventoryEntity {
	
	private int inventoryId;
	private int productId;
	private int quantity;

}
