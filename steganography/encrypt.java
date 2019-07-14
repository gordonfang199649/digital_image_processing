//匯入函式
import java.io.File;
import java.io.IOException;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Scanner;

public class encrypt{
	public static void main(String[] args)throws IOException {
		Scanner keyboard = new Scanner(System.in);
		BufferedImage image = null;

		System.out.println("\n Please leave a secret message that you want to hide in this picture.");
		String message = keyboard.nextLine();
		message = binary_converter(message);

		System.out.println("\n Please type the file name you want to encrypt");
		String file_name = keyboard.nextLine();

		//讀取檔案
		try{
			image = ImageIO.read(new File(file_name));
			System.out.print("Reading completed.\n");
		}catch(IOException e){
			System.out.print("ERROR:"+e);
		}
		
		

		int width = image.getWidth();//圖片寬度
		int height = image.getHeight();//圖片高度 
		
	
		//將圖片轉換灰階影像
		//一旦圖片轉成灰階影像，可表示的色彩就只有二的八次方，256種顏色
		int pixel_val[][] = new int[width][height];
		gray_scale(image, pixel_val, width, height);

		//宣告一陣列，統計灰階圖像的像素值
		int bucket[] = new int [256];
		for (int i = 0 ; i<width; i++) {
			for (int j = 0 ; j < height; j++) {
				for (int k = 0; k<256; k++ ) {
					if (pixel_val[i][j] == k) {
						bucket[k]++;			
					}	
				}
			}
		}

		//取出這張灰階圖像像素值高峰，即為這張集中最多在哪一個像素值
		int peak = 0 ;
  		int max = Integer.MIN_VALUE;
		for (int i = 0 ; i<bucket.length; i++) {
			if (bucket[i]>max) {
				max = bucket[i];
				peak = i;
			}
		}
		
		//將低於高峰值得像素值位移一格
		//空出高峰值前一格
	    for (int i = 0 ; i<width; i++) {
			for (int j = 0 ; j < height; j++) {
				if (pixel_val[i][j]<= (peak-1)) {
					pixel_val[i][j] = pixel_val[i][j] - 1;
				}
			}
		}

	   //利用迴圈將秘密訊息藏進去
	   //假設高峰像素值是175，我們就往前推兩個到173
	   //只要讀到173這個像素值，秘密訊息是1，那讓其位移到一格到174
	   //秘密訊息是0，不移動
	   int count = 0;
		for (int i = 0 ; i<width; i++) {
			for (int j = 0 ; j < height; j++) {
	   			if ((pixel_val[i][j]==(peak-2)) && (count<message.length())){ 
					char char_meg = message.charAt(count++);
					if(char_meg == '1'){
						pixel_val[i][j] = pixel_val[i][j]+1;
					}
					else if(char_meg == '0'){
						pixel_val[i][j] = pixel_val[i][j];
					}
	   			}
			}
		}

		
		//上色
		drawing_pic(image,pixel_val,width,height);
		//寫入檔案
		writing_file(image, new String("Crypted_image.png"));
		
	    //將藏訊息的地方標註出來
	    count = 0;
	    for (int i = 0 ; i<width; i++) {
			for (int j = 0 ; j < height; j++) {
				char char_meg;
	   			if ((pixel_val[i][j]==(peak-1)) && (count<message.length())){ 
					char_meg = message.charAt(count++);
					if(char_meg == '1'){
						pixel_val[i][j] = 0;
					}
				}
				else if ((pixel_val[i][j]==(peak-2)) && (count<message.length())){
					char_meg = message.charAt(count++);
					if(char_meg == '0'){
						pixel_val[i][j] = 0;
					}
				}
			}
		}

	    //上色
		drawing_pic(image,pixel_val,width,height);
		//寫入檔案
		writing_file(image, new String("Crypted_image_marked.png"));
		keyboard.close();
	}

	public static String binary_converter(String meg){
		//將原始訊息轉換成ASCII編碼
		//ASCII編碼再轉換成二進制數值
		StringBuilder builder = new StringBuilder();
		//for (char c : meg_char) {
		//	builder.append(Integer.toBinaryString((int) c));
		//}

		byte []bytes = meg.getBytes();
		for (byte b : bytes){
			int val = b;
			for (int i = 0; i < 8; i++){
		    	builder.append((val & 128) == 0 ? 0 : 1);
		    	val <<= 1;
		 	}
		}
		return builder.toString();
	}

	
	public static void gray_scale(BufferedImage img, int pixel_val[][], int width, int height){
		//轉灰階圖像
		for (int i = 0 ; i<width; i++){
			for (int j = 0 ; j < height; j++) {
				int pixel = img.getRGB(i,j);
				int r = (pixel >> 16) & 0xff; 
	  		 	int g = (pixel >> 8) & 0xff;
	   			int b = pixel & 0xff;
				int gray = (r+g+b)/3; //灰階公式：紅綠藍三色除於三
	   			pixel_val[i][j] = gray;
			}
		}
	}

	public static void drawing_pic(BufferedImage img, int pixel_val[][], int width, int height){
		for (int i = 0 ; i< width; i++) {
			for (int j = 0; j< height ; j++ ) {
					int pixel = img.getRGB(i,j);
					int a = (pixel>>24) & 0xff;		   		
					pixel = (a<<24) | (pixel_val[i][j]<<16) | (pixel_val[i][j]<<8) | pixel_val[i][j];
					img.setRGB(i,j,pixel);
			}
		 }
	}

	public static void writing_file(BufferedImage img, String file_name){
		System.out.print("Writing file...\n");
		try{
	      ImageIO.write(img, "png", new File(file_name));
	    }catch(IOException e){
	      System.out.println(e);
	    }
	}
}