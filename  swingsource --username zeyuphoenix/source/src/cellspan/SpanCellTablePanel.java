package cellspan;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import jtable.tableheader.MyGroupTableHeaderUI;
import jtable.tableheader.MyHeaderButtonRenderer;
import jtable.tableheader.MyTableHeader;

/**
 * 创建可以合并单元格的table
 * 
 * @author zhangshuai
 */
public class SpanCellTablePanel extends JPanel {

    /**
     * UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * table，可以进行单元格合并
     */
    private CustomTabel table = null;
    /**
     * 设置table的头名字和宽度.
     */
    private String[] columnTitle = new String[] { "序号", "名称", "用户", "服务器一",
            "服务器二" };
    /**
     * 保存table合并的列的数组
     */
    private int[][] spanArray = null;

    /**
     * 因为涉及到新加数据单元格合并的问题，所以更改table的数据都是重新创建
     */
    public SpanCellTablePanel(Vector<Vector<?>> datas) {
        init(datas);
    }

    /**
     * 初始化
     */
    private void init(Vector<Vector<?>> datas) {
        CustomTableModel model = null;
        datas = convertSpanData(datas);
        if (datas != null) {
            model = new CustomTableModel(datas, convertToVector(columnTitle));
        } else {
            model = new CustomTableModel(columnTitle, 0);
        }

        ICellAttribute cellAtt = model.getCellAttribute();
        table = new CustomTabel(model) {
            /**
             * 
             */
            private static final long serialVersionUID = 1L;

            protected JTableHeader createDefaultTableHeader() {
                return new MyTableHeader(columnModel);
            }
        };
        table.setOpaque(false);

        table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        // 设置表头列不可拖拽和样式
        table.getTableHeader().setResizingAllowed(false);
        TableCellRenderer renderer2 = new MyHeaderButtonRenderer();
        TableColumnModel model2 = table.getColumnModel();
        for (int i = 0; i < model2.getColumnCount(); i++) {
            model2.getColumn(i).setHeaderRenderer(renderer2);
        }
        table.getTableHeader().setUI(new MyGroupTableHeaderUI());
        JScrollPane scroll = new JScrollPane(table);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        if (datas != null) {
            // 合并单元格
            spanArray = combineSpanData(datas);
            if (spanArray != null) {
                int[] columns = new int[] { 0, 1, 2 };
                for (int i = 0; i < columns.length; i++) {

                    for (int t = 0; t < spanArray.length; t++) {
                        ((ICellSpan) cellAtt).combine(spanArray[t],
                                new int[] { columns[i] });
                    }
                }
                table.clearSelection();
                table.revalidate();
                table.repaint();
            }
        }

        this.setLayout(new BorderLayout());
        this.setOpaque(false);
        // 根据行数动态计算显示宽度
        int rowcount = table.getRowCount();
        this.setPreferredSize(new Dimension(800, 70 + rowcount * 20));

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.add(titlePanel, BorderLayout.NORTH);
        this.add(scroll, BorderLayout.CENTER);
    }

    /**
     * 取得合并单元格的表
     */
    public JTable getTable() {
        return table;
    }

    /**
     * 取得合并的列的数组
     */
    public int[][] getSpanArray() {
        return spanArray;
    }

    /**
     * 根据行号取得行的隐藏id（这里是组合业务id）
     */
    public String getSelectedCbsAppId(int row) {
        for (int i = 0; i < table.getModel().getColumnCount(); i++) {
            if (table.getModel().getColumnName(i) != null
                    && table.getModel().getColumnName(i).trim()
                            .equalsIgnoreCase("obj")) {
                // 取得obj列的值
                Object value = table.getValueAt(table.getSelectedRow(), i);
                return (value == null) ? null : value.toString();
            }
        }
        return null;
    }

    /**
     * 内存的释放
     */
    public void release() {
        spanArray = null;
        table = null;
        this.removeAll();
    }

    /**
     * table头转化为需要的vector对象
     */
    private Vector<Object> convertToVector(Object[] anArray) {
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
     * 为给定的table数据添加序号列
     */
    private Vector<Vector<?>> convertSpanData(Vector<Vector<?>> datas) {
        if (datas == null || datas.isEmpty()) {
            return null;
        }
        Vector<Object> rows = new Vector<Object>();
        Vector<Vector<?>> spanList = new Vector<Vector<?>>();
        Object tempInfo = datas.get(0).get(0);
        int order = 1;
        for (int i = 0; i < datas.size(); i++) {
            if (tempInfo.equals(datas.get(i).get(0))) {
            } else {
                tempInfo = datas.get(i).get(0);
                order++;
            }
            rows = new Vector<Object>();
            rows.addAll(datas.get(i));
            rows.insertElementAt(order, 0);
            spanList.add(rows);
        }
        return spanList;
    }

    /**
     * 根据给定的table数据取得需要合并的单元格
     */
    private int[][] combineSpanData(Vector<Vector<?>> datas) {
        if (datas == null || datas.isEmpty()) {
            return null;
        }
        List<Integer> rows = new ArrayList<Integer>();
        List<List<Integer>> spanList = new ArrayList<List<Integer>>();
        Object tempInfo = datas.get(0).get(0);
        for (int i = 0; i < datas.size(); i++) {
            if (tempInfo.equals(datas.get(i).get(0))) {
                rows.add(i);
            } else {
                spanList.add(rows);
                tempInfo = datas.get(i).get(0);
                rows = new ArrayList<Integer>();
                rows.add(i);
            }
            if (i == datas.size() - 1) {
                spanList.add(rows);
            }
        }

        int[][] spanArray = new int[spanList.size()][];
        for (int i = 0; i < spanList.size(); i++) {
            int[] array = new int[spanList.get(i).size()];
            for (int j = 0; j < spanList.get(i).size(); j++) {
                array[j] = spanList.get(i).get(j);
            }
            spanArray[i] = array;
        }

        return spanArray;
    }
}
