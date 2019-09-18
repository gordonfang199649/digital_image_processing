package com.example.bean;

public class FoodRelayDto{
	private int price;
	private String name;

	public FoodRelayDto(){

	}
	
	public void setPrice(int price){
		this.price = price;
	}

	public int getPrice(){
		return price;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}
}