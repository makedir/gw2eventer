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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mkdr <makedir@gmail.com>
 */
public class OverlayGui extends javax.swing.JFrame {

    HashMap activeBEvents;
    HashMap activePreEvents;
    
    GW2EventerGui mainGui;
    
    /**
     * Creates new form OverlayGui
     */
    public OverlayGui(GW2EventerGui mainGui) {
        
        this.mainGui = mainGui;
        
        this.activeBEvents = new HashMap();
        this.activePreEvents = new HashMap();
        
        initComponents();
    }

    public void setTranslations(String activeBEventsLabel, String activePreEventsLabel) {
        
        this.jLabel1.setText(activeBEventsLabel);
        this.jLabel2.setText(activePreEventsLabel);
    }
    
    public void clearActive() {
        
        this.activeBEvents.clear();
        this.activePreEvents.clear();
    }
    
    public void addActiveB(String name, String color) {
        
        this.activeBEvents.put(name, color);
    }
    
    public void addActivePreEvent(String name, String color) {
        
        this.activePreEvents.put(name, color);
    }
    
    public void renderActive() {
        
        String outBsTmp = "";
        String outPresTmp = "";
        
        Iterator it = this.activeBEvents.entrySet().iterator();
                    
        while (it.hasNext()) {

            Map.Entry pairs = (Map.Entry) it.next();

            String name = (String) pairs.getKey();
            String color = (String) pairs.getValue();
            
            if (color.equals("yellow")) {
                outBsTmp = outBsTmp + "<b><font color=yellow>" + name + "</font></b><br>";
            } else {
                outBsTmp = outBsTmp + "<b>" + name + "</b><br>";
            }
            
            it.remove();
        }
        
        it = this.activePreEvents.entrySet().iterator();
                    
        while (it.hasNext()) {

            Map.Entry pairs = (Map.Entry) it.next();

            String name = (String) pairs.getKey();
            String color = (String) pairs.getValue();
            
            if (color.equals("yellow")) {
                outPresTmp = outPresTmp + "<b><font color=yellow>" + name + "</font></b><br>";
            } else {
                outPresTmp = outPresTmp + "<b>" + name + "</b><br>";
            }
            
            it.remove();
        }
        
        if (outBsTmp.equals("")) {
            outBsTmp = "-";
        } else {
            outBsTmp = "<html>" + outBsTmp + "</html>";
        }
        
        if (outPresTmp.equals("")) {
            outPresTmp = "-";
        } else {
            outPresTmp = "<html>" + outPresTmp + "</html>";
        }
        
        this.jLabelActiveBs.setText(outBsTmp);
        this.jLabelActivePres.setText(outPresTmp);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        jLabel3 = new javax.swing.JLabel();
        jButtonLeft = new javax.swing.JButton();
        jButtonRight = new javax.swing.JButton();
        jButtonUp = new javax.swing.JButton();
        jButtonDown = new javax.swing.JButton();
        jButtonMinimize = new javax.swing.JButton();
        jButtonMaximize = new javax.swing.JButton();
        jButtonClose = new javax.swing.JButton();
        jToolBar2 = new javax.swing.JToolBar();
        jLabel1 = new javax.swing.JLabel();
        jLabelActiveBs = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabelActivePres = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("GW2 Eventer overlay");
        setAlwaysOnTop(true);
        setFocusable(false);
        setFocusableWindowState(false);
        setUndecorated(true);
        setResizable(false);

        jToolBar1.setBorder(null);
        jToolBar1.setFloatable(false);
        jToolBar1.setForeground(new java.awt.Color(255, 255, 255));
        jToolBar1.setFocusable(false);
        jToolBar1.setOpaque(false);

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Events");
        jLabel3.setFocusable(false);
        jToolBar1.add(jLabel3);

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
        jToolBar1.add(jButtonLeft);

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
        jToolBar1.add(jButtonRight);

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
        jToolBar1.add(jButtonUp);

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
        jToolBar1.add(jButtonDown);

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
        jToolBar1.add(jButtonMinimize);

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
        jToolBar1.add(jButtonMaximize);

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
        jToolBar1.add(jButtonClose);

        getContentPane().add(jToolBar1, java.awt.BorderLayout.PAGE_START);

        jToolBar2.setFloatable(false);
        jToolBar2.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jToolBar2.setFocusable(false);
        jToolBar2.setOpaque(false);

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("active B events:");
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jToolBar2.add(jLabel1);

        jLabelActiveBs.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabelActiveBs.setForeground(new java.awt.Color(102, 255, 0));
        jLabelActiveBs.setText("-");
        jLabelActiveBs.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jToolBar2.add(jLabelActiveBs);

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("active pre Events:");
        jLabel2.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jToolBar2.add(jLabel2);

        jLabelActivePres.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabelActivePres.setForeground(new java.awt.Color(102, 255, 0));
        jLabelActivePres.setText("-");
        jLabelActivePres.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jToolBar2.add(jLabelActivePres);

        getContentPane().add(jToolBar2, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
                Logger.getLogger(OverlayGui.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if (x <= 20) {
            x = 0;
        } else {
            x = x - 20;
        }
        
        //this.setLocation(x, this.getY());
        this.mainGui.setOverlayX(x);
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
                Logger.getLogger(OverlayGui.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if (x >= 1720) {
            x = 1720;
        } else {
            x = x + 20;
        }
        
        //this.setLocation(x, this.getY());
        this.mainGui.setOverlayX(x);
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
                Logger.getLogger(OverlayGui.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if (y <= 20) {
            y = 0;
        } else {
            y = y - 20;
        }
        
        //this.setLocation(this.getX(), y);
        this.mainGui.setOverlayY(y);
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
                Logger.getLogger(OverlayGui.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if (y >= 880) {
            y = 880;
        } else {
            y = y + 20;
        }
        
        //this.setLocation(this.getX(), y);
        this.mainGui.setOverlayY(y);
    }//GEN-LAST:event_jButtonDownActionPerformed

    private void jButtonMaximizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonMaximizeActionPerformed

        this.setSize(this.getWidth(), 600);
    }//GEN-LAST:event_jButtonMaximizeActionPerformed

    private void jButtonMinimizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonMinimizeActionPerformed

        this.setSize(this.getWidth(), 25);
    }//GEN-LAST:event_jButtonMinimizeActionPerformed

    private void jButtonCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCloseActionPerformed

        this.mainGui.setOverlayVisible(false);
    }//GEN-LAST:event_jButtonCloseActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonClose;
    private javax.swing.JButton jButtonDown;
    private javax.swing.JButton jButtonLeft;
    private javax.swing.JButton jButtonMaximize;
    private javax.swing.JButton jButtonMinimize;
    private javax.swing.JButton jButtonRight;
    private javax.swing.JButton jButtonUp;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabelActiveBs;
    private javax.swing.JLabel jLabelActivePres;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    // End of variables declaration//GEN-END:variables
}
