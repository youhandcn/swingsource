/*
 * Copyright (c) 2005-2009 Flamingo Kirill Grouchnikov. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met:
 * 
 *  o Redistributions of source code must retain the above copyright notice, 
 *    this list of conditions and the following disclaimer. 
 *     
 *  o Redistributions in binary form must reproduce the above copyright notice, 
 *    this list of conditions and the following disclaimer in the documentation 
 *    and/or other materials provided with the distribution. 
 *     
 *  o Neither the name of Flamingo Kirill Grouchnikov nor the names of 
 *    its contributors may be used to endorse or promote products derived 
 *    from this software without specific prior written permission. 
 *     
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, 
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR 
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR 
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, 
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, 
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; 
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, 
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. 
 */
package common;

import javax.swing.UIManager;

import common.icon.ResizableIcon;
import common.popup.JCommandPopupMenu;
import common.ui.BasicCommandMenuButtonUI;
import common.ui.CommandButtonUI;

/**
 * Command buttons that can be placed in {@link JCommandPopupMenu}s and in the
 * primary / secondary panels of the ribbon application menu.
 * 
 * @author Kirill Grouchnikov
 * @see JCommandPopupMenu#addMenuButton(JCommandMenuButton)
 */
public class JCommandMenuButton extends JCommandButton {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * The UI class ID string.
     */
    public static final String uiClassID = "CommandMenuButtonUI";

    /**
     * Creates a new command menu button.
     * 
     * @param title
     *            Command menu button title.
     * @param icon
     *            Command menu button icon.
     */
    public JCommandMenuButton(String title, ResizableIcon icon) {
        super(title, icon);
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.JComponent#getUIClassID()
     */
    @Override
    public String getUIClassID() {
        return uiClassID;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.JComponent#updateUI()
     */
    @Override
    public void updateUI() {
        if (UIManager.get(getUIClassID()) != null) {
            setUI((CommandButtonUI) UIManager.getUI(this));
        } else {
            setUI(BasicCommandMenuButtonUI.createUI(this));
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jvnet.flamingo.common.JCommandButton#canHaveBothKeyTips()
     */
    @Override
    boolean canHaveBothKeyTips() {
        return true;
    }
}
