package cellspan;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;

/**
 * This interface defines the method required by any object that would like to
 * be a renderer for cells in a <code>JTable</code>.
 */
public class CustomCellRenderer extends JLabel implements TableCellRenderer {

    /**
     * UID.
     */
    private static final long serialVersionUID = 1L;
    /** make the cell no border. */
    private static Border noFocusBorder;

    /**
     * This interface defines the method required by any object that would like
     * to be a renderer for cells in a <code>JTable</code>.
     */
    public CustomCellRenderer() {
        noFocusBorder = new EmptyBorder(1, 2, 1, 2);
        setOpaque(true);
        setBorder(noFocusBorder);
    }

    /**
     * Returns the component used for drawing the cell. This method is used to
     * configure the renderer appropriately before drawing.
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        Color foreground = null;
        Color background = null;
        Font font = null;
        if (isSelected) {
            setForeground((foreground != null) ? foreground : table
                    .getSelectionForeground());
            setBackground(table.getSelectionBackground());
        } else {
            setForeground((foreground != null) ? foreground : table
                    .getForeground());
            setBackground((background != null) ? background : table
                    .getBackground());
        }
        setFont((font != null) ? font : table.getFont());

        if (hasFocus) {
            setBorder(UIManager.getBorder("Table.focusCellHighlightBorder"));
            if (table.isCellEditable(row, column)) {
                setForeground((foreground != null) ? foreground : UIManager
                        .getColor("Table.focusCellForeground"));
                setBackground(UIManager.getColor("Table.focusCellBackground"));
            }
        } else {
            setBorder(noFocusBorder);
        }
        setValue(value);
        return this;
    }

    /**
     * set the cell value.
     * 
     * @param value
     *            cell value.
     */
    protected void setValue(Object value) {
        setText((value == null) ? "" : value.toString());
    }
}
