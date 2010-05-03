/*
 * FolderPaneDemo.java
 *
 * Created on June 8, 2007, 8:53 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package curtainpane.test;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.UIManager;

import curtainpane.CurtainPane;
public class CurtainPaneDemo extends JFrame{
    public CurtainPaneDemo(){
        setTitle("java_source");
        setIconImage(
                Toolkit.getDefaultToolkit()
                .getImage(CurtainPaneDemo.
                class.getResource("title.png")));
        
        initComponent();
        
        setSize(200,400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    private void initComponent(){
        CurtainPane cp=getCurtainPane();
        cp.setAnimated(true);
        add(cp, BorderLayout.CENTER);
        
        JCheckBox box = getAnimationCheckBox(cp);
        add(box, BorderLayout.SOUTH);
    }
    private CurtainPane getCurtainPane(){
        CurtainPane fp=new CurtainPane();
        fp.setAnimated(true);
        fp.addPane("File and Folder Task", getIcon("task.gif"), getFileFolderPane());
        fp.addPane("Other Places", getIcon("other.gif"), getOtherPlacePane());
        fp.addPane("Details", getIcon("detail.gif"), getDetailsPane());
        return fp;
    }
    private Icon getIcon(String iconURL){
        return new ImageIcon(getClass().getResource(iconURL));
    }
    private ListPane getDetailsPane() {
        ListPane p=new ListPane();
        p.addItem("<html><b>java_source</b><br>Folder</html>",null);
        p.addItem("<html>Modify Date: Nov. 8th, 2001, <br>22:39</html>",null);
        p.setSize(185,74);
        return p;
    }
    
    private ListPane getOtherPlacePane() {
        ListPane p=new ListPane();
        p.addItem("System (C:)","drive.png");
        p.addItem("My Documents","mydoc.png");
        p.addItem("Shared Documents","shareddoc.png");
        p.addItem("My Computer","mycom.png");
        p.addItem("Network Neighbor","neighbor.png");
        p.setSize(185,117);
        return p;
    }
    
    private ListPane getFileFolderPane() {
        ListPane p=new ListPane();
        p.addItem("Create a new folder","newfolder.png");
        p.addItem("Publish to the Web","internet.png");
        p.addItem("Share the folder","share.png");
        p.setSize(185,86);
        return p;
    }
    
    private JCheckBox getAnimationCheckBox(final CurtainPane cp) {
        final JCheckBox box=new JCheckBox("Enable Animation");
        box.setSelected(true);
        box.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cp.setAnimated(box.isSelected());
            }
        });
        return box;
    }
    public static void main(String[]args){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {}
        EventQueue.invokeLater(new Runnable(){
            public void run() {
                new CurtainPaneDemo().setVisible(true);
            }
        });
    }
}
