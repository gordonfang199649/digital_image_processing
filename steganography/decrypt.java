//匯入函式
import java.io.File;
import java.io.IOException;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class decrypt{
	public static void main(String[] args)throws IOException {
		BufferedImage image = null;

		//讀取加密圖片檔案
		try{
			image = ImageIO.read(new File("Crypted_image.png"));
			System.out.print("Reading completed.\n");
		}catch(IOException e){
			System.out.print("ERROR:"+e);
		}

		int width = image.getWidth();//圖片寬度
		int height = image.getHeight();//圖片高度 
        
        //取得這張圖檔像素值
        int pixel_val[][] = new int[width][height];
        gray_scale(image, pixel_val, width, height);
        //取得高峰值
        int peak = peak_calculation(pixel_val,width,height);

        //擷取藏密訊息
        StringBuilder builder = new StringBuilder();
        for (int i = 0 ; i<width; i++) {
			for (int j = 0 ; j < height; j++) {
	   			if (pixel_val[i][j]==(peak-1)) { 
					builder.append("1");
					pixel_val[i][j] -=1; 
	   			}
	   			else if (pixel_val[i][j]==(peak-2)) { 
                    builder.append("0");
	   			}
			}
        }
        String message = builder.toString();
        //解碼
        System.out.println("Secret message:"+decode(message,0, message.length()));
        
        //修復圖片
        for (int i = 0 ; i<width; i++) {
			for (int j = 0 ; j < height; j++) {
				 if (pixel_val[i][j]<= (peak-1)) {
					pixel_val[i][j] = pixel_val[i][j] + 1;
				}
			}
        }
        drawing_pic(image,pixel_val,width,height);
        writing_file(image, "recovery image.png");
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

    public static int peak_calculation(int pixel_val[][], int width, int height){
        //計算一張圖片像素高峰值
        //宣告一陣列，統計灰階圖像的像素值
        int peak = 0 ;
        int bucket[] = new int [256];
  		int max = Integer.MIN_VALUE;
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
		for (int i = 0 ; i<bucket.length; i++) {
			if (bucket[i]>max) {
				max = bucket[i];
				peak = i;
			}
        }
        return peak;
    }

    public static String decode(String meg, int start, int len){
        //以遞迴方式解讀隱藏訊息
        if((start+8)>len)
            return "";
        return ((char)Integer.parseInt(meg.substring(start,start+8),2))+decode(meg,start+8,len);
    }

	public static void drawing_pic(BufferedImage img, int pixel_val[][], int width, int height){
        //替圖片上色
		for (int i = 0 ; i< width; i++) {
			for (int j = 0; j< height ; j++ ) {
					int pixel = img.getRGB(i,j);
					int a = (pixel>>24) & 0xff;		   		
					pixel = (a<<24) | (pixel_val[i][j]<<16) | (pixel_val[i][j]<<8) | pixel_val[i][j];
					img.setRGB(i,j,pixel);
			}
		 }
	}

	public static void writing_file(BufferedImage img, String file_name)throws IOException {
        //寫入檔案
		System.out.print("Writing file...\n");
		try{
	      ImageIO.write(img, "png", new File(file_name));
	    }catch(IOException e){
	      System.out.println(e);
	    }
	}
}