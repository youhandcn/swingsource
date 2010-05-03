package test;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.net.URL;

import javax.swing.AbstractButton;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;

public class MyButton extends JButton {
    private static final long serialVersionUID = 39082560987930759L;
    // public static Color BUTTON_COLOR1 = new Color(205, 255, 205);
    // public static Color BUTTON_COLOR2 = new Color(51, 154, 47);
    private static final Color BUTTON_COLOR1 = new Color(216, 229, 252);
    private static final Color BUTTON_COLOR2 = new Color(255, 255, 255);
    private static final Color BUTTON_COLOR3 = new Color(193, 210, 238);

    private static final Color BORDER_COLOR3 = new Color(49, 106, 197);

    public static final Color BUTTON_FOREGROUND_COLOR = Color.white;
    public static final Color BUTTON_DEFAULT_FOREGROUND_COLOR = Color.black;
    private boolean bHover;
    private boolean bRect;// 默认绘制边框

    /**
     * 绘制默认不带边框的按钮
     * 
     * @param text
     * @param icon
     */
    public MyButton() {
        super("", null);
        bRect = false;
        init(LEFT);
    }

    public MyButton(String text, Icon icon) {
        super(text, icon);
        bRect = false;
        init(BOTTOM);
    }

    public MyButton(String text, int TextPosition, Icon icon) {
        super(text, icon);
        bRect = false;
        init(TextPosition);
    }

    /**
     * 指定跳转的functionID
     * 
     * @param text
     * @param icon
     * @param nFunctionID
     */
    public MyButton(String text, Icon icon, int nFunctionID) {
        super(text, icon);
        bRect = false;
        init(BOTTOM);
    }

    /**
     * 绘制是否默认带边框的按钮
     * 
     * @param text
     * @param icon
     * @param flag
     */
    public MyButton(String text, Icon icon, boolean flag) {
        super(text, icon);
        bRect = flag;
        init(BOTTOM);
    }

    private void init(int TextPosition) {
        this.setFont(new Font("宋体", Font.PLAIN, 13));
        setBorderPainted(false);
        this.setFocusPainted(false);
        setForeground(BUTTON_DEFAULT_FOREGROUND_COLOR);
        if (TextPosition == LEFT) {
            this.setVerticalTextPosition(BOTTOM);// 文字在右方
            this.setHorizontalAlignment(LEFT);
        } else {
            this.setVerticalTextPosition(JButton.BOTTOM);// 文字在下方
            this.setHorizontalTextPosition(JButton.CENTER);
        }
        setFocusPainted(false);
        setContentAreaFilled(false);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                bHover = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                bHover = false;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        int h = getHeight();
        int w = getWidth();
        float tran = 1F;
        if (!bHover) {
            tran = 0.3F;
        }
        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint p1;// 边框
        GradientPaint p2;
        GradientPaint gp;// 内部底色

        p1 = new GradientPaint(0, 0, BORDER_COLOR3, 0, h - 1, BORDER_COLOR3);
        if (getModel().isPressed()) {
            // 设置鼠标按下的效果
            p2 = new GradientPaint(0, 1, new Color(255, 255, 255, 100), 0,
                    h - 3, new Color(255, 255, 255, 40));
            gp = new GradientPaint(0.0F, 0.0F, BUTTON_COLOR1, 0.0F, h,
                    BUTTON_COLOR2, true);
            setForeground(BUTTON_DEFAULT_FOREGROUND_COLOR);// 设置菜单文本的颜色
        } else {
            p2 = new GradientPaint(0, 1, new Color(0, 0, 0, 40), 0, h - 3,
                    new Color(128, 128, 128, 100));
            gp = new GradientPaint(0.0F, 0.0F, BUTTON_COLOR3, 0.0F, h,
                    BUTTON_COLOR3, true);
            // setForeground(BUTTON_DEFAULT_FOREGROUND_COLOR);//设置菜单文本的颜色//这里占用cpu
        }
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                tran));
        RoundRectangle2D.Float r2d = new RoundRectangle2D.Float(0, 0, w - 1,
                h - 1, 10, 10);
        Shape clip = g2d.getClip();
        g2d.clip(r2d);

        if (bRect) {
            // 默认绘制边框
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, w, h);
            g2d.setClip(clip);
            g2d.setPaint(p1);
            g2d.drawRoundRect(0, 0, w - 1, h - 1, 10, 10);
            g2d.setPaint(p2);
            g2d.drawRoundRect(1, 1, w - 3, h - 3, 8, 8);
            setForeground(BUTTON_DEFAULT_FOREGROUND_COLOR);
        } else {
            if (bHover) {
                if (!getModel().isPressed()) {
                    setForeground(BUTTON_FOREGROUND_COLOR);
                }
                // 只有在mouseOn的时候才有边框
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
                g2d.setClip(clip);
                g2d.setPaint(p1);
                g2d.drawRoundRect(0, 0, w - 1, h - 1, 10, 10);
                g2d.setPaint(p2);
                g2d.drawRoundRect(0, 0, w - 1, h - 1, 10, 10);
            } else {
                // 鼠标按下没有释放然后移动出控件范围，恢复菜单文本的颜色
                setForeground(BUTTON_DEFAULT_FOREGROUND_COLOR);
            }
        }
        g2d.dispose();

        super.paintComponent(g);
    }

    public static void main(String s[]) {
        URL imageURL = MyButton.class
                .getResource("/resources/images/client/new/point.png");
        ImageIcon icon01 = new ImageIcon(imageURL);
        MyButton icon1 = new MyButton();
        icon1.setText("测试");
        icon1.setIcon(icon01);
        icon1.setVerticalTextPosition(AbstractButton.CENTER);
        icon1.setHorizontalTextPosition(AbstractButton.RIGHT);

        ImageIcon icon02 = new ImageIcon(imageURL);
        MyButton icon2 = new MyButton("测试菜单超长度", icon02, false);

        ImageIcon icon03 = new ImageIcon(imageURL);
        MyButton icon3 = new MyButton("测试按钮3", icon03);

        JPanel panel = new JPanel();
        icon2.setBackground(panel.getBackground());
        panel.setPreferredSize(new Dimension(300, 200));

        JTextField text = new JTextField();
        text.setColumns(15);

        panel.add(icon3);
        panel.add(text);
        JFrame frame = new JFrame();
        JToolBar toolBar = new JToolBar();
        toolBar.add(icon1);
        toolBar.add(icon2);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(toolBar, BorderLayout.NORTH);
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);

        System.out.println(Color.gray);
    }
}
