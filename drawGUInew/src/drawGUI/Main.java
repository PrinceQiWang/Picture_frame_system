package drawGUI;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


import java.io.*;
//定义画图的基本图形单元
public class Main extends JFrame //主类，扩展了JFrame类，用来生成主界面
{
    private ObjectInputStream input;
    private ObjectOutputStream output; //定义输入输出流，用来调用和保存图像文件
    private JButton choices[];   //按钮数组，存放以下名称的功能按钮
    private JPanel panel = new JPanel();   
    private String names[] = {
        "New",
        "Open",
        "Save", //这三个是基本操作按钮，包括"新建"、"打开"、"保存"
        /*接下来是我们的画图板上面有的基本的几个绘图单元按钮*/
        "Pencil", //铅笔画，也就是用鼠标拖动着随意绘图
        "Line", //绘制直线
        "Rect", //绘制空心矩形
        "fRect", //绘制以指定颜色填充的实心矩形
        "Oval", //绘制空心椭圆
        "fOval", //绘制以指定颜色填充的实心椭圆
        "Circle", //绘制圆形
        "fCircle", //绘制以指定颜色填充的实心圆形
        "RoundRect", //绘制空心圆角矩形
        "frRect", //绘制以指定颜色填充的实心圆角矩形
        "Rubber", //橡皮擦，可用来擦去已经绘制好的图案
        "Color", //选择颜色按钮，可用来选择需要的颜色
        "Stroke", //选择线条粗细的按钮，输入需要的数值可以实现绘图线条粗细的变化
        "Word"      //输入文字按钮，可以在绘图板上实现文字输入
    };
    private String styleNames[] = {
        " 宋体 ", " 隶书 ", " 华文彩云 ", " 仿宋_GB2312 ", " 华文行楷 ",
        " 方正舒体 ", " Times New Roman ", " Serif ", " Monospaced ",
        " SonsSerif ", " Garamond "
    };            //可供选择的字体项
    //当然这里的灵活的结构可以让读者自己随意添加系统支持的字体
    private Icon items[];
    private String tipText[] = {
        //这里是鼠标移动到相应按钮上面上停留时给出的提示说明条
        //读者可以参照上面的按钮定义对照着理解
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
    JToolBar buttonPanel;              //定义按钮面板
    private JLabel statusBar;   
    private JLabel statusBar2; 
    private DrawPanel drawingArea;       //画图区域
    private int width = 800,  height = 550;    //定义画图区域初始大小
    drawings[] itemList = new drawings[5000]; //用来存放基本图形的数组
    JPanel [] jPanelItems =  new JPanel[5000];
    private int currentChoice = 1;            //设置默认画图状态为随笔画
    int index = 0;                         //当前已经绘制的图形数目
    private Color color = Color.black;     //当前画笔颜色
    int R, G, B;                           //用来存放当前色彩值
    int f1, f2;                  //用来存放当前字体风格
    String style1;              //用来存放当前字体
    private float stroke = 1.0f;  //设置画笔粗细，默认值为1.0f
    JCheckBox bold, italic;      //定义字体风格选择框
    //bold为粗体，italic为斜体，二者可以同时使用
    JComboBox styles;
    String bianshu="0";
    String area ="";
    String zhouchang ="";
    JPanel areaAndRound = new JPanel();
    public Main() //构造函数
    {
        super("Drawing Pad");
        JMenuBar bar = new JMenuBar();      //定义菜单条
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic('F');
//新建文件菜单条
        JMenuItem newItem = new JMenuItem("New");
        newItem.setMnemonic('N');
        newItem.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        newFile();      //如果被触发，则调用新建文件函数段
                    }
                });
        fileMenu.add(newItem);
//保存文件菜单项
        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.setMnemonic('S');
        saveItem.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        saveFile();     //如果被触发，则调用保存文件函数段
                    }
                });
        fileMenu.add(saveItem);
//打开文件菜单项
        JMenuItem loadItem = new JMenuItem("Load");
        loadItem.setMnemonic('L');
        loadItem.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        loadFile();     //如果被触发，则调用打开文件函数段
                    }
                });
        fileMenu.add(loadItem);
        fileMenu.addSeparator();
//退出菜单项
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.setMnemonic('X');
        exitItem.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        System.exit(0); //如果被触发，则退出画图板程序
                    }
                });
        fileMenu.add(exitItem);
        bar.add(fileMenu);
//设置颜色菜单条
        JMenu colorMenu = new JMenu("Color");
        colorMenu.setMnemonic('C');
