import java.io.File;
import java.io.IOException;
import java.awt.Color;
import java.awt.image.ColorModel;
import java.awt.image.IndexColorModel;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.Scanner;

public class True_color_to_Indexed_color{
	static Scanner keyboard = new Scanner(System.in);
	public static void main(String args[])throws IOException{
		BufferedImage image=null; //圖片暫存變數
		File input; 
		int width; //抓取圖片寬度
		int height; //抓取圖片高度

		//讀取檔案
		System.out.print("Input Filename:");
		String fname = keyboard.nextLine();
		
		try{
			input = new File(fname);
			image = ImageIO.read(input); //使用圖片輸出入API讀取這個圖檔
			System.out.println("Reading image...");
		}catch(IOException e){
			System.out.println("Error:"+e);
		}

		//process image
		System.out.println("Processing...");
		width = image.getWidth(); //抓取圖片寬度
		height = image.getHeight(); //抓取圖片高度

		//宣告BufferedImage物件變數作為將要圖畫的白紙
		//BufferedImage(圖片寬度,圖片高度, 圖片模式(位元表示模式))
		//圖片模式(位元表示模式)選擇TYPE_BYTE_INDEXED 表示圖片是以色彩256bit呈現
		BufferedImage buff = new BufferedImage(width,height,BufferedImage.TYPE_BYTE_INDEXED);
		converter(image, buff, width, height);

		//輸出圖檔
		try{
			File f = new File("Output.png");
			ImageIO.write(buff, "png", f);
			System.out.println("Writing completed.");
		}catch(IOException e){
			System.out.println("Error:"+e);
		}
	}

	public static void converter(BufferedImage image, BufferedImage buff, int width, int height){
			ColorModel cm = buff.getColorModel(); //取得圖片表示模式
			IndexColorModel icm = (IndexColorModel) cm; //將其轉換成IndexColorModel物件
			int palette[] = new int[icm.getMapSize()]; 
			icm.getRGBs(palette); //取得256個索引色

			//取得索引色的紅、藍、綠的顏色，宣告三個陣列分別存放
			int r[]=new int[palette.length];
			int g[]=new int[palette.length];
			int b[]=new int[palette.length];
			for(int i=0;i<palette.length;i++){
				//將讀取到的索引色的像素以Color物件形式儲存
				//如此一來可以使用Color物件中屬性getRed()、getGreen()、getBlue()將三色分離
				Color pc = new Color(palette[i]); 
				r[i]=pc.getRed(); 
				g[i]=pc.getGreen();
				b[i]=pc.getBlue();
			}

			for(int i=0; i<height; i++){
				for(int j=0; j<width; j++){	
					//依照上一個迴圈的做法Color物件取得原圖的像素值
					Color c = new Color(image.getRGB(j, i));
					double distance[]=new double[256];

					//使用歐幾里得距離計算出最相似的像素值，圖片像素質減去索引色的像素值計算出來的差異即為我們所要的
					int minindex=0;
					double min = Math.sqrt(Math.pow((c.getRed()-r[0]),2)+Math.pow((c.getGreen()-g[0]),2)+Math.pow((c.getBlue()-b[0]),2));
					for(int d=1;d<256; d++){
						distance[d]= Math.sqrt(Math.pow((c.getRed()-r[d]),2)+Math.pow((c.getGreen()-g[d]),2)+Math.pow((c.getBlue()-b[d]),2));
						if(min>distance[d]){
							min = distance[d];
							minindex = d;
						}
					}
				//上色
				buff.setRGB(j, i,palette[minindex]);
			}
		}
	}
}