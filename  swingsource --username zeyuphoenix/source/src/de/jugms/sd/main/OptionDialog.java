/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * OptionDialog.java
 *
 * Created on 01.11.2009, 13:36:05
 */
package de.jugms.sd.main;

/**
 * 
 * @author hansolo
 */
public class OptionDialog extends javax.swing.JDialog implements java.awt.event.ActionListener {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    de.jugms.sd.main.MainFrame father;

    /** Creates new form OptionDialog */
    public OptionDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        father = (de.jugms.sd.main.MainFrame) parent;
        initComponents();
        setResizable(false);

        optionPanel.checkBoxTranslucent.setEnabled(de.jugms.sd.tools.SDHelper.INSTANCE
                .isTranslucencySupported());

        optionPanel.radioButtonBlue.addActionListener(this);
        optionPanel.radioButtonRed.addActionListener(this);
        optionPanel.radioButtonOrange.addActionListener(this);
        optionPanel.radioButtonYellow.addActionListener(this);
        optionPanel.radioButtonGreen.addActionListener(this);
        optionPanel.radioButtonGray.addActionListener(this);

        optionPanel.radioButtonSharp.addActionListener(this);
        optionPanel.radioButtonRounded.addActionListener(this);
        optionPanel.radioButtonPill.addActionListener(this);

