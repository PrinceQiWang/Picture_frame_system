package drawGUI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

class Circle extends drawings//circle
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	void draw(Graphics2D g2d) {
        g2d.setPaint(new Color(R, G, B));
        g2d.setStroke(new BasicStroke(stroke));
        
        g2d.drawOval(Math.min(x1, x2), Math.min(y1, y2),
                Math.max(Math.abs(x1 - x2), Math.abs(y1 - y2)),
                Math.max(Math.abs(x1 - x2), Math.abs(y1 - y2)));
       
       round = countZhouChang();
       area = countArea();
       
    
    }
    
    String  countArea(){
    	return "area:"+(Math.abs(x1 - x2)*3.14*Math.abs(x1 - x2))+"";
    }
    
    String countZhouChang(){
    	return "round:"+(Math.abs(x1 - x2)*3.14*2)+"";
    }
    
    
}

