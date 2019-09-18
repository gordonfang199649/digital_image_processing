import java.util.Scanner;
import com.example.bean.FoodDto;
import com.example.bean.FoodRelayDto;
import com.example.controller.FoodController;

public class HomeworkPartFour {
    private static FoodController foodController = new FoodController();
    public static void main(String args[]) {
        System.out.println("請輸入食物品名。(hint:seafood/meat)");
       
        try(Scanner keyboard = new Scanner(System.in)){
            while(keyboard.hasNext("0")==false) {
                String query = keyboard.nextLine();
                FoodDto inputDto = new FoodDto();
                inputDto.setQuery(query);
                FoodRelayDto foodRelayDto = new FoodRelayDto();
                foodRelayDto = foodController.chooseFood(inputDto);
                System.out.printf("名稱: %s\n",foodRelayDto.getName());
                System.out.printf("價錢: %d\n",foodRelayDto.getPrice());
            }
        } 
    }
}