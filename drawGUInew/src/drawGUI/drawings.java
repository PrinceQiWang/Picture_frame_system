package drawGUI;

import java.awt.Graphics2D;
import java.io.Serializable;

class drawings implements Serializable//父类，基本图形单元，用到串行化接口，保存时所用
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int bianshu;
    int x1, y1, x2, y2; //定义坐标属性
    int R, G, B;        //定义色彩属性
    float stroke;       //定义线条粗细属性
    int type;       //定义字体属性
    String s1;
    String s2;      //定义字体风格属性
    String area;
    String round;
	 int bianshuMax = 20;
     int[] x = new int[bianshuMax];
     int[] y = new int[bianshuMax];
    void draw(Graphics2D g2d) {
    };
   
    
    
  
}