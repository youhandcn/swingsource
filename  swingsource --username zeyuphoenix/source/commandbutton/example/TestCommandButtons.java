package example;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.color.ColorSpace;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import common.AbstractCommandButton;
import common.CommandButtonDisplayState;
import common.JCommandButton;
import common.JCommandMenuButton;
import common.JCommandButton.CommandButtonPopupOrientationKind;
import common.icon.EmptyResizableIcon;
import common.icon.FilteredResizableIcon;
import common.popup.JCommandPopupMenu;
import common.popup.JPopupPanel;
import common.popup.PopupPanelCallback;

public class TestCommandButtons extends JFrame {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public TestCommandButtons() {
        super("Command button test");
        this.setIconImage(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB));

        this.setLayout(new BorderLayout());
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());

        addButtons(buttonsPanel, CommandButtonDisplayState.MEDIUM);

        this.add(buttonsPanel, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        this.configureControlPanel(controlPanel);

        this.add(controlPanel, BorderLayout.SOUTH);
    }

    protected static String stamp() {
        return new SimpleDateFormat("HH:mm:ss.SSS").format(new Date());
    }

    protected class TestPopupCallback implements PopupPanelCallback {
        @Override
        public JPopupPanel getPopupPanel(JCommandButton commandButton) {

            JCommandPopupMenu popupMenu = new JCommandPopupMenu();
            popupMenu.addMenuButton(new JCommandMenuButton("Test menu item 1",
                    new EmptyResizableIcon(16)));
            popupMenu.addMenuButton(new JCommandMenuButton("Test menu item 2",
                    new EmptyResizableIcon(16)));
            JCommandMenuButton commandMenuButton = new JCommandMenuButton(
                    "Test menu item 3", new EmptyResizableIcon(16));
            commandMenuButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println(12233);
                }
            });
            popupMenu.addMenuButton(commandMenuButton);
            popupMenu.addMenuSeparator();
            popupMenu.addMenuButton(new JCommandMenuButton("Test menu item 4",
                    new EmptyResizableIcon(16)));
            popupMenu.addMenuButton(new JCommandMenuButton("Test menu item 5",
                    new EmptyResizableIcon(16)));
            return popupMenu;

        }
    }

    private void addButtons(JPanel panel, CommandButtonDisplayState state) {

        JCommandButton actionButton = createActionButton(state);
        panel.add(actionButton);

        JCommandButton actionAndPopupMainActionButton = createActionAndPopupMainActionButton(state);
        panel.add(actionAndPopupMainActionButton);

        JCommandButton actionAndPopupMainPopupButton = createActionAndPopupMainPopupButton(state);
        panel.add(actionAndPopupMainPopupButton);

        JCommandButton popupButton = createPopupButton(state);
        panel.add(popupButton);
    }

    protected JCommandButton createPopupButton(CommandButtonDisplayState state) {
        JCommandButton popupButton = new JCommandButton("Select all",
                new edit_paste());
        popupButton.setExtraText("Extra for select all");
        popupButton.setPopupCallback(new TestPopupCallback());
        popupButton
                .setCommandButtonKind(JCommandButton.CommandButtonKind.POPUP_ONLY);
        popupButton.setDisplayState(state);
        popupButton.setFlat(false);
        return popupButton;
    }

    protected JCommandButton createActionAndPopupMainPopupButton(
            CommandButtonDisplayState state) {
        JCommandButton copyButton = new JCommandButton("Copy", new edit_copy());
        copyButton.setExtraText("Extra for copy");
        copyButton.setPopupCallback(new TestPopupCallback());
        copyButton
                .setCommandButtonKind(JCommandButton.CommandButtonKind.ACTION_AND_POPUP_MAIN_POPUP);
        copyButton.setDisplayState(state);
        copyButton.setFlat(false);
        copyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println(stamp() + ": Copy");
            }
        });
        return copyButton;
    }

    protected JCommandButton createActionAndPopupMainActionButton(
            CommandButtonDisplayState state) {
        JCommandButton cutButton = new JCommandButton("Cut", new edit_cut());
        cutButton.setExtraText("Extra for cut");
        cutButton.setPopupCallback(new TestPopupCallback());
        cutButton
                .setCommandButtonKind(JCommandButton.CommandButtonKind.ACTION_AND_POPUP_MAIN_ACTION);
        cutButton.setDisplayState(state);
        cutButton.setFlat(false);
        cutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println(stamp() + ": Cut");
            }
        });
        return cutButton;
    }

    protected JCommandButton createActionButton(CommandButtonDisplayState state) {
        JCommandButton mainButton = new JCommandButton("Paste",
                new edit_paste());
        mainButton.setDisabledIcon(new FilteredResizableIcon(new edit_paste(),
                new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY),
                        null)));
        mainButton.setExtraText("Extra for paste");
        mainButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println(stamp() + ": Main paste");
            }
        });
        mainButton
                .setCommandButtonKind(JCommandButton.CommandButtonKind.ACTION_ONLY);
        mainButton.setDisplayState(state);
        mainButton.setFlat(false);
        return mainButton;
    }

    protected void configureControlPanel(JPanel controlPanel) {

        final JCheckBox enabled = new JCheckBox("enabled");
        enabled.setSelected(true);
        enabled.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        scan(TestCommandButtons.this);
                        repaint();
                    }

                    private void scan(Container c) {
                        for (int i = 0; i < c.getComponentCount(); i++) {
                            Component child = c.getComponent(i);
                            if (child instanceof AbstractCommandButton)
                                child.setEnabled(enabled.isSelected());
                            if (child instanceof Container)
                                scan((Container) child);
                        }
                    }
                });
            }
        });
        controlPanel.add(enabled);

        final JCheckBox actionEnabled = new JCheckBox("action enabled");
        actionEnabled.setSelected(true);
        actionEnabled.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        scan(TestCommandButtons.this);
                        repaint();
                    }

                    private void scan(Container c) {
                        for (int i = 0; i < c.getComponentCount(); i++) {
                            Component child = c.getComponent(i);
                            if (child instanceof AbstractCommandButton)
                                ((AbstractCommandButton) child)
                                        .getActionModel().setEnabled(
                                                actionEnabled.isSelected());
                            if (child instanceof Container)
                                scan((Container) child);
                        }
                    }
                });
            }
        });
        controlPanel.add(actionEnabled);

        final JCheckBox popupEnabled = new JCheckBox("popup enabled");
        popupEnabled.setSelected(true);
        popupEnabled.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        scan(TestCommandButtons.this);
                        repaint();
                    }

                    private void scan(Container c) {
                        for (int i = 0; i < c.getComponentCount(); i++) {
                            Component child = c.getComponent(i);
                            if (child instanceof JCommandButton)
                                ((JCommandButton) child).getPopupModel()
                                        .setEnabled(popupEnabled.isSelected());
                            if (child instanceof Container)
                                scan((Container) child);
                        }
                    }
                });
            }
        });
        controlPanel.add(popupEnabled);

        final JCheckBox flat = new JCheckBox("flat");
        flat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        scan(TestCommandButtons.this);
                        repaint();
                    }

                    private void scan(Container c) {
                        for (int i = 0; i < c.getComponentCount(); i++) {
                            Component child = c.getComponent(i);
                            if (child instanceof AbstractCommandButton)
                                ((AbstractCommandButton) child).setFlat(flat
                                        .isSelected());
                            if (child instanceof Container)
                                scan((Container) child);
                        }
                    }
                });
            }
        });
        controlPanel.add(flat);

        final JCheckBox downward = new JCheckBox("downward");
        downward.setSelected(true);
        downward.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        scan(TestCommandButtons.this);
                        repaint();
                    }

                    private void scan(Container c) {
                        for (int i = 0; i < c.getComponentCount(); i++) {
                            Component child = c.getComponent(i);
                            if (child instanceof JCommandButton)
                                ((JCommandButton) child)
                                        .setPopupOrientationKind(downward
                                                .isSelected() ? CommandButtonPopupOrientationKind.DOWNWARD
                                                : CommandButtonPopupOrientationKind.SIDEWARD);
                            if (child instanceof Container)
                                scan((Container) child);
                        }
                    }
                });
            }
        });
        controlPanel.add(downward);
    }

    /**
     * Main method for testing.
     * 
     * @param args
     *            Ignored.
     * @throws UnsupportedLookAndFeelException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws ClassNotFoundException
     */
    public static void main(String[] args) throws ClassNotFoundException,
            InstantiationException, IllegalAccessException,
            UnsupportedLookAndFeelException {

        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                TestCommandButtons frame = new TestCommandButtons();
                frame.setSize(800, 400);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            }
        });
    }
}
