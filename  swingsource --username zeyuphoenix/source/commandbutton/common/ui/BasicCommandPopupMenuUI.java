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
package common.ui;

import java.awt.AlphaComposite;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

import javax.swing.CellRendererPane;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.ComponentUI;

import utils.FlamingoUtilities;

import common.AbstractCommandButton;
import common.JCommandButtonPanel;
import common.popup.JCommandPopupMenu;

public class BasicCommandPopupMenuUI extends BasicPopupPanelUI {
    /**
     * The associated popup menu
     */
    protected JCommandPopupMenu popupMenu;

    protected ChangeListener popupMenuChangeListener;

    /**
     * Popup panel that hosts groups of icons.
     * 
     * @author Kirill Grouchnikov
     */
    protected static class ScrollableCommandButtonPanel extends JComponent {
        /**
         * 
         */
        private static final long serialVersionUID = 871745542888995465L;

        /**
         * Maximum dimension of <code>this</code> popup gallery.
         */
        protected Dimension maxDimension;

        /**
         * The internal panel that hosts the icon command buttons. Is hosted in
         * the {@link #scroll}.
         */
        protected JCommandButtonPanel iconPanel;

        /**
         * The maximum number of visible button rows.
         */
        protected int maxVisibleButtonRows;

        /**
         * Scroll panel that hosts {@link #iconPanel}.
         */
        protected JScrollPane scroll;

