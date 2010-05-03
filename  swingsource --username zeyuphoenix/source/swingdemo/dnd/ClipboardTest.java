package dnd;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ClipboardTest {
	JFrame mainFrame;
	JPanel mainPanel;
	JButton button;
	Clipboard cb;// 定义一个剪贴板

	public ClipboardTest() {
		mainFrame = new JFrame();
		mainPanel = new JPanel();
		button = new JButton("Button");
		button.setIcon(new ImageIcon("candle.png"));

		cb = Toolkit.getDefaultToolkit().getSystemClipboard();// 取得系统的剪贴板
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ButtonTextAndImageTransferable btait = new ButtonTextAndImageTransferable(
						button);
				// 设置剪贴板的内容,第一个参数是Transferable类型的,第二个是ClipboardOwner
				cb.setContents(btait, btait);
			}
		});

		mainPanel.add(button);
		mainFrame.getContentPane().add(mainPanel);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.pack();
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
	}

	public static void main(String[] args) {
		new ClipboardTest();
	}
}

class ButtonTextAndImageTransferable extends ImageIcon implements Transferable,
		ClipboardOwner {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	DataFlavor[] flavors;
	JButton button;

	public void lostOwnership(Clipboard clipboard, Transferable contents) {
		System.out.println("lostownership");// ClipboardOwner里的唯一的方法
	}

	public ButtonTextAndImageTransferable(JButton button) {
		flavors = new DataFlavor[2];
		flavors[0] = DataFlavor.stringFlavor;
		flavors[1] = DataFlavor.imageFlavor;
		this.button = button;
	}// 这个数组说明我们的Transferable既有文字,又有图片

	public DataFlavor[] getTransferDataFlavors() {
		return flavors;
	}

	public Object getTransferData(DataFlavor flavor) {
		if (flavor.equals(flavors[0]))// 根据参数决定返回的数据
		{
			return button.getText();
		} else {
			if (flavor.equals(flavors[1])) {
				ImageIcon icon = (ImageIcon) button.getIcon();
				return icon.getImage();
			}
		}
		return null;
	}

	public boolean isDataFlavorSupported(DataFlavor flavor) {
		if (flavor.equals(flavors[0]) || flavor.equals(flavors[1]))
			return true;
		return false;
	}

}
