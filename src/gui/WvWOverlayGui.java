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
import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
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

    private final GW2EventerGui mainGui;

    private final ArrayList labelsEternal;
    private final ArrayList labelsBorderlands;

    private final ArrayList timerLabelEternal;
    private final ArrayList timerLabelBorderlandsRed;
    private final ArrayList timerLabelBorderlandsBlue;
    private final ArrayList timerLabelBorderlandsGreen;

    private String matchId;
    private String matchIdColor;
    private String activeMap;

    private WvWReader wvwReader;

    private HashMap ownerDataCenter;
    private final HashMap ownerDataOldCenter;
    private HashMap ownerDataRed;
    private final HashMap ownerDataOldRed;
    private HashMap ownerDataBlue;
    private final HashMap ownerDataOldBlue;
    private HashMap ownerDataGreen;
    private final HashMap ownerDataOldGreen;

    private HashMap ownerDataPoints;
    private final HashMap ownerDataOldPoints;

    private final HashMap idsToLabelCenter;
    private final HashMap idsToLabelRed;
    private final HashMap idsToLabelBlue;
    private final HashMap idsToLabelGreen;

    private final ImageIcon castleRed;
    private final ImageIcon castleBlue;
    private final ImageIcon castleGreen;

    private final ImageIcon campRed;
    private final ImageIcon campBlue;
    private final ImageIcon campGreen;

    private final ImageIcon towerRed;
    private final ImageIcon towerBlue;
    private final ImageIcon towerGreen;

    private final ImageIcon keepRed;
    private final ImageIcon keepBlue;
    private final ImageIcon keepGreen;

    private Object[] eventLog;

    private Runnable infoBlinkRunnable;
    private Thread infoBlinkThread;
    
    /**
     * Creates new form BorderlandsOverlayGui
     */
    public WvWOverlayGui(GW2EventerGui mainGui) {

        this.mainGui = mainGui;

        this.infoBlinkRunnable = new Runnable() {

            @Override
            public void run() {
                
                Color oldColor = jToolBarEvents.getBackground();
                Color alarmColor = new Color(255, 0, 0, 200);
                
                try {
                    
                    jToolBarEvents.setBackground(alarmColor);
                    Thread.sleep(200);
                    jToolBarEvents.setBackground(oldColor);
                    Thread.sleep(200);
                    jToolBarEvents.setBackground(alarmColor);
                    Thread.sleep(200);
                    jToolBarEvents.setBackground(oldColor);
                    //Thread.sleep(200);
                    repaint();
                } catch (InterruptedException ex) {
                    Logger.getLogger(WvWOverlayGui.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        
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

        this.ownerDataCenter = new HashMap();
        this.ownerDataOldCenter = new HashMap();
        this.ownerDataRed = new HashMap();
        this.ownerDataOldRed = new HashMap();
        this.ownerDataBlue = new HashMap();
        this.ownerDataOldBlue = new HashMap();
        this.ownerDataGreen = new HashMap();
        this.ownerDataOldGreen = new HashMap();
        this.ownerDataPoints = new HashMap();
        this.ownerDataOldPoints = new HashMap();

        /*
         this.wvwReader = new WvWReader(this);
         this.wvwReader.setResult(this.ownerData);
         this.wvwReader.setMatchId(this.matchId);
         */
        this.idsToLabelCenter = new HashMap();
        this.idsToLabelRed = new HashMap();
        this.idsToLabelBlue = new HashMap();
        this.idsToLabelGreen = new HashMap();

        //id: api, id: icon
        this.idsToLabelCenter.put("1", new String[]{"3", "keep", "Overlook", "Center", "Aufsichtspunkt"});
        this.idsToLabelCenter.put("2", new String[]{"18", "keep", "Valley", "Center", "Tal"});
        this.idsToLabelCenter.put("3", new String[]{"15", "keep", "Lowlands", "Center", "Tiefland"});
        this.idsToLabelCenter.put("4", new String[]{"20", "camp", "Golanta Clearing", "Center", "Golanta-Licht"});
        this.idsToLabelCenter.put("5", new String[]{"6", "camp", "Pangloss Rise", "Center", "Pangloss-Anhöhe"});
        this.idsToLabelCenter.put("6", new String[]{"1", "camp", "Speldan Clearcut", "Center", "Speldan Kahlschlag"});
        this.idsToLabelCenter.put("7", new String[]{"21", "camp", "Danelon Passage", "Center", "Danelon-Passage"});
        this.idsToLabelCenter.put("8", new String[]{"12", "camp", "Umberglade Woods", "Center", "Umberlichtung-Forst"});
        this.idsToLabelCenter.put("9", new String[]{"10", "castle", "Stonemist Castle", "Center", "Schloss Steinnebel"});
        this.idsToLabelCenter.put("10", new String[]{"8", "camp", "Rogue's Quarry", "Center", "Schurbenbruch"});
        this.idsToLabelCenter.put("11", new String[]{"13", "tower", "Aldon's Ledge", "Center", "Aldons Vorsprung"});
        this.idsToLabelCenter.put("12", new String[]{"9", "tower", "Wildcreek Run", "Center", "Wildbachstrecke"});
        this.idsToLabelCenter.put("13", new String[]{"19", "tower", "Jerrifer's Slough", "Center", "Jerrifers Sumpfloch"});
        this.idsToLabelCenter.put("14", new String[]{"16", "tower", "Klovan Gully", "Center", "Klovan-Senke"});
        this.idsToLabelCenter.put("15", new String[]{"22", "tower", "Langor Gulch", "Center", "Langor-Schlucht"});
        this.idsToLabelCenter.put("16", new String[]{"17", "tower", "Quentin Lake", "Center", "Quentinsee"});
        this.idsToLabelCenter.put("17", new String[]{"2", "tower", "Mendon's Gap", "Center", "Mendons Spalt"});
        this.idsToLabelCenter.put("18", new String[]{"7", "tower", "Anzalias Pass", "Center", "Anzalias-Pass"});
        this.idsToLabelCenter.put("19", new String[]{"5", "tower", "Ogrewatch Cut", "Center", "Ogerwatch-Kanal"});
        this.idsToLabelCenter.put("20", new String[]{"4", "tower", "Veloka Slope", "Center", "Veloka-Hang"});
        this.idsToLabelCenter.put("21", new String[]{"11", "tower", "Durios Gulch", "Center", "Durios-Schlucht"});
        this.idsToLabelCenter.put("22", new String[]{"14", "tower", "Bravost Escarpment", "Center", "Bravost-Abhang"});

        /*
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
         */
        // neu
        this.idsToLabelRed.put("99", new String[]{"2", "camp", "Hamm's Lab", "RedHome", "Hamms Labor"});
        this.idsToLabelRed.put("100", new String[]{"12", "camp", "Bauer Farmstead", "RedHome", "Bauer-Gehöft"});
        this.idsToLabelRed.put("101", new String[]{"9", "camp", "McLain's Encampment", "RedHome", "McLains Lager"});
        this.idsToLabelRed.put("102", new String[]{"1", "tower", "O'del Academy", "RedHome", "O'del-Akademie"});
        this.idsToLabelRed.put("104", new String[]{"3", "tower", "Eternal Necropolis", "RedHome", "Ewige Nekropole"});
        this.idsToLabelRed.put("105", new String[]{"11", "tower", "Crankshaft Depot", "RedHome", "Kurbelwellen-Depot"});
        this.idsToLabelRed.put("106", new String[]{"7", "keep", "Blistering Undercroft", "RedHome", "Brennende Gruft"});
        this.idsToLabelRed.put("109", new String[]{"6", "camp", "Roy's Refuge", "RedHome", "Roys Zuflucht"});
        this.idsToLabelRed.put("110", new String[]{"10", "tower", "Parched Outpost", "RedHome", "Verdörrter Außenposten"});
        this.idsToLabelRed.put("113", new String[]{"5", "keep", "Stoic Rampart", "RedHome", "Stoischer Festungswall"});
        this.idsToLabelRed.put("114", new String[]{"8", "keep", "Osprey's Palace", "RedHome", "Fischadler-Palast"});
        this.idsToLabelRed.put("115", new String[]{"4", "camp", "Boettiger's Hideaway", "RedHome", "Boettigers Versteck"});
        this.idsToLabelRed.put("116", new String[]{"13", "camp", "Dustwhisper Well", "RedHome", "Brunnen des Staubflüsterns"});

        this.idsToLabelBlue.put("99", new String[]{"2", "camp", "Zakk's Lab", "BlueHome", "Zakks Labor"});
        this.idsToLabelBlue.put("100", new String[]{"12", "camp", "Gee Farmstead", "BlueHome", "Gee-Gehöft"});
        this.idsToLabelBlue.put("101", new String[]{"9", "camp", "Habib's Encampment", "BlueHome", "Habibs Lager"});
        this.idsToLabelBlue.put("102", new String[]{"1", "tower", "Kay'li Academy", "BlueHome", "Kay'li-Akademie"});
        this.idsToLabelBlue.put("104", new String[]{"3", "tower", "Undying Necropolis", "BlueHome", "Unsterbliche Nekropole"});
        this.idsToLabelBlue.put("105", new String[]{"11", "tower", "Flywheel Depot", "BlueHome", "Schwungrad-Depot"});
        this.idsToLabelBlue.put("106", new String[]{"7", "keep", "Torrid Undercroft", "BlueHome", "Glühende Gruft"});
        this.idsToLabelBlue.put("109", new String[]{"6", "camp", "Oliver's Refuge", "BlueHome", "Olivers Zuflucht"});
        this.idsToLabelBlue.put("110", new String[]{"10", "tower", "Barren Outpost", "BlueHome", "Öder Außenposten"});
        this.idsToLabelBlue.put("113", new String[]{"5", "keep", "Hardened Rampart", "BlueHome", "Stahlharter Festungswall"});
        this.idsToLabelBlue.put("114", new String[]{"8", "keep", "Shrike's Palace", "BlueHome", "Würger-Palast"});
        this.idsToLabelBlue.put("115", new String[]{"4", "camp", "Berdrow's Hideaway", "BlueHome", "Berdrows Versteck"});
        this.idsToLabelBlue.put("116", new String[]{"13", "camp", "Lastgasp Well", "BlueHome", "Brunnden des Letzten Schnaufers"});

        this.idsToLabelGreen.put("99", new String[]{"2", "camp", "Lesh's Lab", "GreenHome", "Leshs Labor"});
        this.idsToLabelGreen.put("100", new String[]{"12", "camp", "Barrett Farmstead", "GreenHome", "Barrett-Gehöft"});
        this.idsToLabelGreen.put("101", new String[]{"9", "camp", "Patrick's Encampment", "GreenHome", "Patricks Lager"});
        this.idsToLabelGreen.put("102", new String[]{"1", "tower", "Y'lan Academy", "GreenHome", "Y'lan-Akademie"});
        this.idsToLabelGreen.put("104", new String[]{"3", "tower", "Deathless Necropolis", "GreenHome", "Todlose Nekropole"});
        this.idsToLabelGreen.put("105", new String[]{"11", "tower", "Sparkplug Depot", "GreenHome", "Zündfunken-Depot"});
        this.idsToLabelGreen.put("106", new String[]{"7", "keep", "Scorching Undercroft", "GreenHome", "Versengende Gruft"});
        this.idsToLabelGreen.put("109", new String[]{"6", "camp", "Norfolk's Refuge", "GreenHome", "Norfolks Zuflucht"});
        this.idsToLabelGreen.put("110", new String[]{"10", "tower", "Withered Outpost", "GreenHome", "Welker Außenposten"});
        this.idsToLabelGreen.put("113", new String[]{"5", "keep", "Impassive Rampart", "GreenHome", "Unbeeindruckter Festungswall"});
        this.idsToLabelGreen.put("114", new String[]{"8", "keep", "Harrier's Palace", "GreenHome", "Weihen Palast"});
        this.idsToLabelGreen.put("115", new String[]{"4", "camp", "Hughe's Hideaway", "GreenHome", "Hughes Versteck"});
        this.idsToLabelGreen.put("116", new String[]{"13", "camp", "Smashedhope Well", "GreenHome", "Brunnen der Zerschlagenen Hoffnung"});

        this.eventLog = new Object[]{null, null, null};

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

    private void setTick(int tick) {

        this.jLabelTick.setText("+" + tick);
    }

    private void setDiff1(int difference) {

        this.jLabelDiff1.setText(difference + "");
    }

    private void setDiff2(int difference) {

        this.jLabelDiff2.setText(difference + "");
    }

    private void startInfoBlink() {
        
        if ((this.infoBlinkThread == null) || (this.infoBlinkThread.getState() == Thread.State.TERMINATED)) {

            this.infoBlinkThread = new Thread(this.infoBlinkRunnable);
            this.infoBlinkThread.start();
        }
    }
    
    private void pushEvent(String event) {

        this.eventLog[2] = this.eventLog[1];
        this.eventLog[1] = this.eventLog[0];
        this.eventLog[0] = new Object[]{event, new Date()};
        
        this.startInfoBlink();
    }

    private String getEvent(int i) {

        String event = "";

        Object[] eventObject = (Object[]) this.eventLog[i];

        if (eventObject != null) {

            Date date = (Date) eventObject[1];
            Date currentDate = new Date();
            int secDiff = (int) ((currentDate.getTime() - date.getTime()) / 1000);

            if (secDiff < 300) {

                int minDiff = secDiff / 60;
                secDiff = secDiff % 60;

                String secDiffString = secDiff + "";

                if (secDiff < 10) {
                    secDiffString = "0" + secDiffString;
                }

                event = (String) eventObject[0] + " (" + minDiff + ":" + secDiffString + ")</html>";
            }
        }

        return event;
    }

    public void refresh(int timeDifference) {

        this.setAlwaysOnTop(true);
        this.toFront();
        this.repaint();

        Iterator it;

        if (this.ownerDataOldCenter.isEmpty()) {

            it = this.ownerDataCenter.entrySet().iterator();

            while (it.hasNext()) {

                Map.Entry pairs = (Map.Entry) it.next();

                String id = (String) pairs.getKey();
                String owner = (String) pairs.getValue();

                this.ownerDataOldCenter.put(new String(id), new String(owner));
            }

            it = this.ownerDataRed.entrySet().iterator();

            while (it.hasNext()) {

                Map.Entry pairs = (Map.Entry) it.next();

                String id = (String) pairs.getKey();
                String owner = (String) pairs.getValue();

                this.ownerDataOldRed.put(new String(id), new String(owner));
            }

            it = this.ownerDataBlue.entrySet().iterator();

            while (it.hasNext()) {

                Map.Entry pairs = (Map.Entry) it.next();

                String id = (String) pairs.getKey();
                String owner = (String) pairs.getValue();

                this.ownerDataOldBlue.put(new String(id), new String(owner));
            }

            it = this.ownerDataGreen.entrySet().iterator();

            while (it.hasNext()) {

                Map.Entry pairs = (Map.Entry) it.next();

                String id = (String) pairs.getKey();
                String owner = (String) pairs.getValue();

                this.ownerDataOldGreen.put(new String(id), new String(owner));
            }
            
            it = this.ownerDataPoints.entrySet().iterator();

            while (it.hasNext()) {

                Map.Entry pairs = (Map.Entry) it.next();

                String owner = (String) pairs.getKey();
                String[] points = (String[]) pairs.getValue();

                this.ownerDataOldPoints.put(new String(owner), new String[]{points[0], points[1], points[2]});
            }

            if (this.activeMap.equals("Center")) {
                this.setEternalColors();
            } else {
                this.setBorderlandsColors();
            }
        }

        //String[] points = ((String) this.ownerData.get("0")).split(",");
        //String[] pointsOld = ((String) this.ownerDataOld.get("0")).split(",");
        String[] points = ((String[]) this.ownerDataPoints.get("all"));
        String[] pointsOld = ((String[]) this.ownerDataOldPoints.get("all"));

        int diff1 = 0;
        int diff1Old = 0;
        int diff2 = 0;
        int diff2Old = 0;

        switch (this.matchIdColor) {
            case "red":
                diff1 = Integer.parseInt(points[0]) - Integer.parseInt(points[1]);
                diff2 = Integer.parseInt(points[0]) - Integer.parseInt(points[2]);

                diff1Old = Integer.parseInt(pointsOld[0]) - Integer.parseInt(pointsOld[1]);
                diff2Old = Integer.parseInt(pointsOld[0]) - Integer.parseInt(pointsOld[2]);
                break;
            case "blue":
                diff1 = Integer.parseInt(points[1]) - Integer.parseInt(points[0]);
                diff2 = Integer.parseInt(points[1]) - Integer.parseInt(points[2]);

                diff1Old = Integer.parseInt(pointsOld[1]) - Integer.parseInt(pointsOld[0]);
                diff2Old = Integer.parseInt(pointsOld[1]) - Integer.parseInt(pointsOld[2]);
                break;
            case "green":
                diff1 = Integer.parseInt(points[2]) - Integer.parseInt(points[0]);
                diff2 = Integer.parseInt(points[2]) - Integer.parseInt(points[1]);

                diff1Old = Integer.parseInt(pointsOld[2]) - Integer.parseInt(pointsOld[0]);
                diff2Old = Integer.parseInt(pointsOld[2]) - Integer.parseInt(pointsOld[1]);
                break;
        }

        if (diff1Old < diff1) {
            this.jLabelDiff1.setForeground(Color.green);
        } else if (diff1Old == diff1) {
            this.jLabelDiff1.setForeground(Color.white);
        } else {
            this.jLabelDiff1.setForeground(Color.red);
        }

        if (diff2Old < diff2) {
            this.jLabelDiff2.setForeground(Color.green);
        } else if (diff2Old == diff2) {
            this.jLabelDiff2.setForeground(Color.white);
        } else {
            this.jLabelDiff2.setForeground(Color.red);
        }

        this.setDiff1(diff1);
        this.setDiff2(diff2);

        int newTick = 0;

        for (int i = 0; i <= 3; i++) {

            HashMap currentOwnerData;
            HashMap currentOwnerDataOld;
            HashMap currentIdsToLabel;
            String homeLand;

            if (i == 0) {
                currentOwnerData = this.ownerDataCenter;
                currentOwnerDataOld = this.ownerDataOldCenter;
                currentIdsToLabel = this.idsToLabelCenter;
                homeLand = "Center";
            } else if (i == 1) {
                currentOwnerData = this.ownerDataRed;
                currentOwnerDataOld = this.ownerDataOldRed;
                currentIdsToLabel = this.idsToLabelRed;
                homeLand = "RedHome";
            } else if (i == 2) {
                currentOwnerData = this.ownerDataBlue;
                currentOwnerDataOld = this.ownerDataOldBlue;
                currentIdsToLabel = this.idsToLabelBlue;
                homeLand = "BlueHome";
            } else {
                currentOwnerData = this.ownerDataGreen;
                currentOwnerDataOld = this.ownerDataOldGreen;
                currentIdsToLabel = this.idsToLabelGreen;
                homeLand = "GreenHome";
            }

            it = currentOwnerData.entrySet().iterator();

            while (it.hasNext()) {

                Map.Entry pairs = (Map.Entry) it.next();

                String id = (String) pairs.getKey();
                String owner = (String) pairs.getValue();
                String onwerOld = (String) currentOwnerDataOld.get(id);

                if (currentIdsToLabel.containsKey(id)) {

                    int labelNumber = Integer.parseInt(((String[]) currentIdsToLabel.get(id))[0]);

                    String labelType = ((String[]) currentIdsToLabel.get(id))[1];
                    String labelName;
                    
                    if (mainGui.getLanguage().equals("EN")) {
                        labelName = ((String[]) currentIdsToLabel.get(id))[2];
                    } else {
                        labelName = ((String[]) currentIdsToLabel.get(id))[4];
                    }
                    
                    String labelHome = ((String[]) currentIdsToLabel.get(id))[3];

                    EventTimerLabel currentTimer = null;
                    JLabel currentLabel = null;

                    if (owner.toLowerCase().equals(this.matchIdColor)) {

                        switch (labelType) {
                            case "keep":
                                newTick = newTick + 25;
                                break;
                            case "camp":
                                newTick = newTick + 5;
                                break;
                            case "tower":
                                newTick = newTick + 10;
                                break;
                            case "castle":
                                newTick = newTick + 35;
                                break;
                        }
                    }

                    this.setTick(newTick);

                    String homeSpeak;
                    String homeSpeakColor;
                    
                    if (labelHome.equals("BlueHome")) {
                        currentTimer = (EventTimerLabel) this.timerLabelBorderlandsBlue.get(labelNumber - 1);
                        homeSpeak = "blue";
                        homeSpeakColor = "#26c9ff";
                        
                        if (mainGui.getLanguage().equals("DE")) {
                            homeSpeak = "Blau";
                        }
                    } else if (labelHome.equals("RedHome")) {
                        currentTimer = (EventTimerLabel) this.timerLabelBorderlandsRed.get(labelNumber - 1);
                        homeSpeak = "red";
                        homeSpeakColor = "#ff6d6d";
                        
                        if (mainGui.getLanguage().equals("DE")) {
                            homeSpeak = "Rot";
                        }
                    } else if(labelHome.equals("GreenHome")) {
                        currentTimer = (EventTimerLabel) this.timerLabelBorderlandsGreen.get(labelNumber - 1);
                        homeSpeak = "green";
                        homeSpeakColor = "#6dff88";
                        
                        if (mainGui.getLanguage().equals("DE")) {
                            homeSpeak = "Grün";
                        }
                    } else {
                        currentTimer = (EventTimerLabel) this.timerLabelEternal.get(labelNumber - 1);
                        homeSpeak = "center";
                        homeSpeakColor = "white";
                        
                        if (mainGui.getLanguage().equals("DE")) {
                            homeSpeak = "Ewige";
                        }
                    }

                    if ((!owner.equals(onwerOld)) && (currentTimer != null)) {
                        //if (!currentTimer.isTicking()) { // ??
                        currentTimer.resetTimer(); // ??
                        currentTimer.setCounter(currentTimer.getCounter() - timeDifference);
                        currentTimer.startTimer();

                        String ownerSpeak;
                        String ownerSpeakColor;
                        
                        if (mainGui.getLanguage().equals("DE")) {
                            
                            if (owner.equals("Blue")) {
                                ownerSpeak = "Blau";
                                ownerSpeakColor = "#26c9ff";
                            } else if (owner.equals("Red")) {
                                ownerSpeak = "Rot";
                                ownerSpeakColor = "#ff6d6d";
                            } else {
                                ownerSpeak = "Grün";
                                ownerSpeakColor = "#6dff88";
                            }
                            
                            mainGui.writeAndSpeak(labelName + ", auf " + homeSpeak + ", geht zu " + ownerSpeak);
                        } else {
                            
                            if (owner.equals("Blue")) {
                                ownerSpeak = "blue";
                                ownerSpeakColor = "#26c9ff";
                            } else if (owner.equals("Red")) {
                                ownerSpeak = "red";
                                ownerSpeakColor = "#ff6d6d";
                            } else {
                                ownerSpeak = "green";
                                ownerSpeakColor = "#6dff88";
                            }
                            
                            mainGui.writeAndSpeak(labelName + ", on " + homeSpeak + ", change to " + ownerSpeak);
                        }
                        
                        /*
                         this.pushEvent("<html>" + labelName
                         + "(" + labelHome + ") <b><font color="
                         + onwerOld + ">" + onwerOld
                         + "</font></b> => <b><font color="
                         + owner + ">" + owner + "</font></b>");*/
                        
                        String subLabelName = labelName;
                        
                        if (labelName.length() >= 20) {
                            subLabelName = labelName.substring(0, 17) + "...";
                        }
                        
                        this.pushEvent("<html>" + subLabelName
                                + " (M: <b><font color="
                                + homeSpeakColor + ">" + homeSpeak + "</font></b>) => <b><font color="
                                + ownerSpeakColor + ">" + ownerSpeak + "</font></b>");

                        //this.repaint();
                        
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
                                } catch (IllegalArgumentException | IllegalAccessException ex) {
                                    Logger.getLogger(WvWOverlayGui.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            } catch (NoSuchFieldException | SecurityException ex) {
                                Logger.getLogger(WvWOverlayGui.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        //}
                    }
                }
            }
        }

        for (int i = 0; i <= 3; i++) {

            HashMap currentOwnerData;
            HashMap currentOwnerDataOld;

            if (i == 0) {
                currentOwnerData = this.ownerDataCenter;
                currentOwnerDataOld = this.ownerDataOldCenter;
            } else if (i == 1) {
                currentOwnerData = this.ownerDataRed;
                currentOwnerDataOld = this.ownerDataOldRed;
            } else if (i == 2) {
                currentOwnerData = this.ownerDataBlue;
                currentOwnerDataOld = this.ownerDataOldBlue;
            } else {
                currentOwnerData = this.ownerDataGreen;
                currentOwnerDataOld = this.ownerDataOldGreen;
            }
            
            currentOwnerDataOld.clear();
            it = currentOwnerData.entrySet().iterator();

            while (it.hasNext()) {

                Map.Entry pairs = (Map.Entry) it.next();

                String id = (String) pairs.getKey();
                String owner = (String) pairs.getValue();

                currentOwnerDataOld.put(new String(id), new String(owner));
            }
        }

        it = this.ownerDataPoints.entrySet().iterator();

        while (it.hasNext()) {

            Map.Entry pairs = (Map.Entry) it.next();

            String owner = (String) pairs.getKey();
            String[] curpoints = (String[]) pairs.getValue();

            this.ownerDataOldPoints.put(new String(owner), new String[]{curpoints[0], curpoints[1], curpoints[2]});
        }
        
        /*
         if (this.activeMap.equals("Center")) {
         this.setEternalColors();
         } else {
         this.setBorderlandsColors();
         }*/
        
        String event1 = this.getEvent(0);
        String event2 = this.getEvent(1);
        String event3 = this.getEvent(2);
        
        this.jLabelEventLog1.setText(event1);
        this.jLabelEventLog2.setText(event2);
        this.jLabelEventLog3.setText(event3);
        
        this.repaint();
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

        this.eventLog = new Object[]{null, null, null};

        this.jButtonRefresh.setEnabled(true);

        this.jToolBarMenu.setVisible(true);
        this.jComboBoxWvW.setVisible(true);
        this.jLabelMatchId.setVisible(true);

        //this.jToolBarInfo.setVisible(true);
        //this.jButtonCoherent.setVisible(true);

        this.mainGui.setMatchId();

        this.setMatchIdColor(this.mainGui.getMatchIdColor());
        this.setMatchId(this.mainGui.getMatchId());

        this.ownerDataOldCenter.clear();
        this.ownerDataOldRed.clear();
        this.ownerDataOldBlue.clear();
        this.ownerDataOldGreen.clear();
        this.ownerDataOldPoints.clear();

        this.resetTimer();

        this.ownerDataCenter = new HashMap();
        this.ownerDataRed = new HashMap();
        this.ownerDataBlue = new HashMap();
        this.ownerDataGreen = new HashMap();
        this.ownerDataPoints = new HashMap();

        this.wvwReader = new WvWReader(this);
        this.wvwReader.setResultMaps(this.ownerDataRed, this.ownerDataBlue, this.ownerDataGreen, this.ownerDataCenter, this.ownerDataPoints);
        this.wvwReader.setMatchId(this.matchId);

        this.wvwReader.start();

        this.eventTimerLabelCoherent.startTimer();
        this.eventTimerLabelCoherent.setVisible(true);

        this.setVisible(true);
    }

    public void deactivateGui() {

        if (this.wvwReader != null) {

            this.wvwReader.interrupt();

            this.ownerDataOldCenter.clear();
            this.ownerDataOldRed.clear();
            this.ownerDataOldBlue.clear();
            this.ownerDataOldGreen.clear();
            this.ownerDataOldPoints.clear();

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
            } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
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
            } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
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
            } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
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

        if (!this.ownerDataOldCenter.isEmpty()) {

            Field f;
            
            for (int i = 0; i <= 0; i++) {

                HashMap currentOwnerData;
                HashMap currentOwnerDataOld;
                HashMap currentIdsToLabel;
                String homeLand;

                if (i == 0) {
                    currentOwnerData = this.ownerDataCenter;
                    currentOwnerDataOld = this.ownerDataOldCenter;
                    currentIdsToLabel = this.idsToLabelCenter;
                    homeLand = "Center";
                } else if (i == 1) {
                    currentOwnerData = this.ownerDataRed;
                    currentOwnerDataOld = this.ownerDataOldRed;
                    currentIdsToLabel = this.idsToLabelRed;
                    homeLand = "RedHome";
                } else if (i == 2) {
                    currentOwnerData = this.ownerDataBlue;
                    currentOwnerDataOld = this.ownerDataOldBlue;
                    currentIdsToLabel = this.idsToLabelBlue;
                    homeLand = "BlueHome";
                } else {
                    currentOwnerData = this.ownerDataGreen;
                    currentOwnerDataOld = this.ownerDataOldGreen;
                    currentIdsToLabel = this.idsToLabelGreen;
                    homeLand = "GreenHome";
                }
                
                Iterator it = currentOwnerDataOld.entrySet().iterator();

                while (it.hasNext()) {

                    Map.Entry pairs = (Map.Entry) it.next();

                    String id = (String) pairs.getKey();
                    String owner = (String) pairs.getValue();
                    
                    if (currentIdsToLabel.containsKey(id)) {

                        int labelNumber = Integer.parseInt(((String[]) currentIdsToLabel.get(id))[0]);

                        String labelType = ((String[]) currentIdsToLabel.get(id))[1];
                        String labelToolTip;
                        
                        if (mainGui.getLanguage().equals("EN")) {
                            labelToolTip = ((String[]) currentIdsToLabel.get(id))[2];
                        } else {
                            labelToolTip = ((String[]) currentIdsToLabel.get(id))[4];
                        }
                        
                        String labelHome = ((String[]) currentIdsToLabel.get(id))[3];
                        
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
                                } catch (IllegalArgumentException | IllegalAccessException ex) {
                                    Logger.getLogger(WvWOverlayGui.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            } catch (NoSuchFieldException | SecurityException ex) {
                                Logger.getLogger(WvWOverlayGui.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }
            }
        }
    }

    private void setBorderlandsColors() {

        if (!this.ownerDataOldCenter.isEmpty()) {

            Field f;
            
            for (int i = 1; i <= 3; i++) {

                HashMap currentOwnerData;
                HashMap currentOwnerDataOld;
                HashMap currentIdsToLabel;
                String homeLand;

                if (i == 0) {
                    currentOwnerData = this.ownerDataCenter;
                    currentOwnerDataOld = this.ownerDataOldCenter;
                    currentIdsToLabel = this.idsToLabelCenter;
                    homeLand = "Center";
                } else if (i == 1) {
                    currentOwnerData = this.ownerDataRed;
                    currentOwnerDataOld = this.ownerDataOldRed;
                    currentIdsToLabel = this.idsToLabelRed;
                    homeLand = "RedHome";
                } else if (i == 2) {
                    currentOwnerData = this.ownerDataBlue;
                    currentOwnerDataOld = this.ownerDataOldBlue;
                    currentIdsToLabel = this.idsToLabelBlue;
                    homeLand = "BlueHome";
                } else {
                    currentOwnerData = this.ownerDataGreen;
                    currentOwnerDataOld = this.ownerDataOldGreen;
                    currentIdsToLabel = this.idsToLabelGreen;
                    homeLand = "GreenHome";
                }
                
                Iterator it = currentOwnerDataOld.entrySet().iterator();

                while (it.hasNext()) {

                    Map.Entry pairs = (Map.Entry) it.next();

                    String id = (String) pairs.getKey();
                    String owner = (String) pairs.getValue();

                    if (currentIdsToLabel.containsKey(id)) {

                        int labelNumber = Integer.parseInt(((String[]) currentIdsToLabel.get(id))[0]);

                        String labelType = ((String[]) currentIdsToLabel.get(id))[1];
                        String labelToolTip;

                        if (mainGui.getLanguage().equals("EN")) {
                            labelToolTip = ((String[]) currentIdsToLabel.get(id))[2];
                        } else {
                            labelToolTip = ((String[]) currentIdsToLabel.get(id))[4];
                        }
                        
                        String labelHome = ((String[]) currentIdsToLabel.get(id))[3];
                        
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
                                } catch (IllegalArgumentException | IllegalAccessException ex) {
                                    Logger.getLogger(WvWOverlayGui.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            } catch (NoSuchFieldException | SecurityException ex) {
                                Logger.getLogger(WvWOverlayGui.class.getName()).log(Level.SEVERE, null, ex);
                            }
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
            } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
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
        jLabel1 = new javax.swing.JLabel();
        jButtonRefresh = new javax.swing.JButton();
        jButtonMinimize = new javax.swing.JButton();
        jButtonMaximize = new javax.swing.JButton();
        jButtonMove = new javax.swing.JButton();
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
        jLabelTick = new javax.swing.JLabel();
        jLabelDiff1 = new javax.swing.JLabel();
        jLabelDiff2 = new javax.swing.JLabel();
        jToolBarEvents = new javax.swing.JToolBar();
        eventTimerLabelCoherent = new gui.EventTimerLabel();
        jLabelEventLog1 = new javax.swing.JLabel();
        jLabelEventLog2 = new javax.swing.JLabel();
        jLabelEventLog3 = new javax.swing.JLabel();

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
        getContentPane().add(jComboBoxWvW, new org.netbeans.lib.awtextra.AbsoluteConstraints(236, 40, -1, -1));

        jToolBarMenu.setBorder(null);
        jToolBarMenu.setForeground(new java.awt.Color(255, 255, 255));
        jToolBarMenu.setRollover(true);
        jToolBarMenu.setFocusable(false);
        jToolBarMenu.setOpaque(false);

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("WvW");
        jToolBarMenu.add(jLabel1);

        jButtonRefresh.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jButtonRefresh.setForeground(new java.awt.Color(255, 255, 255));
        jButtonRefresh.setText("reset");
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

        getContentPane().add(jToolBarMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jLabelEternal22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        jLabelEternal22.setFocusable(false);
        getContentPane().add(jLabelEternal22, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 300, -1, -1));

        jLabelEternal21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        jLabelEternal21.setFocusable(false);
        getContentPane().add(jLabelEternal21, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 300, -1, -1));

        jLabelEternal20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        jLabelEternal20.setFocusable(false);
        getContentPane().add(jLabelEternal20, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 280, -1, -1));

        jLabelEternal19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        jLabelEternal19.setFocusable(false);
        getContentPane().add(jLabelEternal19, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 290, -1, -1));

        jLabelEternal18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        jLabelEternal18.setFocusable(false);
        getContentPane().add(jLabelEternal18, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 260, -1, -1));

        jLabelEternal17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        jLabelEternal17.setFocusable(false);
        getContentPane().add(jLabelEternal17, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 270, -1, -1));

        jLabelEternal16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        getContentPane().add(jLabelEternal16, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 250, -1, -1));

        jLabelEternal15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        jLabelEternal15.setFocusable(false);
        getContentPane().add(jLabelEternal15, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 260, -1, -1));

        jLabelEternal14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        jLabelEternal14.setFocusable(false);
        getContentPane().add(jLabelEternal14, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 220, -1, -1));

        jLabelEternal13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        jLabelEternal13.setFocusable(false);
        getContentPane().add(jLabelEternal13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, -1, -1));

        jLabelEternal12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        jLabelEternal12.setFocusable(false);
        getContentPane().add(jLabelEternal12, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 190, -1, -1));

        jLabelEternal11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        jLabelEternal11.setFocusable(false);
        getContentPane().add(jLabelEternal11, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 200, -1, -1));

        jLabelEternal10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        jLabelEternal10.setFocusable(false);
        getContentPane().add(jLabelEternal10, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 200, -1, -1));

        jLabelEternal9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        jLabelEternal9.setFocusable(false);
        getContentPane().add(jLabelEternal9, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 200, -1, -1));

        jLabelEternal8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        jLabelEternal8.setFocusable(false);
        getContentPane().add(jLabelEternal8, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 190, -1, -1));

        jLabelEternal7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        jLabelEternal7.setFocusable(false);
        getContentPane().add(jLabelEternal7, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 150, -1, -1));

        jLabelEternal6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        jLabelEternal6.setFocusable(false);
        getContentPane().add(jLabelEternal6, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 120, -1, -1));

        jLabelEternal5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        jLabelEternal5.setFocusable(false);
        getContentPane().add(jLabelEternal5, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 130, -1, -1));

        jLabelEternal4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        jLabelEternal4.setFocusable(false);
        getContentPane().add(jLabelEternal4, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 90, -1, -1));

        jLabelEternal3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        jLabelEternal3.setFocusable(false);
        getContentPane().add(jLabelEternal3, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 100, -1, -1));

        jLabelEternal2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        jLabelEternal2.setFocusable(false);
        getContentPane().add(jLabelEternal2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 80, -1, -1));

        jLabelEternal1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        jLabelEternal1.setFocusable(false);
        getContentPane().add(jLabelEternal1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 100, -1, -1));

        jLabelBorderlands5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        jLabelBorderlands5.setFocusable(false);
        getContentPane().add(jLabelBorderlands5, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 150, -1, -1));

        jLabelBorderlands9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        jLabelBorderlands9.setFocusable(false);
        getContentPane().add(jLabelBorderlands9, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 280, -1, -1));

        jLabelBorderlands3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        jLabelBorderlands3.setFocusable(false);
        getContentPane().add(jLabelBorderlands3, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 70, -1, -1));

        jLabelBorderlands1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        jLabelBorderlands1.setFocusable(false);
        getContentPane().add(jLabelBorderlands1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 70, -1, -1));

        jLabelBorderlands6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        jLabelBorderlands6.setFocusable(false);
        getContentPane().add(jLabelBorderlands6, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 130, -1, -1));

        jLabelBorderlands8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        jLabelBorderlands8.setFocusable(false);
        getContentPane().add(jLabelBorderlands8, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 220, -1, -1));

        jLabelBorderlands4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        jLabelBorderlands4.setFocusable(false);
        getContentPane().add(jLabelBorderlands4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, -1, -1));

        jLabelBorderlands2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        jLabelBorderlands2.setFocusable(false);
        getContentPane().add(jLabelBorderlands2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 60, -1, -1));

        jLabelBorderlands7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        jLabelBorderlands7.setFocusable(false);
        getContentPane().add(jLabelBorderlands7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, -1, -1));

        jLabelBorderlands10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        jLabelBorderlands10.setFocusable(false);
        getContentPane().add(jLabelBorderlands10, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 290, -1, -1));

        jLabelBorderlands13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        jLabelBorderlands13.setFocusable(false);
        getContentPane().add(jLabelBorderlands13, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 330, -1, -1));

        jLabelBorderlands11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        jLabelBorderlands11.setFocusable(false);
        getContentPane().add(jLabelBorderlands11, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 300, -1, -1));

        jLabelBorderlands12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw/camp_red.png"))); // NOI18N
        jLabelBorderlands12.setFocusable(false);
        getContentPane().add(jLabelBorderlands12, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 270, -1, -1));

        jLabelMatchId.setForeground(new java.awt.Color(255, 255, 255));
        jLabelMatchId.setText("matchid:");
        getContentPane().add(jLabelMatchId, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 25, -1, -1));

        jLabelMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/wvw.png"))); // NOI18N
        jLabelMenu.setToolTipText("Menu");
        jLabelMenu.setFocusable(false);
        jLabelMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabelMenuMousePressed(evt);
            }
        });
        getContentPane().add(jLabelMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(235, 20, -1, -1));

        jLabelToolTip.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabelToolTip.setForeground(new java.awt.Color(255, 255, 255));
        jLabelToolTip.setFocusable(false);
        getContentPane().add(jLabelToolTip, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 14, -1, -1));

        jLabelTick.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabelTick.setForeground(new java.awt.Color(255, 255, 255));
        getContentPane().add(jLabelTick, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 42, -1, -1));

        jLabelDiff1.setForeground(new java.awt.Color(255, 255, 255));
        getContentPane().add(jLabelDiff1, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 60, -1, -1));

        jLabelDiff2.setForeground(new java.awt.Color(255, 255, 255));
        getContentPane().add(jLabelDiff2, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 80, -1, -1));

        jToolBarEvents.setBackground(new Color(0, 0, 0, 120));
        jToolBarEvents.setFloatable(false);
        jToolBarEvents.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jToolBarEvents.setBorderPainted(false);
        jToolBarEvents.setFocusable(false);

        eventTimerLabelCoherent.setBackground(new java.awt.Color(0, 0, 0));
        eventTimerLabelCoherent.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eventTimerLabelCoherentMouseClicked(evt);
            }
        });
        jToolBarEvents.add(eventTimerLabelCoherent);

        jLabelEventLog1.setBackground(new java.awt.Color(0, 0, 0));
        jLabelEventLog1.setForeground(new java.awt.Color(255, 255, 255));
        jToolBarEvents.add(jLabelEventLog1);

        jLabelEventLog2.setForeground(new java.awt.Color(255, 255, 255));
        jToolBarEvents.add(jLabelEventLog2);

        jLabelEventLog3.setForeground(new java.awt.Color(255, 255, 255));
        jToolBarEvents.add(jLabelEventLog3);

        getContentPane().add(jToolBarEvents, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 365, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

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

    private void jButtonMoveMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonMoveMouseDragged

        int x = this.getX();
        int y = this.getY();

        Point mouseLoc;
        Robot rob;

        try {
            rob = new Robot();
            mouseLoc = MouseInfo.getPointerInfo().getLocation();

            int newx = mouseLoc.x - 150;
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

    private void eventTimerLabelCoherentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eventTimerLabelCoherentMouseClicked

        this.eventTimerLabelCoherent.resetTimer();
    }//GEN-LAST:event_eventTimerLabelCoherentMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private gui.EventTimerLabel eventTimerLabelCoherent;
    private javax.swing.JButton jButtonClose;
    private javax.swing.JButton jButtonMaximize;
    private javax.swing.JButton jButtonMinimize;
    private javax.swing.JButton jButtonMove;
    private javax.swing.JButton jButtonRefresh;
    private javax.swing.JComboBox jComboBoxWvW;
    private javax.swing.JLabel jLabel1;
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
    private javax.swing.JLabel jLabelDiff1;
    private javax.swing.JLabel jLabelDiff2;
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
    private javax.swing.JLabel jLabelEventLog1;
    private javax.swing.JLabel jLabelEventLog2;
    private javax.swing.JLabel jLabelEventLog3;
    private javax.swing.JLabel jLabelMatchId;
    private javax.swing.JLabel jLabelMenu;
    private javax.swing.JLabel jLabelTick;
    private javax.swing.JLabel jLabelToolTip;
    private javax.swing.JToolBar jToolBarEvents;
    private javax.swing.JToolBar jToolBarMenu;
    // End of variables declaration//GEN-END:variables
}
