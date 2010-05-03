/*
 *dnd是drag and drop的缩写.
 *java中的dnd主要涉及到3个类:TransferHandler(用来处理数据的拖放过程),
 *Transferable(用来包装拖放的数据),和DataFlavor(用来表示拖放的数据的类型).
 */
package dnd;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.TransferHandler;

/**
 * TransferHandler(String property) 构造一个通过剪贴板或拖放操作可以将 Java Bean
 * 属性从一个组件传输到另一个组件的传输处理程序。 如,JLabel和JTextField都有text<br/>
 * 这个属性,所以可以很简单地实现从JTextField里 拖文本到JLabel里 ,改变它的文本.<br/>
 * 下面是一个例子 在textField里输入文本后,往label里拖,label的文本就变为textField<br/>
 * 里的文本了. 如果要实现从label往textField里拖 ,还要另外的方法,先不说
 */
public final class LabelDND {

	private JFrame mainFrame = null;
	private JPanel panel = null;
	private JLabel label = null;
	private JTextField textField = null;

	private JColorChooser colorChooser = null;

	public LabelDND() {
		mainFrame = new JFrame();

		panel = new JPanel(new BorderLayout());

		colorChooser = new JColorChooser();
		colorChooser.setDragEnabled(true);

		label = new JLabel("可以拖到txt框");

		// 这里调用了TransferHandler的第二个构造函数,参数是一个Java Bean 属性
		label.setTransferHandler(new TransferHandler("text"));
		// label.setTransferHandler(new TransferHandler("foreground"));

		label.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				JComponent c = (JComponent) e.getSource();
				TransferHandler handler = c.getTransferHandler();
				handler.exportAsDrag(c, e, TransferHandler.COPY);// 调用了exportAsDrag
			}
		});

		textField = new JTextField(20);
		// 打开textField自带的拖放功能
		textField.setDragEnabled(true);
		panel.add(label, BorderLayout.PAGE_START);
		panel.add(colorChooser, BorderLayout.CENTER);
		panel.add(textField, BorderLayout.PAGE_END);
		mainFrame.getContentPane().add(panel);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.pack();
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
	}

	public static void main(String[] args) {
		new LabelDND();
	}
}
