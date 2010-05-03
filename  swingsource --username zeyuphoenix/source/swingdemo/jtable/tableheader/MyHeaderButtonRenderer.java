package jtable.tableheader;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

/**
 * This interface defines the method required by any object that would like to
 * be a renderer for cells in a <code>JTable</code>.
 * 
 * @author zeyuphoenix <br>
 *         daily jtable.tableheadertest MyHeaderButtonRenderer.java <br>
 *         2008 2008/03/27 13:49:47 <br>
 */
public class MyHeaderButtonRenderer extends JButton implements
        TableCellRenderer, MouseListener {
    /**
     * UID.
     */
    private static final long serialVersionUID = 1L;
    /** no sorter. */
    public static final int NONE = 0;
    /** down sorter. */
    public static final int DOWN = 1;
    /** up sorter. */
    public static final int UP = 2;
    /** table header height. */
    private int headerHeight = 18;
    /** press column. */
    private int pushedColumn = 0;
    /** save the header state. */
    private Hashtable<Integer, Integer> state = null;
    /** none button. */
    private JButton noneButton = null;
    /** down button. */
    private JButton downButton = null;
    /** up button. */
    private JButton upButton = null;
    /** JTableHeader */
    private JTableHeader header = null;
    /** MyHeaderButtonRenderer. */
    private MyHeaderButtonRenderer renderer = null;
    /** table sorter. */
    private MyTableSorter sorter = null;

    /**
     * This interface defines the method required by any object that would like
     * to be a renderer for cells in a <code>JTable</code>.
     * 
     * @param header
     *            JTableHeader.
     * @param renderer
     *            MyHeaderButtonRenderer
     */
    public MyHeaderButtonRenderer(JTableHeader header,
            MyHeaderButtonRenderer renderer) {
        this(header, renderer, null);
    }

    /**
     * This interface defines the method required by any object that would like
     * to be a renderer for cells in a <code>JTable</code>.
     */
    public MyHeaderButtonRenderer() {
        this(null, null);
    }

    /**
     * This interface defines the method required by any object that would like
     * to be a renderer for cells in a <code>JTable</code>.
     */
    public MyHeaderButtonRenderer(JTableHeader header,
            MyHeaderButtonRenderer renderer, MyTableSorter sorter) {
        pushedColumn = -1;
        state = new Hashtable<Integer, Integer>();
        setMargin(new Insets(0, 0, 0, 0));
        setHorizontalTextPosition(LEFT);
        setIcon(new MyBlankIcon());
        this.header = header;
        this.renderer = renderer;
        this.sorter = sorter;
        init();
    }

    /**
     * Returns the component used for drawing the cell. This method is used to
     * configure the renderer appropriately before drawing.
     * 
     * @param table
     *            the <code>JTable</code> that is asking the renderer to draw;
     *            can be <code>null</code>
     * @param value
     *            the value of the cell to be rendered. It is up to the specific
     *            renderer to interpret and draw the value. For example, if
     *            <code>value</code> is the string "true", it could be
     *            rendered as a string or it could be rendered as a check box
     *            that is checked. <code>null</code> is a valid value
     * @param isSelected
     *            true if the cell is to be rendered with the selection
     *            highlighted; otherwise false
     * @param hasFocus
     *            if true, render cell appropriately. For example, put a special
     *            border on the cell, if the cell can be edited, render in the
     *            color used to indicate editing
     * @param row
     *            the row index of the cell being drawn. When drawing the
     *            header, the value of <code>row</code> is -1
     * @param column
     *            the column index of the cell being drawn
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {

        JButton button = this;
        Object obj = state.get(new Integer(column));
        if (obj != null) {
            if (((Integer) obj).intValue() == DOWN) {
                button = downButton;
            } else if (((Integer) obj).intValue() == UP) {
                button = upButton;
            } else {
                button = noneButton;
            }
        }
        // set some color font about header.
        JTableHeader header = table.getTableHeader();
        if (header != null) {
            Color fgColor = null;
            Color bgColor = null;
            if (hasFocus) {
                fgColor = UIManager.getColor("TableHeader.focusCellForeground");
                bgColor = UIManager.getColor("TableHeader.focusCellBackground");
            }
            if (fgColor == null) {
                fgColor = header.getForeground();
            }
            if (bgColor == null) {
                bgColor = header.getBackground();
            }
            setForeground(fgColor);
            setBackground(bgColor);

            setFont(header.getFont());
        }
        // set height.
        if (headerHeight != 0) {
            setPreferredSize(new Dimension(getWidth(), headerHeight));
        } else {
            setPreferredSize(new Dimension(getWidth(), getHeight()));
        }

        Border border = null;
        if (hasFocus) {
            border = UIManager.getBorder("TableHeader.focusCellBorder");
        }
        if (border == null) {
            border = UIManager.getBorder("TableHeader.cellBorder");
        }
        setBorder(border);

        button.setText((value == null) ? "" : value.toString());
        if (header != null && renderer != null) {
            boolean isPressed = (column == pushedColumn);
            button.getModel().setPressed(isPressed);
            button.getModel().setArmed(isPressed);
        }
        return button;
    }

    /**
     * init.
     */
    private void init() {
        noneButton = new JButton();
        noneButton.setMargin(new Insets(0, 0, 0, 0));
        noneButton.setHorizontalTextPosition(LEFT);
        noneButton.setIcon(new MyBlankIcon());

        downButton = new JButton();
        downButton.setMargin(new Insets(0, 0, 0, 0));
        downButton.setHorizontalTextPosition(LEFT);
        downButton.setIcon(new MySortIcon(DOWN, false, false));
        downButton.setPressedIcon(new MySortIcon(DOWN, false, true));

        upButton = new JButton();
        upButton.setMargin(new Insets(0, 0, 0, 0));
        upButton.setHorizontalTextPosition(LEFT);
        upButton.setIcon(new MySortIcon(UP, false, false));
        upButton.setPressedIcon(new MySortIcon(UP, false, true));
    }

    /**
     * set which column is press.
     * 
     * @param col
     *            press column.
     */
    public void setPressedColumn(int col) {
        pushedColumn = col;
    }

    /**
     * set select column state.
     * 
     * @param col
     *            select column.
     */
    public void setSelectedColumn(int col) {
        if (col < 0) {
            return;
        }
        Integer value = null;
        Object obj = state.get(new Integer(col));
        if (obj == null) {
            value = new Integer(DOWN);
        } else {
            if (((Integer) obj).intValue() == DOWN) {
                value = new Integer(UP);
            } else if (((Integer) obj).intValue() == UP) {
                value = new Integer(NONE);
            } else {
                value = new Integer(DOWN);
            }
        }
        state.clear();
        if (value > 0) {
            state.put(new Integer(col), value);
        }

    }

    /**
     * get the header sorter state.
     * 
     * @param col
     *            header column.
     * @return header sorter state.
     */
    public int getState(int col) {
        int retValue;
        Object obj = state.get(new Integer(col));
        if (obj == null) {
            retValue = NONE;
        } else {
            if (((Integer) obj).intValue() == DOWN) {
                retValue = DOWN;
            } else if (((Integer) obj).intValue() == UP) {
                retValue = UP;
            } else {
                retValue = NONE;
            }
        }
        return retValue;
    }

    /**
     * set table header height.
     * 
     * @param headerheight
     *            table header height.
     */
    public void setHeight(int headerheight) {
        headerHeight = headerheight;
    }

    /**
     * Invoked when the mouse button has been clicked (pressed and released) on
     * a component.
     */
    public void mouseClicked(MouseEvent e) {
        if (header != null && renderer != null) {
            int col = header.columnAtPoint(e.getPoint());
            int sortCol = header.getTable().convertColumnIndexToModel(col);
            renderer.setPressedColumn(col);
            renderer.setSelectedColumn(col);
            header.repaint();

            if (header.getTable().isEditing()) {
                header.getTable().getCellEditor().stopCellEditing();
            }
            if (!(DOWN == renderer.getState(col))
                    && !(UP == renderer.getState(col))) {
                sorter.setUNSort(sortCol);
            }
        }
    }

    /**
     * Invoked when a mouseR button has been pressed on a component.
     */
    public void mouseEntered(MouseEvent e) {
    }

    /**
     * Invoked when a mouse button has been released on a component.
     */
    public void mouseExited(MouseEvent e) {
    }

    /**
     * Invoked when the mouse enters a component.
     */
    public void mousePressed(MouseEvent e) {

    }

    /**
     * Invoked when the mouse exits a component.
     */
    public void mouseReleased(MouseEvent e) {
        if (header != null && renderer != null) {
            header.columnAtPoint(e.getPoint());
            // clear state.
            renderer.setPressedColumn(-1);
            header.repaint();
        }
    }
}
