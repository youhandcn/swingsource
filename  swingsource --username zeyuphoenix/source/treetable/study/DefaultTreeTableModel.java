package study;

import java.io.Serializable;
import java.util.Vector;

import javax.swing.event.TableModelListener;
import javax.swing.tree.TreeNode;

public class DefaultTreeTableModel extends AbstractTreeTableModel implements
        Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    //
    // Instance Variables
    //

    /**
     * The <code>Vector</code> of <code>Vectors</code> of <code>Object</code>
     * values.
     */
    protected Vector<Vector<Object>> dataVector;

    /** The <code>Vector</code> of column identifiers. */
    protected Vector<Object> columnIdentifiers;

    //
    // Constructors
    //

    public DefaultTreeTableModel(Object root) {
        this(root, 0, 0);
    }

    /**
     * Constructs a <code>DefaultTableModel</code> with <code>rowCount</code>
     * and <code>columnCount</code> of <code>null</code> object values.
     * 
     * @param rowCount
     *            the number of rows the table holds
     * @param columnCount
     *            the number of columns the table holds
     * 
     * @see #setValueAt
     */
    public DefaultTreeTableModel(Object root, int rowCount, int columnCount) {
        this(root, newVector(columnCount), rowCount);
    }

    /**
     * Constructs a <code>DefaultTableModel</code> with as many columns as there
     * are elements in <code>columnNames</code> and <code>rowCount</code> of
     * <code>null</code> object values. Each column's name will be taken from
     * the <code>columnNames</code> vector.
     * 
     * @param columnNames
     *            <code>vector</code> containing the names of the new columns;
     *            if this is <code>null</code> then the model has no columns
     * @param rowCount
     *            the number of rows the table holds
     * @see #setDataVector
     * @see #setValueAt
     */
    public DefaultTreeTableModel(Object root, Vector<Object> columnNames,
            int rowCount) {
        super(root);
        setDataVector(newVectors(rowCount), columnNames);
    }

    /**
     * Constructs a <code>DefaultTableModel</code> with as many columns as there
     * are elements in <code>columnNames</code> and <code>rowCount</code> of
     * <code>null</code> object values. Each column's name will be taken from
     * the <code>columnNames</code> array.
     * 
     * @param columnNames
     *            <code>array</code> containing the names of the new columns; if
     *            this is <code>null</code> then the model has no columns
     * @param rowCount
     *            the number of rows the table holds
     * @see #setDataVector
     * @see #setValueAt
     */
    public DefaultTreeTableModel(Object root, Object[] columnNames, int rowCount) {
        this(root, convertToVector(columnNames), rowCount);
    }

    /**
     * Constructs a <code>DefaultTableModel</code> and initializes the table by
     * passing <code>data</code> and <code>columnNames</code> to the
     * <code>setDataVector</code> method.
     * 
     * @param data
     *            the data of the table, a <code>Vector</code> of
     *            <code>Vector</code>s of <code>Object</code> values
     * @param columnNames
     *            <code>vector</code> containing the names of the new columns
     * @see #getDataVector
     * @see #setDataVector
     */
    public DefaultTreeTableModel(Object root, Vector<Vector<Object>> data,
            Vector<Object> columnNames) {
        super(root);
        setDataVector(data, columnNames);
    }

    /**
     * Constructs a <code>DefaultTableModel</code> and initializes the table by
     * passing <code>data</code> and <code>columnNames</code> to the
     * <code>setDataVector</code> method. The first index in the
     * <code>Object[][]</code> array is the row index and the second is the
     * column index.
     * 
     * @param data
     *            the data of the table
     * @param columnNames
     *            the names of the columns
     * @see #getDataVector
     * @see #setDataVector
     */
    public DefaultTreeTableModel(Object root, Object[][] data,
            Object[] columnNames) {
        super(root);
        setDataVector(data, columnNames);
    }

    /**
     * Replaces the current <code>dataVector</code> instance variable with the
     * new <code>Vector</code> of rows, <code>dataVector</code>. Each row is
     * represented in <code>dataVector</code> as a <code>Vector</code> of
     * <code>Object</code> values. <code>columnIdentifiers</code> are the names
     * of the new columns. The first name in <code>columnIdentifiers</code> is
     * mapped to column 0 in <code>dataVector</code>. Each row in
     * <code>dataVector</code> is adjusted to match the number of columns in
     * <code>columnIdentifiers</code> either by truncating the
     * <code>Vector</code> if it is too long, or adding <code>null</code> values
     * if it is too short.
     * <p>
     * Note that passing in a <code>null</code> value for
     * <code>dataVector</code> results in unspecified behavior, an possibly an
     * exception.
     * 
     * @param dataVector
     *            the new data vector
     * @param columnIdentifiers
     *            the names of the columns
     * @see #getDataVector
     */
    public void setDataVector(Vector<Vector<Object>> dataVector,
            Vector<Object> columnIdentifiers) {
        this.dataVector = nonNullVectors(dataVector);
        this.columnIdentifiers = nonNullVector(columnIdentifiers);
    }

    /**
     * Replaces the value in the <code>dataVector</code> instance variable with
     * the values in the array <code>dataVector</code>. The first index in the
     * <code>Object[][]</code> array is the row index and the second is the
     * column index. <code>columnIdentifiers</code> are the names of the new
     * columns.
     * 
     * @param dataVector
     *            the new data vector
     * @param columnIdentifiers
     *            the names of the columns
     * @see #setDataVector(Vector, Vector)
     */
    public void setDataVector(Object[][] dataVector, Object[] columnIdentifiers) {
        setDataVector(convertToVector(dataVector),
                convertToVector(columnIdentifiers));
    }

    @Override
    public Object getValueAt(Object node, int column) {
        return null;
    }

    @Override
    public void setValueAt(Object aValue, Object node, int column) {
        // TODO Auto-generated method stub
        super.setValueAt(aValue, node, column);
    }

    @Override
    public void addTableModelListener(TableModelListener l) {

    }

    @Override
    public void removeTableModelListener(TableModelListener l) {

    }

    /**
     * Returns the number of columns in this data table.
     * 
     * @return the number of columns in the model
     */
    @Override
    public int getColumnCount() {
        return columnIdentifiers.size();
    }

    /**
     * Returns the column name.
     * 
     * @return a name for this column using the string value of the appropriate
     *         member in <code>columnIdentifiers</code>. If
     *         <code>columnIdentifiers</code> does not have an entry for this
     *         index, returns the default name provided by the superclass.
     */
    @Override
    public String getColumnName(int column) {
        Object id = null;
        // This test is to cover the case when
        // getColumnCount has been subclassed by mistake ...
        if (column < columnIdentifiers.size() && (column >= 0)) {
            id = columnIdentifiers.elementAt(column);
        }
        return (id == null) ? super.getColumnName(column) : id.toString();
    }

    @Override
    public Class<?> getColumnClass(int column) {
        return super.getColumnClass(column);
    }

    /**
     * TreeModel method to locate a particular child of the specified node.
     * Since <code>node</code> is a TreeNode, this can be answered via the
     * TreeNode method <code>getChild</code>.
     */
    @Override
    public Object getChild(Object node, int i) {
        return ((TreeNode) node).getChildAt(i);
    }

    /**
     * TreeModel method to return the number of children of a particular node.
     * Since <code>node</code> is a TreeNode, this can be answered via the
     * TreeNode method <code>getChildCount</code>.
     */
    @Override
    public int getChildCount(Object node) {
        return ((TreeNode) node).getChildCount();
    }

    /**
     * TreeModel method to determine if a node is a leaf. Since
     * <code>node</code> is a TreeNode, this can be answered via the TreeNode
     * method <code>isLeaf</code>.
     */
    @Override
    public boolean isLeaf(Object node) {
        return ((TreeNode) node).isLeaf();
    }

    //
    // private Methods
    //
    private static Vector<Object> newVector(int size) {
        Vector<Object> v = new Vector<Object>(size);
        v.setSize(size);
        return v;
    }

    private static Vector<Vector<Object>> newVectors(int size) {
        Vector<Vector<Object>> v = new Vector<Vector<Object>>(size);
        v.setSize(size);
        return v;
    }

    private static Vector<Object> nonNullVector(Vector<Object> v) {
        return (v != null) ? v : new Vector<Object>();
    }

    private static Vector<Vector<Object>> nonNullVectors(
            Vector<Vector<Object>> v) {
        return (v != null) ? v : new Vector<Vector<Object>>();
    }

    //
    // Protected Methods
    //

    /**
     * Returns a vector that contains the same objects as the array.
     * 
     * @param anArray
     *            the array to be converted
     * @return the new vector; if <code>anArray</code> is <code>null</code>,
     *         returns <code>null</code>
     */
    protected static Vector<Object> convertToVector(Object[] anArray) {
        if (anArray == null) {
            return null;
        }
        Vector<Object> v = new Vector<Object>(anArray.length);
        for (int i = 0; i < anArray.length; i++) {
            v.addElement(anArray[i]);
        }
        return v;
    }

    /**
     * Returns a vector of vectors that contains the same objects as the array.
     * 
     * @param anArray
     *            the double array to be converted
     * @return the new vector of vectors; if <code>anArray</code> is
     *         <code>null</code>, returns <code>null</code>
     */
    protected static Vector<Vector<Object>> convertToVector(Object[][] anArray) {
        if (anArray == null) {
            return null;
        }
        Vector<Vector<Object>> v = new Vector<Vector<Object>>(anArray.length);
        for (int i = 0; i < anArray.length; i++) {
            v.addElement(convertToVector(anArray[i]));
        }
        return v;
    }
}
