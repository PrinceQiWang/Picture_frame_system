package drawGUI;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


import java.io.*;
//���廭ͼ�Ļ���ͼ�ε�Ԫ
public class Main extends JFrame //���࣬��չ��JFrame�࣬��������������
{
    private ObjectInputStream input;
    private ObjectOutputStream output; //����������������������úͱ���ͼ���ļ�
    private JButton choices[];   //��ť���飬����������ƵĹ��ܰ�ť
    private JPanel panel = new JPanel();   
    private String names[] = {
        "New",
        "Open",
        "Save", //�������ǻ���������ť������"�½�"��"��"��"����"
        /*�����������ǵĻ�ͼ�������еĻ����ļ�����ͼ��Ԫ��ť*/
        "Pencil", //Ǧ�ʻ���Ҳ����������϶��������ͼ
        "Line", //����ֱ��
        "Rect", //���ƿ��ľ���
        "fRect", //������ָ����ɫ����ʵ�ľ���
        "Oval", //���ƿ�����Բ
        "fOval", //������ָ����ɫ����ʵ����Բ
        "Circle", //����Բ��
        "fCircle", //������ָ����ɫ����ʵ��Բ��
        "RoundRect", //���ƿ���Բ�Ǿ���
        "frRect", //������ָ����ɫ����ʵ��Բ�Ǿ���
        "Rubber", //��Ƥ������������ȥ�Ѿ����ƺõ�ͼ��
        "Color", //ѡ����ɫ��ť��������ѡ����Ҫ����ɫ
        "Stroke", //ѡ��������ϸ�İ�ť��������Ҫ����ֵ����ʵ�ֻ�ͼ������ϸ�ı仯
        "Word"      //�������ְ�ť�������ڻ�ͼ����ʵ����������
    };
    private String styleNames[] = {
        " ���� ", " ���� ", " ���Ĳ��� ", " ����_GB2312 ", " �����п� ",
        " �������� ", " Times New Roman ", " Serif ", " Monospaced ",
        " SonsSerif ", " Garamond "
    };            //�ɹ�ѡ���������
    //��Ȼ��������Ľṹ�����ö����Լ���������ϵͳ֧�ֵ�����
    private Icon items[];
    private String tipText[] = {
        //����������ƶ�����Ӧ��ť������ͣ��ʱ��������ʾ˵����
        //���߿��Բ�������İ�ť�������������
        "Draw a new picture",
        "Open a saved picture",
        "Save current drawing",
        "Draw at will",
        "Draw a straight line",
        "Draw a rectangle",
        "Fill a ractangle",
        "Draw an oval",
        "Fill an oval",
        "Draw a circle",
        "Fill a circle",
        "Draw a round rectangle",
        "Fill a round rectangle",
        "Erase at will",
        "Choose current drawing color",
        "Set current drawing stroke",
        "Write down what u want"
    };
    JToolBar buttonPanel;              //���尴ť���
    private JLabel statusBar;   
    private JLabel statusBar2; 
    private DrawPanel drawingArea;       //��ͼ����
    private int width = 800,  height = 550;    //���廭ͼ�����ʼ��С
    drawings[] itemList = new drawings[5000]; //������Ż���ͼ�ε�����
    JPanel [] jPanelItems =  new JPanel[5000];
    private int currentChoice = 1;            //����Ĭ�ϻ�ͼ״̬Ϊ��ʻ�
    int index = 0;                         //��ǰ�Ѿ����Ƶ�ͼ����Ŀ
    private Color color = Color.black;     //��ǰ������ɫ
    int R, G, B;                           //������ŵ�ǰɫ��ֵ
    int f1, f2;                  //������ŵ�ǰ������
    String style1;              //������ŵ�ǰ����
    private float stroke = 1.0f;  //���û��ʴ�ϸ��Ĭ��ֵΪ1.0f
    JCheckBox bold, italic;      //����������ѡ���
    //boldΪ���壬italicΪб�壬���߿���ͬʱʹ��
    JComboBox styles;
    String bianshu="0";
    String area ="";
    String zhouchang ="";
    JPanel areaAndRound = new JPanel();
    public Main() //���캯��
    {
        super("Drawing Pad");
        JMenuBar bar = new JMenuBar();      //����˵���
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic('F');
//�½��ļ��˵���
        JMenuItem newItem = new JMenuItem("New");
        newItem.setMnemonic('N');
        newItem.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        newFile();      //�����������������½��ļ�������
                    }
                });
        fileMenu.add(newItem);
