package drawGUI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

class FillRect extends drawings//æÿ–Œ¿‡
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	java.text.DecimalFormat   df=new   java.text.DecimalFormat("#.##"); 
    void draw(Graphics2D g2d) {
        g2d.setPaint(new Color(R, G, B));
        g2d.setStroke(new BasicStroke(stroke));
        Rectangle r= new  Rectangle();
       g2d.fillRect(Math.min(x1, x2), Math.min(y1, y2),
                Math.abs(x1 - x2), Math.abs(y1 - y2));
       //g2d.drawString(countArea()+countRound(), (x1+x2)/2, (y1+y2)/2-30);
       area = countArea();
       round = countRound();
    }
    
    String countArea(){
		return "area:"+df.format(Math.abs(x1 - x2)*Math.abs(y1 - y2))+"";
	}
	

	String countRound(){
		return "round:"+df.format((Math.abs(x1 - x2)+Math.abs(y1 - y2))*2);
	}
	
}