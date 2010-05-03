/*
 * FolderPaneDemo.java
 *
 * Created on June 8, 2007, 8:53 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package folderedpane.test;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

import folderedpane.FolderPane;

/**
 * 折叠面板的演示程序
 * 编译生成foldered_pane.jar后就可以双击观看效果
 * 窗口底部有个Enable Animation的JCheckBox，选中能出现动画推拉和淡出效果
 *
 * @author William Chen
 * @mail rehte@hotmail.com
 */
public class FolderPaneDemo extends JFrame{
    public FolderPaneDemo(){
        setTitle("java_source");
        setIconImage(
                Toolkit.getDefaultToolkit()
                .getImage(FolderPaneDemo.
                class.getResource("/folderedpane/test/title.png")));
        
        initComponent();
        
        setSize(238,510);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    private void initComponent(){
        FolderPane fp = getFolderPane();
        //添加到JScrollPane
        JScrollPane jsp=new JScrollPane(fp);
        //为了好看，Viewport背景设置成FolderPane的背景。这儿可以自己控制
        jsp.getViewport().setBackground(fp.getBackground());
        add(jsp, BorderLayout.CENTER);
        
        JCheckBox box = getAnimationCheckBox(fp);
        add(box, BorderLayout.SOUTH);
    }

    private FolderPane getFolderPane() {
        FolderPane fp=new FolderPane();
        fp.setAnimated(true);
        fp.addFolder("文件和文件夹任务", getFileFolderPane());
        fp.addFolder("其他位置", getOtherPlacePane());
        fp.addFolder("详细信息", getDetailsPane());
        return fp;
    }

    private ListPane getDetailsPane() {
        ListPane p=new ListPane();
        p.addItem("<html><b>java_source</b><br>文件夹</html>",null);
        p.addItem("<html>修改日期: 2001年11月8日,<br>22:39</html>",null);
        p.setSize(185,74);
        return p;
    }

    private ListPane getOtherPlacePane() {
        ListPane p=new ListPane();
        p.addItem("System (C:)","drive.png");
        p.addItem("我的文档","mydoc.png");
        p.addItem("共享文档","shareddoc.png");
        p.addItem("我的电脑","mycom.png");
        p.addItem("网络邻居","neighbor.png");
        p.setSize(185,117);
        return p;
    }

    private ListPane getFileFolderPane() {
        ListPane p=new ListPane();
        p.addItem("创建一个新文件夹","/folderedpane/test/newfolder.png");
        p.addItem("将这个文件夹发布到Web","/folderedpane/test/internet.png");
        p.addItem("共享此文件夹","/folderedpane/test/share.png");
        p.setSize(185,86);
        return p;
    }

    private JCheckBox getAnimationCheckBox(final FolderPane fp) {
        final JCheckBox box=new JCheckBox("Enable Animation");
        box.setSelected(true);
        box.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fp.setAnimated(box.isSelected());
            }
        });
        return box;
    }
    public static void main(String[]args){
        try {
            System.setProperty("swing.useSystemFontSettings", "false");
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {}
        EventQueue.invokeLater(new Runnable(){
            public void run() {
                new FolderPaneDemo().setVisible(true);
            }
        });
    }
}
