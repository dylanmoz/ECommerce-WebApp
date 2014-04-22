package dylanandapril.database;

import java.math.BigDecimal;

public class Product {
	
	private long id;
	private String name;
	private long category;
	private String sku;
	private double price;
	
	public Product() {
		
	}
	
	public Product(String name, long category, String sku, double price) {
		this.name = name;
		this.category = category;
		this.sku = sku;
		this.price = price;
	}
	
	public Product(long id, String name, long category, String sku, double price) {
		this(name, category, sku, price);
		this.id = id;
	}
	
	public long getId() {
		return id;
	}
	public Product setId(long id) {
		this.id = id;
		return this;
	}
	public String getName() {
		return name;
	}
	public Product setName(String name) {
		this.name = name;
		return this;
	}
	public long getCategory() {
		return category;
	}
	public Product setCategory(long category) {
		this.category = category;
		return this;
	}
	public String getSku() {
		return sku;
	}
	public Product setSku(String sku) {
		this.sku = sku;
		return this;
	}
	public double getPrice() {
		return price;
	}
	public Product setPrice(double price) {
		this.price = price;
		return this;
	}
	
}