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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author mkdr <makedir@gmail.com>
 */
public class WvWOverlayGui extends javax.swing.JFrame {

    private GW2EventerGui mainGui;
    
    private ArrayList labelsEternal;
    private ArrayList labelsBorderlands;
    
    private ArrayList timerLabelEternal;
    private ArrayList timerLabelBorderlandsRed;
    private ArrayList timerLabelBorderlandsBlue;
    private ArrayList timerLabelBorderlandsGreen;
    
    private String matchId;
    private String matchIdColor;
    private String activeMap;
    
    private WvWReader wvwReader;
    
    private HashMap ownerData;
    private HashMap ownerDataOld;
    
    private HashMap idsToLabel;
    
    private ImageIcon castleRed;
    private ImageIcon castleBlue;
    private ImageIcon castleGreen;
    
    private ImageIcon campRed;
    private ImageIcon campBlue;
    private ImageIcon campGreen;
    
    private ImageIcon towerRed;
    private ImageIcon towerBlue;
    private ImageIcon towerGreen;
    
    private ImageIcon keepRed;
    private ImageIcon keepBlue;
    private ImageIcon keepGreen;
    
    /**
     * Creates new form BorderlandsOverlayGui
     */
    public WvWOverlayGui(GW2EventerGui mainGui) {
        
        this.mainGui = mainGui;
        
        this.castleRed = new ImageIcon(getClass().getResource("/media/wvw/castle_red.png"));
        this.castleBlue = new ImageIcon(getClass().getResource("/media/wvw/castle_blue.png"));
        this.castleGreen = new ImageIcon(getClass().getResource("/media/wvw/castle_green.png"));
        
        this.campRed = new ImageIcon(getClass().getResource("/media/wvw/camp_red.png"));
        this.campBlue = new ImageIcon(getClass().getResource("/media/wvw/camp_blue.png"));
        this.campGreen = new ImageIcon(getClass().getResource("/media/wvw/camp_green.png"));
        
        this.towerRed = new ImageIcon(getClass().getResource("/media/wvw/tower_red.png"));
        this.towerBlue = new ImageIcon(getClass().getResource("/media/wvw/tower_blue.png"));
        this.towerGreen = new ImageIcon(getClass().getResource("/media/wvw/tower_green.png"));
        
        this.keepRed = new ImageIcon(getClass().getResource("/media/wvw/keep_red.png"));
        this.keepBlue = new ImageIcon(getClass().getResource("/media/wvw/keep_blue.png"));
        this.keepGreen = new ImageIcon(getClass().getResource("/media/wvw/keep_green.png"));
        
        this.labelsEternal = new ArrayList();
        this.labelsBorderlands = new ArrayList();
        
        this.timerLabelEternal = new ArrayList();
        this.timerLabelBorderlandsRed = new ArrayList();
        this.timerLabelBorderlandsBlue = new ArrayList();
        this.timerLabelBorderlandsGreen = new ArrayList();
        
        this.activeMap = "Center"; //Center=eternal, RedHome, BlueHome, GreenHome
        
        this.matchId = this.mainGui.getMatchId();
        this.matchIdColor = this.mainGui.getMatchIdColor();
        
        this.ownerData = new HashMap();
        this.ownerDataOld = new HashMap();
        
        /*
        this.wvwReader = new WvWReader(this);
        this.wvwReader.setResult(this.ownerData);
        this.wvwReader.setMatchId(this.matchId);
        */
        
        this.idsToLabel = new HashMap();
        
        this.idsToLabel.put("1", new String[]{"3", "keep", "Overlook", "Center"});
        this.idsToLabel.put("2", new String[]{"18", "keep", "Valley", "Center"});
        this.idsToLabel.put("3", new String[]{"15", "keep", "Lowlands", "Center"});
        this.idsToLabel.put("4", new String[]{"20", "camp", "Golanta Clearing", "Center"});
        this.idsToLabel.put("5", new String[]{"6", "camp", "Pangloss Rise", "Center"});
        this.idsToLabel.put("6", new String[]{"1", "camp", "Speldan Clearcut", "Center"});
        this.idsToLabel.put("7", new String[]{"21", "camp", "Danelon Passage", "Center"});
        this.idsToLabel.put("8", new String[]{"12", "camp", "Umberglade Woods", "Center"});
        this.idsToLabel.put("9", new String[]{"10", "castle", "Stonemist Castle", "Center"});
        this.idsToLabel.put("10", new String[]{"8", "camp", "Rogue's Quarry", "Center"});
        this.idsToLabel.put("11", new String[]{"13", "tower", "Aldon's Ledge", "Center"});
        this.idsToLabel.put("12", new String[]{"9", "tower", "Wildcreek Run", "Center"});
        this.idsToLabel.put("13", new String[]{"19", "tower", "Jerrifer's Slough", "Center"});
        this.idsToLabel.put("14", new String[]{"16", "tower", "Klovan Gully", "Center"});
        this.idsToLabel.put("15", new String[]{"22", "tower", "Langor Gulch", "Center"});
        this.idsToLabel.put("16", new String[]{"17", "tower", "Quentin Lake", "Center"});
        this.idsToLabel.put("17", new String[]{"2", "tower", "Mendon's Gap", "Center"});
        this.idsToLabel.put("18", new String[]{"7", "tower", "Anzalias Pass", "Center"});
        this.idsToLabel.put("19", new String[]{"5", "tower", "Ogrewatch Cut", "Center"});
        this.idsToLabel.put("20", new String[]{"4", "tower", "Veloka Slope", "Center"});
        this.idsToLabel.put("21", new String[]{"11", "tower", "Durios Gulch", "Center"});
        this.idsToLabel.put("22", new String[]{"14", "tower", "Bravost Escarpment", "Center"});
        
        this.idsToLabel.put("23", new String[]{"6", "keep", "Garrison", "BlueHome"});
        this.idsToLabel.put("24", new String[]{"13", "camp", "Champion's demense", "BlueHome"});
        this.idsToLabel.put("25", new String[]{"10", "tower", "Redbriar", "BlueHome"});
        this.idsToLabel.put("26", new String[]{"11", "tower", "Greenlake", "BlueHome"});
        this.idsToLabel.put("27", new String[]{"7", "keep", "Ascension Bay", "BlueHome"});
        this.idsToLabel.put("28", new String[]{"4", "tower", "Dawn's Eyrie", "BlueHome"});
        this.idsToLabel.put("29", new String[]{"1", "camp", "The Spiritholme", "BlueHome"});
        this.idsToLabel.put("30", new String[]{"3", "tower", "Woodhaven", "BlueHome"});
        this.idsToLabel.put("31", new String[]{"8", "keep", "Askalion Hills", "BlueHome"});
        this.idsToLabel.put("32", new String[]{"8", "keep", "Etheron Hills", "RedHome"});
        this.idsToLabel.put("33", new String[]{"7", "keep", "Dreaming Bay", "RedHome"});
        this.idsToLabel.put("34", new String[]{"13", "camp", "Victors's Lodge", "RedHome"});
        this.idsToLabel.put("35", new String[]{"10", "tower", "Greenbriar", "RedHome"});
        this.idsToLabel.put("36", new String[]{"11", "tower", "Bluelake", "RedHome"});
        this.idsToLabel.put("37", new String[]{"6", "keep", "Garrison", "RedHome"});
        this.idsToLabel.put("38", new String[]{"3", "tower", "Longview", "RedHome"});
        this.idsToLabel.put("39", new String[]{"1", "camp", "The Godsword", "RedHome"});
        this.idsToLabel.put("40", new String[]{"4", "tower", "Cliffside", "RedHome"});
        this.idsToLabel.put("41", new String[]{"8", "keep", "Shadaran Hills", "GreenHome"});
        this.idsToLabel.put("42", new String[]{"11", "tower", "Redlake", "GreenHome"});
        this.idsToLabel.put("43", new String[]{"13", "camp", "Hero's Lodge", "GreenHome"});
        this.idsToLabel.put("44", new String[]{"7", "keep", "Dreadfall Bay", "GreenHome"});
        this.idsToLabel.put("45", new String[]{"10", "tower", "Bluebriar", "GreenHome"});
        this.idsToLabel.put("46", new String[]{"6", "keep", "Garrison", "GreenHome"});
        this.idsToLabel.put("47", new String[]{"3", "tower", "Sunnyhill", "GreenHome"});
        this.idsToLabel.put("48", new String[]{"2", "camp", "Faithleap", "GreenHome"});
        this.idsToLabel.put("49", new String[]{"9", "camp", "Bluevale Refuge", "GreenHome"});
        this.idsToLabel.put("50", new String[]{"12", "camp", "Bluewater Lowlands", "RedHome"});
        this.idsToLabel.put("51", new String[]{"5", "camp", "Astralholme", "RedHome"});
        this.idsToLabel.put("52", new String[]{"2", "camp", "Arah's Hope", "RedHome"});
        this.idsToLabel.put("53", new String[]{"9", "camp", "Greenvale Refuge", "RedHome"});
        this.idsToLabel.put("54", new String[]{"5", "camp", "Foghaven", "GreenHome"});
        this.idsToLabel.put("55", new String[]{"12", "camp", "Redwater Lowlands", "GreenHome"});
        this.idsToLabel.put("56", new String[]{"1", "camp", "The Titanpaw", "GreenHome"});
        this.idsToLabel.put("57", new String[]{"4", "tower", "Cragtop", "GreenHome"});
        this.idsToLabel.put("58", new String[]{"2", "camp", "Godslore", "BlueHome"});
        this.idsToLabel.put("59", new String[]{"9", "camp", "Redvale Refuge", "BlueHome"});
        this.idsToLabel.put("60", new String[]{"5", "camp", "Stargrove", "BlueHome"});
        this.idsToLabel.put("61", new String[]{"12", "camp", "Greenwater Lowlands", "BlueHome"});
        
        /*
        this.idsToLabel.put("62", new String[]{"1", "", "", ""});
        this.idsToLabel.put("63", new String[]{"1", "", "", ""});
        this.idsToLabel.put("64", new String[]{"1", "", "", ""});
        this.idsToLabel.put("65", new String[]{"1", "", "", ""});
        this.idsToLabel.put("66", new String[]{"1", "", "", ""});
        this.idsToLabel.put("67", new String[]{"1", "", "", ""});
        this.idsToLabel.put("68", new String[]{"1", "", "", ""});
        this.idsToLabel.put("69", new String[]{"1", "", "", ""});
        this.idsToLabel.put("70", new String[]{"1", "", "", ""});
        this.idsToLabel.put("71", new String[]{"1", "", "", ""});
        this.idsToLabel.put("72", new String[]{"1", "", "", ""});
        this.idsToLabel.put("73", new String[]{"1", "", "", ""});
        this.idsToLabel.put("74", new String[]{"1", "", "", ""});
        this.idsToLabel.put("75", new String[]{"1", "", "", ""});
        this.idsToLabel.put("76", new String[]{"1", "", "", ""});
        */
        
        initComponents();
        
        this.jLabelMatchId.setText("matchid: " + this.matchId);
        
        this.eventTimerLabelCoherent.setCustomText("Time until data is coherent: ");
        
        this.showBorderlands(false);
        this.initTimerandLabels();
    }
    