//选择颜色菜单项
        JMenuItem colorItem = new JMenuItem("Choose Color");
        colorItem.setMnemonic('O');
        colorItem.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        chooseColor();  //如果被触发，则调用选择颜色函数段
                    }
                });
        colorMenu.add(colorItem);
        bar.add(colorMenu);
//设置线条粗细菜单条
        JMenu strokeMenu = new JMenu("Stroke");
        strokeMenu.setMnemonic('S');
//设置线条粗细菜单项
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
//设置提示菜单条
       
       

        items = new ImageIcon[names.length];
//创建各种基本图形的按钮
        drawingArea = new DrawPanel();
        choices = new JButton[names.length];
        buttonPanel = new JToolBar(JToolBar.VERTICAL);
        buttonPanel = new JToolBar(JToolBar.HORIZONTAL);
        ButtonHandler handler = new ButtonHandler();
        ButtonHandler1 handler1 = new ButtonHandler1();
//导入我们需要的图形图标，这些图标都存放在与源文件相同的目录下面
        for (int i = 0; i < choices.length; i++) {//items[i]=new ImageIcon( MiniDrawPad.class.getResource(names[i] +".gif"));
            //如果在jbuilder下运行本程序，则应该用这条语句导入图片
            items[i] = new ImageIcon(names[i] + ".gif");
            //默认的在jdk或者jcreator下运行，用此语句导入图片
            choices[i] = new JButton("", items[i]);
            choices[i].setToolTipText(tipText[i]);
            buttonPanel.add(choices[i]);
        }
//将动作侦听器加入按钮里面
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
//字体风格选择
        styles = new JComboBox(styleNames);
        styles.setMaximumRowCount(8);
        styles.addItemListener(
                new ItemListener() {
                    public void itemStateChanged(ItemEvent e) {
                        style1 = styleNames[styles.getSelectedIndex()];
                    }
                });
//字体选择
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
//按钮侦听器ButtonHanler类，内部类，用来侦听基本按钮的操作
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
//按钮侦听器ButtonHanler1类，用来侦听颜色选择、画笔粗细设置、文字输入按钮的操作
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
//鼠标事件mouseA类，继承了MouseAdapter，用来完成鼠标相应事件操作
    class mouseA extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
        	 itemList[index].bianshu=(bianshu.equals("")==false?Integer.parseInt(bianshu):2);
            statusBar.setText("     Mouse Pressed @:[" + e.getX() +
                    ", " + e.getY() + "]");//设置状态提示
            itemList[index].x1 = itemList[index].x2 = e.getX();
            itemList[index].y1 = itemList[index].y2 = e.getY();
            //如果当前选择的图形是随笔画或者橡皮擦，则进行下面的操作
            if (currentChoice == 1 || currentChoice == 11) {
                itemList[index].x1 = itemList[index].x2 = e.getX();
                itemList[index].y1 = itemList[index].y2 = e.getY();
                index++;
                createNewItem();
            }
            //如果当前选择的图形式文字输入，则进行下面操作
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
//鼠标事件mouseB类继承了MouseMotionAdapter，用来完成鼠标拖动和鼠标移动时的相应操作
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
//选择字体风格时候用到的事件侦听器类，加入到字体风格的选择框中
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
//画图面板类，用来画图
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
            Graphics2D g2d = (Graphics2D) g;    //定义画笔
            int j = 0;
            while (j <= index) {
                draw(g2d, itemList[j]);
                j++;
            }
        }
        void draw(Graphics2D g2d, drawings i) {
            i.draw(g2d);//将画笔传入到各个子类中，用来完成各自的绘图
            statusBar.setText(i.area+i.round);
        }
    }
//新建一个画图基本单元对象的程序段
    void createNewItem() {
        if (currentChoice == 14)//进行相应的游标设置
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
//选择当前颜色程序段
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
//选择当前线条粗细程序段
    public void setStroke() {
        String input;
        input = JOptionPane.showInputDialog(
                "Please input a float stroke value! ( >0 )");
        stroke = Float.parseFloat(input);
        itemList[index].stroke = stroke;
    }
//保存图形文件程序段
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
                    output.flush();    //将所有图形信息强制转换成父类线性化存储到文件中
                }
                output.close();
                fos.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }
//打开一个图形文件程序段
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
//新建一个文件程序段
    public void newFile() {
        index = 0;
        currentChoice = 3;
        color = Color.black;
        stroke = 1.0f;
        createNewItem();
        repaint();//将有关值设置为初始状态，并且重画
    }
//主函数段
    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }//将界面设置为当前windows风格
        Main newPad = new Main();
        newPad.addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }
                });
    }
}
