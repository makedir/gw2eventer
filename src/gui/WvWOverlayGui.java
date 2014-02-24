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
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;

/**
 *
 * @author mkdr <makedir@gmail.com>
 */
public class WvWOverlayGui extends javax.swing.JFrame {

    private GW2EventerGui mainGui;
    
    private ArrayList timerLabelEternal;
    private ArrayList timerLabelBorderlandsRed;
    private ArrayList timerLabelBorderlandsBlue;
    private ArrayList timerLabelBorderlandsGreen;
    
    /**
     * Creates new form BorderlandsOverlayGui
     */
    public WvWOverlayGui(GW2EventerGui mainGui) {
        
        this.mainGui = mainGui;
        this.timerLabelEternal = new ArrayList();
        this.timerLabelBorderlandsRed = new ArrayList();
        this.timerLabelBorderlandsBlue = new ArrayList();
        this.timerLabelBorderlandsGreen = new ArrayList();
        
        initComponents();
        
        this.showBorderlands(false);
        this.initTimer();
    }

    public void startGui() {
        
        this.eventTimerLabelCoherent.startTimer();
        this.eventTimerLabelCoherent.setVisible(true);
        this.setVisible(true);
    }
    
    public void deactivateGui() {
        
        this.eventTimerLabelCoherent.resetTimer();
        this.setVisible(false);
    }
    
    private void initTimer() {
        
        for (int i = 1; i <= 22; i++) {

            Field f;

            try {
                f = getClass().getDeclaredField("jLabelEternal" + i);

                JLabel l = (JLabel) f.get(this);
                int width = l.getWidth();
                int height = l.getHeight();
                
                EventTimerLabel eventTimerLabel = new EventTimerLabel();
                this.timerLabelEternal.add(eventTimerLabel);
                
                getContentPane().add(eventTimerLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(width + 10, height - 10, -1, -1));
            } catch (    NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
                Logger.getLogger(WvWOverlayGui.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        
        for (int i = 1; i <= 13; i++) {

            Field f;

            try {
                f = getClass().getDeclaredField("jLabelBorderlands" + i);

                JLabel l = (JLabel) f.get(this);
                int width = l.getWidth();
                int height = l.getHeight();
                
                EventTimerLabel eventTimerLabel = new EventTimerLabel();
                this.timerLabelBorderlandsRed.add(eventTimerLabel);
                
                getContentPane().add(eventTimerLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(width + 10, height - 10, -1, -1));
                
                eventTimerLabel = new EventTimerLabel();
                this.timerLabelBorderlandsGreen.add(eventTimerLabel);
                
                getContentPane().add(eventTimerLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(width + 10, height - 10, -1, -1));
                
                eventTimerLabel = new EventTimerLabel();
                this.timerLabelBorderlandsBlue.add(eventTimerLabel);
                
                getContentPane().add(eventTimerLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(width + 10, height - 10, -1, -1));
            } catch (    NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
                Logger.getLogger(WvWOverlayGui.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
    
    private void showEternal(boolean show) {
        
        for (int i = 1; i <= 22; i++) {

            Field f;

            try {
                f = getClass().getDeclaredField("jLabelEternal" + i);

                JLabel l = (JLabel) f.get(this);
                l.setVisible(show);
            } catch (    NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
                Logger.getLogger(WvWOverlayGui.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
    
    private void showBorderlands(boolean show) {
        
        for (int i = 1; i <= 13; i++) {

            Field f;

            try {
                f = getClass().getDeclaredField("jLabelBorderlands" + i);

                JLabel l = (JLabel) f.get(this);
                l.setVisible(show);
            } catch (    NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
                Logger.getLogger(WvWOverlayGui.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
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
        jComboBoxWvW = new javax.swing.JComboBox();
        jButtonRefresh = new javax.swing.JButton();
        jButtonLeft = new javax.swing.JButton();
        jButtonRight = new javax.swing.JButton();
        jButtonUp = new javax.swing.JButton();
        jButtonDown = new javax.swing.JButton();
        jButtonMinimize = new javax.swing.JButton();
        jButtonMaximize = new javax.swing.JButton();
        jButtonClose = new javax.swing.JButton();
        jLabelEternal22 = new javax.swing.JLabel();
        jLabelEternal21 = new javax.swing.JLabel();
        jLabelEternal20 = new javax.swing.JLabel();
        jLabelEternal19 = new javax.swing.JLabel();
        jLabelEternal18 = new javax.swing.JLabel();
        jLabelEternal17 = new javax.swing.JLabel();
        jLabelEternal16 = new javax.swing.JLabel();
        jLabelEternal15 = new javax.swing.JLabel();
        jLabelEternal14 = new javax.swing.JLabel();
        jLabelEternal13 = new javax.swing.JLabel();
        jLabelEternal12 = new javax.swing.JLabel();
        jLabelEternal11 = new javax.swing.JLabel();
        jLabelEternal10 = new javax.swing.JLabel();
        jLabelEternal9 = new javax.swing.JLabel();
        jLabelEternal8 = new javax.swing.JLabel();
        jLabelEternal7 = new javax.swing.JLabel();
        jLabelEternal6 = new javax.swing.JLabel();
        jLabelEternal5 = new javax.swing.JLabel();
        jLabelEternal4 = new javax.swing.JLabel();
        jLabelEternal3 = new javax.swing.JLabel();
        jLabelEternal2 = new javax.swing.JLabel();
        jLabelEternal1 = new javax.swing.JLabel();
        jLabelBorderlands5 = new javax.swing.JLabel();
        jLabelBorderlands9 = new javax.swing.JLabel();
        jLabelBorderlands3 = new javax.swing.JLabel();
        jLabelBorderlands1 = new javax.swing.JLabel();
        jLabelBorderlands6 = new javax.swing.JLabel();
        jLabelBorderlands8 = new javax.swing.JLabel();
        jLabelBorderlands4 = new javax.swing.JLabel();
        jLabelBorderlands2 = new javax.swing.JLabel();
        jLabelBorderlands7 = new javax.swing.JLabel();
        jLabelBorderlands10 = new javax.swing.JLabel();
        jLabelBorderlands13 = new javax.swing.JLabel();
        jLabelBorderlands11 = new javax.swing.JLabel();
        jLabelBorderlands12 = new javax.swing.JLabel();
        jLabelCoherent = new javax.swing.JLabel();
        eventTimerLabelCoherent = new gui.EventTimerLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setAlwaysOnTop(true);
        setFocusable(false);
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jToolBar1.setBorder(null);
        jToolBar1.setForeground(new java.awt.Color(255, 255, 255));
        jToolBar1.setRollover(true);
        jToolBar1.setFocusable(false);
        jToolBar1.setOpaque(false);

        jComboBoxWvW.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Eternal", "Green", "Blue", "Red" }));
        jComboBoxWvW.setOpaque(false);
        jComboBoxWvW.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxWvWActionPerformed(evt);
            }
        });
        jToolBar1.add(jComboBoxWvW);

        jButtonRefresh.setForeground(new java.awt.Color(255, 255, 255));
        jButtonRefresh.setText("Refresh");
        jButtonRefresh.setFocusable(false);
        jButtonRefresh.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonRefresh.setOpaque(false);
        jButtonRefresh.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButtonRefresh);

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

        getContentPane().add(jToolBar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jLabelEternal22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        getContentPane().add(jLabelEternal22, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 250, -1, -1));

        jLabelEternal21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        getContentPane().add(jLabelEternal21, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 250, -1, -1));

        jLabelEternal20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        getContentPane().add(jLabelEternal20, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 230, -1, -1));

        jLabelEternal19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        getContentPane().add(jLabelEternal19, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 240, -1, -1));

        jLabelEternal18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        getContentPane().add(jLabelEternal18, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 210, -1, -1));

        jLabelEternal17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        getContentPane().add(jLabelEternal17, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 220, -1, -1));

        jLabelEternal16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        getContentPane().add(jLabelEternal16, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 200, -1, -1));

        jLabelEternal15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        getContentPane().add(jLabelEternal15, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 210, -1, -1));

        jLabelEternal14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        getContentPane().add(jLabelEternal14, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 170, -1, -1));

        jLabelEternal13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        getContentPane().add(jLabelEternal13, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, -1, -1));

        jLabelEternal12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        getContentPane().add(jLabelEternal12, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 140, -1, -1));

        jLabelEternal11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        getContentPane().add(jLabelEternal11, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 150, -1, -1));