    public void setTranslations(String coherent, String eternal, String green, String red, String blue) {
        
        this.eventTimerLabelCoherent.setCustomText(coherent);
        
        this.jComboBoxWvW.removeAllItems();
        this.jComboBoxWvW.addItem(eternal);
        this.jComboBoxWvW.addItem(green);
        this.jComboBoxWvW.addItem(red);
        this.jComboBoxWvW.addItem(blue);
    }
    
    public void setMatchIdColor(String color) {
        
        this.matchIdColor = color;
    }
    
    public void setMatchId(String matchId) {
        
        this.matchId = matchId;
        this.jLabelMatchId.setText("<html>" + matchId + "(<b>" + this.matchIdColor + "</b>)</html>");
    }
    
    public void refresh() {
        
        Iterator it;
        
        if (this.ownerDataOld.isEmpty()) {
            
            it = this.ownerData.entrySet().iterator();
            
            while (it.hasNext()) {

                Map.Entry pairs = (Map.Entry)it.next();

                String id = (String) pairs.getKey();
                String owner = (String) pairs.getValue();
                
                this.ownerDataOld.put(new String(id), new String(owner));
            }
            
            if (this.activeMap.equals("Center")) {
                this.setEternalColors();
            } else {
                this.setBorderlandsColors();
            }
        }
        
        it = this.ownerData.entrySet().iterator();
                    
        while (it.hasNext()) {

            Map.Entry pairs = (Map.Entry)it.next();

            String id = (String) pairs.getKey();
            String owner = (String) pairs.getValue();
            String onwerOld = (String) this.ownerDataOld.get(id);
            
            if (this.idsToLabel.containsKey(id)) {
                
                int labelNumber = Integer.parseInt(((String[]) this.idsToLabel.get(id))[0]);
                String labelType = ((String[]) this.idsToLabel.get(id))[1];
                String labelHome = ((String[]) this.idsToLabel.get(id))[3];
                
                EventTimerLabel currentTimer = null;
                JLabel currentLabel = null;
                
                switch (labelHome) {
                    case "Center":
                        currentTimer = (EventTimerLabel) this.timerLabelEternal.get(labelNumber - 1);
                        break;
                    case "RedHome":
                        currentTimer = (EventTimerLabel) this.timerLabelBorderlandsRed.get(labelNumber - 1);
                        break;
                    case "BlueHome":
                        currentTimer = (EventTimerLabel) this.timerLabelBorderlandsBlue.get(labelNumber - 1);
                        break;
                    case "GreenHome":
                        currentTimer = (EventTimerLabel) this.timerLabelBorderlandsGreen.get(labelNumber - 1);
                        break;
                }
                
                if ((!owner.equals(onwerOld)) && (currentTimer != null)) {
                    if (!currentTimer.isTicking()) {
                        currentTimer.startTimer();
                        
                        if (this.activeMap.equals(labelHome)) {
                            currentTimer.setVisible(true);

                            if (this.activeMap.equals("Center")) {
                                currentLabel = (JLabel) this.labelsEternal.get(labelNumber - 1);
                            } else {
                                currentLabel = (JLabel) this.labelsBorderlands.get(labelNumber - 1);
                            }
                            
                            try {
                                Field f = getClass().getDeclaredField(labelType + owner);
                                ImageIcon icon;

                                try {
                                    icon = (ImageIcon) f.get(this);
                                    currentLabel.setIcon(icon);
                                } catch (        IllegalArgumentException | IllegalAccessException ex) {
                                    Logger.getLogger(WvWOverlayGui.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            } catch (    NoSuchFieldException | SecurityException ex) {
                                Logger.getLogger(WvWOverlayGui.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }
            }
        }
        
        this.ownerDataOld.clear();
        it = this.ownerData.entrySet().iterator();
                    
        while (it.hasNext()) {

            Map.Entry pairs = (Map.Entry)it.next();

            String id = (String) pairs.getKey();
            String owner = (String) pairs.getValue();
            
            this.ownerDataOld.put(new String(id), new String(owner));
        }
        
        /*
        if (this.activeMap.equals("Center")) {
            this.setEternalColors();
        } else {
            this.setBorderlandsColors();
        }*/
    }
    
    private void resetTimer() {
        
        for (int i = 0; i < this.timerLabelEternal.size(); i++) {
            
            EventTimerLabel timer = (EventTimerLabel) this.timerLabelEternal.get(i);
            timer.resetTimer();
        }
        
        for (int i = 0; i < this.timerLabelBorderlandsRed.size(); i++) {
            
            EventTimerLabel timer = (EventTimerLabel) this.timerLabelBorderlandsRed.get(i);
            timer.resetTimer();
        }
        
        for (int i = 0; i < this.timerLabelBorderlandsBlue.size(); i++) {
            
            EventTimerLabel timer = (EventTimerLabel) this.timerLabelBorderlandsBlue.get(i);
            timer.resetTimer();
        }
        
        for (int i = 0; i < this.timerLabelBorderlandsGreen.size(); i++) {
            
            EventTimerLabel timer = (EventTimerLabel) this.timerLabelBorderlandsGreen.get(i);
            timer.resetTimer();
        }
    }
    
    public void startGui() {
        
        this.jButtonRefresh.setEnabled(true);
        
        this.jToolBarMenu.setVisible(true);
        this.jComboBoxWvW.setVisible(true);
        this.jLabelMatchId.setVisible(true);
        
        this.jToolBarInfo.setVisible(true);
        this.jButtonCoherent.setVisible(true);
        
        this.mainGui.setMatchId();
        
        this.setMatchIdColor(this.mainGui.getMatchIdColor());
        this.setMatchId(this.mainGui.getMatchId());
        
        this.ownerDataOld.clear();
        this.resetTimer();
        
        this.ownerData = new HashMap();
        
        this.wvwReader = new WvWReader(this);
        this.wvwReader.setResult(this.ownerData);
        this.wvwReader.setMatchId(this.matchId);
        
        this.wvwReader.start();
        
        this.eventTimerLabelCoherent.startTimer();
        this.eventTimerLabelCoherent.setVisible(true);
        
        this.setVisible(true);
    }
    
    public void deactivateGui() {
        
        if (this.wvwReader != null) {
            
            this.wvwReader.interrupt();
        
            this.ownerDataOld.clear();
            this.resetTimer();

            this.eventTimerLabelCoherent.resetTimer();
            this.setVisible(false);
        }
    }
    
    private void showToolTip(String toolTip) {
        
        this.jLabelToolTip.setText(toolTip);
        this.jLabelToolTip.setVisible(true);
    }
    
    private void hideToolTip() {
        
        this.jLabelToolTip.setVisible(false);
    }
    
    private void initTimerandLabels() {
        
        for (int i = 1; i <= 22; i++) {

            Field f;

            try {
                f = getClass().getDeclaredField("jLabelEternal" + i);

                final JLabel l = (JLabel) f.get(this);
                
                l.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                        showToolTip(l.getToolTipText());
                    }
                    public void mouseExited(java.awt.event.MouseEvent evt) {
                        hideToolTip();
                    }
                });
                
                int width = l.getX();
                int height = l.getY();
                
                this.labelsEternal.add(l);
                
                EventTimerLabel eventTimerLabel = new EventTimerLabel();
                this.timerLabelEternal.add(eventTimerLabel);
                
                getContentPane().add(eventTimerLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(width + 6, height + 10, -1, -1), 0);
            } catch (    NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
                Logger.getLogger(WvWOverlayGui.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        for (int i = 1; i <= 13; i++) {

            Field f;

            try {
                f = getClass().getDeclaredField("jLabelBorderlands" + i);

                final JLabel l = (JLabel) f.get(this);
                
                l.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                        showToolTip(l.getToolTipText());
                    }
                    public void mouseExited(java.awt.event.MouseEvent evt) {
                        hideToolTip();
                    }
                });
                
                int width = l.getX();
                int height = l.getY();
                
                this.labelsBorderlands.add(l);
                
                EventTimerLabel eventTimerLabel = new EventTimerLabel();
                this.timerLabelBorderlandsRed.add(eventTimerLabel);
                
                getContentPane().add(eventTimerLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(width + 6, height + 10, -1, -1), 0);
                
                eventTimerLabel = new EventTimerLabel();
                this.timerLabelBorderlandsGreen.add(eventTimerLabel);
                
                getContentPane().add(eventTimerLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(width + 6, height + 10, -1, -1), 0);
                
                eventTimerLabel = new EventTimerLabel();
                this.timerLabelBorderlandsBlue.add(eventTimerLabel);
                
                getContentPane().add(eventTimerLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(width + 6, height + 10, -1, -1), 0);
            } catch (    NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
                Logger.getLogger(WvWOverlayGui.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void showEternal(boolean show) {
        
        if (show) {
            this.setEternalColors();
        }
        
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
        
        this.showEternalTimer(show);
    }
    
    private void showEternalTimer(boolean show) {
        
        for (int i = 0; i < this.timerLabelEternal.size(); i++) {

            EventTimerLabel label = (EventTimerLabel) this.timerLabelEternal.get(i);
            
            if (show && label.isTicking()) {
                label.setVisible(true);
            } else {
                label.setVisible(false);
            }
        }
    }
    
    private void showBorderlandsTimer(boolean show) {
        
        switch (this.activeMap) {
            case "RedHome":
                this.showBorderlandsRedTimer(show);
                this.showBorderlandsBlueTimer(false);
                this.showBorderlandsGreenTimer(false);
                break;
            case "BlueHome":
                this.showBorderlandsRedTimer(false);
                this.showBorderlandsBlueTimer(show);
                this.showBorderlandsGreenTimer(false);
                break;
            case "GreenHome":
                this.showBorderlandsRedTimer(false);
                this.showBorderlandsBlueTimer(false);
                this.showBorderlandsGreenTimer(show);
                break;
            case "Center":
                this.showBorderlandsRedTimer(false);
                this.showBorderlandsBlueTimer(false);
                this.showBorderlandsGreenTimer(false);
                break;
        }
    }
    
    private void showBorderlandsRedTimer(boolean show) {
        
        for (int i = 0; i < this.timerLabelBorderlandsRed.size(); i++) {

            EventTimerLabel label = (EventTimerLabel) this.timerLabelBorderlandsRed.get(i);

            if (show && label.isTicking()) {
                label.setVisible(true);
            } else {
                label.setVisible(false);
            }
        }
    }
    
    private void showBorderlandsBlueTimer(boolean show) {
        
        for (int i = 0; i < this.timerLabelBorderlandsBlue.size(); i++) {

            EventTimerLabel label = (EventTimerLabel) this.timerLabelBorderlandsBlue.get(i);

            if (show && label.isTicking()) {
                label.setVisible(true);
            } else {
                label.setVisible(false);
            }
        }
    }
    
    private void showBorderlandsGreenTimer(boolean show) {
        
        for (int i = 0; i < this.timerLabelBorderlandsGreen.size(); i++) {

            EventTimerLabel label = (EventTimerLabel) this.timerLabelBorderlandsGreen.get(i);

            if (show && label.isTicking()) {
                label.setVisible(true);
            } else {
                label.setVisible(false);
            }
        }
    }
    
    private void setEternalColors() {
        
        if (!this.ownerDataOld.isEmpty()) {
            
            Field f;
            Iterator it = this.ownerDataOld.entrySet().iterator();
            
            while (it.hasNext()) {

                Map.Entry pairs = (Map.Entry)it.next();

                String id = (String) pairs.getKey();
                String owner = (String) pairs.getValue();

                if (this.idsToLabel.containsKey(id)) {

                    int labelNumber = Integer.parseInt(((String[]) this.idsToLabel.get(id))[0]);
                    String labelType = ((String[]) this.idsToLabel.get(id))[1];
                    String labelHome = ((String[]) this.idsToLabel.get(id))[3];
                    String labelToolTip = ((String[]) this.idsToLabel.get(id))[2];
                    
                    if (labelHome.equals(this.activeMap)) {
                        
                        //System.out.println("id: " + id + ", labelnumber: " + labelNumber + ", type: " + labelType);
                        
                        JLabel currentLabel = (JLabel) this.labelsEternal.get(labelNumber - 1);
                        
                        currentLabel.setToolTipText(labelToolTip);
                        
                        try {
                            f = getClass().getDeclaredField(labelType + owner);
                            ImageIcon icon;

                            try {
                                icon = (ImageIcon) f.get(this);
                                currentLabel.setIcon(icon);
                            } catch (        IllegalArgumentException | IllegalAccessException ex) {
                                Logger.getLogger(WvWOverlayGui.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } catch (    NoSuchFieldException | SecurityException ex) {
                            Logger.getLogger(WvWOverlayGui.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            } 
        }
    }
    
    private void setBorderlandsColors() {
        
        if (!this.ownerDataOld.isEmpty()) {
            
            Field f;
            Iterator it = this.ownerDataOld.entrySet().iterator();
            
            while (it.hasNext()) {

                Map.Entry pairs = (Map.Entry)it.next();

                String id = (String) pairs.getKey();
                String owner = (String) pairs.getValue();

                if (this.idsToLabel.containsKey(id)) {

                    int labelNumber = Integer.parseInt(((String[]) this.idsToLabel.get(id))[0]);
                    String labelType = ((String[]) this.idsToLabel.get(id))[1];
                    String labelHome = ((String[]) this.idsToLabel.get(id))[3];
                    String labelToolTip = ((String[]) this.idsToLabel.get(id))[2];
                    
                    if (labelHome.equals(this.activeMap)) {
                        
                        //System.out.println("id: " + id + ", labelnumber: " + labelNumber + ", type: " + labelType);
                        
                        JLabel currentLabel = (JLabel) this.labelsBorderlands.get(labelNumber - 1);

                        currentLabel.setToolTipText(labelToolTip);
                        
                        try {
                            f = getClass().getDeclaredField(labelType + owner);
                            ImageIcon icon;

                            try {
                                icon = (ImageIcon) f.get(this);
                                currentLabel.setIcon(icon);
                            } catch (        IllegalArgumentException | IllegalAccessException ex) {
                                Logger.getLogger(WvWOverlayGui.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } catch (    NoSuchFieldException | SecurityException ex) {
                            Logger.getLogger(WvWOverlayGui.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            } 
        }
    }
    
    private void showBorderlands(boolean show) {
        
        if (show) {
            this.setBorderlandsColors();
        }
        
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
        
        this.showBorderlandsTimer(show);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jComboBoxWvW = new javax.swing.JComboBox();
        jToolBarMenu = new javax.swing.JToolBar();
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
        jLabelMatchId = new javax.swing.JLabel();
        jLabelMenu = new javax.swing.JLabel();
        jLabelToolTip = new javax.swing.JLabel();
        jToolBarInfo = new javax.swing.JToolBar();
        jButtonCoherent = new javax.swing.JButton();
        eventTimerLabelCoherent = new gui.EventTimerLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setAlwaysOnTop(true);
        setFocusable(false);
        setFocusableWindowState(false);
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jComboBoxWvW.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Eternal", "Green", "Red", "Blue" }));
        jComboBoxWvW.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jComboBoxWvW.setFocusable(false);
        jComboBoxWvW.setOpaque(false);
        jComboBoxWvW.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxWvWActionPerformed(evt);
            }
        });
        getContentPane().add(jComboBoxWvW, new org.netbeans.lib.awtextra.AbsoluteConstraints(239, 40, -1, -1));

        jToolBarMenu.setBorder(null);
        jToolBarMenu.setForeground(new java.awt.Color(255, 255, 255));
        jToolBarMenu.setRollover(true);
        jToolBarMenu.setFocusable(false);
        jToolBarMenu.setOpaque(false);

        jButtonRefresh.setForeground(new java.awt.Color(255, 255, 255));
        jButtonRefresh.setText("Reset");
        jButtonRefresh.setFocusable(false);
        jButtonRefresh.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonRefresh.setOpaque(false);
        jButtonRefresh.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRefreshActionPerformed(evt);
            }
        });
        jToolBarMenu.add(jButtonRefresh);

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

        getContentPane().add(jToolBarMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jLabelEternal22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        jLabelEternal22.setFocusable(false);
        getContentPane().add(jLabelEternal22, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 250, -1, -1));

        jLabelEternal21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        jLabelEternal21.setFocusable(false);
        getContentPane().add(jLabelEternal21, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 250, -1, -1));

        jLabelEternal20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        jLabelEternal20.setFocusable(false);
        getContentPane().add(jLabelEternal20, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 230, -1, -1));

        jLabelEternal19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        jLabelEternal19.setFocusable(false);
        getContentPane().add(jLabelEternal19, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 240, -1, -1));

        jLabelEternal18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        jLabelEternal18.setFocusable(false);
        getContentPane().add(jLabelEternal18, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 210, -1, -1));

        jLabelEternal17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        jLabelEternal17.setFocusable(false);
        getContentPane().add(jLabelEternal17, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 220, -1, -1));

        jLabelEternal16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        getContentPane().add(jLabelEternal16, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 200, -1, -1));

        jLabelEternal15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        jLabelEternal15.setFocusable(false);
        getContentPane().add(jLabelEternal15, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 210, -1, -1));

        jLabelEternal14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        jLabelEternal14.setFocusable(false);
        getContentPane().add(jLabelEternal14, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 170, -1, -1));

        jLabelEternal13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        jLabelEternal13.setFocusable(false);
        getContentPane().add(jLabelEternal13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, -1, -1));

        jLabelEternal12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        jLabelEternal12.setFocusable(false);
        getContentPane().add(jLabelEternal12, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 140, -1, -1));

        jLabelEternal11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        jLabelEternal11.setFocusable(false);
        getContentPane().add(jLabelEternal11, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 150, -1, -1));

        jLabelEternal10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        jLabelEternal10.setFocusable(false);
        getContentPane().add(jLabelEternal10, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 150, -1, -1));

        jLabelEternal9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        jLabelEternal9.setFocusable(false);
        getContentPane().add(jLabelEternal9, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 150, -1, -1));

        jLabelEternal8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        jLabelEternal8.setFocusable(false);
        getContentPane().add(jLabelEternal8, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 140, -1, -1));

