/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * SDInputPanel.java
 *
 * Created on 16.09.2009, 20:39:36
 */
package de.jugms.sd.components.panels;

/**
 * 
 * @author hansolo
 */
public class SDInputPanel extends javax.swing.JPanel {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /** Creates new form SDInputPanel */
    public SDInputPanel() {
        initComponents();
        setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(0, 0, 0, 0)));
        progressBar.setTilted(true);
        scrollPaneComment.setOpaque(false);
        scrollPaneComment.setBorder(null);
        de.jugms.sd.components.panels.SDScrollPaneViewport viewPort = new de.jugms.sd.components.panels.SDScrollPaneViewport();
        viewPort.setView(textPaneComment);
        scrollPaneComment.setViewport(viewPort);
        bar = new de.jugms.sd.components.misc.SDScrollBar();
        scrollPaneComment.setVerticalScrollBar(bar);
        scrollPaneComment.setHorizontalScrollBar(bar);
    }

    @Override
    public String toString() {
        return "SDInputPanel";
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

        buttonGroupContact = new javax.swing.ButtonGroup();
        labelContact = new javax.swing.JLabel();
        scrollPaneComment = new javax.swing.JScrollPane();
        textPaneComment = new de.jugms.sd.components.misc.SDTextPane();
        cancelButton = new de.jugms.sd.components.buttons.SDButton();
        submitButton = new de.jugms.sd.components.buttons.SDButton();
        textFieldMail = new de.jugms.sd.components.textfields.SDTextField();
        textFieldPhone = new de.jugms.sd.components.textfields.SDTextField();
        textFieldSubject = new de.jugms.sd.components.textfields.SDTextField();
        textFieldFirstName = new de.jugms.sd.components.textfields.SDTextField();
        textFieldLastName = new de.jugms.sd.components.textfields.SDTextField();
        progressBar = new de.jugms.sd.components.misc.SDGlassProgressBar();
        radioButtonMail = new de.jugms.sd.components.buttons.SDRadioButton();
        radioButtonPhone = new de.jugms.sd.components.buttons.SDRadioButton();

        setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.gray));
        setOpaque(false);

        labelContact.setForeground(new java.awt.Color(255, 255, 255));
        labelContact.setText("Please contact me by");
        labelContact.setName("labelContact"); // NOI18N

        scrollPaneComment.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 1, 2, 1));
        scrollPaneComment.setName("scrollPaneComment"); // NOI18N
        scrollPaneComment.setOpaque(false);

        textPaneComment.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 1, 2, 1));
        textPaneComment.setText("comment...");
        textPaneComment.setToolTipText("e.g. \"may the force be with you.....always\"");
        textPaneComment.setAutoscrolls(false);
        textPaneComment.setMargin(new java.awt.Insets(2, 0, 2, 0));
        textPaneComment.setName("textPaneComment"); // NOI18N
        textPaneComment.setOpaque(false);
        textPaneComment.setPreferredSize(new java.awt.Dimension(12, 6));
        scrollPaneComment.setViewportView(textPaneComment);

        cancelButton.setText("Cancel");
        cancelButton.setMaximumSize(new java.awt.Dimension(86, 25));
        cancelButton.setMinimumSize(new java.awt.Dimension(86, 25));
        cancelButton.setName("cancelButton"); // NOI18N

        submitButton.setText("Submit");
        submitButton.setEnabled(false);
        submitButton.setMaximumSize(new java.awt.Dimension(88, 25));
        submitButton.setMinimumSize(new java.awt.Dimension(88, 25));
        submitButton.setName("submitButton"); // NOI18N

        textFieldMail.setToolTipText("e.g. \"luke.skywalker@alliance.net");
        textFieldMail.setName("textFieldMail"); // NOI18N

        textFieldPhone.setToolTipText("e.g. \"+49 1802 100101\"");
        textFieldPhone.setName("textFieldPhone"); // NOI18N
        textFieldPhone.setType(de.jugms.sd.components.textfields.SDTextField.ICON_TYPE.PHONE);

        textFieldSubject.setToolTipText("e.g. \"The force\"");
        textFieldSubject.setName("textFieldSubject"); // NOI18N
        textFieldSubject.setType(de.jugms.sd.components.textfields.SDTextField.ICON_TYPE.PEN);

        textFieldFirstName.setToolTipText("e.g. \"Skywalker\"");
        textFieldFirstName.setDefaultText("last name");
        textFieldFirstName.setName("textFieldFirstName"); // NOI18N
        textFieldFirstName.setType(de.jugms.sd.components.textfields.SDTextField.ICON_TYPE.NO_ICON);

        textFieldLastName.setToolTipText("e.g. \"Luke\"");
        textFieldLastName.setDefaultText("first name");
        textFieldLastName.setName("textFieldLastName"); // NOI18N
        textFieldLastName.setType(de.jugms.sd.components.textfields.SDTextField.ICON_TYPE.NO_ICON);

        progressBar.setInfiniteText("submission");
        progressBar.setName("progressBar"); // NOI18N
        progressBar.setShowInfiniteText(true);

        javax.swing.GroupLayout progressBarLayout = new javax.swing.GroupLayout(progressBar);
        progressBar.setLayout(progressBarLayout);
        progressBarLayout.setHorizontalGroup(progressBarLayout.createParallelGroup(
                javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 162, Short.MAX_VALUE));
        progressBarLayout.setVerticalGroup(progressBarLayout.createParallelGroup(
                javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 22, Short.MAX_VALUE));

        buttonGroupContact.add(radioButtonMail);
        radioButtonMail.setSelected(true);
        radioButtonMail.setName("radioButtonMail"); // NOI18N

        buttonGroupContact.add(radioButtonPhone);
        radioButtonPhone.setName("radioButtonPhone"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout
                .setHorizontalGroup(layout
                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(
                                javax.swing.GroupLayout.Alignment.TRAILING,
                                layout
                                        .createSequentialGroup()
                                        .addContainerGap()
                                        .addGroup(
                                                layout
                                                        .createParallelGroup(
                                                                javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(
                                                                textFieldSubject,
                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                362, Short.MAX_VALUE)
                                                        .addComponent(
                                                                textFieldLastName,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                362, Short.MAX_VALUE)
                                                        .addComponent(
                                                                textFieldFirstName,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                362, Short.MAX_VALUE)
                                                        .addComponent(
                                                                scrollPaneComment,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                362, Short.MAX_VALUE)
                                                        .addComponent(
                                                                labelContact,
                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                362, Short.MAX_VALUE)
                                                        .addGroup(
                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                layout
                                                                        .createSequentialGroup()
                                                                        .addComponent(
                                                                                cancelButton,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addGap(18, 18, 18)
                                                                        .addComponent(
                                                                                progressBar,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                162,
                                                                                Short.MAX_VALUE)
                                                                        .addGap(18, 18, 18)
                                                                        .addComponent(
                                                                                submitButton,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(
                                                                layout
                                                                        .createSequentialGroup()
                                                                        .addGroup(
                                                                                layout
                                                                                        .createParallelGroup(
                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                        .addComponent(
                                                                                                textFieldPhone,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                332,
                                                                                                Short.MAX_VALUE)
                                                                                        .addComponent(
                                                                                                textFieldMail,
                                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                332,
                                                                                                Short.MAX_VALUE))
                                                                        .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addGroup(
                                                                                layout
                                                                                        .createParallelGroup(
                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                false)
                                                                                        .addComponent(
                                                                                                radioButtonPhone,
                                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                0,
                                                                                                0,
                                                                                                Short.MAX_VALUE)
                                                                                        .addComponent(
                                                                                                radioButtonMail,
                                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                24,
                                                                                                Short.MAX_VALUE))))
                                        .addContainerGap()));
        layout
                .setVerticalGroup(layout
                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(
                                layout
                                        .createSequentialGroup()
                                        .addContainerGap()
                                        .addComponent(textFieldLastName,
                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(
                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(textFieldFirstName,
                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(labelContact)
                                        .addGap(7, 7, 7)
                                        .addGroup(
                                                layout
                                                        .createParallelGroup(
                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(
                                                                layout
                                                                        .createSequentialGroup()
                                                                        .addComponent(
                                                                                textFieldMail,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addGroup(
                                                                                layout
                                                                                        .createParallelGroup(
                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                        .addComponent(
                                                                                                textFieldPhone,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                        .addComponent(
                                                                                                radioButtonPhone,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                21,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                        .addComponent(
                                                                radioButtonMail,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                22,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(
                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(textFieldSubject,
                                                javax.swing.GroupLayout.PREFERRED_SIZE, 22,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(
                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(scrollPaneComment,
                                                javax.swing.GroupLayout.PREFERRED_SIZE, 100,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(12, 12, 12)
                                        .addGroup(
                                                layout
                                                        .createParallelGroup(
                                                                javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addGroup(
                                                                layout
                                                                        .createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                false)
                                                                        .addComponent(
                                                                                cancelButton,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                        .addComponent(
                                                                                submitButton,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addComponent(
                                                                progressBar,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                Short.MAX_VALUE)));
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroupContact;
    public de.jugms.sd.components.buttons.SDButton cancelButton;
    private javax.swing.JLabel labelContact;
    public de.jugms.sd.components.misc.SDGlassProgressBar progressBar;
    public de.jugms.sd.components.buttons.SDRadioButton radioButtonMail;
    public de.jugms.sd.components.buttons.SDRadioButton radioButtonPhone;
    public javax.swing.JScrollPane scrollPaneComment;
    public de.jugms.sd.components.buttons.SDButton submitButton;
    public de.jugms.sd.components.textfields.SDTextField textFieldFirstName;
    public de.jugms.sd.components.textfields.SDTextField textFieldLastName;
    public de.jugms.sd.components.textfields.SDTextField textFieldMail;
    public de.jugms.sd.components.textfields.SDTextField textFieldPhone;
    public de.jugms.sd.components.textfields.SDTextField textFieldSubject;
    public javax.swing.JTextPane textPaneComment;
    // End of variables declaration//GEN-END:variables
    private de.jugms.sd.components.misc.SDScrollBar bar;
}