//�����ļ��˵���
        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.setMnemonic('S');
        saveItem.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        saveFile();     //���������������ñ����ļ�������
                    }
                });
        fileMenu.add(saveItem);
//���ļ��˵���
        JMenuItem loadItem = new JMenuItem("Load");
        loadItem.setMnemonic('L');
        loadItem.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        loadFile();     //���������������ô��ļ�������
                    }
                });
        fileMenu.add(loadItem);
        fileMenu.addSeparator();
//�˳��˵���
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.setMnemonic('X');
        exitItem.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        System.exit(0); //��������������˳���ͼ�����
                    }
                });
        fileMenu.add(exitItem);
        bar.add(fileMenu);
//������ɫ�˵���
        JMenu colorMenu = new JMenu("Color");
        colorMenu.setMnemonic('C');
//ѡ����ɫ�˵���
        JMenuItem colorItem = new JMenuItem("Choose Color");
        colorItem.setMnemonic('O');
        colorItem.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        chooseColor();  //����������������ѡ����ɫ������
                    }
                });
        colorMenu.add(colorItem);
        bar.add(colorMenu);
//����������ϸ�˵���
        JMenu strokeMenu = new JMenu("Stroke");
        strokeMenu.setMnemonic('S');
//����������ϸ�˵���
        JMenuItem strokeItem = new JMenuItem("Set Stroke");
        strokeItem.setMnemonic('K');
        strokeItem.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        setStroke();
                    }
                });
        strokeMenu.add(strokeItem);
        bar.add(strokeMenu);
//������ʾ�˵���
       
       

        items = new ImageIcon[names.length];
//�������ֻ���ͼ�εİ�ť
        drawingArea = new DrawPanel();
        choices = new JButton[names.length];
        buttonPanel = new JToolBar(JToolBar.VERTICAL);
        buttonPanel = new JToolBar(JToolBar.HORIZONTAL);
        ButtonHandler handler = new ButtonHandler();
        ButtonHandler1 handler1 = new ButtonHandler1();
//����������Ҫ��ͼ��ͼ�꣬��Щͼ�궼�������Դ�ļ���ͬ��Ŀ¼����
        for (int i = 0; i < choices.length; i++) {//items[i]=new ImageIcon( MiniDrawPad.class.getResource(names[i] +".gif"));
            //�����jbuilder�����б�������Ӧ����������䵼��ͼƬ
            items[i] = new ImageIcon(names[i] + ".gif");
            //Ĭ�ϵ���jdk����jcreator�����У��ô���䵼��ͼƬ
            choices[i] = new JButton("", items[i]);
            choices[i].setToolTipText(tipText[i]);
            buttonPanel.add(choices[i]);
        }
//���������������밴ť����
        for (int i = 3; i < choices.length - 3; i++) {
            choices[i].addActionListener(handler);
        }
        choices[0].addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        newFile();
                    }
                });
        choices[1].addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        loadFile();
                    }
                });
        choices[2].addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        saveFile();
                    }
                });
        choices[choices.length - 3].addActionListener(handler1);
        choices[choices.length - 2].addActionListener(handler1);
        choices[choices.length - 1].addActionListener(handler1);
//������ѡ��
        styles = new JComboBox(styleNames);
        styles.setMaximumRowCount(8);
        styles.addItemListener(
                new ItemListener() {
                    public void itemStateChanged(ItemEvent e) {
                        style1 = styleNames[styles.getSelectedIndex()];
                    }
                });
