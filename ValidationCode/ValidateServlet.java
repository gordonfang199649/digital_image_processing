package com.captcha.examples.validate;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ValidateServlet extends HttpServlet{
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		//使用者輸入的驗證碼
		String parameterCode = req.getParameter("checkCode");
		HttpSession session = req.getSession();
		res.setCharacterEncoding("UTF-8"); //設定reponse編碼方式
		res.setContentType("text/html"); //設定response文件類型為html
		PrintWriter out = res.getWriter();
		
		//Session產生的驗證碼
		String sessionCode = (String) session.getAttribute("CHECK_CODE_KEY");
		out.println("<!DOCTYPE>");
		out.println("<HTML>");
		out.println("<HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("<BODY>");
		if(parameterCode!=null && parameterCode.toLowerCase().equals(sessionCode.toLowerCase())){
				out.println("<b>驗證成功</b>");
				session.removeAttribute("CHECK_CODE_KEY");
		}
		else {
			out.println("<b>驗證失敗</b>");
			//res.sendRedirect("/demoApp/identity.html");
		}
		out.println("</BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}
}
