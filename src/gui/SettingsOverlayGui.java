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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mkdr <makedir@gmail.com>
 */
public class SettingsOverlayGui extends javax.swing.JFrame {

    private GW2EventerGui mainGui;
    
    /**
     * Creates new form SettingsOverlayGui
     */
    public SettingsOverlayGui(GW2EventerGui mainGui) {
        
        this.mainGui = mainGui;
        
        initComponents();
    }

    public void setSound(boolean sound) {
        
        this.jCheckBoxSound.setSelected(sound);
    }
    
    public void setEvents(boolean events) {
        
        this.jCheckBoxEvents.setSelected(events);
    }
    
    public void setWvW(boolean wvw) {
        
        this.jCheckBoxWvW.setEnabled(true);
        this.jCheckBoxWvW.setSelected(wvw);
    }
    
    public void setWvWEnabled(boolean enabled) {
        
        this.jCheckBoxWvW.setEnabled(enabled);
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
        jCheckBoxSound = new javax.swing.JCheckBox();
        jCheckBoxEvents = new javax.swing.JCheckBox();
        jCheckBoxWvW = new javax.swing.JCheckBox();
        jButtonLeft = new javax.swing.JButton();
        jButtonRight = new javax.swing.JButton();
        jButtonUp = new javax.swing.JButton();
        jButtonDown = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setAlwaysOnTop(true);
        setFocusable(false);
        setFocusableWindowState(false);
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(400, 50));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/menu.png"))); // NOI18N
        jLabelMenu.setToolTipText("Menu");
        jLabelMenu.setFocusable(false);
        jLabelMenu.setInheritsPopupMenu(false);
        jLabelMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabelMenuMousePressed(evt);
            }
        });
        getContentPane().add(jLabelMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 20, -1, -1));

        jToolBarMenu.setFloatable(false);
        jToolBarMenu.setBorderPainted(false);
        jToolBarMenu.setFocusable(false);
        jToolBarMenu.setOpaque(false);
        jToolBarMenu.setRequestFocusEnabled(false);

        jCheckBoxSound.setForeground(new java.awt.Color(255, 255, 255));
        jCheckBoxSound.setSelected(true);
        jCheckBoxSound.setText("Sounds ");
        jCheckBoxSound.setFocusable(false);
        jCheckBoxSound.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jCheckBoxSound.setOpaque(false);
        jCheckBoxSound.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jCheckBoxSound.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxSoundActionPerformed(evt);
            }
        });
        jToolBarMenu.add(jCheckBoxSound);

        jCheckBoxEvents.setForeground(new java.awt.Color(255, 255, 255));
        jCheckBoxEvents.setText("Events ");
        jCheckBoxEvents.setFocusable(false);
        jCheckBoxEvents.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jCheckBoxEvents.setOpaque(false);
        jCheckBoxEvents.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jCheckBoxEvents.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxEventsActionPerformed(evt);
            }
        });
        jToolBarMenu.add(jCheckBoxEvents);

        jCheckBoxWvW.setForeground(new java.awt.Color(255, 255, 255));
        jCheckBoxWvW.setText("WvW ");
        jCheckBoxWvW.setToolTipText("");
        jCheckBoxWvW.setEnabled(false);
        jCheckBoxWvW.setFocusable(false);
        jCheckBoxWvW.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jCheckBoxWvW.setOpaque(false);
        jCheckBoxWvW.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jCheckBoxWvW.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxWvWActionPerformed(evt);
            }
        });
        jToolBarMenu.add(jCheckBoxWvW);

        jButtonLeft.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jButtonLeft.setForeground(new java.awt.Color(255, 255, 255));
        jButtonLeft.setText("<<");
        jButtonLeft.setFocusable(false);
        jButtonLeft.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonLeft.setOpaque(false);
        jButtonLeft.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonLeft.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLeftActionPerformed(evt);
            }
        });
        jToolBarMenu.add(jButtonLeft);

        jButtonRight.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jButtonRight.setForeground(new java.awt.Color(255, 255, 255));
        jButtonRight.setText(">>");
        jButtonRight.setFocusable(false);
        jButtonRight.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonRight.setOpaque(false);
        jButtonRight.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonRight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRightActionPerformed(evt);
            }
        });
        jToolBarMenu.add(jButtonRight);

        jButtonUp.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jButtonUp.setForeground(new java.awt.Color(255, 255, 255));
        jButtonUp.setText("up");
        jButtonUp.setFocusable(false);
        jButtonUp.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonUp.setOpaque(false);
        jButtonUp.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUpActionPerformed(evt);
            }
        });
        jToolBarMenu.add(jButtonUp);

        jButtonDown.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jButtonDown.setForeground(new java.awt.Color(255, 255, 255));
        jButtonDown.setText("down");
        jButtonDown.setFocusable(false);
        jButtonDown.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonDown.setOpaque(false);
        jButtonDown.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonDown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDownActionPerformed(evt);
            }
        });
        jToolBarMenu.add(jButtonDown);

        getContentPane().add(jToolBarMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 320, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabelMenuMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelMenuMousePressed

        if (this.jToolBarMenu.isVisible()) {
            this.jToolBarMenu.setVisible(false);
        } else {
            this.jToolBarMenu.setVisible(true);
        }
    }//GEN-LAST:event_jLabelMenuMousePressed

    private void jButtonLeftActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLeftActionPerformed

        int x = this.getX();
        
        if (x > 0) {
            Point mouseLoc;
            Robot rob;

            try {
                rob = new Robot();
                mouseLoc = MouseInfo.getPointerInfo().getLocation();
                rob.mouseMove(mouseLoc.x - 20, mouseLoc.y);
            } catch (AWTException ex) {
                Logger.getLogger(SettingsOverlayGui.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if (x <= 20) {
            x = 0;
        } else {
            x = x - 20;
        }
        
        this.mainGui.setSettingsOverlayX(x);
    }//GEN-LAST:event_jButtonLeftActionPerformed

    private void jButtonRightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRightActionPerformed

        int x = this.getX();
        
        if (x < 1720) {
            Point mouseLoc;
            Robot rob;

            try {
                rob = new Robot();
                mouseLoc = MouseInfo.getPointerInfo().getLocation();
                rob.mouseMove(mouseLoc.x + 20, mouseLoc.y);
            } catch (AWTException ex) {
                Logger.getLogger(SettingsOverlayGui.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if (x >= 1720) {
            x = 1720;
        } else {
            x = x + 20;
        }
        
        this.mainGui.setSettingsOverlayX(x);
    }//GEN-LAST:event_jButtonRightActionPerformed

    private void jButtonUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUpActionPerformed

        int y = this.getY();
        
        if (y > 0) {
            Point mouseLoc;
            Robot rob;

            try {
                rob = new Robot();
                mouseLoc = MouseInfo.getPointerInfo().getLocation();
                rob.mouseMove(mouseLoc.x, mouseLoc.y - 20);
            } catch (AWTException ex) {
                Logger.getLogger(SettingsOverlayGui.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if (y <= 20) {
            y = 0;
        } else {
            y = y - 20;
        }
        
        this.mainGui.setSettingsOverlayY(y);
    }//GEN-LAST:event_jButtonUpActionPerformed

    private void jButtonDownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDownActionPerformed

        int y = this.getY();
        
        if (y < 880) {
            Point mouseLoc;
            Robot rob;

            try {
                rob = new Robot();
                mouseLoc = MouseInfo.getPointerInfo().getLocation();
                rob.mouseMove(mouseLoc.x, mouseLoc.y + 20);
            } catch (AWTException ex) {
                Logger.getLogger(SettingsOverlayGui.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if (y >= 880) {
            y = 880;
        } else {
            y = y + 20;
        }
        
        this.mainGui.setSettingsOverlayY(y);
    }//GEN-LAST:event_jButtonDownActionPerformed

    private void jCheckBoxSoundActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxSoundActionPerformed

        this.mainGui.setSoundPlaying(this.jCheckBoxSound.isSelected());
    }//GEN-LAST:event_jCheckBoxSoundActionPerformed

    private void jCheckBoxEventsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxEventsActionPerformed
        
        this.mainGui.setOverlayVisible(this.jCheckBoxEvents.isSelected());
    }//GEN-LAST:event_jCheckBoxEventsActionPerformed

    private void jCheckBoxWvWActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxWvWActionPerformed

        this.mainGui.setWvWOverlayVisible(this.jCheckBoxWvW.isSelected());
    }//GEN-LAST:event_jCheckBoxWvWActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonDown;
    private javax.swing.JButton jButtonLeft;
    private javax.swing.JButton jButtonRight;
    private javax.swing.JButton jButtonUp;
    private javax.swing.JCheckBox jCheckBoxEvents;
    private javax.swing.JCheckBox jCheckBoxSound;
    private javax.swing.JCheckBox jCheckBoxWvW;
    private javax.swing.JLabel jLabelMenu;
    private javax.swing.JToolBar jToolBarMenu;
    // End of variables declaration//GEN-END:variables
}
