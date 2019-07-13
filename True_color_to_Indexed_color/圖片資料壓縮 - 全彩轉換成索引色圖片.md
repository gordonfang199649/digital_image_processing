# 圖片資料壓縮 - 全彩轉換成索引色圖片
## 大綱

[TOC]

## 索引色概念 - Indexed color
![](https://i.imgur.com/RKpdZgW.png)

在計算機領域當中，索引色是一種資料壓縮的技巧，主要是用來快速呈現圖片、或是加速資料傳輸，也稱之「向量量化壓縮」。如果一張圖片是上述方式編碼，顏色資訊就不會直接存在該張圖片裡，而是另外一個檔案中稱「調色盤」，以陣列的方式儲存，陣列中的每一個元素都代表著一個顏色。

換言之，該張圖片並不包含原圖的所有顏色，而是參照另一個檔案所提供的顏色，編寫而成。

調色盤的大小 - Palette size
---
![](https://i.imgur.com/QaSSchQ.png)


|              | ALPHA |  RED  | GREEN | BLUE |
|--------------|-------|-------|-------|------|
| BIT POSITION | 31-24 | 23-16 |  15-8 | 7-0  |

一張數位全彩影像(含透明值)由32個位元所組成，Alpha、紅、綠、藍各占4個bit，可以表示的顏色為2的24次方，相當為16,777,216‬個顏色，我們可以得知控制位元大小可以控制可以表現出來的色彩。

![](https://i.imgur.com/fZo44nF.png)

調色盤為儲存索引顏色的地方，最常見有4色、 16色、或256色，電腦數字表示都是01表示法，會根據位元的多寡來呈現，所以色彩種類都是2的次方。256色就是由一個位元組(8個位元)所組成的，4個位元則可以表示16種顏色，以此類推。

PNG圖檔、或是視訊覆蓋技術有使用到透明值，調色盤會額外保留一個位置來儲存透明值。


轉換公式 - Formula
---
![](https://i.imgur.com/W8U1hjx.png)

在本作品裡頭是使用256種索引色彩，我使用歐幾里德距離公式對一張全彩的圖片進行色彩置換，我們國中所學的數學公式正好可以運用在此，利用巢狀走訪所有的像素值，將該像素值的RGB值與256色的索引色套此公式，會得到一個數值，求出數值差異最小的寫入一張空白的圖片上。


![](https://i.imgur.com/c85pxJs.png)
>公式：dist((r1, r2), (g1, g2), (b1, b2)) = √((r1 - r2)² + (g1 - g2)² + (b1 - b2)²)

進一步說明，這套公式是在歐氏空間內求出距離，一顆像素值有紅、綠、藍，如果我們將紅、綠、藍視為座標，這顆像素則存在在**三維空間**，另外我們是用這顆像素值跟索引色比較，兩顆像素形成一**向量**。

## 主要程式
```java
for(int i=0; i<height; i++){
    for(int j=0; j<width; j++){	
        //變數是原圖像素值
        Color c = new Color(image.getRGB(j, i));
        //distance陣列存放歐幾里德公式算出的距離
        double distance[]=new double[256];

        int minindex=0; //抓取最小距離的索引色之索引
        double min = Math.sqrt(Math.pow((c.getRed()-r[0]),2)+Math.pow((c.getGreen()-g[0]),2)+Math.pow((c.getBlue()-b[0]),2));
        //兩兩比較算出最小距離
        for(int d=1;d<256; d++){
            distance[d]= Math.sqrt(Math.pow((c.getRed()-r[d]),2)+Math.pow((c.getGreen()-g[d]),2)+Math.pow((c.getBlue()-b[d]),2));
            if(min>distance[d]){
                min = distance[d];
                minindex = d;
            }
        }
    //上色，填上索引色
    buff.setRGB(j, i,palette[minindex]);
}
``` 
作品結果
---
### 原圖
![](https://i.imgur.com/nBbKWyQ.png)

### 處理過的圖片
![](https://i.imgur.com/VAKR4aP.png)