        optionPanel.checkBoxTranslucent.addActionListener(this);
        optionPanel.checkBoxReflection.addActionListener(this);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        optionPanel = new de.jugms.sd.components.panels.SDOptionPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        optionPanel.setName("optionPanel"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(
                javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(optionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 303,
                        javax.swing.GroupLayout.PREFERRED_SIZE));
        layout.setVerticalGroup(layout.createParallelGroup(
                javax.swing.GroupLayout.Alignment.LEADING).addComponent(optionPanel,
                javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
                javax.swing.GroupLayout.PREFERRED_SIZE));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public de.jugms.sd.components.panels.SDOptionPanel optionPanel;

    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(java.awt.event.ActionEvent event) {
        if (event.getSource().equals(optionPanel.radioButtonBlue)) {
            de.jugms.sd.tools.SDGlobals.INSTANCE
                    .setActiveGradient(de.jugms.sd.tools.SDTwoColorGradients.BLUE);
            de.jugms.sd.tools.SDGlobals.INSTANCE
                    .setActiveTranspaGradient(de.jugms.sd.tools.SDTwoColorGradients.BLUE_TRANSPA);
            father.glass.refresh();
            father.inputPanel.progressBar.refresh();
            father.bubblePanel.refresh();
            father.repaint();
        }
        if (event.getSource().equals(optionPanel.radioButtonRed)) {
            de.jugms.sd.tools.SDGlobals.INSTANCE
                    .setActiveGradient(de.jugms.sd.tools.SDTwoColorGradients.RED);
            de.jugms.sd.tools.SDGlobals.INSTANCE
                    .setActiveTranspaGradient(de.jugms.sd.tools.SDTwoColorGradients.RED_TRANSPA);
            father.glass.refresh();
            father.inputPanel.progressBar.refresh();
            father.bubblePanel.refresh();
            father.repaint();
        }
        if (event.getSource().equals(optionPanel.radioButtonOrange)) {
            de.jugms.sd.tools.SDGlobals.INSTANCE
                    .setActiveGradient(de.jugms.sd.tools.SDTwoColorGradients.ORANGE);
            de.jugms.sd.tools.SDGlobals.INSTANCE
                    .setActiveTranspaGradient(de.jugms.sd.tools.SDTwoColorGradients.ORANGE_TRANSPA);
            father.glass.refresh();
            father.inputPanel.progressBar.refresh();
            father.bubblePanel.refresh();
            father.repaint();
        }
        if (event.getSource().equals(optionPanel.radioButtonYellow)) {
            de.jugms.sd.tools.SDGlobals.INSTANCE
                    .setActiveGradient(de.jugms.sd.tools.SDTwoColorGradients.YELLOW);
            de.jugms.sd.tools.SDGlobals.INSTANCE
                    .setActiveTranspaGradient(de.jugms.sd.tools.SDTwoColorGradients.YELLOW_TRANSPA);
            father.glass.refresh();
            father.inputPanel.progressBar.refresh();
            father.bubblePanel.refresh();
            father.repaint();
        }
        if (event.getSource().equals(optionPanel.radioButtonGreen)) {
            de.jugms.sd.tools.SDGlobals.INSTANCE
                    .setActiveGradient(de.jugms.sd.tools.SDTwoColorGradients.GREEN);
            de.jugms.sd.tools.SDGlobals.INSTANCE
                    .setActiveTranspaGradient(de.jugms.sd.tools.SDTwoColorGradients.GREEN_TRANSPA);
            father.glass.refresh();
            father.inputPanel.progressBar.refresh();
            father.bubblePanel.refresh();
            father.repaint();
        }
        if (event.getSource().equals(optionPanel.radioButtonGray)) {
            de.jugms.sd.tools.SDGlobals.INSTANCE
                    .setActiveGradient(de.jugms.sd.tools.SDTwoColorGradients.GRAY);
            de.jugms.sd.tools.SDGlobals.INSTANCE
                    .setActiveTranspaGradient(de.jugms.sd.tools.SDTwoColorGradients.GRAY_TRANSPA);
            father.glass.refresh();
            father.inputPanel.progressBar.refresh();
            father.bubblePanel.refresh();
            father.repaint();
        }

        if (event.getSource().equals(optionPanel.checkBoxTranslucent)) {
            father.setTranslucent(optionPanel.checkBoxTranslucent.isSelected());
            father.repaint();
        }

        if (event.getSource().equals(optionPanel.checkBoxReflection)) {
            father.inputPanel.progressBar
                    .setReflection(optionPanel.checkBoxReflection.isSelected());
            father.inputPanel.cancelButton.setReflection(optionPanel.checkBoxReflection
                    .isSelected());
            father.inputPanel.submitButton.setReflection(optionPanel.checkBoxReflection
                    .isSelected());
            father.inputPanel.revalidate();
            if (optionPanel.checkBoxReflection.isSelected()) {
                father.setSize(father.getWidth(), father.getPreferredSize().height
                        + father.inputPanel.cancelButton.getHeight() / 2);
            } else {
                father.setSize(father.getWidth(), father.getPreferredSize().height);
            }
            father.repaint();
        }

        if (event.getSource().equals(optionPanel.radioButtonSharp)) {
            father.inputPanel.textFieldFirstName.setRadiusType(de.jugms.sd.tools.RadiusType.SHARP);
            father.inputPanel.textFieldLastName.setRadiusType(de.jugms.sd.tools.RadiusType.SHARP);
            father.inputPanel.textFieldMail.setRadiusType(de.jugms.sd.tools.RadiusType.SHARP);
            father.inputPanel.textFieldPhone.setRadiusType(de.jugms.sd.tools.RadiusType.SHARP);
            father.inputPanel.textFieldSubject.setRadiusType(de.jugms.sd.tools.RadiusType.SHARP);
            father.inputPanel.cancelButton.setRadiusType(de.jugms.sd.tools.RadiusType.SHARP);
            father.inputPanel.submitButton.setRadiusType(de.jugms.sd.tools.RadiusType.SHARP);
            ((de.jugms.sd.components.buttons.SDTitleBarButton) father.titleBarPanel.closeButton)
                    .setRadiusType(de.jugms.sd.tools.RadiusType.SHARP);
            father.repaint();
        }

        if (event.getSource().equals(optionPanel.radioButtonRounded)) {
            father.inputPanel.textFieldFirstName
                    .setRadiusType(de.jugms.sd.tools.RadiusType.ROUNDED);
            father.inputPanel.textFieldLastName.setRadiusType(de.jugms.sd.tools.RadiusType.ROUNDED);
            father.inputPanel.textFieldMail.setRadiusType(de.jugms.sd.tools.RadiusType.ROUNDED);
            father.inputPanel.textFieldPhone.setRadiusType(de.jugms.sd.tools.RadiusType.ROUNDED);
            father.inputPanel.textFieldSubject.setRadiusType(de.jugms.sd.tools.RadiusType.ROUNDED);
            father.inputPanel.cancelButton.setRadiusType(de.jugms.sd.tools.RadiusType.ROUNDED);
            father.inputPanel.submitButton.setRadiusType(de.jugms.sd.tools.RadiusType.ROUNDED);
            ((de.jugms.sd.components.buttons.SDTitleBarButton) father.titleBarPanel.closeButton)
                    .setRadiusType(de.jugms.sd.tools.RadiusType.ROUNDED);
            father.repaint();
        }

        if (event.getSource().equals(optionPanel.radioButtonPill)) {
            father.inputPanel.textFieldFirstName.setRadiusType(de.jugms.sd.tools.RadiusType.PILL);
            father.inputPanel.textFieldLastName.setRadiusType(de.jugms.sd.tools.RadiusType.PILL);
            father.inputPanel.textFieldMail.setRadiusType(de.jugms.sd.tools.RadiusType.PILL);
            father.inputPanel.textFieldPhone.setRadiusType(de.jugms.sd.tools.RadiusType.PILL);
            father.inputPanel.textFieldSubject.setRadiusType(de.jugms.sd.tools.RadiusType.PILL);
            father.inputPanel.cancelButton.setRadiusType(de.jugms.sd.tools.RadiusType.PILL);
            father.inputPanel.submitButton.setRadiusType(de.jugms.sd.tools.RadiusType.PILL);
            ((de.jugms.sd.components.buttons.SDTitleBarButton) father.titleBarPanel.closeButton)
                    .setRadiusType(de.jugms.sd.tools.RadiusType.PILL);
            father.repaint();
        }
        repaint();
    }
}
