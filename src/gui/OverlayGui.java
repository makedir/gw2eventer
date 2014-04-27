/*
 * The MIT License
 *
 * Copyright 2014 mkdr <makedir@gmail.com>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package gui;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;

/**
 *
 * @author mkdr <makedir@gmail.com>
 */
public class OverlayGui extends javax.swing.JFrame {

    private TreeMap activeBEvents;
    private TreeMap activePreEvents;
    
    private HashMap activeBEventsIndexes;
    private HashMap activePreEventsIndexes;
    
    private GW2EventerGui mainGui;
    
    /**
     * Creates new form OverlayGui
     */
    public OverlayGui(GW2EventerGui mainGui) {
        
        this.mainGui = mainGui;
        
        this.activeBEvents = new TreeMap();
        this.activePreEvents = new TreeMap();
        
        this.activeBEventsIndexes = new HashMap();
        this.activePreEventsIndexes = new HashMap();
        
        initComponents();
        
        this.jLabelPreEvents.setVisible(false);
    }

    public void setTranslations(String activeBEventsLabel, String activePreEventsLabel) {
        
        this.jLabelBossEvents.setText(activeBEventsLabel);
        this.jLabelPreEvents.setText(activePreEventsLabel);
    }
    
    public void clearActive() {
        
        this.activeBEvents.clear();
        this.activePreEvents.clear();
        
        this.activeBEventsIndexes.clear();
        this.activePreEventsIndexes.clear();
    }
    
    public boolean containsActiveB(String activeB) {
        
        return this.activeBEvents.containsKey(activeB);
    }
    
    public boolean containsActivePre(String activePre) {
        
        return this.activePreEvents.containsKey(activePre);
    }
    
    public void addActiveB(String name, String color, int index) {
        
        this.activeBEvents.put(name, color);
        this.activeBEventsIndexes.put(name, index);
        this.activeBEventsIndexes.put(index + "", name);
    }
    
    public void addActivePreEvent(String name, String color, int index) {
        
        this.activePreEvents.put(name, color);
        this.activePreEventsIndexes.put(name, index);
        this.activePreEventsIndexes.put(index + "", name);
    }
    
    private void setLooted(int index, int preBoss) {
        
        boolean looted = false;
        
        if (preBoss == 1) {
            String name = (String) this.activePreEventsIndexes.get(index + "");
            String color = (String) this.activePreEvents.get(name);
            
            if (color.equals("yellow")) {
                looted = false;
                this.activePreEvents.remove(name);
                this.activePreEvents.put(name, "green");
            } else {
                looted = true;
                this.activePreEvents.remove(name);
                this.activePreEvents.put(name, "yellow");
            }
            
            if (this.containsActiveB(name)) {
                if (color.equals("yellow")) {
                    this.activeBEvents.remove(name);
                    this.activeBEvents.put(name, "green");
                } else {
                    this.activeBEvents.remove(name);
                    this.activeBEvents.put(name, "yellow");
                }
            }
        } else {
            String name = (String) this.activeBEventsIndexes.get(index + "");
            String color = (String) this.activeBEvents.get(name);
            
            if (color.equals("yellow")) {
                looted = false;
                this.activeBEvents.remove(name);
                this.activeBEvents.put(name, "green");
            } else {
                looted = true;
                this.activeBEvents.remove(name);
                this.activeBEvents.put(name, "yellow");
            }
            
            if (this.containsActivePre(name)) {
                if (color.equals("yellow")) {
                    this.activePreEvents.remove(name);
                    this.activePreEvents.put(name, "green");
                } else {
                    this.activePreEvents.remove(name);
                    this.activePreEvents.put(name, "yellow");
                }
            }
        }
        
        this.renderActive();
        
        this.mainGui.setLooted(index, looted);
    }
    
