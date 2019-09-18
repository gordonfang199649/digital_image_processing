package com.example.service;
import com.example.bean.FoodOutputBo;
import com.example.bean.MeatDo;
import com.example.bean.SeaFoodDo;
import com.example.bean.FoodInputBo;
import com.example.dataaccessor.FoodDataAccessor;

public class FoodService{
  private FoodDataAccessor foodDataAccessor = new FoodDataAccessor();
  /**
    constructor
  */
  public FoodService(){

  }
 
  /**
   * 
   * @author ESB20201
   * @param inputBo
   * @return foodOutputBo
   * 邏輯判斷inputBo.TYPE是meat或seafood
   * 根據型態封裝成foodOutputBo物件
   */
  public FoodOutputBo getFood(FoodInputBo inputBo){
    FoodOutputBo foodOutputBo = new FoodOutputBo();
    String foodType = inputBo.getFoodType();
    String name = inputBo.getName();
    int price = inputBo.getPrice();

    if(foodType.equals("seafood")){
      SeaFoodDo seafoodDo = foodDataAccessor.getSeafood(foodType,name,price);
      price = seafoodDo.getPrice();
      name = seafoodDo.getName();
      foodOutputBo.setPrice(price);
      foodOutputBo.setName(name);
    }
    else if(foodType.equals("meat")){
      MeatDo meatDo = foodDataAccessor.getMeat(foodType);
      price = meatDo.getPrice();
      name = meatDo.getName();
      foodOutputBo.setPrice(price);
      foodOutputBo.setName(name);
    }
    return foodOutputBo;
  }
}