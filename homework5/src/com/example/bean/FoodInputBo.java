package com.example.bean;

public class FoodInputBo{
	private String foodType;
	private String query;
	private String name;
	private int price;
	
	public void setQuery(String query){
		this.query = query;
	}

	public String getQuery(){
		return query;
	}
	
	public void setFoodType(String foodtype){
		this.foodType = foodtype;
	}

	public String getFoodType(){
		return foodType;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}
	
	public int getPrice() {
		return price;
	}
}