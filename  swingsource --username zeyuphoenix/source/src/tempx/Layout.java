package tempx;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

public class Layout {

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		frame.getContentPane().setLayout(new BorderLayout());
		Layout layout = new Layout();
		frame.getContentPane().add(layout.new AlarmMainPanel(),
				BorderLayout.CENTER);
		frame.setSize(800, 700);
		frame.setVisible(true);
	}

	private class AlarmMainPanel extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public AlarmMainPanel() {
			super();
			init();
		}

		private void init() {
			this.setLayout(new BorderLayout());
			JPanel centerPanel = new JPanel();
			centerPanel.setLayout(new BorderLayout());
			AlarmConditionPanel conditionPanel = new AlarmConditionPanel();

			JPanel northPanel = new JPanel();
			northPanel.setLayout(new BorderLayout());
			northPanel.add(conditionPanel, BorderLayout.CENTER);

			centerPanel.add(northPanel, BorderLayout.NORTH);
			centerPanel.add(new AlarmTablePanel(), BorderLayout.CENTER);
			centerPanel.add(getDescPanel(), BorderLayout.SOUTH);

			this.add(centerPanel, BorderLayout.NORTH);
		}

		private JPanel getDescPanel() {
			JPanel panel = new JPanel();
			Dimension dimen = new Dimension();
			dimen.height = 70;
			panel.setPreferredSize(dimen);

			panel.setBorder(BorderFactory.createTitledBorder("告警显示查询条件"));
			panel.setLayout(new BorderLayout());
			JScrollPane js = new JScrollPane();
			js
					.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			js
					.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
			js.getViewport().add(new JTextArea());
			panel.add(js, "Center");

			JPanel dataMessagePanel = new JPanel();
			dataMessagePanel.setLayout(new BorderLayout());
			dataMessagePanel.add(new JLabel(), BorderLayout.CENTER);

			panel.add(dataMessagePanel, BorderLayout.EAST);

			return panel;
		}

	}

	private class AlarmTablePanel extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * 
		 */
		public AlarmTablePanel() {
			super();
			init();
		}

		private void init() {
			this.setLayout(new BorderLayout());
			JPanel panelCenter = new JPanel();
			panelCenter.setLayout(new BorderLayout());

			panelCenter.setBorder(BorderFactory.createTitledBorder("告警信息"));

			JTable table = new JTable();
			JScrollPane scpanel = new JScrollPane();
			scpanel.getViewport().add(table);
			panelCenter.add(scpanel, BorderLayout.CENTER);

			this.add(panelCenter, BorderLayout.CENTER);
		}
	}

	private class AlarmConditionPanel extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = -5599755810180869531L;

		// 删除所选告警
		private JButton buttonDelete;

		private JButton buttonSearch;
		private JButton buttonClear;

		private JLabel labelLever;
		private JLabel labelStatus;
		private JLabel labelType;
		private JLabel labelkey;

		private JComboBox alarmLerver;
		private JComboBox alarmStatus;
		private JComboBox alarmType;
		private JTextField keyText;
		private JPanel datePanel;

		private JButton buttonHost;
		private JButton buttonAccountAdmin;
		private JButton buttonAccountServer;

		private JButton buttonOutPut;
		private JButton buttonLevelInfo;

		public AlarmConditionPanel() {
			super();
			init();
		}

		private void init() {

			datePanel = new JPanel();
			this.setLayout(new BorderLayout());

			JPanel panelCenter = new JPanel();
			panelCenter.setLayout(new BorderLayout());

			panelCenter.setBorder(BorderFactory.createTitledBorder("告警显示查询条件"));

			JPanel panelButton = getPanelButton();
			JPanel panelCondition = getPanelCondition();
			panelCenter.add(panelCondition, BorderLayout.CENTER);

			this.add(panelButton, BorderLayout.SOUTH);
			this.add(panelCenter, BorderLayout.CENTER);

		}

		public JButton getButtonAccAdmin() {
			return buttonAccountAdmin;
		}

		public JButton getButtonHost() {
			return buttonHost;
		}

		public JButton getButtonAccServer() {
			return buttonAccountServer;
		}

		public static final String ALARM_TIMEDB = "告警时间";

		public static final String ALARM_NUM = "告警序号";
		public static final String ALARM_TIME = "时间";
		public static final String ALARM_LEVER = "告警等级";
		public static final String ALARM_STATUS = "告警状态";
		public static final String ALARM_TYPE = "告警类型";
		public static final String ALARM_DESC = "告警描述";
		public static final String ALARM_DID = "DID值";

		public static final String ALARM_LEVER_NUM = "告警数字等级";

		private JPanel getPanelCondition() {

			JPanel panel = new JPanel();
			panel.setLayout(new GridLayout(2, 1, 5, 5));

			labelLever = new JLabel(ALARM_LEVER);
			labelStatus = new JLabel(ALARM_STATUS);
			labelType = new JLabel(ALARM_TYPE);
			labelkey = new JLabel(ALARM_DESC);

			labelLever.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
			labelStatus.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
			labelType.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
			labelkey.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));

			alarmLerver = new JComboBox();
			alarmStatus = new JComboBox();
			alarmType = new JComboBox();

			keyText = new JTextField();
			keyText.setColumns(10);

			JPanel panelLever = new JPanel();
			Dimension d = new Dimension();
			d.width = 260;
			panelLever.setPreferredSize(d);
			JPanel panelStatus = new JPanel();
			Dimension dstatus = new Dimension();
			dstatus.width = 140;
			panelStatus.setPreferredSize(dstatus);

			JPanel panelType = new JPanel();
			JPanel panelKey = new JPanel();
			Dimension dkey = new Dimension();
			dkey.width = 260;
			panelKey.setPreferredSize(dkey);

			panelLever.setBorder(BorderFactory.createEmptyBorder(8, 10, 6, 10));
			panelStatus.setBorder(BorderFactory.createEmptyBorder(8, 10, 6, 0));
			panelType.setBorder(BorderFactory.createEmptyBorder(8, 5, 6, 3));
			panelKey.setBorder(BorderFactory.createEmptyBorder(8, 10, 6, 10));

			panelLever.setLayout(new BorderLayout());
			panelStatus.setLayout(new BorderLayout());
			panelType.setLayout(new BorderLayout());
			panelKey.setLayout(new BorderLayout());

			panelLever.add(labelLever, "West");
			panelLever.add(alarmLerver, "Center");
			panelStatus.add(labelStatus, "West");
			panelStatus.add(alarmStatus, "Center");
			panelType.add(labelType, "West");
			panelType.add(alarmType, "Center");
			panelKey.add(labelkey, "West");
			panelKey.add(keyText, "Center");

			labelStatus.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));

			// 完成界面组件定义,开始构造界面
			JPanel colOne = new JPanel();
			JPanel colTwo = new JPanel();
			Dimension dcol = new Dimension();
			dcol.height = 40;
			colOne.setPreferredSize(dcol);
			colTwo.setPreferredSize(dcol);

			panel.add(colOne);
			panel.add(colTwo);
			colOne.setLayout(new BorderLayout());
			colOne.add(datePanel, "Center");
			colOne.add(panelLever, "East");
			colTwo.setLayout(new BorderLayout());
			colTwo.add(panelStatus, "West");
			JPanel colTwoCen = new JPanel();
			colTwoCen.setLayout(new BorderLayout());
			colTwo.add(colTwoCen, "Center");
			colTwoCen.add(panelType, "Center");
			colTwoCen.add(panelKey, "East");

			return panel;

		}

		public JComboBox getAlarmType() {
			return alarmType;
		}

		private JPanel panelLeft = new JPanel();

		private JPanel getPanelButton() {

			String text = "策略配置";

			buttonHost = new JButton(text);
			buttonAccountAdmin = new JButton(text);
			buttonAccountServer = new JButton(text);

			buttonOutPut = new JButton("告警信息导出");
			buttonLevelInfo = new JButton("告警级别说明");

			buttonSearch = new JButton("查询");
			buttonClear = new JButton("重置条件");
			buttonDelete = new JButton("删除选择告警");

			JPanel panelButton = new JPanel();
			panelButton.setLayout(new FlowLayout(FlowLayout.CENTER));
			panelButton.add(buttonSearch);
			panelButton.add(buttonClear);
			panelButton.add(buttonLevelInfo);
			panelButton.add(buttonDelete);

			JPanel panel = new JPanel();
			panel.setLayout(new BorderLayout());

			panelLeft.setLayout(new FlowLayout(FlowLayout.LEFT));
			addPanelLeft();

			panel.add(panelLeft, "West");
			panel.add(panelButton, "Center");

			return panel;
		}

		private void addPanelLeft() {
			panelLeft.removeAll();
			if (booleanHost) {
				panelLeft.add(buttonHost);
			}
			if (boolAdmin) {
				panelLeft.add(buttonAccountAdmin);
			}
			if (boolServer) {
				panelLeft.add(buttonAccountServer);
			}
			panelLeft.add(buttonOutPut);
			panelLeft.revalidate();
			panelLeft.repaint();
		}

		private boolean booleanHost = true;
		private boolean boolAdmin = false;
		private boolean boolServer = false; 

	}
}