//����ѡ��
        bold = new JCheckBox("BOLD");
        italic = new JCheckBox("ITALIC");
        checkBoxHandler cHandler = new checkBoxHandler();
        bold.addItemListener(cHandler);
        italic.addItemListener(cHandler);
        JPanel wordPanel = new JPanel();
        buttonPanel.add(bold);
        buttonPanel.add(italic);
        buttonPanel.add(styles);
        styles.setMinimumSize(new Dimension(50, 20));
        styles.setMaximumSize(new Dimension(100, 20));
        Container c = getContentPane();
        super.setJMenuBar(bar);
        c.add(buttonPanel, BorderLayout.NORTH);
        c.add(drawingArea, BorderLayout.CENTER);
        statusBar = new JLabel();
        statusBar2 = new JLabel();
        c.add(statusBar, BorderLayout.SOUTH);
        
        JButton line = new JButton("Line");
        JButton pencil = new JButton("Pancil");
        JButton word = new JButton("Word");
        JLabel jl1 = new JLabel("Please enter polygon side size:");
        JTextField jtf = new JTextField(10);
        JButton myPolygon1 = new JButton("Hollow polygon");
        JButton fillPolygon = new JButton("Solid polygon");
        JButton circle = new JButton("Hollow circle");
        JButton fillCircle = new JButton("Solid round");
        JButton rect = new JButton("Hollow rect");
        JButton fillRect = new JButton("Solid rect");
        JButton rubber = new JButton("Rubber");
       // jtf.setText("");
        JButton smaller = new JButton("smaller");
        JButton bigger = new JButton("enlarge");
        JButton moveL = new JButton("moveLeft");
        JButton moveR = new JButton("moveRight");
        JButton moveU = new JButton("moveUp");
        JButton moveD = new JButton("moveDown");
        JButton romateL = new JButton("turnRight");
        JButton romateR = new JButton("turnLeft");
        JButton reback = new JButton("Revoked");
        JButton coup = new JButton("Axisymmetric");
        JButton center = new JButton("Centrosymmetric");
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
       // panel2.add(romateL);
      //  panel2.add(romateR);
        panel2.add(fillCircle);
        panel2.add(fillRect);
        panel2.add(rubber);
        panel2.add(coup);
        panel2.add(center);
         panel2.add(reback);
         panel2.add(statusBar2);
        panel.add(line);
        panel.add(pencil);
        panel.add(word);
        panel.add(jl1);
        panel.add(jtf);
        panel.add(myPolygon1);
        panel.add(fillPolygon);
        panel.add(circle);
        panel.add(rect);
        
        panel.add(smaller);
        panel.add(bigger);
        panel.add(moveL);
        panel.add(moveR);
        panel.add(moveU);
        panel.add(moveD);
        drawingArea.add(panel2, BorderLayout.NORTH);
       // drawingArea.add(panel3,BorderLayout.NORTH);
        c.add(panel, BorderLayout.NORTH);
        line.addActionListener(new ActionListener()
  	   {

		@Override
		public void actionPerformed(ActionEvent arg0) {
		
			currentChoice=7;
		}
        	
  	   });
        
        fillPolygon.addActionListener(new ActionListener()
   	   {

 		@Override
 		public void actionPerformed(ActionEvent arg0) {
 		
 			currentChoice=8;
 		}
         	
   	   });
        
        pencil.addActionListener(new ActionListener()
   	   {

 		@Override
 		public void actionPerformed(ActionEvent arg0) {
 		
 			currentChoice=1;
 		}
         	
   	   });
        
        myPolygon1.addActionListener(new ActionListener()
   	   {

 		@Override
 		public void actionPerformed(ActionEvent arg0) {
 		
 			currentChoice=2;
 		}
         	
   	   });
        
       
        jtf.addFocusListener(new FocusListener(){

			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				if (arg0.getSource() == jtf) {
		           
		        }
			}

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				bianshu= jtf.getText();
			}
    		
    	});
        circle.addActionListener(new ActionListener()
 	   {

		@Override
		public void actionPerformed(ActionEvent arg0) {
		
			currentChoice=4;
		}
       	
 	   });
        fillCircle.addActionListener(new ActionListener()
  	   {

 		@Override
 		public void actionPerformed(ActionEvent arg0) {
 		
 			currentChoice=10;
 		}
        	
  	   });
        rect.addActionListener(new ActionListener()
  	   {

 		@Override
 		public void actionPerformed(ActionEvent arg0) {
 		
 			currentChoice=5;
 		}
        	
  	   });
        
        rubber.addActionListener(new ActionListener()
   	   {

  		@Override
  		public void actionPerformed(ActionEvent arg0) {
  		
  			currentChoice=11;
  		}
         	
   	   });
       
        fillRect.addActionListener(new ActionListener()
   	   {

  		@Override
  		public void actionPerformed(ActionEvent arg0) {
  		
  			currentChoice=9;
  		}
         	
   	   });
         
        
        word.addActionListener(new ActionListener()
   	   {

  		@Override
  		public void actionPerformed(ActionEvent arg0) {
  		
  			currentChoice=6;
  		}
         	
   	   });
      
        
        
        smaller.addActionListener(new ActionListener()
    	   {

   		@Override
   		public void actionPerformed(ActionEvent arg0) {
   		
   			for(int i=0;i<=index;i++){
   				itemList[i].x1=itemList[i].x1>itemList[i].x2?itemList[i].x1-(int)(Math.abs(itemList[i].x1 - itemList[i].x2)/3):itemList[i].x1+(int)(Math.abs(itemList[i].x1 - itemList[i].x2)/3);
   			
   				itemList[i].y1=itemList[i].y1>itemList[i].y2?itemList[i].y1-(int)(Math.abs(itemList[i].y1 - itemList[i].y2)/3):itemList[i].y1+(int)(Math.abs(itemList[i].y1 - itemList[i].y2)/3);
   				
   			}
   			repaint();
   		}
          	
    	   });
        
        bigger.addActionListener(new ActionListener()
 	   {

		@Override
		public void actionPerformed(ActionEvent arg0) {
		
			for(int i=0;i<=index;i++){
				itemList[i].x1=itemList[i].x1>itemList[i].x2?itemList[i].x1+(int)(Math.abs(itemList[i].x1 - itemList[i].x2)/3):itemList[i].x1-(int)(Math.abs(itemList[i].x1 - itemList[i].x2)/3);
			
				itemList[i].y1=itemList[i].y1>itemList[i].y2?itemList[i].y1+(int)(Math.abs(itemList[i].y1 - itemList[i].y2)/3):itemList[i].y1-(int)(Math.abs(itemList[i].y1 - itemList[i].y2)/3);
				
			}
			repaint();
		}
       	
 	   });
        moveL.addActionListener(new ActionListener()
  	   {

    		@Override
    		public void actionPerformed(ActionEvent arg0) {
    		
    			for(int i=0;i<=index;i++){
    				itemList[i].x1=itemList[i].x1-50;
    			
    				itemList[i].x2=itemList[i].x2-50;
    				
    			}
    			repaint();
    		}
           	
     	   });
        
        moveR.addActionListener(new ActionListener()
   	   {

     		@Override
     		public void actionPerformed(ActionEvent arg0) {
     		
     			for(int i=0;i<=index;i++){
     				itemList[i].x1=itemList[i].x1+50;
     			
     				itemList[i].x2=itemList[i].x2+50;
     				
     			}
     			repaint();
     		}
            	
      	   });
        
        moveU.addActionListener(new ActionListener()
    	   {

      		@Override
      		public void actionPerformed(ActionEvent arg0) {
      		
      			for(int i=0;i<=index;i++){
      				itemList[i].y1=itemList[i].y1-50;
      			
      				itemList[i].y2=itemList[i].y2-50;
      				
      			}
      			repaint();
      		}
             	
       	   });
        
        moveD.addActionListener(new ActionListener()
 	   {

   		@Override
   		public void actionPerformed(ActionEvent arg0) {
   		
   			for(int i=0;i<=index;i++){
   				itemList[i].y1=itemList[i].y1+50;
   			
   				itemList[i].y2=itemList[i].y2+50;
   				
   			}
   			repaint();
   		}
          	
    	   });
        romateL.addActionListener(new ActionListener()
  	   {

       		@Override
       		public void actionPerformed(ActionEvent arg0) {
       		
       			for(int i=0;i<=index;i++){
       				int x = (itemList[i].x1+itemList[i].x2)/2;
       				int y = (itemList[i].y1+itemList[i].y2)/2;
       				double r=Math.sqrt((itemList[i].x1-itemList[i].x2)*(itemList[i].x1-itemList[i].x2)+(itemList[i].y1-itemList[i].y2)*(itemList[i].y1-itemList[i].y2))/2;
       				double arc = Math.PI/3;
       				 itemList[i].x1=new Integer((int) ((itemList[i].x1-x)*Math.cos(arc) +(itemList[i].y1-y)*Math.sin(arc)+x));
       				itemList[i].y1=new Integer((int) (-(itemList[i].x1-x)*Math.sin(arc) + (itemList[i].y1-y)*Math.cos(arc)+y));
       				itemList[i].x2=new Integer((int) ((itemList[i].x2-x)*Math.cos(arc) +(itemList[i].y2-y)*Math.sin(arc)+x));
       				itemList[i].y2=new Integer((int) (-(itemList[i].x2-x)*Math.sin(arc) + (itemList[i].y2-y)*Math.cos(arc)+y));
       				
       				
       			}
       			repaint();
       		}
              	
        	   });
        
        reback.addActionListener(new ActionListener()
  	   {

       		@Override
       		public void actionPerformed(ActionEvent arg0) {
       		
       			if(index>0){
       				index--;
       			}
       			repaint();
       		}
              	
        	   });
        
        coup.addActionListener(new ActionListener()
  	   {

       		@Override
       		public void actionPerformed(ActionEvent arg0) {
       		
       			for(int i=0;i<=index;i++){
       				itemList[i].x1=itemList[i].x1+(300*2-itemList[i].x2-itemList[i].x1)/2;
       			
       				itemList[i].x2=itemList[i].x2+(300*2-itemList[i].x2-itemList[i].x1)/2;
       			
       				
       			}
       			repaint();
       		}
              	
        	   });
        center.addActionListener(new ActionListener()
  	   {

       		@Override
       		public void actionPerformed(ActionEvent arg0) {
       		
       			for(int i=0;i<=index;i++){
       				itemList[i].x1=itemList[i].x1+(300*2-itemList[i].x2-itemList[i].x1)/2;
       			
       				itemList[i].x2=itemList[i].x2+(300*2-itemList[i].x2-itemList[i].x1)/2;
       				itemList[i].y1=itemList[i].y1+(300*2-itemList[i].y2-itemList[i].y1)/2;
           			
       				itemList[i].y2=itemList[i].y2+(300*2-itemList[i].y2-itemList[i].y1)/2;
       			
       				
       			}
       			repaint();
       		}
              	
        	   });
        statusBar.setText("     Welcome To The Little Drawing Pad!!!  :)");
        createNewItem();
        setSize(width, height);
        show();
    }