        jLabelEternal7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        jLabelEternal7.setFocusable(false);
        getContentPane().add(jLabelEternal7, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 100, -1, -1));

        jLabelEternal6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        jLabelEternal6.setFocusable(false);
        getContentPane().add(jLabelEternal6, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 70, -1, -1));

        jLabelEternal5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        jLabelEternal5.setFocusable(false);
        getContentPane().add(jLabelEternal5, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 80, -1, -1));

        jLabelEternal4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        jLabelEternal4.setFocusable(false);
        getContentPane().add(jLabelEternal4, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 40, -1, -1));

        jLabelEternal3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        jLabelEternal3.setFocusable(false);
        getContentPane().add(jLabelEternal3, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 50, -1, -1));

        jLabelEternal2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        jLabelEternal2.setFocusable(false);
        getContentPane().add(jLabelEternal2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 30, -1, -1));

        jLabelEternal1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        jLabelEternal1.setFocusable(false);
        getContentPane().add(jLabelEternal1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 50, -1, -1));

        jLabelBorderlands5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        jLabelBorderlands5.setFocusable(false);
        getContentPane().add(jLabelBorderlands5, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 130, -1, -1));

        jLabelBorderlands9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        jLabelBorderlands9.setFocusable(false);
        getContentPane().add(jLabelBorderlands9, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 270, -1, -1));

        jLabelBorderlands3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        jLabelBorderlands3.setFocusable(false);
        getContentPane().add(jLabelBorderlands3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 120, -1, -1));

        jLabelBorderlands1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        jLabelBorderlands1.setFocusable(false);
        getContentPane().add(jLabelBorderlands1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 30, -1, -1));

        jLabelBorderlands6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        jLabelBorderlands6.setFocusable(false);
        getContentPane().add(jLabelBorderlands6, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 150, -1, -1));

        jLabelBorderlands8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        jLabelBorderlands8.setFocusable(false);
        getContentPane().add(jLabelBorderlands8, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 200, -1, -1));

        jLabelBorderlands4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        jLabelBorderlands4.setFocusable(false);
        getContentPane().add(jLabelBorderlands4, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 110, -1, -1));

        jLabelBorderlands2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        jLabelBorderlands2.setFocusable(false);
        getContentPane().add(jLabelBorderlands2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, -1, -1));

        jLabelBorderlands7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        jLabelBorderlands7.setFocusable(false);
        getContentPane().add(jLabelBorderlands7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, -1, -1));

        jLabelBorderlands10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        jLabelBorderlands10.setFocusable(false);
        getContentPane().add(jLabelBorderlands10, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 250, -1, -1));

        jLabelBorderlands13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        jLabelBorderlands13.setFocusable(false);
        getContentPane().add(jLabelBorderlands13, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 330, -1, -1));

        jLabelBorderlands11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        jLabelBorderlands11.setFocusable(false);
        getContentPane().add(jLabelBorderlands11, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 250, -1, -1));

        jLabelBorderlands12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        jLabelBorderlands12.setFocusable(false);
        getContentPane().add(jLabelBorderlands12, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 260, -1, -1));

        jLabelMatchId.setForeground(new java.awt.Color(255, 255, 255));
        jLabelMatchId.setText("matchid:");
        getContentPane().add(jLabelMatchId, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 25, -1, -1));

        jLabelMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/menu.png"))); // NOI18N
        jLabelMenu.setToolTipText("Menu");
        jLabelMenu.setFocusable(false);
        jLabelMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabelMenuMousePressed(evt);
            }
        });
        getContentPane().add(jLabelMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 20, -1, -1));

        jLabelToolTip.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabelToolTip.setForeground(new java.awt.Color(255, 255, 255));
        jLabelToolTip.setFocusable(false);
        getContentPane().add(jLabelToolTip, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 360, -1, -1));

        jToolBarInfo.setFloatable(false);
        jToolBarInfo.setFocusable(false);
        jToolBarInfo.setOpaque(false);

        jButtonCoherent.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jButtonCoherent.setForeground(new java.awt.Color(255, 255, 255));
        jButtonCoherent.setText("X");
        jButtonCoherent.setBorderPainted(false);
        jButtonCoherent.setFocusPainted(false);
        jButtonCoherent.setFocusable(false);
        jButtonCoherent.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonCoherent.setOpaque(false);
        jButtonCoherent.setPreferredSize(new java.awt.Dimension(40, 25));
        jButtonCoherent.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonCoherent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCoherentActionPerformed(evt);
            }
        });
        jToolBarInfo.add(jButtonCoherent);
        jToolBarInfo.add(eventTimerLabelCoherent);

        getContentPane().add(jToolBarInfo, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 367, 290, -1));

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

        this.setSize(this.getWidth(), 25);
    }//GEN-LAST:event_jButtonMinimizeActionPerformed

    private void jButtonMaximizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonMaximizeActionPerformed

        this.setSize(this.getWidth(), 600);
    }//GEN-LAST:event_jButtonMaximizeActionPerformed

    private void jButtonCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCloseActionPerformed

        this.mainGui.setWvWOverlayVisible(false);
    }//GEN-LAST:event_jButtonCloseActionPerformed

    private void jComboBoxWvWActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxWvWActionPerformed

        this.jComboBoxWvW.setVisible(false);
        this.jLabelMatchId.setVisible(false);
        this.jToolBarMenu.setVisible(false);
        
        int selection = this.jComboBoxWvW.getSelectedIndex();
        
        /*
        if (this.activeMap.equals("Center")) {
            this.setEternalColors();
        } else {
            this.setBorderlandsColors();
        }*/
        
        switch (selection) {
            case 0:
                this.activeMap = "Center";
                this.showEternal(true);
                this.showBorderlands(false);
                break;
            case 1:
                this.activeMap = "GreenHome";
                this.showEternal(false);
                this.showBorderlands(true);
                break;
            case 2:
                this.activeMap = "RedHome";
                this.showEternal(false);
                this.showBorderlands(true);
                break;
            case 3:
                this.activeMap = "BlueHome";
                this.showEternal(false);
                this.showBorderlands(true);
                break;
        }
    }//GEN-LAST:event_jComboBoxWvWActionPerformed

    private void jButtonRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRefreshActionPerformed

        this.jButtonRefresh.setEnabled(false);
        this.mainGui.reloadMatchIds();
    }//GEN-LAST:event_jButtonRefreshActionPerformed

    private void jButtonCoherentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCoherentActionPerformed

        this.eventTimerLabelCoherent.resetTimer();
        this.jToolBarInfo.setVisible(false);
        this.jButtonCoherent.setVisible(false);
    }//GEN-LAST:event_jButtonCoherentActionPerformed

    private void jLabelMenuMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelMenuMousePressed

        if (this.jToolBarMenu.isVisible()) {
            this.jToolBarMenu.setVisible(false);
            this.jComboBoxWvW.setVisible(false);
            this.jLabelMatchId.setVisible(false);
        } else {
            this.jToolBarMenu.setVisible(true);
            this.jComboBoxWvW.setVisible(true);
            this.jLabelMatchId.setVisible(true);
        }
    }//GEN-LAST:event_jLabelMenuMousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private gui.EventTimerLabel eventTimerLabelCoherent;
    private javax.swing.JButton jButtonClose;
    private javax.swing.JButton jButtonCoherent;
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
    private javax.swing.JLabel jLabelMatchId;
    private javax.swing.JLabel jLabelMenu;
    private javax.swing.JLabel jLabelToolTip;
    private javax.swing.JToolBar jToolBarInfo;
    private javax.swing.JToolBar jToolBarMenu;
    // End of variables declaration//GEN-END:variables
}
