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
package utils;

import java.awt.AWTEvent;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.ActionListener;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EventListener;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.event.EventListenerList;

import common.popup.JPopupPanel;
import common.popup.PopupPanelManager;
import common.popup.PopupPanelManager.PopupInfo;

public class KeyTipManager {
    boolean isShowingKeyTips;

    List<KeyTipChain> keyTipChains;

    protected EventListenerList listenerList;

    private static final KeyTipManager instance = new KeyTipManager();

    public interface KeyTipLinkTraversal {
        public KeyTipChain getNextChain();
    }

    public static interface KeyTipListener extends EventListener {
        public void keyTipsShown(KeyTipEvent event);

        public void keyTipsHidden(KeyTipEvent event);
    }

    public static class KeyTipEvent extends AWTEvent {
        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        public KeyTipEvent(Object source, int id) {
            super(source, id);
        }
    }

    /**
     * Annotation to mark a command button that shows UI content with associated
     * keytips on clicking its action area. Can be used to associate keytips
     * with menu command buttons in the popup menu shown when the ribbon gallery
     * is expanded.
     * 
     * @author Kirill Grouchnikov
     */
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    public static @interface HasNextKeyTipChain {
    }

    public class KeyTipLink {
        public String keyTipString;

        public JComponent comp;

        public Point prefAnchorPoint;

        public ActionListener onActivated;

        public KeyTipLinkTraversal traversal;

        public boolean enabled;
    }

    public class KeyTipChain {
        private List<KeyTipLink> links;

        public int keyTipLookupIndex;

        public JComponent chainParentComponent;

        public KeyTipChain(JComponent chainParentComponent) {
            this.chainParentComponent = chainParentComponent;
            this.links = new ArrayList<KeyTipLink>();
            this.keyTipLookupIndex = 0;
        }

        public void addLink(KeyTipLink link) {
            this.links.add(link);
        }
    }

    public static KeyTipManager defaultManager() {
        return instance;
    }

    private KeyTipManager() {
        this.isShowingKeyTips = false;
        this.keyTipChains = new ArrayList<KeyTipChain>();
        this.listenerList = new EventListenerList();
    }

    public boolean isShowingKeyTips() {
        return !this.keyTipChains.isEmpty();
    }



    public Collection<KeyTipLink> getCurrentlyShownKeyTips() {
        if (this.keyTipChains.isEmpty())
            return Collections.emptyList();
        return Collections.unmodifiableCollection(this.keyTipChains
                .get(this.keyTipChains.size() - 1).links);
    }

    public KeyTipChain getCurrentlyShownKeyTipChain() {
        if (this.keyTipChains.isEmpty())
            return null;
        return this.keyTipChains.get(this.keyTipChains.size() - 1);
    }

    public void showPreviousChain() {
        if (this.keyTipChains.isEmpty())
            return;
        this.keyTipChains.remove(this.keyTipChains.size() - 1);
        // System.out.println("After PC " + this.keyTipChains.size());
        repaintWindows();
    }

    private void repaintWindows() {
        for (Window window : Window.getWindows()) {
            window.repaint();
        }
        List<PopupInfo> popups = PopupPanelManager.defaultManager()
                .getShownPath();
        for (PopupPanelManager.PopupInfo popup : popups) {
            JPopupPanel popupPanel = popup.getPopupPanel();
            popupPanel.paintImmediately(new Rectangle(0, 0, popupPanel
                    .getWidth(), popupPanel.getHeight()));
        }
    }

    public void addKeyTipListener(KeyTipListener keyTipListener) {
        this.listenerList.add(KeyTipListener.class, keyTipListener);
    }

    public void removeKeyTipListener(KeyTipListener keyTipListener) {
        this.listenerList.remove(KeyTipListener.class, keyTipListener);
    }
}