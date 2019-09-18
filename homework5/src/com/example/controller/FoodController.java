package com.example.controller;
import com.example.service.FoodService;
import com.example.bean.FoodRelayDto;
import com.example.bean.FoodDto;
import com.example.bean.FoodInputBo;
import com.example.bean.FoodOutputBo;

public class FoodController {
    private FoodService foodService = new FoodService();
    /**
     * @author gordon
     * @param inputDto
     * @return foodRelayDto
     * 
     */
    public FoodRelayDto chooseFood(FoodDto inputDto) {
        //取得Bo物件，foodService.getFood方法需要用到這個參數
        FoodInputBo inputBo = getFoodInputBo(inputDto);
        //判斷命令是新增/修改/刪除/查尋
        String[] queries = inputBo.getQuery().split(" ");
        FoodOutputBo foodOutputBo = new FoodOutputBo();
        switch(queries[0]) {
            case "add":
                inputBo.setFoodType(queries[1]);
                inputBo.setName(queries[2]);
                inputBo.setPrice(Integer.parseInt(queries[3]));
                foodOutputBo = foodService.getFood(inputBo);
                break;
            case "update":
                break;
            case "delete":
                break;
            case "query":
                break;
            case "search":
                break;
            default:
                break;
        }
        FoodRelayDto foodRelayDto = setUpFoodRelayDto(foodOutputBo);
        return foodRelayDto;
    }
   
    /**
     * @author gordon
     * @param inputDto
     * @return inputBo
     * 將前端傳進來的inputDto物件
     * 取得Query，寫入到inputBo物件
     */
    private FoodInputBo getFoodInputBo(FoodDto inputDto){
        FoodInputBo inputBo = new FoodInputBo();
        String query = inputDto.getQuery();
        inputBo.setQuery(query);
        return inputBo;
    }
   
    /**
     * @author gordon
     * @param foodOutputBo
     * @return foodRelayDto
     * Service層回傳回來的資料型態是FoodOutputBo
     * 透過函式取得其屬性，封裝至foodRelayDto物件
     */
    private FoodRelayDto setUpFoodRelayDto(FoodOutputBo foodOutputBo) {
        FoodRelayDto foodRelayDto = new FoodRelayDto();
        String name = foodOutputBo.getName();
        int price = foodOutputBo.getPrice();
        foodRelayDto.setName(name);
        foodRelayDto.setPrice(price);
        return foodRelayDto;
    }
}