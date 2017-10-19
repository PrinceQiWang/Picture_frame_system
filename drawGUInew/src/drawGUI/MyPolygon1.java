package drawGUI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

public class MyPolygon1 extends drawings{

	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

  

   
	
    void draw(Graphics2D g2d) {
        java.text.DecimalFormat   df=new   java.text.DecimalFormat("#.##");   
    	MyPolygon mplg = new MyPolygon(x, y,x1,y1,x2,y2);
        g2d.setPaint(new Color(R, G, B));
        g2d.setStroke(new BasicStroke(stroke));
        
        
        if (bianshu <= this.bianshuMax) {
            mplg.posOfPoint(bianshu);
            g2d.drawPolygon(x, y, bianshu);
            
            round = "round:"+df.format(mplg.r*bianshu);
            area = "area:"+df.format(mplg.r*0.5*mplg.r*bianshu)+"";
          
          
        } else {
            bianshuMax += 20;
            x = new int[bianshuMax];
            y = new int[bianshuMax];
            mplg = new MyPolygon(x, y,x1,y1,x2,y2);
            
        }

    }
	
	
	
	
	class MyPolygon {// 求正多边形的顶点坐标
	    private int[] x;
	    private int[] y;
	    private int startX;// 顶点的X坐标
	    private int startY;// 顶点的Y坐标
	    private double r;// 外接圆的半径

	    public MyPolygon(int[] x, int[] y,int startX1,int startY1,int startX2,int startY2) {
	        this.x = x;
	        this.y = y;
	        this.startX = startX1;
	        this.startY = startY1;
	        r =Math.sqrt((startX2-startX1)*(startX2-startX1)+(startY2-startY1)*(startY2-startY1))/2;
	       // r=200;
	    }

	    public void posOfPoint(int bianshu) {
	        x[0] = startX;
	        y[0] = startY;
	        Point p = new Point();
	        for (int i = 1; i < bianshu; i++) {
	            p = nextPoint(((2 * Math.PI) / bianshu) * i);
	            x[i] = p.x;
	            y[i] = p.y;
	        }
	    }

	    public Point nextPoint(double arc) {// arc为弧度，在顶点处建立直角坐标系，用r和arc确定下一个点的坐标
	        Point p = new Point();
	        p.x = (int) (x[0] - r * Math.sin(arc));
	        p.y = (int) (y[0] + r - r * Math.cos(arc));
	        return p;
	    }
	}
}