        jLabelEternal10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        getContentPane().add(jLabelEternal10, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 150, -1, -1));

        jLabelEternal9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        getContentPane().add(jLabelEternal9, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 150, -1, -1));

        jLabelEternal8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        getContentPane().add(jLabelEternal8, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 140, -1, -1));

        jLabelEternal7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        getContentPane().add(jLabelEternal7, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 100, -1, -1));

        jLabelEternal6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        getContentPane().add(jLabelEternal6, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 70, -1, -1));

        jLabelEternal5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        getContentPane().add(jLabelEternal5, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 80, -1, -1));

        jLabelEternal4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        getContentPane().add(jLabelEternal4, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 40, -1, -1));

        jLabelEternal3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        getContentPane().add(jLabelEternal3, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 50, -1, -1));

        jLabelEternal2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        getContentPane().add(jLabelEternal2, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 30, -1, -1));

        jLabelEternal1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        getContentPane().add(jLabelEternal1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 50, -1, -1));

        jLabelBorderlands5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        getContentPane().add(jLabelBorderlands5, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 130, -1, -1));

        jLabelBorderlands9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        getContentPane().add(jLabelBorderlands9, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 270, -1, -1));

        jLabelBorderlands3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        getContentPane().add(jLabelBorderlands3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 120, -1, -1));

        jLabelBorderlands1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        getContentPane().add(jLabelBorderlands1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 30, -1, -1));

        jLabelBorderlands6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        getContentPane().add(jLabelBorderlands6, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 150, -1, -1));

        jLabelBorderlands8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        getContentPane().add(jLabelBorderlands8, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 200, -1, -1));

        jLabelBorderlands4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        getContentPane().add(jLabelBorderlands4, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 110, -1, -1));

        jLabelBorderlands2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        getContentPane().add(jLabelBorderlands2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, -1, -1));

        jLabelBorderlands7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        getContentPane().add(jLabelBorderlands7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, -1, -1));

        jLabelBorderlands10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        getContentPane().add(jLabelBorderlands10, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 250, -1, -1));

        jLabelBorderlands13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        getContentPane().add(jLabelBorderlands13, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 330, -1, -1));

        jLabelBorderlands11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        getContentPane().add(jLabelBorderlands11, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 250, -1, -1));

        jLabelBorderlands12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        getContentPane().add(jLabelBorderlands12, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 260, -1, -1));

        jLabelCoherent.setForeground(new java.awt.Color(255, 255, 255));
        jLabelCoherent.setText("Time until data coherent:");
        getContentPane().add(jLabelCoherent, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        eventTimerLabelCoherent.setText("eventTimerLabel1");
        getContentPane().add(eventTimerLabelCoherent, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 370, -1, -1));

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
        this.mainGui.setWvWOverlayX(x);
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
        this.mainGui.setWvWOverlayX(x);
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
        this.mainGui.setWvWOverlayY(y);
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
        this.mainGui.setWvWOverlayY(y);
    }//GEN-LAST:event_jButtonDownActionPerformed

    private void jButtonMinimizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonMinimizeActionPerformed

        this.setSize(this.getWidth(), 27);
    }//GEN-LAST:event_jButtonMinimizeActionPerformed

    private void jButtonMaximizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonMaximizeActionPerformed

        this.setSize(this.getWidth(), 600);
    }//GEN-LAST:event_jButtonMaximizeActionPerformed

    private void jButtonCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCloseActionPerformed

        this.mainGui.setWvWOverlayVisible(false);
    }//GEN-LAST:event_jButtonCloseActionPerformed

    private void jComboBoxWvWActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxWvWActionPerformed

        String selection = (String) this.jComboBoxWvW.getSelectedItem();
        
        switch (selection) {
            case "Eternal":
                this.showBorderlands(false);
                this.showEternal(true);
                break;
            case "Red":
                this.showEternal(false);
                this.showBorderlands(true);
                break;
            case "Green":
                this.showEternal(false);
                this.showBorderlands(true);
                break;
            case "Blue":
                this.showEternal(false);
                this.showBorderlands(true);
                break;
        }
    }//GEN-LAST:event_jComboBoxWvWActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private gui.EventTimerLabel eventTimerLabelCoherent;
    private javax.swing.JButton jButtonClose;
    private javax.swing.JButton jButtonDown;
    private javax.swing.JButton jButtonLeft;
    private javax.swing.JButton jButtonMaximize;
    private javax.swing.JButton jButtonMinimize;
    private javax.swing.JButton jButtonRefresh;
    private javax.swing.JButton jButtonRight;
    private javax.swing.JButton jButtonUp;
    private javax.swing.JComboBox jComboBoxWvW;
    private javax.swing.JLabel jLabelBorderlands1;
    private javax.swing.JLabel jLabelBorderlands10;
    private javax.swing.JLabel jLabelBorderlands11;
    private javax.swing.JLabel jLabelBorderlands12;
    private javax.swing.JLabel jLabelBorderlands13;
    private javax.swing.JLabel jLabelBorderlands2;
    private javax.swing.JLabel jLabelBorderlands3;
    private javax.swing.JLabel jLabelBorderlands4;
    private javax.swing.JLabel jLabelBorderlands5;
    private javax.swing.JLabel jLabelBorderlands6;
    private javax.swing.JLabel jLabelBorderlands7;
    private javax.swing.JLabel jLabelBorderlands8;
    private javax.swing.JLabel jLabelBorderlands9;
    private javax.swing.JLabel jLabelCoherent;
    private javax.swing.JLabel jLabelEternal1;
    private javax.swing.JLabel jLabelEternal10;
    private javax.swing.JLabel jLabelEternal11;
    private javax.swing.JLabel jLabelEternal12;
    private javax.swing.JLabel jLabelEternal13;
    private javax.swing.JLabel jLabelEternal14;
    private javax.swing.JLabel jLabelEternal15;
    private javax.swing.JLabel jLabelEternal16;
    private javax.swing.JLabel jLabelEternal17;
    private javax.swing.JLabel jLabelEternal18;
    private javax.swing.JLabel jLabelEternal19;
    private javax.swing.JLabel jLabelEternal2;
    private javax.swing.JLabel jLabelEternal20;
    private javax.swing.JLabel jLabelEternal21;
    private javax.swing.JLabel jLabelEternal22;
    private javax.swing.JLabel jLabelEternal3;
    private javax.swing.JLabel jLabelEternal4;
    private javax.swing.JLabel jLabelEternal5;
    private javax.swing.JLabel jLabelEternal6;
    private javax.swing.JLabel jLabelEternal7;
    private javax.swing.JLabel jLabelEternal8;
    private javax.swing.JLabel jLabelEternal9;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
