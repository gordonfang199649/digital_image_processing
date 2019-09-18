package com.example.dataaccessor;
import com.example.bean.SeaFoodDo;
import java.util.HashMap;
import java.util.Map;

import com.example.bean.MeatDo;

public class FoodDataAccessor{
	private Map<String, SeaFoodDo> map = new HashMap<String, SeaFoodDo>();
    
    /**
     * @author gordon fang
     * @param foodType
     * @return seafoodDo
     * 封裝成SeafoodDo物件
     */
    public SeaFoodDo getSeafood(String foodType, String name,int price){
        SeaFoodDo seafoodDo = new SeaFoodDo();
        seafoodDo.setName(name);
        seafoodDo.setPrice(price);
        addFoodInfo(foodType, seafoodDo);
        seafoodDo.setMap(map);
        return seafoodDo;
    }
    
    /**
     * @author gordon fang
     * @param foodType
     * @return meatDo
     * 封裝成meatDo物件
     */
    public MeatDo getMeat(String foodType){
        MeatDo meatDo = new MeatDo();
        return meatDo;
    }

    /**
     * @author gordon fang
     * @param foodType 食物型態 作為key
     * @param seafoodDo包含（name 食物名稱/price 食物價格）作為value
     * @return void
     * 使用HashMap資料結構儲存資料
     */
	public void addFoodInfo(String foodType, SeaFoodDo seafoodDo) {
		//key值不為空
		if (foodType!=null) {
			map.put(foodType, seafoodDo);
		}
	}
	
	/**
     * @author gordon fang
     * @param foodType 食物型態 作為key
     * @param name 食物名稱 將要更新的值
     * @return void
     * 判斷map是否有這把key, 取其值（資料型態:SeaFoodDo), 進行更新
     */
	public void updateName(String foodType, String name){
		if(map.containsKey(foodType)) {
			SeaFoodDo seaFoodDo = map.get(foodType);
			seaFoodDo.setName(name);
		}
	}
	
	/**
     * @author gordon fang
     * @param foodType 食物型態 作為key
     * @param price 食物價格 將要更新的值
     * @return void
     * 判斷map是否有這把key, 取其值（資料型態:SeaFoodDo), 進行更新
     */
	public void updatePrice(String foodType, int price){
		if(map.containsKey(foodType)) {
			SeaFoodDo seaFoodDo = map.get(foodType);
			seaFoodDo.setPrice(price);
		}
	}
	
	/**
     * @author gordon fang
     * @param foodType 食物型態 作為key
     * @return void
     * 判斷map是否有這把key, 取其值（資料型態:SeaFoodDo), 進行更新
     */
	public SeaFoodDo getAllSeafood(String foodType){
        SeaFoodDo seafoodDo = new SeaFoodDo();
        seafoodDo.setName(name);
        seafoodDo.setPrice(price);
        addFoodInfo(foodType, seafoodDo);
        seafoodDo.setMap(map);
        return seafoodDo;
    }
	
	/**
     * @author gordon fang
     * @param foodType 食物型態 作為key
     * @return void
     * 根據key值(foodType)刪除對應的資料
     */
	public void deleteFoodInfo(String foodType) {
		if(map.containsKey(foodType)) {
			map.remove(foodType);
		}
	}
}