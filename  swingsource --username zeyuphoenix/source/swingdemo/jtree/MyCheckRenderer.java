package jtree;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.tree.TreeCellRenderer;

/**
 * the tree renderer that I rewrite it.
 * 
 * @author zeyuphoenix <br>
 *         daily jtreetest MyTreeRenderer.java <br>
 *         2008 2008/03/25 17:41:12 <br>
 */
public class MyCheckRenderer extends JPanel implements TreeCellRenderer {
	/**
	 * UID.
	 */
	private static final long serialVersionUID = 1L;
	/** check box in tree node. */
	private JCheckBox checkBox = null;
	/** label text in tree node. */
	private TreeLabel labelText = null;

	/**
	 * Returns a new instance of DefaultTreeCellRenderer.
	 */
	public MyCheckRenderer() {
		setLayout(null);
		add(checkBox = new JCheckBox());
		add(labelText = new TreeLabel());
		checkBox.setBackground(UIManager.getColor("Tree.textBackground"));
		labelText.setForeground(UIManager.getColor("Tree.textForeground"));
	}

	/**
	 * Configures the renderer based on the passed in components. The value is
	 * set from messaging the tree with <code>convertValueToText</code>,
	 * which ultimately invokes <code>toString</code> on <code>value</code>.
	 * The foreground color is set based on the selection and the icon is set
	 * based on the <code>leaf</code> and <code>expanded</code> parameters.
	 */
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean isSelected, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {
		String stringValue = tree.convertValueToText(value, isSelected,
				expanded, leaf, row, hasFocus);
		setEnabled(tree.isEnabled());
		checkBox.setSelected(((MyTreeNode) value).isSelected());
		labelText.setFont(tree.getFont());
		labelText.setText(stringValue);
		labelText.setSelected(isSelected);
		labelText.setFocus(hasFocus);
		if (leaf) {
			labelText.setIcon(UIManager.getIcon("Tree.leafIcon"));
		} else if (expanded) {
			labelText.setIcon(UIManager.getIcon("Tree.openIcon"));
		} else {
			labelText.setIcon(UIManager.getIcon("Tree.closedIcon"));
		}
		return this;
	}

	/**
	 * set select node's prefer size.
	 * 
	 * @return size.
	 */
	public Dimension getPreferredSize() {
		Dimension d_check = checkBox.getPreferredSize();
		Dimension d_label = labelText.getPreferredSize();
		return new Dimension(d_check.width + d_label.width,
				(d_check.height < d_label.height ? d_label.height
						: d_check.height));
	}

	/**
	 * set tree select node layout.
	 */
	public void doLayout() {
		Dimension d_check = checkBox.getPreferredSize();
		Dimension d_label = labelText.getPreferredSize();
		int y_check = 0;
		int y_label = 0;
		if (d_check.height < d_label.height) {
			y_check = (d_label.height - d_check.height) / 2;
		} else {
			y_label = (d_check.height - d_label.height) / 2;
		}
		checkBox.setLocation(0, y_check);
		checkBox.setBounds(0, y_check, d_check.width, d_check.height);
		labelText.setLocation(d_check.width, y_label);
		labelText.setBounds(d_check.width, y_label, d_label.width,
				d_label.height);
	}

	/**
	 * set select background.
	 * 
	 * @param color
	 *            back color.
	 */
	public void setBackground(Color color) {
		if (color instanceof ColorUIResource) {
			color = null;
		}
		super.setBackground(color);
	}

	/**
	 * the label in the tree.
	 * 
	 * @author zeyuphoenix <br>
	 *         daily jtreetest MyTreeRenderer.java <br>
	 *         2008 2008/03/25 17:53:32 <br>
	 */
	private class TreeLabel extends JLabel {
		/**
		 * UID
		 */
		private static final long serialVersionUID = 1L;
		/** is select. */
		private boolean isSelected = false;
		/** is have focus. */
		private boolean hasFocus = false;

		/**
		 * the label in the tree.
		 */
		public TreeLabel() {
		}

		/**
		 * set label background.
		 * 
		 * @param color
		 *            back color.
		 */
		public void setBackground(Color color) {
			if (color instanceof ColorUIResource) {
				color = null;
			}
			super.setBackground(color);
		}

		/**
		 * repaint the label.
		 * 
		 * @param g
		 *            Graphics.
		 */
		public void paint(Graphics g) {
			String str;
			if ((str = getText()) != null) {
				if (0 < str.length()) {
					if (isSelected) {
						g.setColor(UIManager
								.getColor("Tree.selectionBackground"));
					} else {
						g.setColor(UIManager.getColor("Tree.textBackground"));
					}
					Dimension d = getPreferredSize();
					int imageOffset = 0;
					Icon currentI = getIcon();
					if (currentI != null) {
						imageOffset = currentI.getIconWidth()
								+ Math.max(0, getIconTextGap() - 1);
					}
					g.fillRect(imageOffset, 0, d.width - 1 - imageOffset,
							d.height);
					if (hasFocus) {
						g.setColor(UIManager
								.getColor("Tree.selectionBorderColor"));
						g.drawRect(imageOffset, 0, d.width - 1 - imageOffset,
								d.height - 1);
					}
				}
			}
			super.paint(g);
		}

		/**
		 * set label prefer size.
		 * 
		 * @return size.
		 */
		public Dimension getPreferredSize() {
			Dimension retDimension = super.getPreferredSize();
			if (retDimension != null) {
				retDimension = new Dimension(retDimension.width + 3,
						retDimension.height);
			}
			return retDimension;
		}

		/**
		 * set the label text is select.
		 * 
		 * @param isSelected
		 *            is label select.
		 */
		public void setSelected(boolean isSelected) {
			this.isSelected = isSelected;
		}

		/**
		 * set the label text have focus.
		 * 
		 * @param hasFocus
		 *            is have focus
		 */
		public void setFocus(boolean hasFocus) {
			this.hasFocus = hasFocus;
		}
	}

}