//��ť������ButtonHanler�࣬�ڲ��࣬��������������ť�Ĳ���
    public class ButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            for (int j = 3; j < choices.length - 3; j++) {
                if (e.getSource() == choices[j]) {
                    currentChoice = j;
                    createNewItem();
                    repaint();
                }
            }
        }
    }
//��ť������ButtonHanler1�࣬����������ɫѡ�񡢻��ʴ�ϸ���á��������밴ť�Ĳ���
    public class ButtonHandler1 implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == choices[choices.length - 3]) {
                chooseColor();
            }
            if (e.getSource() == choices[choices.length - 2]) {
                setStroke();
            }
            if (e.getSource() == choices[choices.length - 1]) {
                JOptionPane.showMessageDialog(null,
                        "Please hit the drawing pad to choose the word input position",
                        "Hint", JOptionPane.INFORMATION_MESSAGE);
                currentChoice = 14;
                createNewItem();
                repaint();
            }
        }
    }
//����¼�mouseA�࣬�̳���MouseAdapter��������������Ӧ�¼�����
    class mouseA extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
        	 itemList[index].bianshu=(bianshu.equals("")==false?Integer.parseInt(bianshu):2);
            statusBar.setText("     Mouse Pressed @:[" + e.getX() +
                    ", " + e.getY() + "]");//����״̬��ʾ
            itemList[index].x1 = itemList[index].x2 = e.getX();
            itemList[index].y1 = itemList[index].y2 = e.getY();
            //�����ǰѡ���ͼ������ʻ�������Ƥ�������������Ĳ���
            if (currentChoice == 1 || currentChoice == 11) {
                itemList[index].x1 = itemList[index].x2 = e.getX();
                itemList[index].y1 = itemList[index].y2 = e.getY();
                index++;
                createNewItem();
            }
            //�����ǰѡ���ͼ��ʽ�������룬������������
            if (currentChoice == 6) {
                itemList[index].x1 = e.getX();
                itemList[index].y1 = e.getY();
                String input;
                input = JOptionPane.showInputDialog(
                        "Please input the text you want!");
                itemList[index].s1 = input;
                itemList[index].x2 = f1;
                itemList[index].y2 = f2;
                itemList[index].s2 = style1;
                index++;
                createNewItem();
               
                currentChoice = 6;
                
                drawingArea.repaint();
            }
        }
        public void mouseReleased(MouseEvent e) {
        	 itemList[index].bianshu=(bianshu.equals("")==false?Integer.parseInt(bianshu):2);
            statusBar.setText("     Mouse Released @:[" + e.getX() +
                    ", " + e.getY() + "]");
            if (currentChoice == 1 || currentChoice == 11) {
                itemList[index].x1 = e.getX();
                itemList[index].y1 = e.getY();
            }
            itemList[index].x2 = e.getX();
            itemList[index].y2 = e.getY();
            index++;
            repaint();
            createNewItem();
           
            
        }
        
        public void mouseEntered(MouseEvent e) {
            statusBar.setText("     Mouse Entered @:[" + e.getX() +
                    ", " + e.getY() + "]");
        }
        public void mouseExited(MouseEvent e) {
            statusBar.setText("     Mouse Exited @:[" + e.getX() +
                    ", " + e.getY() + "]");
        }
        
    }
