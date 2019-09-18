package com.example.bean;

import java.util.HashMap;
import java.util.Map;

public class SeaFoodDo{
	private int price;
	private String name;
	private Map<String, SeaFoodDo> map = new HashMap<String, SeaFoodDo>();
	
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
	
	public void setMap(Map<String, SeaFoodDo> map) {
		this.map = map;
	}
	
	public Map<String, SeaFoodDo> getMap() {
		return map;
	}
}