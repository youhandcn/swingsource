package dnd;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.TransferHandler;

public class PictureDnd {
	JFrame mainFrame;
	JPanel mainPanel;
	PictureComponent[] pictures;

	public PictureDnd() {
		mainFrame = new JFrame();
		mainPanel = new JPanel(new GridLayout(2, 2));
		// PictureComponent是一个自定义的类
		pictures = new PictureComponent[4];
		pictures[0] = new PictureComponent(new ImageIcon("images/Adele.jpg")
				.getImage());
		pictures[1] = new PictureComponent(new ImageIcon("images/Anya.jpg")
				.getImage());
		pictures[2] = new PictureComponent(null);
		pictures[3] = new PictureComponent(null);
		mainPanel.add(pictures[0]);
		mainPanel.add(pictures[1]);
		mainPanel.add(pictures[2]);
		mainPanel.add(pictures[3]);
		mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		mainFrame.getContentPane().add(mainPanel);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(350, 400);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
	}

	private class PictureComponent extends JComponent implements FocusListener,
			MouseListener, MouseMotionListener {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		Image image;

		public PictureComponent(Image image) {
			this.image = image;
			setPreferredSize(new Dimension(125, 125));
			setFocusable(true);
			setTransferHandler(new PictureTransferHandler());
			addFocusListener(this);
			addMouseListener(this);
			addMouseMotionListener(this);
		}

		public Image getImage() {
			return image;
		}

		public void setImage(Image image) {
			this.image = image;
			repaint();
		}

		// 绘制我们的类,简单的在上面画一幅图
		public void paintComponent(Graphics graphics) {
			Graphics g = graphics.create();
			g.setColor(Color.white);
			g.fillRect(0, 0, image != null ? image.getWidth(this) : 125,
					image != null ? image.getHeight(this) : 125);
			if (image != null)
				g.drawImage(image, 0, 0, this);
			if (isFocusOwner())
				g.setColor(Color.red);
			else
				g.setColor(Color.black);
			// 画边框,如果是焦点获得者,边框为红色,否则为黑色
			g.drawRect(0, 0, image != null ? image.getWidth(this) : 125,
					image != null ? image.getHeight(this) : 125);
			g.dispose();
		}

		public void focusGained(FocusEvent e) {
			repaint();
		}

		public void focusLost(FocusEvent e) {
			repaint();
		}

		public void mouseClicked(MouseEvent e) {
			requestFocusInWindow();
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}

		public void mousePressed(MouseEvent e) {
		}

		public void mouseReleased(MouseEvent e) {
		}

		public void mouseDragged(MouseEvent e) {
			JComponent c = (JComponent) e.getSource();
			TransferHandler handler = c.getTransferHandler();
			// 调用exportAsDrag
			handler.exportAsDrag(c, e, TransferHandler.COPY);
		}

		public void mouseMoved(MouseEvent e) {
		}

	}

	private class TransferablePicture implements Transferable {// 一个用来包装数据的类
		// 我们的类只包含有图片类型的数据.DataFlavor.imageFlavor
		// 是DataFlavor定义的一个DataFlavor类型,因为图片常用,所以它自带有这种类型,不用我们自己定义.以后再看如何自己定义
		DataFlavor flavors[] = { DataFlavor.imageFlavor };
		Image image;

		public TransferablePicture(Image image) {
			this.image = image;
		}

		public DataFlavor[] getTransferDataFlavors() {// 返回我们的Transferable包含哪些数据类型
			return flavors;
		}

		public Object getTransferData(DataFlavor flavor) {// 根据参数返回数据
			if (flavor.equals(DataFlavor.imageFlavor))
				return image;
			return null;
		}

		public boolean isDataFlavorSupported(DataFlavor flavor) {
			return flavor.equals(DataFlavor.imageFlavor);
		}

	}

	private class PictureTransferHandler extends TransferHandler {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public Transferable createTransferable(JComponent c) {
			PictureComponent pc = (PictureComponent) c;
			return new TransferablePicture(pc.getImage());// 调用构造函数以包装数据,将我们要包装的数据以参数形式传入
		}

		public boolean canImport(JComponent c, DataFlavor[] flavors) {// 判断是否可以导入数据
			for (DataFlavor flavor : flavors) {
				if (flavor.equals(DataFlavor.imageFlavor))
					return true;
			}
			return false;
		}

		public boolean importData(JComponent c, Transferable t) {// 导入数据
			if (canImport(c, t.getTransferDataFlavors())) {// 如前所说,调用了getTransferDataFlavors()
				PictureComponent pc = (PictureComponent) c;
				try {// 取得我们需要类型的的数据
					Image image = (Image) t
							.getTransferData(DataFlavor.imageFlavor);
					pc.setImage(image);
					return true;
				} catch (UnsupportedFlavorException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return false;
		}

		public void exportDone(JComponent c, Transferable data, int action) {
			// 传完数据以后,要判断是移动还是复制,然后决定要不要删除拖放源的数据
			PictureComponent picture = (PictureComponent) c;
			if (action == MOVE) {
				picture.setImage(null);
			}
		}

		public int getSourceActions(JComponent c) {
			return COPY_OR_MOVE;
		}
	}

	public static void main(String[] args) {
		new PictureDnd();
	}

}
