package cellspan;

import java.awt.BorderLayout;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class TestCellSpan {
    // ///////////////////////////////////////////////////////////////////////
    // Test
    // ///////////////////////////////////////////////////////////////////////
    static SpanCellTablePanel spanTablePanel = null;

    public static void main(String[] args) {

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());
        spanTablePanel = new SpanCellTablePanel(createTestData());
        frame.getContentPane().add(spanTablePanel, BorderLayout.CENTER);
        frame.setSize(600, 520);

        spanTablePanel.getTable().getSelectionModel().addListSelectionListener(
                new ListSelectionListener() {

                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        if (!e.getValueIsAdjusting()) {
                            int[] allSelectRows = getSelectRowArray(spanTablePanel
                                    .getTable().getSelectedRow());
                            if (allSelectRows == null
                                    || allSelectRows.length < 1) {
                                return;
                            }
                            spanTablePanel.getTable()
                                    .setColumnSelectionInterval(
                                            0,
                                            spanTablePanel.getTable()
                                                    .getColumnCount() - 1);
                            spanTablePanel
                                    .getTable()
                                    .getSelectionModel()
                                    .setSelectionInterval(
                                            allSelectRows[0],
                                            allSelectRows[allSelectRows.length - 1]);
                        }

                    }
                });

        frame.setVisible(true);
    }

    private static int[] getSelectRowArray(int row) {
        for (int i = 0; i < spanTablePanel.getSpanArray().length; i++) {
            for (int j = 0; j < spanTablePanel.getSpanArray()[i].length; j++) {
                if (row == spanTablePanel.getSpanArray()[i][j]) {
                    return spanTablePanel.getSpanArray()[i];
                }
            }
        }
        return null;
    }

    private static Vector<Vector<?>> createTestData() {

        Vector<String> data1 = new Vector<String>();
        data1.add("物流系统");
        data1.add("终端用户1");
        data1.add("192.168.0.1");
        data1.add("mysql");
        data1.add("12312");
        Vector<String> data2 = new Vector<String>();
        data2.add("物流系统");
        data2.add("终端用户1");
        data2.add("192.168.0.12");
        data2.add("oracle");
        data2.add("12312");
        Vector<String> data3 = new Vector<String>();
        data3.add("物流系统");
        data3.add("终端用户1");
        data3.add("192.168.20.1");
        data3.add("sap");
        data3.add("12312");

        Vector<String> data4 = new Vector<String>();
        data4.add("门户网站");
        data4.add("终端用户2");
        data4.add("192.168.0.12");
        data4.add("oracle");
        Vector<String> data5 = new Vector<String>();
        data5.add("门户网站");
        data5.add("终端用户2");
        data5.add("192.168.2.1");
        data5.add("db2");

        Vector<String> data6 = new Vector<String>();
        data6.add("办公系统");
        data6.add("终端用户2");
        data6.add("127.0.0.1");
        data6.add("access");

        Vector<String> data7 = new Vector<String>();
        data7.add("ERP");
        data7.add("终端用户3");
        data7.add("192.168.0.12");
        data7.add("oracle");
        Vector<String> data8 = new Vector<String>();
        data8.add("ERP");
        data8.add("终端用户3");
        data8.add("192.168.2.1");
        data8.add("db2");

        Vector<Vector<?>> datas = new Vector<Vector<?>>();
        datas.add(data1);
        datas.add(data2);
        datas.add(data3);
        datas.add(data4);
        datas.add(data5);
        datas.add(data6);
        datas.add(data7);
        datas.add(data8);

        return datas;
    }

    // ///////////////////////////////////////////////////////////////////////
    // Test
    // ///////////////////////////////////////////////////////////////////////
}