//����¼�mouseB��̳���MouseMotionAdapter�������������϶�������ƶ�ʱ����Ӧ����
    class mouseB extends MouseMotionAdapter {
        public void mouseDragged(MouseEvent e) {
        	 itemList[index].bianshu=(bianshu.equals("")==false?Integer.parseInt(bianshu):2);
            statusBar.setText("     Mouse Dragged @:[" + e.getX() +
                    ", " + e.getY() + "]");
            if (currentChoice == 1 || currentChoice == 11) {
                itemList[index - 1].x1 = itemList[index].x2 = itemList[index].x1 = e.getX();
                itemList[index - 1].y1 = itemList[index].y2 = itemList[index].y1 = e.getY();
                index++;
                createNewItem();
            } else {
                itemList[index].x2 = e.getX();
                itemList[index].y2 = e.getY();
            }
            repaint();
        }
        public void mouseMoved(MouseEvent e) {
            statusBar.setText("     Mouse Moved @:[" + e.getX() +
                    ", " + e.getY() + "]");
        }
    }
//ѡ��������ʱ���õ����¼��������࣬���뵽�������ѡ�����
    private class checkBoxHandler implements ItemListener {
        public void itemStateChanged(ItemEvent e) {
            if (e.getSource() == bold) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    f1 = Font.BOLD;
                } else {
                    f1 = Font.PLAIN;
                }
            }
            if (e.getSource() == italic) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    f2 = Font.ITALIC;
                } else {
                    f2 = Font.PLAIN;
                }
            }
        }
    }