        /**
         * Creates new a icon popup panel.
         * 
         * @param iconPanel
         *            The internal panel that hosts icon command buttons.
         * @param maxButtonColumns
         *            The maximum number of button columns.
         * @param maxVisibleButtonRows
         *            The maximum number of visible button rows.
         */
        public ScrollableCommandButtonPanel(JCommandButtonPanel iconPanel,
                int maxButtonColumns, int maxVisibleButtonRows) {
            this.iconPanel = iconPanel;
            this.iconPanel.setMaxButtonColumns(maxButtonColumns);
            this.maxVisibleButtonRows = maxVisibleButtonRows;

            int maxButtonWidth = 0;
            int maxButtonHeight = 0;
            int groupCount = iconPanel.getGroupCount();
            for (int i = 0; i < groupCount; i++) {
                for (AbstractCommandButton button : iconPanel
                        .getGroupButtons(i)) {
                    maxButtonWidth = Math.max(maxButtonWidth, button
                            .getPreferredSize().width);
                    maxButtonHeight = Math.max(maxButtonHeight, button
                            .getPreferredSize().height);
                }
            }

            updateMaxDimension();

            this.scroll = new JScrollPane(this.iconPanel,
                    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            this.scroll.setBorder(new EmptyBorder(0, 0, 0, 0));
            this.iconPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
            this.scroll.setOpaque(false);
            this.scroll.getViewport().setOpaque(false);
            this.setLayout(new IconPopupLayout());

            this.add(this.scroll);

            this.setBorder(new Border() {
                @Override
                public Insets getBorderInsets(Component c) {
                    return new Insets(0, 0, 1, 0);
                }

                @Override
                public boolean isBorderOpaque() {
                    return true;
                }

                @Override
                public void paintBorder(Component c, Graphics g, int x, int y,
                        int width, int height) {
                    g.setColor(FlamingoUtilities.getBorderColor());
                    g.drawLine(x, y + height - 1, x + width, y + height - 1);
                }
            });
        }

        /**
         * Updates the max dimension of this panel. This method is for internal
         * use only.
         */
        public void updateMaxDimension() {
            if (this.iconPanel == null)
                return;
            this.iconPanel.setPreferredSize(null);
            Dimension prefIconPanelDim = this.iconPanel.getPreferredSize();
            // fix for issue 13 - respect the gaps and insets
            BasicCommandButtonPanelUI panelUI = (BasicCommandButtonPanelUI) iconPanel
                    .getUI();
            int titlePanelCount = iconPanel.isToShowGroupLabels() ? 1 : 0;
            this.maxDimension = new Dimension(prefIconPanelDim.width, panelUI
                    .getPreferredHeight(this.maxVisibleButtonRows,
                            titlePanelCount));
            this.setPreferredSize(null);
        }

        /**
         * Layout manager for <code>this</code> popup gallery.
         * 
         * @author Kirill Grouchnikov
         */
        protected class IconPopupLayout implements LayoutManager {
            /*
             * (non-Javadoc)
             * 
             * @see java.awt.LayoutManager#addLayoutComponent(java.lang.String,
             *      java.awt.Component)
             */
            public void addLayoutComponent(String name, Component comp) {
            }

            /*
             * (non-Javadoc)
             * 
             * @see java.awt.LayoutManager#removeLayoutComponent(java.awt.Component)
             */
            public void removeLayoutComponent(Component comp) {
            }

            /*
             * (non-Javadoc)
             * 
             * @see java.awt.LayoutManager#layoutContainer(java.awt.Container)
             */
            public void layoutContainer(Container parent) {
                Insets insets = parent.getInsets();
                int left = insets.left;
                int right = insets.right;
                int top = insets.top;
                int bottom = insets.bottom;
                scroll.setBounds(left, top, parent.getWidth() - left - right,
                        parent.getHeight() - top - bottom);
            }

            /*
             * (non-Javadoc)
             * 
             * @see java.awt.LayoutManager#minimumLayoutSize(java.awt.Container)
             */
            public Dimension minimumLayoutSize(Container parent) {
                return this.preferredLayoutSize(parent);
            }

            /*
             * (non-Javadoc)
             * 
             * @see java.awt.LayoutManager#preferredLayoutSize(java.awt.Container)
             */
            public Dimension preferredLayoutSize(Container parent) {
                Insets insets = parent.getInsets();
                int left = insets.left;
                int right = insets.right;
                int top = insets.top;
                int bottom = insets.bottom;
                Dimension controlPanelDim = iconPanel.getPreferredSize();
                if (controlPanelDim == null)
                    controlPanelDim = new Dimension(0, 0);
                int w = Math.min(controlPanelDim.width, maxDimension.width)
                        + left + right;
                int h = Math.min(controlPanelDim.height, maxDimension.height)
                        + top + bottom;
                if (h == (maxDimension.height + top + bottom)) {
                    int scrollBarWidth = UIManager.getInt("ScrollBar.width");
                    if (scrollBarWidth == 0) {
                        // Nimbus
                        scrollBarWidth = new JScrollBar(JScrollBar.VERTICAL)
                                .getPreferredSize().width;
                    }
                    w += scrollBarWidth;
                    // h += 5;
                }
                return new Dimension(w, h);
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.plaf.ComponentUI#createUI(javax.swing.JComponent)
     */
    public static ComponentUI createUI(JComponent c) {
        return new BasicCommandPopupMenuUI();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jvnet.flamingo.common.ui.BasicPopupPanelUI#installUI(javax.swing.
     *      JComponent)
     */
    @Override
    public void installUI(JComponent c) {
        this.popupMenu = (JCommandPopupMenu) c;
        super.installUI(this.popupMenu);

        this.popupMenu.setLayout(this.createLayoutManager());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jvnet.flamingo.common.ui.BasicPopupPanelUI#installComponents()
     */
    @Override
    protected void installComponents() {
        super.installComponents();

        syncComponents();
    }

    protected void syncComponents() {
        if (this.popupMenu.hasCommandButtonPanel()) {
            ScrollableCommandButtonPanel iconPanel = createScrollableButtonPanel();
            this.popupMenu.add(iconPanel);
        }

        java.util.List<Component> menuComponents = this.popupMenu
                .getMenuComponents();
        if (menuComponents != null) {
            for (Component menuComponent : menuComponents) {
                this.popupMenu.add(menuComponent);
            }
        }
    }

    protected ScrollableCommandButtonPanel createScrollableButtonPanel() {
        return new ScrollableCommandButtonPanel(this.popupMenu
                .getMainButtonPanel(), this.popupMenu.getMaxButtonColumns(),
                this.popupMenu.getMaxVisibleButtonRows());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jvnet.flamingo.common.ui.BasicPopupPanelUI#uninstallComponents()
     */
    @Override
    protected void uninstallComponents() {
        this.popupMenu.removeAll();
        super.uninstallComponents();
    }

    @Override
    protected void installListeners() {
        super.installListeners();

        this.popupMenuChangeListener = new ChangeListener() {
            /**
             * @param e
             */
            @Override
            public void stateChanged(ChangeEvent e) {
                popupMenu.removeAll();
                syncComponents();
            }
        };
        this.popupMenu.addChangeListener(this.popupMenuChangeListener);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jvnet.flamingo.common.ui.BasicPopupPanelUI#uninstallListeners()
     */
    @Override
    protected void uninstallListeners() {
        this.popupMenu.removeChangeListener(this.popupMenuChangeListener);
        this.popupMenuChangeListener = null;

        super.uninstallListeners();
    }

    protected LayoutManager createLayoutManager() {
        return new PopupMenuLayoutManager();
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        this.paintIconGutterBackground(g);
        this.paintIconGutterSeparator(g);
    }

    protected void paintIconGutterSeparator(Graphics g) {
        CellRendererPane buttonRendererPane = new CellRendererPane();
        JSeparator rendererSeparator = new JSeparator(JSeparator.VERTICAL);

        buttonRendererPane.setBounds(0, 0, this.popupMenu.getWidth(),
                this.popupMenu.getHeight());
        buttonRendererPane.paintComponent(g, rendererSeparator, this.popupMenu,
                26, 2, 2, this.popupMenu.getHeight() - 4, true);
    }

    protected void paintIconGutterBackground(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.clipRect(0, 0, 28, this.popupMenu.getHeight());
        AffineTransform at = AffineTransform.getTranslateInstance(0,
                this.popupMenu.getHeight());
        at.rotate(-Math.PI / 2);
        g2d.transform(at);

        g2d.setComposite(AlphaComposite.SrcOver.derive(0.7f));

        FlamingoUtilities.renderSurface(g2d, this.popupMenu, new Rectangle(0,
                0, this.popupMenu.getHeight(), 50), false, false, false);
        g2d.dispose();
    }

    protected static class PopupMenuLayoutManager implements LayoutManager {
        @Override
        public void addLayoutComponent(String name, Component comp) {
        }

        @Override
        public void removeLayoutComponent(Component comp) {
        }

        @Override
        public Dimension minimumLayoutSize(Container parent) {
            return null;
        }

        @Override
        public Dimension preferredLayoutSize(Container parent) {
            int height = 0;
            int width = 0;
            for (int i = 0; i < parent.getComponentCount(); i++) {
                Dimension pref = parent.getComponent(i).getPreferredSize();
                height += pref.height;
                width = Math.max(width, pref.width);
            }

            Insets ins = parent.getInsets();
            return new Dimension(width + ins.left + ins.right, height + ins.top
                    + ins.bottom);
        }

        @Override
        public void layoutContainer(Container parent) {
            Insets ins = parent.getInsets();

            int bottomY = parent.getHeight() - ins.bottom;
            boolean hasMainPanel = ((JCommandPopupMenu) parent)
                    .hasCommandButtonPanel();
            int lastMenuIndex = hasMainPanel ? 1 : 0;
            for (int i = parent.getComponentCount() - 1; i >= lastMenuIndex; i--) {
                Component comp = parent.getComponent(i);
                Dimension pref = comp.getPreferredSize();
                int topY = bottomY - pref.height;
                comp.setBounds(ins.left, topY, parent.getWidth() - ins.left
                        - ins.right, bottomY - topY);
                bottomY = topY;
            }

            if (hasMainPanel) {
                Component mainButtonPanelComp = parent.getComponent(0);
                mainButtonPanelComp.setBounds(ins.left, ins.top, parent
                        .getWidth()
                        - ins.left - ins.right, bottomY - ins.top);
                mainButtonPanelComp.invalidate();
                mainButtonPanelComp.validate();
                mainButtonPanelComp.doLayout();
            }
        }
    }
}