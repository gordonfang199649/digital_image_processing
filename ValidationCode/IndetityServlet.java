package com.captcha.examples.Image;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class IndetityServlet extends HttpServlet{
	//隨字元字典，不包含O,O,1,I 易混淆的字母數字
	public static final char[] CHARS = {'2','3','5','6','7','8','9','A','B','C','D','E','F','G','H','J','K','L','P','Q','R'
			,'S','T','U','V','W','X','Y','Z'};
	public static Random random = new Random();
	public static final String CHECK_CODE_KEY = "CHECK_CODE_KEY";
	
	//每次取六個隨機字母或數字
	public static String getRandomString() {
		StringBuffer buffer = new StringBuffer();
		for(int i = 0 ; i<6 ; i++)
			buffer.append(CHARS[random.nextInt(CHARS.length)]);
		return buffer.toString();
	}
	
	//產生隨機顏色
	public static Color getRandomColor() {
		return new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255));
	}
	
	//回傳某顏色的反色 255減去原本的色彩
	public static Color getReverseColor(Color color) {
			return new Color(255 - color.getRed(), 255 - color.getGreen(), 255 - color.getBlue());
	}
	//產生隨機圖片
	public static BufferedImage generateImage(int width, int height, Color backgroundColor, Color fontColor, String randomString) {
		//設定圖片類型是RGB
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		//在image中建立 Graphics2D類別的圖像
		Graphics2D graphic = image.createGraphics();
		//設定文字字型、粗體、大小
		graphic.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
		//設定背景顏色
		graphic.setColor(backgroundColor);
		//畫出填滿顏色的長方形
		graphic.fillRect(0, 0, width, height);
		//設定文字顏色
		graphic.setColor(fontColor);
		//畫出字串
		graphic.drawString(randomString, 18, 20);
		
		//製作噪點 出現在圖片隨機的位置上
		for(int i = 0, n = random.nextInt(100); i<n ; i++)
			graphic.fillRect(random.nextInt(width), random.nextInt(height), 1, 1);
		
		return image;
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		String randomString = getRandomString(); //取得隨機字母數字
		int width = 100; //圖片寬度
		int height = 30; //圖片高度
		Color backgroundColor = getRandomColor(); //取得背景顏色
		Color fontColor = getReverseColor(backgroundColor); //取得文字顏色
		//製作驗證碼圖片
		BufferedImage image = generateImage(width,height,backgroundColor,fontColor,randomString);
	
		res.setContentType("image/jpeg"); //設定輸出類型
		//不提供圖片快取
		res.setHeader("pragma","no-cache");
		res.setHeader("Cache-Control","no-cache");
		res.setDateHeader("Expires", 0);
		//將產生好的驗證碼 放入Session中，以便後面登入時可以做比較
		req.getSession(true).setAttribute(CHECK_CODE_KEY, randomString);
		ServletOutputStream sos = null;
		sos = res.getOutputStream();
		
		//寫入圖片
		try {
			ImageIO.write(image, "JPEG", sos); 
		}catch(IOException e) {
			PrintWriter out = res.getWriter();
			out.println(e);
		}
		sos.flush();
		sos.close();
	}	
}