//��ͼ����࣬������ͼ
    class DrawPanel extends JPanel {
        public DrawPanel() {
            setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
            setBackground(Color.white);
            addMouseListener(new mouseA());
            addMouseMotionListener(new mouseB());
        }
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;    //���廭��
            int j = 0;
            while (j <= index) {
                draw(g2d, itemList[j]);
                j++;
            }
        }
        void draw(Graphics2D g2d, drawings i) {
            i.draw(g2d);//�����ʴ��뵽���������У�������ɸ��ԵĻ�ͼ
            statusBar.setText(i.area+i.round);
        }
    }
//�½�һ����ͼ������Ԫ����ĳ����
    void createNewItem() {
        if (currentChoice == 14)//������Ӧ���α�����
        {
            drawingArea.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
        } else {
            drawingArea.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        }
       
        switch (currentChoice) {
            case 1:
                itemList[index] = new Pencil();
                break;
           
          
            case 2:
                itemList[index] = new MyPolygon1();
                break;
          
                
            case 4:
                itemList[index] = new Circle();
                break;
           
            case 5:
                itemList[index] = new Rect();
                break;
                
            case 6:
                itemList[index] = new Word();
                break;
                
            case 7:
                itemList[index] = new Line();
                break;
            case 8:
                itemList[index] = new fillPolygon();
                break;
            case 9:
                itemList[index] = new FillRect();
                break;
            case 10:
                itemList[index] = new FillCircle();
                break;
            case 11:
                itemList[index] = new Rubber();
                break;
           
      
           
        }
       // itemList[index].bianshu=6;
        itemList[index].type = currentChoice;
        itemList[index].R = R;
        itemList[index].G = G;
        itemList[index].B = B;
        itemList[index].stroke = stroke;
        jPanelItems[index] = new JPanel(); 
        
        
     
        
        //jPanelItems[index].add(itemList[index]);
      // ZX x.,m RotateImage.Rotate(jPanelItems[index], 50);
       // itemList[index].
    }