    public void renderActive() {
        
        this.setAlwaysOnTop(true);
        this.toFront();
        this.repaint();
        
        this.jToolBarActiveBs.removeAll();
        
        Iterator it = this.activeBEvents.entrySet().iterator();
                    
        while (it.hasNext()) {

            Map.Entry pairs = (Map.Entry) it.next();

            final String name = (String) pairs.getKey();
            String color = (String) pairs.getValue();
            
            final JLabel newLabel = new javax.swing.JLabel();
            newLabel.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
            newLabel.setForeground(new java.awt.Color(102, 255, 0));
            
            newLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/looted.png")));
            
            final int index = (int) this.activeBEventsIndexes.get(name);
            
            newLabel.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(java.awt.event.MouseEvent evt) {
                        setLooted(index, 0);
                        newLabel.setText("<html><b><font color=yellow>" + name + "</font></b></html>");
                    }
            });
            
            if (color.equals("yellow")) {
                newLabel.setText("<html><b><font color=yellow>" + name + "</font></b></html>");
            } else {
                newLabel.setText("<html><b>" + name + "</b></html>");
            }
            
            newLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
            this.jToolBarActiveBs.add(newLabel);
            
            //it.remove();
        }
        
        this.jToolBarActivePres.removeAll();
        
        it = this.activePreEvents.entrySet().iterator();
                    
        while (it.hasNext()) {

            Map.Entry pairs = (Map.Entry) it.next();

            final String name = (String) pairs.getKey();
            String color = (String) pairs.getValue();
            
            final JLabel newLabel = new javax.swing.JLabel();
            newLabel.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
            newLabel.setForeground(new java.awt.Color(102, 255, 0));
            
            newLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/looted.png")));
            
            final int index = (int) this.activePreEventsIndexes.get(name);
            
            newLabel.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mousePressed(java.awt.event.MouseEvent evt) {
                        setLooted(index, 1);
                        newLabel.setText("<html><b><font color=yellow>" + name + "</font></b></html>");
                    }
            });
            
            if (color.equals("yellow")) {
                newLabel.setText("<html><b><font color=yellow>" + name + "</font></b></html>");
            } else {
                newLabel.setText("<html><b>" + name + "</b></html>");
            }
            
            newLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
            this.jToolBarActivePres.add(newLabel);
            
            //it.remove();
        }
        
        pack();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelMenu = new javax.swing.JLabel();
        jToolBarMenu = new javax.swing.JToolBar();
        jLabel3 = new javax.swing.JLabel();
        jButtonMinimize = new javax.swing.JButton();
        jButtonMaximize = new javax.swing.JButton();
        jButtonMove = new javax.swing.JButton();
        jButtonClose = new javax.swing.JButton();
        jToolBarContent = new javax.swing.JToolBar();
        jLabelBossEvents = new javax.swing.JLabel();
        jToolBarActiveBs = new javax.swing.JToolBar();
        jLabelPreEvents = new javax.swing.JLabel();
        jToolBarActivePres = new javax.swing.JToolBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("GW2 Eventer overlay");
        setAlwaysOnTop(true);
        setFocusable(false);
        setFocusableWindowState(false);
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/events.png"))); // NOI18N
        jLabelMenu.setToolTipText("Menu");
        jLabelMenu.setFocusable(false);
        jLabelMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabelMenuMousePressed(evt);
            }
        });
        getContentPane().add(jLabelMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 20, -1, -1));

        jToolBarMenu.setBorder(null);
        jToolBarMenu.setFloatable(false);
        jToolBarMenu.setForeground(new java.awt.Color(255, 255, 255));
        jToolBarMenu.setFocusable(false);
        jToolBarMenu.setOpaque(false);

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Events");
        jLabel3.setFocusable(false);
        jToolBarMenu.add(jLabel3);

        jButtonMinimize.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jButtonMinimize.setForeground(new java.awt.Color(255, 255, 255));
        jButtonMinimize.setText("min");
        jButtonMinimize.setFocusable(false);
        jButtonMinimize.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonMinimize.setOpaque(false);
        jButtonMinimize.setPreferredSize(new java.awt.Dimension(30, 23));
        jButtonMinimize.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonMinimize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonMinimizeActionPerformed(evt);
            }
        });
        jToolBarMenu.add(jButtonMinimize);

        jButtonMaximize.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jButtonMaximize.setForeground(new java.awt.Color(255, 255, 255));
        jButtonMaximize.setText("max");
        jButtonMaximize.setFocusable(false);
        jButtonMaximize.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonMaximize.setOpaque(false);
        jButtonMaximize.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonMaximize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonMaximizeActionPerformed(evt);
            }
        });
        jToolBarMenu.add(jButtonMaximize);

        jButtonMove.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jButtonMove.setForeground(new java.awt.Color(255, 255, 255));
        jButtonMove.setText("move");
        jButtonMove.setFocusable(false);
        jButtonMove.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonMove.setOpaque(false);
        jButtonMove.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonMove.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jButtonMoveMouseDragged(evt);
            }
        });
        jToolBarMenu.add(jButtonMove);

        jButtonClose.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jButtonClose.setForeground(new java.awt.Color(255, 255, 255));
        jButtonClose.setText("X");
        jButtonClose.setFocusable(false);
        jButtonClose.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonClose.setOpaque(false);
        jButtonClose.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCloseActionPerformed(evt);
            }
        });
        jToolBarMenu.add(jButtonClose);

        getContentPane().add(jToolBarMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 246, -1));

        jToolBarContent.setFloatable(false);
        jToolBarContent.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jToolBarContent.setFocusable(false);
        jToolBarContent.setOpaque(false);

        jLabelBossEvents.setForeground(new java.awt.Color(255, 255, 255));
        jLabelBossEvents.setText("active events:");
        jLabelBossEvents.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabelBossEvents.setFocusable(false);
        jToolBarContent.add(jLabelBossEvents);

        jToolBarActiveBs.setFloatable(false);
        jToolBarActiveBs.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jToolBarActiveBs.setFocusable(false);
        jToolBarActiveBs.setOpaque(false);
        jToolBarContent.add(jToolBarActiveBs);

        jLabelPreEvents.setForeground(new java.awt.Color(255, 255, 255));
        jLabelPreEvents.setText("active pre Events:");
        jLabelPreEvents.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabelPreEvents.setFocusable(false);
        jToolBarContent.add(jLabelPreEvents);

        jToolBarActivePres.setFloatable(false);
        jToolBarActivePres.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jToolBarActivePres.setFocusable(false);
        jToolBarActivePres.setOpaque(false);
        jToolBarContent.add(jToolBarActivePres);

        getContentPane().add(jToolBarContent, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 23, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonMaximizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonMaximizeActionPerformed

        this.setSize(this.getWidth(), 600);
    }//GEN-LAST:event_jButtonMaximizeActionPerformed

    private void jButtonMinimizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonMinimizeActionPerformed

        this.setSize(this.getWidth(), 25);
    }//GEN-LAST:event_jButtonMinimizeActionPerformed

    private void jButtonCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCloseActionPerformed

        this.mainGui.setOverlayVisible(false);
    }//GEN-LAST:event_jButtonCloseActionPerformed

    private void jLabelMenuMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelMenuMousePressed

        if (this.jToolBarMenu.isVisible()) {
            this.jToolBarMenu.setVisible(false);
        } else {
            this.jToolBarMenu.setVisible(true);
        }
    }//GEN-LAST:event_jLabelMenuMousePressed

    private void jButtonMoveMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonMoveMouseDragged

        int x = this.getX();
        int y = this.getY();

        Point mouseLoc;
        Robot rob;

        try {
            rob = new Robot();
            mouseLoc = MouseInfo.getPointerInfo().getLocation();
            
            int newx = mouseLoc.x - 118;
            int newy = mouseLoc.y - 11;

            if (x <= 0) {
                newx = 20;
            }

            if (x >= 1720) {
                newx = 1700;
            }

            if (y <= 0) {
                newy = 20;
            }

            if (y >= 880) {
                newy = 860;
            }

            this.setLocation(newx, newy);
        } catch (AWTException ex) {
            Logger.getLogger(OverlayGui.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonMoveMouseDragged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonClose;
    private javax.swing.JButton jButtonMaximize;
    private javax.swing.JButton jButtonMinimize;
    private javax.swing.JButton jButtonMove;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabelBossEvents;
    private javax.swing.JLabel jLabelMenu;
    private javax.swing.JLabel jLabelPreEvents;
    private javax.swing.JToolBar jToolBarActiveBs;
    private javax.swing.JToolBar jToolBarActivePres;
    private javax.swing.JToolBar jToolBarContent;
    private javax.swing.JToolBar jToolBarMenu;
    // End of variables declaration//GEN-END:variables
}
