# 驗證碼實作
## 大綱 Content

[TOC]

驗證碼概述 - Validation Code
---
![](https://i.imgur.com/B7nthrf.png)

全自動區分電腦和人類的公開圖靈測試（英語：Completely Automated Public Turing test to tell Computers and Humans Apart，簡稱CAPTCHA），俗稱驗證碼。

主要伺服器會產一道讓使用者回答，但作答必須只能由人類回答，由於電腦無法解答（目前有第三方Library可以做到圖像字元辨識)，所以回答者會被辨識為人類。

另外一種常見的驗證碼是文字扭曲，如上圖，是為了不被光學字元識別（OCR, Optical Character Recognition）自動辨識圖上的字母或數字。

目的 - Purpose
---
1. 目前留言板上運用最廣泛，防止惡意洗版
2. 訂票系統、網路銀行也使用驗證碼，避免大量交易的發生。


實作簡易驗證碼 - Generating a validation code
---
>本次實作是使用Java AWT(抽象視窗工具組) 、和Java Servlet框架，利用AWT函式庫上色產生驗證碼，Servlet則是將結果輸出、以及後續的驗證工作。
1. 定義好驗證碼會出現的字母及數字
```=java
public static final char[] CHARS = {'2','3','5','6','7','8','9','A','B','C','D','E','F','G','H','J','K','L','P','Q','R'
,'S','T','U','V','W','X','Y','Z'};

//每次取六個隨機字母或數字
public static String getRandomString() {
    //使用StringBuffer的append方法串接隨機字母和數字
    StringBuffer buffer = new StringBuffer();
    for(int i = 0 ; i<6 ; i++)
        buffer.append(CHARS[random.nextInt(CHARS.length)]);
    return buffer.toString();
}
```

2. 隨機挑選背景顏色、字體顏色則是其反色
```=java
//產生隨機顏色
public static Color getRandomColor() {
    return new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255));
}

//回傳某顏色的反色 255減去原本的色彩
public static Color getReverseColor(Color color) {
    return new Color(255 - color.getRed(), 255 - color.getGreen(), 255 - color.getBlue());
}
```

3. 製造一些噪點隨機出現在圖片 
```=java
//製作噪點 出現在圖片隨機的位置上
for(int i = 0, n = random.nextInt(100); i<n ; i++)
    graphic.fillRect(random.nextInt(width), random.nextInt(height), 1, 1);
```

4. 將產生好的驗證碼放入Session
```=java
//將產生好的驗證碼 放入Session中，以便後面登入時可以做比較
req.getSession(true).setAttribute(CHECK_CODE_KEY, randomString);
```

5. 產生的驗證碼
![](https://i.imgur.com/R4mMCGQ.png)
![](https://i.imgur.com/dA8HSVZ.png)
![](https://i.imgur.com/jI6GzyA.png)
![](https://i.imgur.com/tsFXzpx.png)


後端驗證工作 - Validation
---
1. 擷取先前存在Session驗證碼字串與前端使用者傳過來的字串
```=java
//使用者輸入的驗證碼
String parameterCode = req.getParameter("checkCode");
HttpSession session = req.getSession();
//Session產生的驗證碼
String sessionCode = (String) session.getAttribute("CHECK_CODE_KEY");
```

2. 比較是否為一樣的字串
```=java
if(parameterCode!=null && parameterCode.toLowerCase().equals(sessionCode.toLowerCase())){
        System.out.println("<b>驗證成功</b>");
        //移除Session CHECK_CODE_KEY 這個屬性
        session.removeAttribute("CHECK_CODE_KEY");
        res.sendRedirect("/demoApp/identity.html");
}
else {
    System.out.println("<b>驗證失敗</b>");
    //重新導向
    res.sendRedirect("/demoApp/identity.html");
}
```

網頁畫面
---
![](https://i.imgur.com/JPeUgNU.png)