//ѡ��ǰ��ɫ�����
    public void chooseColor() {
        color = JColorChooser.showDialog(Main.this,
                "Choose a color", color);
        R = color.getRed();
        G = color.getGreen();
        B = color.getBlue();
        itemList[index].R = R;
        itemList[index].G = G;
        itemList[index].B = B;
    }
//ѡ��ǰ������ϸ�����
    public void setStroke() {
        String input;
        input = JOptionPane.showInputDialog(
                "Please input a float stroke value! ( >0 )");
        stroke = Float.parseFloat(input);
        itemList[index].stroke = stroke;
    }
//����ͼ���ļ������
    public void saveFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.CANCEL_OPTION) {
            return;
        }
        File fileName = fileChooser.getSelectedFile();
        fileName.canWrite();
        if (fileName == null || fileName.getName().equals("")) {
            JOptionPane.showMessageDialog(fileChooser, "Invalid File Name",
                    "Invalid File Name", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                fileName.delete();
                FileOutputStream fos = new FileOutputStream(fileName);
                output = new ObjectOutputStream(fos);
                drawings record;
                output.writeInt(index);
                for (int i = 0; i < index; i++) {
                    drawings p = itemList[i];
                    output.writeObject(p);
                    output.flush();    //������ͼ����Ϣǿ��ת���ɸ������Ի��洢���ļ���
                }
                output.close();
                fos.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }
//��һ��ͼ���ļ������
    public void loadFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.CANCEL_OPTION) {
            return;
        }
        File fileName = fileChooser.getSelectedFile();
        fileName.canRead();
        if (fileName == null || fileName.getName().equals("")) {
            JOptionPane.showMessageDialog(fileChooser, "Invalid File Name",
                    "Invalid File Name", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                FileInputStream fis = new FileInputStream(fileName);
                input = new ObjectInputStream(fis);
                drawings inputRecord;
                int countNumber = 0;
                countNumber = input.readInt();
                int len = index+countNumber;
               //index++;
                for (; index < len; index++) {
                    inputRecord = (drawings) input.readObject();
                    itemList[index] = inputRecord;
                }
                createNewItem();
                input.close();
                repaint();
            } catch (EOFException endofFileException) {
                JOptionPane.showMessageDialog(this, "no more record in file",
                        "class not found", JOptionPane.ERROR_MESSAGE);
            } catch (ClassNotFoundException classNotFoundException) {
                JOptionPane.showMessageDialog(this, "Unable to Create Object",
                        "end of file", JOptionPane.ERROR_MESSAGE);
            } catch (IOException ioException) {
                JOptionPane.showMessageDialog(this, "error during read from file",
                        "read Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
//�½�һ���ļ������
    public void newFile() {
        index = 0;
        currentChoice = 3;
        color = Color.black;
        stroke = 1.0f;
        createNewItem();
        repaint();//���й�ֵ����Ϊ��ʼ״̬�������ػ�
    }
//��������
    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }//����������Ϊ��ǰwindows���
        Main newPad = new Main();
        newPad.addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }
                });
    }
}