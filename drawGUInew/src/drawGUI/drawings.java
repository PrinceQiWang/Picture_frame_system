package drawGUI;

import java.awt.Graphics2D;
import java.io.Serializable;

class drawings implements Serializable//���࣬����ͼ�ε�Ԫ���õ����л��ӿڣ�����ʱ����
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int bianshu;
    int x1, y1, x2, y2; //������������
    int R, G, B;        //����ɫ������
    float stroke;       //����������ϸ����
    int type;       //������������
    String s1;
    String s2;      //��������������
    String area;
    String round;
	 int bianshuMax = 20;
     int[] x = new int[bianshuMax];
     int[] y = new int[bianshuMax];
    void draw(Graphics2D g2d) {
    };
   
    
    
  
}