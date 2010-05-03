/*
 * ListPane.java
 *
 * Created on June 8, 2007, 9:17 PM
 */

package folderedpane.test;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/**
 * 模拟Windows文件浏览器左边折叠面板的一个面板组件
 * 其实就是一些JLabel
 *
 * @author  William Chen
 * @mail rehte@hotmail.com
 */
public class ListPane extends JPanel {
    private static final int HORZ_PAD=12;
    private static final int VERT_PAD=6;
    /**
     * Creates new form ListPane
     */
    public ListPane() {
        initComponents();
        Border b=BorderFactory.createCompoundBorder(
                new MyBorder(Color.WHITE),
                BorderFactory.createEmptyBorder(VERT_PAD,HORZ_PAD,VERT_PAD,HORZ_PAD));
        setBorder(b);
        
    }
    
    private void initComponents() {
        setLayout(new GridLayout(0, 1));
        setBackground(new Color(214, 223, 247));
    }
    //添加一项，按照标签添加
    public void addItem(String text, String iconURL){
        JLabel lblItem=new JLabel();
        if(iconURL!=null){
            lblItem.setIcon(new ImageIcon(getClass().getResource(iconURL)));
            lblItem.setForeground(new Color(33, 93, 198));
        }else
            lblItem.setForeground(Color.BLACK);
        lblItem.setText(text);
        add(lblItem);
    }
    //为了模仿的相似，自定义的一个Border
    class MyBorder extends LineBorder{
        public MyBorder(Color color) {
            super(color, 1, false);
        }
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Color oldColor = g.getColor();
            int i;
            g.setColor(lineColor);
            for(i = 0; i < thickness; i++)  {
                g.drawLine(x+i, y+i, x+i, height-i-i-1);
                g.drawLine(x+i, height-i-i-1, width-i-i-1, height-i-i-1);
                g.drawLine(width-i-i-1, y+i, width-i-i-1, height-i-i-1);
            }
            g.setColor(oldColor);
        }
    }
}
