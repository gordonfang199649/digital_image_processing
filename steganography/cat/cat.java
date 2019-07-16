//匯入函式
import java.io.File;
import java.io.IOException;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class cat{
	public static void main(String[] args)throws IOException {
		BufferedImage image = null;

		//讀取加密圖片檔案
		try{
			image = ImageIO.read(new File("Steganography_original.png"));
			System.out.print("Reading completed.\n");
		}catch(IOException e){
			System.out.print("ERROR:"+e);
		}

		int width = image.getWidth();//圖片寬度
		int height = image.getHeight();//圖片高度 
        
        decode(image, width, height);
        writing_file(image, "cat.png");
	}

    public static void decode(BufferedImage img, int width, int height){
        for (int i = 0 ; i<width; i++){
            for (int j = 0 ; j < height; j++) {
                int pixel = img.getRGB(i,j);
                int a = ((pixel)>> 24) & 0xff; 
                int r = ((pixel >> 16) & 0x3) * (255/3);
                int g = ((pixel >> 8) & 0x3)  * (255/3);
                int b = (pixel & 0x3)  * (255/3);
                pixel = (a<<24) | (r<<16) | (g<<8) | b;
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