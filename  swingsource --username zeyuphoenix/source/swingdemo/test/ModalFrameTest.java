package test;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.UIManager;

public class ModalFrameTest {
    public static void main(String[] args){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e){
            e.printStackTrace();
        }
        final JFrame mainFrame = new JFrame(
                "Are   you   missing   maximize   button   in   JDialog   ??   -   santhosh@in.fiorano.com");
        mainFrame
                .getContentPane()
                .add(
                        new JScrollPane(
                                new JTextArea(
                                        "this   is   simle   text   area.    you   won't   be   able   to   edit   me   while   modal   frame   is   visible...")));
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(400, 300);
        Action action = new AbstractAction("Show   Modal   Frame...") {
            /**
             * 
             */
            private static final long serialVersionUID = 1L;

            public void actionPerformed(ActionEvent e){
                JFrame frame = new JFrame();
                frame.getContentPane().add(new JScrollPane(new JTree()));
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setSize(300, 400);
                ModalFrameUtil.showAsModal(frame, mainFrame);
                // this statement will be executed only after
                // the modal frame is closed
                JOptionPane.showMessageDialog(mainFrame,
                        "modal   frame   closed.");
            }
        };
        mainFrame.getContentPane().add(new JButton(action), BorderLayout.SOUTH);
        mainFrame.setVisible(true);
    }
}
