---
title: 'Steganography'
author: gordon fang
---

圖像隱碼術 Steganography
===
## 大綱

[TOC]

## 什麼是圖像藏密學? What is Steganography?
![](https://i.imgur.com/GlZf6yi.jpg)


>希臘文中的 steganos 一詞代表隱藏書寫的意思。圖像藏密學是一門加密技術，資料隱藏是為了不被接收方以外的人知曉，與一般加密的資料不同之處在於，一般加密的資料知道藏密的所在，可以使用各種破解方法解之，而圖像藏密學是知道資料是被加密的，但不能夠輕易得知藏密的所在之處。

案例 Case
---
### 日落
![](https://i.imgur.com/ybHxzD7.png)
>某一些惡意的程式將設定檔案是一張漂亮的風景，但在影像檔案中卻多了出一些資料，將由解密，就不再是一張漂亮的風景圖。
### 樹與貓
![](https://i.imgur.com/2RmpFwz.png)
>這是一棵樹的相片，內含了隱蔽的圖像。如果把每個色彩空間和數字3進行按位元與（Bit-wise AND）運算，再把亮度增強85倍，得到下圖。

#### Java程式實作
```java
public static void decode(BufferedImage img, int width, int height){
    for (int i = 0 ; i<width; i++){
        for (int j = 0 ; j < height; j++) {
            int pixel = img.getRGB(i,j);
            int a = ((pixel)>> 24) & 0xff; 
            int r = ((pixel >> 16) & 0x3) * (256/4);
            int g = ((pixel >> 8) & 0x3)  * (256/4);
            int b = (pixel & 0x3)  * (256/4);
            pixel = (a<<24) | (r<<16) | (g<<8) | b;
            img.setRGB(i,j,pixel);
        }
    }
}
```

圖像隱碼術實作-直方圖位移 Histogram Shifting Technique
---
>本作品採用是直方圖位移，是一種可逆式的資訊隱藏技術，該方法是統計一張圖片的像素值分佈，進行機密訊息嵌入。

### 灰階圖片
![](https://i.imgur.com/f8MBFTz.png)
>測試圖片是lena的灰階圖片，使用到的是八個位元的灰階像素值，是將紅綠藍三種顏色的像素值相加後除三所得到的綜合值。

### 找出高峰值
![](https://i.imgur.com/U8pcWYa.png)

### 位移
![](https://i.imgur.com/Jy8xuTb.png)
>找出高峰值的用意在於要將高峰值前的像素值往前推一格，假設某張圖片高峰值集中在像素值175，我讓175之前的像素值位移一格，接下來我們就可以空出來的空間，即像素值174藏秘密訊息。

#### Java程式實作
```java
//將低於高峰值得像素值位移一格
//空出高峰值前s一格
//pixel_val[i][j]讀取的每一顆像素值
//peak是高峰值
for (int i = 0 ; i<width; i++) {
    for (int j = 0 ; j < height; j++) {
        if (pixel_val[i][j]<= (peak-1)) {
            pixel_val[i][j] = pixel_val[i][j] - 1;
        }
    }
}
```

### 藏密
![](https://i.imgur.com/iEb4CSf.png)
>上圖為像素值讀取的區塊，演示的是直方圖的位移的過程，a是原圖的像素值區塊，我們將小於高峰值的像素值往前推一格，公式如同上一節所描述的，推完過後會呈現像b的狀態，接著是重頭戲「藏密」，只要是碰到高峰值-2的像素值，如果藏的位元是1，讓其位移一格到24，反之，維持原樣。

#### Java程式實作
```java
//peak：高峰值
//pixel_val：讀取像素值
//char_meg：秘密訊息（位元）
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
```
### 藏密前後
![](https://i.imgur.com/5XCCO6R.jpg)

### 標註藏密地方
![](https://i.imgur.com/Vd8fTYN.png)
>Secret Message：‘Where there is discord, may we bring harmony. Where there is error, may we bring truth. Where there is doubt, may we bring faith. And where there is despair, may we bring hope’ 

>猜到了嗎？我是將以上的訊息（柴契爾夫人受訪談論）藏進圖片中，一般很難肉眼看出端倪來。

### 最低有效位元
![](https://i.imgur.com/ra4WtCo.png)
>為什麼我們會看不出藏密的地方，原因是因為更動位元是最低有效位元，說白話文就是讓像素值加一減一，並不是劇烈的更動，一般難以使用肉眼分辨出其中古怪之處。

### 解密
![](https://i.imgur.com/hIJVSiH.png)
>逆向操作
![](https://i.imgur.com/qhEvdob.png)
>解碼訊息
#### Java程式實作
```java
public static void main(){
    ///取得高峰值
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
}

public static String decode(String meg, int start, int len){
        //以遞迴方式解讀隱藏訊息
        if((start+8)>len)
            return "";
        return ((char)Integer.parseInt(meg.substring(start,start+8),2))+decode(meg,start+8,len);
    }
```
缺點
---
:::danger
1. 藏密的位元數有限
    * 上述的實作只有位移一格空間，當藏密訊密量大時，僅僅一格恐怕是不夠的。
2. 高峰值異動
    * 在位移的過程，高峰值有可能發生變動。
:::

## 改良

:::info
1. 空出更多格子
    * 另一種方法是將左右各位移一格，將高峰值位移到左右兩格。
    ![](https://i.imgur.com/Z6fMdrD.png)
:::

