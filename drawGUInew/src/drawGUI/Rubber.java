package drawGUI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

class Rubber extends drawings//œ∆§≤¡¿‡
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	void draw(Graphics2D g2d) {
        g2d.setPaint(new Color(255, 255, 255));
        g2d.setStroke(new BasicStroke(stroke + 4,
                BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
        g2d.drawLine(x1, y1, x2, y2);
    }
}