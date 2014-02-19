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
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.text.DefaultFormatter;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author mkdr <makedir@gmail.com>
 */
public class GW2EventerGui extends javax.swing.JFrame {
    
    private static final String LANG_AUTO_REFRESH_DE = "auto. neuladen";
    private static final String LANG_AUTO_REFRESH_EN = "auto refresh";
    private static final String LANG_AUTO_REFRESH_ES = "auto refresh";
    private static final String LANG_AUTO_REFRESH_FR = "auto refresh";
    
    private static final String LANG_PLAY_SOUNDS_DE = "Sounds abspielen";
    private static final String LANG_PLAY_SOUNDS_EN = "play sounds";
    private static final String LANG_PLAY_SOUNDS_ES = "play sounds";
    private static final String LANG_PLAY_SOUNDS_FR = "play sounds";
    
    private static final String LANG_PREVENT_SLEEP_DE = "PC Sleepmode verhindern";
    private static final String LANG_PREVENT_SLEEP_EN = "prevent pc sleep";
    private static final String LANG_PREVENT_SLEEP_ES = "prevent pc sleep";
    private static final String LANG_PREVENT_SLEEP_FR = "prevent pc sleep";
    
    private static final String LANG_RELOAD_BTN_DE = "Setzen/Neuladen";
    private static final String LANG_RELOAD_BTN_EN = "Set/Reload";
    private static final String LANG_RELOAD_BTN_ES = "Set/Reload";
    private static final String LANG_RELOAD_BTN_FR = "Set/Reload";
    
    private static final String LANG_NOT_RUNNING_DE = "läuft nicht";
    private static final String LANG_NOT_RUNNING_EN = "not running";
    private static final String LANG_NOT_RUNNING_ES = "not running";
    private static final String LANG_NOT_RUNNING_FR = "not running";
    
    private static final String LANG_WORKING_DE = "Bitte warten...";
    private static final String LANG_WORKING_EN = "please wait...";
    private static final String LANG_WORKING_ES = "please wait...";
    private static final String LANG_WORKING_FR = "please wait...";
    
    private static final String LANG_TIP1_DE = "<html><font color=\"white\">Klicken Sie auf eines der X, Nummer oder 'B'<br>für Sound und 'looted' Optionen für das<br>jeweilige Event.</font></html>";
    private static final String LANG_TIP1_EN = "<html><font color=\"white\">You can click on each X, number or 'B'<br>for sound select and looted options for the<br>specific event.</font></html>";
    private static final String LANG_TIP1_ES = "<html><font color=\"white\">You can click on each X, number or 'B'<br>for sound select and looted options for the<br>specific event.</font></html>";
    private static final String LANG_TIP1_FR = "<html><font color=\"white\">You can click on each X, number or 'B'<br>for sound select and looted options for the<br>specific event.</font></html>";
    
    public static final int EVENT_COUNT = 23;
    
    private static final String VERSION = "1.0";
    
    private JButton workingButton;
    private JCheckBox refreshSelector;
    
    private Image guiIcon;
    private ApiManager apiManager;
    
    private ArrayList eventLabels;
    private ArrayList eventLabelsTimer;
    
    private HashMap homeWorlds;
    
    private String language;
    private String worldID;
    
    public boolean preventSystemSleep;
    
    private PushGui pushGui;
    
    private Date lastPush;
    
    /**
     * Creates new form GW2EventerGui
     */
    public GW2EventerGui() {
       
        this.guiIcon = new ImageIcon(
                ClassLoader.getSystemResource("media/icon.png")).getImage();
        
        initComponents();
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(screenSize.width/2-this.getSize().width/2,
                (screenSize.height/2-this.getSize().height/2) - 20);
        
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
         
        if ((width == 1280) && (height == 720 || height == 768 || height == 800)) {
            this.setExtendedState(this.MAXIMIZED_BOTH);
            //this.setLocation(0, 0);
        }
        
        JSpinner.NumberEditor jsEditor = (JSpinner.NumberEditor)this.jSpinnerRefreshTime.getEditor();
        DefaultFormatter formatter = (DefaultFormatter) jsEditor.getTextField().getFormatter();
        formatter.setAllowsInvalid(false);

        this.workingButton = this.jButtonRefresh;
        this.refreshSelector = this.jCheckBoxAutoRefresh;
        
        String selectedLang = (String) this.jComboBoxLanguage.getModel().getSelectedItem();
        
        this.addWindowListener(new WindowAdapter() {
            
            @Override
            public void windowClosing(WindowEvent e) {
                
                apiManager.saveSettingstoFile();
                System.exit(0);
            }
        });
        
        switch (selectedLang) {
            case "DE":
                this.jButtonRefresh.setText(LANG_RELOAD_BTN_DE);
                this.jCheckBoxAutoRefresh.setText(LANG_AUTO_REFRESH_DE);
                this.jCheckBoxPlaySounds.setText(LANG_PLAY_SOUNDS_DE);
                this.jCheckBoxSystemSleep.setText(LANG_PREVENT_SLEEP_DE);
                this.jLabelServer.setText(LANG_NOT_RUNNING_DE);
                this.jLabelWorking.setText(LANG_WORKING_DE);
                this.jLabelTips.setText(LANG_TIP1_DE);
                break;
            case "EN":
                this.jButtonRefresh.setText(LANG_RELOAD_BTN_EN);
                this.jCheckBoxAutoRefresh.setText(LANG_AUTO_REFRESH_EN);
                this.jCheckBoxPlaySounds.setText(LANG_PLAY_SOUNDS_EN);
                this.jCheckBoxSystemSleep.setText(LANG_PREVENT_SLEEP_EN);
                this.jLabelServer.setText(LANG_NOT_RUNNING_EN);
                this.jLabelWorking.setText(LANG_WORKING_EN);
                this.jLabelTips.setText(LANG_TIP1_EN);
                break;
            case "ES":
                this.jButtonRefresh.setText(LANG_RELOAD_BTN_ES);
                this.jCheckBoxAutoRefresh.setText(LANG_AUTO_REFRESH_ES);
                this.jCheckBoxPlaySounds.setText(LANG_PLAY_SOUNDS_ES);
                this.jCheckBoxSystemSleep.setText(LANG_PREVENT_SLEEP_ES);
                this.jLabelServer.setText(LANG_NOT_RUNNING_ES);
                this.jLabelWorking.setText(LANG_WORKING_ES);
                this.jLabelTips.setText(LANG_TIP1_ES);
                break;
            case "FR":
                this.jButtonRefresh.setText(LANG_RELOAD_BTN_FR);
                this.jCheckBoxAutoRefresh.setText(LANG_AUTO_REFRESH_FR);
                this.jCheckBoxPlaySounds.setText(LANG_PLAY_SOUNDS_FR);
                this.jCheckBoxSystemSleep.setText(LANG_PREVENT_SLEEP_FR);
                this.jLabelServer.setText(LANG_NOT_RUNNING_FR);
                this.jLabelWorking.setText(LANG_WORKING_FR);
                this.jLabelTips.setText(LANG_TIP1_FR);
                break;
        }
        
        this.language = "en";
        this.worldID = "2206"; //Millersund [DE]
        
        this.eventLabels = new ArrayList();
        this.eventLabelsTimer = new ArrayList();
        
        this.homeWorlds = new HashMap();
        
        this.preventSystemSleep = true;
        
        for (int i = 1; i <= EVENT_COUNT; i++) {
            
            try {

                Field f = getClass().getDeclaredField("labelEvent" + i);
                JLabel l = (JLabel) f.get(this);
                l.setPreferredSize(new Dimension(70,28));
                l.setToolTipText("");
                
                this.eventLabels.add(l);
                
                f = getClass().getDeclaredField("labelTimer" + i);
                l = (JLabel) f.get(this);
                l.setEnabled(true);
                l.setVisible(false);
                l.setForeground(Color.green);
                
                this.eventLabelsTimer.add(l);
                
            } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
                Logger.getLogger(GW2EventerGui.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        this.lastPush = new Date();
        
        if (this.apiManager == null) {
            
            this.apiManager = new ApiManager(this, this.jSpinnerRefreshTime,
                this.jCheckBoxAutoRefresh.isSelected(), this.eventLabels,
                this.language, this.worldID, this.homeWorlds,
                this.jComboBoxHomeWorld, this.jLabelServer, this.jLabelWorking,
                this.jCheckBoxPlaySounds.isSelected(), this.workingButton,
                this.refreshSelector, this.eventLabelsTimer, this.jComboBoxLanguage);
        }
        
        this.pushGui = new PushGui(this, true, "", "");
        this.pushGui.setIconImage(guiIcon);
        
        this.preventSleepMode();
        this.runUpdateService();
        this.runPushService();
        this.runTips();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jComboBoxHomeWorld = new javax.swing.JComboBox();
        jComboBoxLanguage = new javax.swing.JComboBox();
        jButtonRefresh = new javax.swing.JButton();
        jSpinnerRefreshTime = new javax.swing.JSpinner();
        jLabelSeconds = new javax.swing.JLabel();
        jCheckBoxAutoRefresh = new javax.swing.JCheckBox();
        jCheckBoxPlaySounds = new javax.swing.JCheckBox();
        jCheckBoxSystemSleep = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabelWorking = new javax.swing.JLabel();
        jLabelServer = new javax.swing.JLabel();
        labelEvent1 = new javax.swing.JLabel();
        labelEvent2 = new javax.swing.JLabel();
        labelEvent3 = new javax.swing.JLabel();
        labelEvent4 = new javax.swing.JLabel();
        labelEvent5 = new javax.swing.JLabel();
        labelEvent6 = new javax.swing.JLabel();
        labelEvent7 = new javax.swing.JLabel();
        labelEvent8 = new javax.swing.JLabel();
        labelEvent9 = new javax.swing.JLabel();
        labelEvent10 = new javax.swing.JLabel();
        labelEvent11 = new javax.swing.JLabel();
        labelEvent12 = new javax.swing.JLabel();
        labelEvent13 = new javax.swing.JLabel();
        labelEvent14 = new javax.swing.JLabel();
        labelEvent15 = new javax.swing.JLabel();
        labelEvent16 = new javax.swing.JLabel();
        labelEvent17 = new javax.swing.JLabel();
        labelEvent18 = new javax.swing.JLabel();
        labelEvent19 = new javax.swing.JLabel();
        labelEvent20 = new javax.swing.JLabel();
        labelEvent21 = new javax.swing.JLabel();
        labelEvent22 = new javax.swing.JLabel();
        labelEvent23 = new javax.swing.JLabel();
        labelTimer23 = new javax.swing.JLabel();
        labelTimer22 = new javax.swing.JLabel();
        labelTimer21 = new javax.swing.JLabel();
        labelTimer20 = new javax.swing.JLabel();
        labelTimer19 = new javax.swing.JLabel();
        labelTimer18 = new javax.swing.JLabel();
        labelTimer17 = new javax.swing.JLabel();
        labelTimer16 = new javax.swing.JLabel();
        labelTimer15 = new javax.swing.JLabel();
        labelTimer14 = new javax.swing.JLabel();
        labelTimer13 = new javax.swing.JLabel();
        labelTimer12 = new javax.swing.JLabel();
        labelTimer11 = new javax.swing.JLabel();
        labelTimer10 = new javax.swing.JLabel();
        labelTimer9 = new javax.swing.JLabel();
        labelTimer8 = new javax.swing.JLabel();
        labelTimer7 = new javax.swing.JLabel();
        labelTimer6 = new javax.swing.JLabel();
        labelTimer5 = new javax.swing.JLabel();
        labelTimer4 = new javax.swing.JLabel();
        labelTimer3 = new javax.swing.JLabel();
        labelTimer2 = new javax.swing.JLabel();
        labelTimer1 = new javax.swing.JLabel();
        jLabelTips = new javax.swing.JLabel();
        backGround = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("GW2 Eventer");
        setBackground(new java.awt.Color(102, 102, 102));
        setIconImage(this.guiIcon);
        setResizable(false);

        jPanel1.setMaximumSize(new java.awt.Dimension(1280, 33));
        jPanel1.setMinimumSize(new java.awt.Dimension(1280, 33));
        jPanel1.setPreferredSize(new java.awt.Dimension(1280, 33));
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));

        jPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jComboBoxHomeWorld.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-" }));
        jComboBoxHomeWorld.setEnabled(false);
        jComboBoxHomeWorld.setMinimumSize(new java.awt.Dimension(22, 22));
        jComboBoxHomeWorld.setPreferredSize(new java.awt.Dimension(170, 22));
        jComboBoxHomeWorld.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxHomeWorldActionPerformed(evt);
            }
        });
        jPanel3.add(jComboBoxHomeWorld);

        jComboBoxLanguage.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "DE", "EN", "ES", "FR" }));
        jComboBoxLanguage.setSelectedIndex(1);
        jComboBoxLanguage.setEnabled(false);
        jComboBoxLanguage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxLanguageActionPerformed(evt);
            }
        });
        jPanel3.add(jComboBoxLanguage);

        jButtonRefresh.setText("Set/Reload");
        jButtonRefresh.setEnabled(false);
        jButtonRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRefreshActionPerformed(evt);
            }
        });
        jPanel3.add(jButtonRefresh);

        jSpinnerRefreshTime.setModel(new javax.swing.SpinnerNumberModel(20, 20, 300, 20));
        jSpinnerRefreshTime.setPreferredSize(new java.awt.Dimension(50, 22));
        jSpinnerRefreshTime.setValue(20);
        jPanel3.add(jSpinnerRefreshTime);

        jLabelSeconds.setText("sec");
        jPanel3.add(jLabelSeconds);

        jCheckBoxAutoRefresh.setText("auto refresh");
        jCheckBoxAutoRefresh.setEnabled(false);
        jCheckBoxAutoRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxAutoRefreshActionPerformed(evt);
            }
        });
        jPanel3.add(jCheckBoxAutoRefresh);

        jCheckBoxPlaySounds.setSelected(true);
        jCheckBoxPlaySounds.setText("play sounds");
        jCheckBoxPlaySounds.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxPlaySoundsActionPerformed(evt);
            }
        });
        jPanel3.add(jCheckBoxPlaySounds);

        jCheckBoxSystemSleep.setSelected(true);
        jCheckBoxSystemSleep.setText("prevent sleep");
        jCheckBoxSystemSleep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxSystemSleepActionPerformed(evt);
            }
        });
        jPanel3.add(jCheckBoxSystemSleep);

        jPanel1.add(jPanel3);

        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/paypal-button.png"))); // NOI18N
        jLabel3.setToolTipText("Buy me a coffee :-)");
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel3);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("?");
        jLabel1.setToolTipText("Info");
        jLabel1.setPreferredSize(new java.awt.Dimension(20, 21));
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel1);

        jPanel1.add(jPanel2);

        getContentPane().add(jPanel1, java.awt.BorderLayout.NORTH);

        jPanel4.setBackground(new java.awt.Color(51, 51, 51));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelWorking.setBackground(new java.awt.Color(255, 50, 50));
        jLabelWorking.setFont(new java.awt.Font("Tahoma", 3, 48)); // NOI18N
        jLabelWorking.setForeground(new java.awt.Color(199, 199, 199));
        jLabelWorking.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelWorking.setText("working...");
        jLabelWorking.setBorder(new javax.swing.border.MatteBorder(null));
        jLabelWorking.setEnabled(false);
        jLabelWorking.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jPanel4.add(jLabelWorking, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 290, 1280, 150));

        jLabelServer.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabelServer.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelServer.setText("not running");
        jLabelServer.setEnabled(false);
        jPanel4.add(jLabelServer, new org.netbeans.lib.awtextra.AbsoluteConstraints(1090, 690, 170, -1));

        labelEvent1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        labelEvent1.setForeground(new java.awt.Color(255, 51, 0));
        labelEvent1.setText("x");
        labelEvent1.setToolTipText("123");
        labelEvent1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        labelEvent1.setEnabled(false);
        labelEvent1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelEvent1MouseClicked(evt);
            }
        });
        jPanel4.add(labelEvent1, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 160, -1, -1));

        labelEvent2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        labelEvent2.setForeground(new java.awt.Color(255, 51, 0));
        labelEvent2.setText("x");
        labelEvent2.setToolTipText("123");
        labelEvent2.setEnabled(false);
        labelEvent2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelEvent2MouseClicked(evt);
            }
        });
        jPanel4.add(labelEvent2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 350, -1, -1));

        labelEvent3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        labelEvent3.setForeground(new java.awt.Color(255, 51, 0));
        labelEvent3.setText("x");
        labelEvent3.setToolTipText("123");
        labelEvent3.setEnabled(false);
        labelEvent3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelEvent3MouseClicked(evt);
            }
        });
        jPanel4.add(labelEvent3, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 330, -1, -1));

        labelEvent4.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        labelEvent4.setForeground(new java.awt.Color(255, 51, 0));
        labelEvent4.setText("x");
        labelEvent4.setToolTipText("123");
        labelEvent4.setEnabled(false);
        labelEvent4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelEvent4MouseClicked(evt);
            }
        });
        jPanel4.add(labelEvent4, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 580, -1, -1));

        labelEvent5.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        labelEvent5.setForeground(new java.awt.Color(255, 51, 0));
        labelEvent5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelEvent5.setText("x");
        labelEvent5.setToolTipText("123");
        labelEvent5.setEnabled(false);
        labelEvent5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelEvent5MouseClicked(evt);
            }
        });
        jPanel4.add(labelEvent5, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 130, -1, -1));

        labelEvent6.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        labelEvent6.setForeground(new java.awt.Color(255, 51, 0));
        labelEvent6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelEvent6.setText("x");
        labelEvent6.setToolTipText("123");
        labelEvent6.setEnabled(false);
        labelEvent6.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        labelEvent6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelEvent6MouseClicked(evt);
            }
        });
        jPanel4.add(labelEvent6, new org.netbeans.lib.awtextra.AbsoluteConstraints(1200, 410, -1, -1));

        labelEvent7.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        labelEvent7.setForeground(new java.awt.Color(255, 51, 0));
        labelEvent7.setText("x");
        labelEvent7.setToolTipText("123");
        labelEvent7.setEnabled(false);
        labelEvent7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelEvent7MouseClicked(evt);
            }
        });
        jPanel4.add(labelEvent7, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 40, -1, -1));

        labelEvent8.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        labelEvent8.setForeground(new java.awt.Color(255, 51, 0));
        labelEvent8.setText("x");
        labelEvent8.setToolTipText("123");
        labelEvent8.setEnabled(false);
        labelEvent8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelEvent8MouseClicked(evt);
            }
        });
        jPanel4.add(labelEvent8, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 410, -1, -1));

        labelEvent9.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        labelEvent9.setForeground(new java.awt.Color(255, 51, 0));
        labelEvent9.setText("x");
        labelEvent9.setToolTipText("123");
        labelEvent9.setEnabled(false);
        labelEvent9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelEvent9MouseClicked(evt);
            }
        });
        jPanel4.add(labelEvent9, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 340, -1, -1));

        labelEvent10.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        labelEvent10.setForeground(new java.awt.Color(255, 51, 0));
        labelEvent10.setText("x");
        labelEvent10.setToolTipText("123");
        labelEvent10.setEnabled(false);
        labelEvent10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelEvent10MouseClicked(evt);
            }
        });
        jPanel4.add(labelEvent10, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 610, -1, -1));

        labelEvent11.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        labelEvent11.setForeground(new java.awt.Color(255, 51, 0));
        labelEvent11.setText("x");
        labelEvent11.setToolTipText("123");
        labelEvent11.setEnabled(false);
        labelEvent11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelEvent11MouseClicked(evt);
            }
        });
        jPanel4.add(labelEvent11, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 40, -1, -1));

        labelEvent12.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        labelEvent12.setForeground(new java.awt.Color(255, 51, 0));
        labelEvent12.setText("x");
        labelEvent12.setToolTipText("123");
        labelEvent12.setEnabled(false);
        labelEvent12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelEvent12MouseClicked(evt);
            }
        });
        jPanel4.add(labelEvent12, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 625, -1, -1));

        labelEvent13.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        labelEvent13.setForeground(new java.awt.Color(255, 51, 0));
        labelEvent13.setText("x");
        labelEvent13.setToolTipText("123");
        labelEvent13.setEnabled(false);
        labelEvent13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelEvent13MouseClicked(evt);
            }
        });
        jPanel4.add(labelEvent13, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 480, -1, -1));

        labelEvent14.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        labelEvent14.setForeground(new java.awt.Color(255, 51, 0));
        labelEvent14.setText("x");
        labelEvent14.setToolTipText("123");
        labelEvent14.setEnabled(false);
        labelEvent14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelEvent14MouseClicked(evt);
            }
        });
        jPanel4.add(labelEvent14, new org.netbeans.lib.awtextra.AbsoluteConstraints(1210, 160, -1, -1));

        labelEvent15.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        labelEvent15.setForeground(new java.awt.Color(255, 51, 0));
        labelEvent15.setText("x");
        labelEvent15.setToolTipText("123");
        labelEvent15.setEnabled(false);
        labelEvent15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelEvent15MouseClicked(evt);
            }
        });
        jPanel4.add(labelEvent15, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 560, -1, -1));

        labelEvent16.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        labelEvent16.setForeground(new java.awt.Color(255, 51, 0));
        labelEvent16.setText("x");
        labelEvent16.setToolTipText("123");
        labelEvent16.setEnabled(false);
        labelEvent16.setPreferredSize(new java.awt.Dimension(15, 28));
        labelEvent16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelEvent16MouseClicked(evt);
            }
        });
        jPanel4.add(labelEvent16, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 40, -1, -1));

        labelEvent17.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        labelEvent17.setForeground(new java.awt.Color(255, 51, 0));
        labelEvent17.setText("x");
        labelEvent17.setToolTipText("123");
        labelEvent17.setEnabled(false);
        labelEvent17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelEvent17MouseClicked(evt);
            }
        });
        jPanel4.add(labelEvent17, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 690, -1, -1));

        labelEvent18.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        labelEvent18.setForeground(new java.awt.Color(255, 51, 0));
        labelEvent18.setText("x");
        labelEvent18.setToolTipText("123");
        labelEvent18.setEnabled(false);
        labelEvent18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelEvent18MouseClicked(evt);
            }
        });
        jPanel4.add(labelEvent18, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 620, -1, -1));

        labelEvent19.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        labelEvent19.setForeground(new java.awt.Color(255, 51, 0));
        labelEvent19.setText("x");
        labelEvent19.setToolTipText("123");
        labelEvent19.setEnabled(false);
        labelEvent19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelEvent19MouseClicked(evt);
            }
        });
        jPanel4.add(labelEvent19, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 600, -1, -1));

        labelEvent20.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        labelEvent20.setForeground(new java.awt.Color(255, 51, 0));
        labelEvent20.setText("x");
        labelEvent20.setToolTipText("123");
        labelEvent20.setEnabled(false);
        labelEvent20.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelEvent20MouseClicked(evt);
            }
        });
        jPanel4.add(labelEvent20, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 690, -1, -1));

        labelEvent21.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        labelEvent21.setForeground(new java.awt.Color(255, 51, 0));
        labelEvent21.setText("x");
        labelEvent21.setToolTipText("123");
        labelEvent21.setEnabled(false);
        labelEvent21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelEvent21MouseClicked(evt);
            }
        });
        jPanel4.add(labelEvent21, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 580, -1, -1));

        labelEvent22.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        labelEvent22.setForeground(new java.awt.Color(255, 51, 0));
        labelEvent22.setText("x");
        labelEvent22.setToolTipText("123");
        labelEvent22.setEnabled(false);
        labelEvent22.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelEvent22MouseClicked(evt);
            }
        });
        jPanel4.add(labelEvent22, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 660, -1, -1));

        labelEvent23.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        labelEvent23.setForeground(new java.awt.Color(255, 51, 0));
        labelEvent23.setText("x");
        labelEvent23.setToolTipText("123");
        labelEvent23.setEnabled(false);
        labelEvent23.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelEvent23MouseClicked(evt);
            }
        });
        jPanel4.add(labelEvent23, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 400, -1, -1));

        labelTimer23.setText("x min ago");
        labelTimer23.setEnabled(false);
        jPanel4.add(labelTimer23, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 425, -1, -1));

        labelTimer22.setText("x min ago");
        labelTimer22.setEnabled(false);
        jPanel4.add(labelTimer22, new org.netbeans.lib.awtextra.AbsoluteConstraints(238, 651, -1, -1));

        labelTimer21.setText("x min ago");
        labelTimer21.setEnabled(false);
        jPanel4.add(labelTimer21, new org.netbeans.lib.awtextra.AbsoluteConstraints(385, 568, -1, -1));

        labelTimer20.setText("x min ago");
        labelTimer20.setEnabled(false);
        jPanel4.add(labelTimer20, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 678, -1, -1));

        labelTimer19.setText("x min ago");
        labelTimer19.setEnabled(false);
        jPanel4.add(labelTimer19, new org.netbeans.lib.awtextra.AbsoluteConstraints(275, 588, -1, -1));

        labelTimer18.setText("x min ago");
        labelTimer18.setEnabled(false);
        jPanel4.add(labelTimer18, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 647, -1, -1));

        labelTimer17.setText("x min ago");
        labelTimer17.setEnabled(false);
        jPanel4.add(labelTimer17, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 683, -1, -1));

        labelTimer16.setText("x min ago");
        labelTimer16.setEnabled(false);
        jPanel4.add(labelTimer16, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 65, -1, -1));

        labelTimer15.setText("x min ago");
        labelTimer15.setEnabled(false);
        jPanel4.add(labelTimer15, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 585, -1, -1));

        labelTimer14.setText("x min ago");
        labelTimer14.setEnabled(false);
        jPanel4.add(labelTimer14, new org.netbeans.lib.awtextra.AbsoluteConstraints(1210, 185, -1, -1));

        labelTimer13.setText("x min ago");
        labelTimer13.setEnabled(false);
        jPanel4.add(labelTimer13, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 505, -1, -1));

        labelTimer12.setText("x min ago");
        labelTimer12.setEnabled(false);
        jPanel4.add(labelTimer12, new org.netbeans.lib.awtextra.AbsoluteConstraints(252, 620, -1, -1));

        labelTimer11.setText("x min ago");
        labelTimer11.setEnabled(false);
        jPanel4.add(labelTimer11, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 65, -1, -1));

        labelTimer10.setText("x min ago");
        labelTimer10.setEnabled(false);
        jPanel4.add(labelTimer10, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 635, -1, -1));

        labelTimer9.setText("x min ago");
        labelTimer9.setEnabled(false);
        jPanel4.add(labelTimer9, new org.netbeans.lib.awtextra.AbsoluteConstraints(522, 365, -1, -1));

        labelTimer8.setText("x min ago");
        labelTimer8.setEnabled(false);
        jPanel4.add(labelTimer8, new org.netbeans.lib.awtextra.AbsoluteConstraints(702, 435, -1, -1));

        labelTimer7.setText("x min ago");
        labelTimer7.setEnabled(false);
        jPanel4.add(labelTimer7, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 65, -1, -1));

        labelTimer6.setText("x min ago");
        labelTimer6.setEnabled(false);
        jPanel4.add(labelTimer6, new org.netbeans.lib.awtextra.AbsoluteConstraints(1210, 435, -1, -1));

        labelTimer5.setText("x min ago");
        labelTimer5.setEnabled(false);
        jPanel4.add(labelTimer5, new org.netbeans.lib.awtextra.AbsoluteConstraints(885, 155, -1, -1));

        labelTimer4.setText("x min ago");
        labelTimer4.setEnabled(false);
        jPanel4.add(labelTimer4, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 605, -1, -1));

        labelTimer3.setText("x min ago");
        labelTimer3.setEnabled(false);
        jPanel4.add(labelTimer3, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 355, -1, -1));

        labelTimer2.setText("x min ago");
        labelTimer2.setEnabled(false);
        jPanel4.add(labelTimer2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 375, -1, -1));

        labelTimer1.setText("x min ago");
        labelTimer1.setEnabled(false);
        jPanel4.add(labelTimer1, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 185, -1, -1));

        jLabelTips.setText("<html><font color=\"white\">You can click on each X or number\n<br>for sound select and looted options.</font></html>");
        jLabelTips.setToolTipText("");
        jPanel4.add(jLabelTips, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, 60));

        backGround.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/v3.jpg"))); // NOI18N
        jPanel4.add(backGround, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        getContentPane().add(jPanel4, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    public void setLastPushDate(Date lastPush) {
        
        this.lastPush = lastPush;
    }
    
    public Date getLastPushDate() {
        
        return this.lastPush;
    }
    
    private void runTips() {
        
        Thread t = new Thread() {
            
          @Override public void run() {
              
              try {
                  
                  Thread.sleep(20000);
                  
                  jLabelTips.setVisible(false);
              } catch (InterruptedException ex) {
                  Logger.getLogger(GW2EventerGui.class.getName()).log(Level.SEVERE, null, ex);
              }
          }
        };
        
        t.start();
    }
    
    private void runPushService() {
        
        Thread t = new Thread() {
            
          @Override public void run() {
              
                RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(10 * 1000).build();
                HttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();

                HttpGet request = new HttpGet("http://mkdr.de/gw2/push");

                HttpResponse response;

                String line = "";
                String out = "";
              
                //HashMap result = new HashMap();
                String date = "";
                String enabled = "false";
                String title = "";
                String message = "";
                
                while (!this.isInterrupted()) {
                    
                    try {
                        
                        try {
                            Thread.sleep(60000 * 33);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(GW2EventerGui.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        response = client.execute(request);
                        
                        if (response.getStatusLine().toString().contains("200")) {
                            
                            BufferedReader rd = new BufferedReader(new InputStreamReader(
                                    response.getEntity().getContent(), Charset.forName("UTF-8")));
                            
                            line = "";
                            out = "";
                            
                            while ((line = rd.readLine()) != null) {
                                
                                out = out + line;
                            }

                            JSONParser parser = new JSONParser();
                            
                            Object obj;
                            
                            try {

                                obj = parser.parse(out);
                                
                                JSONArray array = (JSONArray) obj;
                                
                                for (int i = 0; i < array.size(); i++) {
                                    
                                    JSONObject obj2 = (JSONObject) array.get(i);
                                    //result.put(obj2.get("version"), obj2.get("changelog"));
                                    date = (String) obj2.get("date");
                                    enabled = (String) obj2.get("enabled");
                                    title = (String) obj2.get("title");
                                    message = (String) obj2.get("message");
                                }
                                
                                if (!date.equals("") && enabled.equals("true")) {

                                    try {

                                        Date dateData = new Date(Long.parseLong(date));
                                        //long stampNow = dateNow.getTime();

                                        if (!dateData.equals(getLastPushDate())) {

                                            setLastPushDate(dateData);
                                            showPushGui(title, message);
                                        }
                                    } catch (java.lang.NumberFormatException ex) {
                                        //
                                    }
                                }
                            } catch (ParseException ex) {
                                
                                Logger.getLogger(ApiManager.class.getName()).log(
                                        Level.SEVERE, null, ex);
                            }
                            
                            request.releaseConnection();
                            //this.interrupt();
                        } else {
                            try {
                                request.releaseConnection();
                                Thread.sleep(20000);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(EventAllReader.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    } catch (IOException | IllegalStateException ex) {
                        try {
                            Logger.getLogger(EventReader.class.getName()).log(
                                    Level.SEVERE, null, ex);
                            
                            request.releaseConnection();
                            Thread.sleep(20000);
                        } catch (InterruptedException ex1) {
                            Logger.getLogger(EventAllReader.class.getName()).log(Level.SEVERE, null, ex1);
                            
                            this.interrupt();
                        }
                    }
                }
          }
        };
        
        t.start();
    }
    
    private void runUpdateService() {
        
        Thread t = new Thread() {
            
          @Override public void run() {
              
                RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(10 * 1000).build();
                HttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();

                HttpGet request = new HttpGet("http://mkdr.de/gw2/version");

                HttpResponse response;

                String line = "";
                String out = "";
              
                //HashMap result = new HashMap();
                String version = "";
                String changelog = "";
                
                while (!this.isInterrupted()) {
                    
                    try {
                        
                        response = client.execute(request);
                        
                        if (response.getStatusLine().toString().contains("200")) {
                            
                            BufferedReader rd = new BufferedReader(new InputStreamReader(
                                    response.getEntity().getContent(), Charset.forName("UTF-8")));
                            
                            line = "";
                            out = "";
                            
                            while ((line = rd.readLine()) != null) {
                                
                                out = out + line;
                            }

                            JSONParser parser = new JSONParser();
                            
                            Object obj;
                            
                            try {

                                obj = parser.parse(out);
                                
                                JSONArray array = (JSONArray) obj;
                                
                                for (int i = 0; i < array.size(); i++) {
                                    
                                    JSONObject obj2 = (JSONObject) array.get(i);
                                    //result.put(obj2.get("version"), obj2.get("changelog"));
                                    version = (String) obj2.get("version");
                                    changelog = (String) obj2.get("changelog");
                                }
                                
                                if (!version.equals("")) {
                                    if (!version.equals(VERSION)) {
                                        showPushGui("New version out", version + " " + changelog);
                                    }
                                }
                            } catch (ParseException ex) {
                                
                                Logger.getLogger(ApiManager.class.getName()).log(
                                        Level.SEVERE, null, ex);
                            }
                            
                            request.releaseConnection();
                            //this.interrupt();
                            
                            try {
                                Thread.sleep(60000 * 60);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(GW2EventerGui.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else {
                            try {
                                request.releaseConnection();
                                Thread.sleep(20000);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(EventAllReader.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    } catch (IOException | IllegalStateException ex) {
                        try {
                            Logger.getLogger(EventReader.class.getName()).log(
                                    Level.SEVERE, null, ex);
                            
                            request.releaseConnection();
                            Thread.sleep(20000);
                        } catch (InterruptedException ex1) {
                            Logger.getLogger(EventAllReader.class.getName()).log(Level.SEVERE, null, ex1);
                            
                            this.interrupt();
                        }
                    }
                }
          }
        };
        
        t.start();
    }
    
    public void setNewApiManager(ApiManager apiManager) {
        
        this.apiManager = apiManager;
    }
    
    private void showSoundSelector(int event) {
        
        this.apiManager.showSoundSelectGui(this, event);
    }    
    
    private void showPushGui(String title, String content) {
        
        this.pushGui.setNewTitle(title);
        this.pushGui.setContent(content);
        this.pushGui.setLocationRelativeTo(this);
        this.pushGui.setResizable(false);
        this.pushGui.pack();
        this.pushGui.setVisible(true);
    }  
    
    private void preventSleepMode() {
        
        Thread t = new Thread() {
            
          @Override public void run() {
              try {
                  Point mouseLoc;
                  Robot rob = new Robot();
                  
                  while (true) {
                      try {
                          Thread.sleep(55000);
                      } catch (InterruptedException ex) {
                          Logger.getLogger(GW2EventerGui.class.getName()).log(Level.SEVERE, null, ex);
                          this.interrupt();
                      }
                      
                      if (preventSystemSleep) {
                          mouseLoc = MouseInfo.getPointerInfo().getLocation();
                          rob.mouseMove(mouseLoc.x, mouseLoc.y);
                      }
                  }
              } catch (AWTException ex) {
                  Logger.getLogger(GW2EventerGui.class.getName()).log(Level.SEVERE, null, ex);
              }
          }
        };
        
        t.start();
    }
    
    private void jButtonRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRefreshActionPerformed

        this.apiManager.resetLooted();
        
        this.apiManager.eventReaderStop();
        
        this.jLabelWorking.setVisible(true);
        this.jCheckBoxAutoRefresh.setEnabled(false);
        this.jButtonRefresh.setEnabled(false);
        this.jComboBoxLanguage.setEnabled(false);
        
        String homeWorldSelected = (String) this.homeWorlds.get((String) this.jComboBoxHomeWorld.getSelectedItem());
        
        this.apiManager.setRefreshTime((Integer)this.jSpinnerRefreshTime.getValue());
        
        this.apiManager.eventReaderStart((Integer)this.jSpinnerRefreshTime.getValue(),
                this.jCheckBoxAutoRefresh.isSelected(), homeWorldSelected);
        
        this.resetLabels();
    }//GEN-LAST:event_jButtonRefreshActionPerformed

    private void jComboBoxLanguageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxLanguageActionPerformed

        this.jLabelWorking.setVisible(true);
        this.jCheckBoxAutoRefresh.setEnabled(false);
        this.jButtonRefresh.setEnabled(false);
        this.jComboBoxLanguage.setEnabled(false);
        
        String selectedLang = (String) this.jComboBoxLanguage.getSelectedItem();
        
        switch (selectedLang) {
            case "DE":
                this.jButtonRefresh.setText(LANG_RELOAD_BTN_DE);
                this.jCheckBoxAutoRefresh.setText(LANG_AUTO_REFRESH_DE);
                this.jCheckBoxPlaySounds.setText(LANG_PLAY_SOUNDS_DE);
                this.jCheckBoxSystemSleep.setText(LANG_PREVENT_SLEEP_DE);
                this.jLabelServer.setText(LANG_NOT_RUNNING_DE);
                this.jLabelWorking.setText(LANG_WORKING_DE);
                this.jLabelTips.setText(LANG_TIP1_DE);
                break;
            case "EN":
                this.jButtonRefresh.setText(LANG_RELOAD_BTN_EN);
                this.jCheckBoxAutoRefresh.setText(LANG_AUTO_REFRESH_EN);
                this.jCheckBoxPlaySounds.setText(LANG_PLAY_SOUNDS_EN);
                this.jCheckBoxSystemSleep.setText(LANG_PREVENT_SLEEP_EN);
                this.jLabelServer.setText(LANG_NOT_RUNNING_EN);
                this.jLabelWorking.setText(LANG_WORKING_EN);
                this.jLabelTips.setText(LANG_TIP1_EN);
                break;
            case "ES":
                this.jButtonRefresh.setText(LANG_RELOAD_BTN_ES);
                this.jCheckBoxAutoRefresh.setText(LANG_AUTO_REFRESH_ES);
                this.jCheckBoxPlaySounds.setText(LANG_PLAY_SOUNDS_ES);
                this.jCheckBoxSystemSleep.setText(LANG_PREVENT_SLEEP_ES);
                this.jLabelServer.setText(LANG_NOT_RUNNING_ES);
                this.jLabelWorking.setText(LANG_WORKING_ES);
                this.jLabelTips.setText(LANG_TIP1_ES);
                break;
            case "FR":
                this.jButtonRefresh.setText(LANG_RELOAD_BTN_FR);
                this.jCheckBoxAutoRefresh.setText(LANG_AUTO_REFRESH_FR);
                this.jCheckBoxPlaySounds.setText(LANG_PLAY_SOUNDS_FR);
                this.jCheckBoxSystemSleep.setText(LANG_PREVENT_SLEEP_FR);
                this.jLabelServer.setText(LANG_NOT_RUNNING_FR);
                this.jLabelWorking.setText(LANG_WORKING_FR);
                this.jLabelTips.setText(LANG_TIP1_FR);
                break;
        }
        
        if (this.apiManager == null) {
            
            this.apiManager = new ApiManager(this, this.jSpinnerRefreshTime,
                this.jCheckBoxAutoRefresh.isSelected(), this.eventLabels,
                this.language, this.worldID, this.homeWorlds,
                this.jComboBoxHomeWorld, this.jLabelServer, this.jLabelWorking,
                this.jCheckBoxPlaySounds.isSelected(), this.workingButton,
                this.refreshSelector, this.eventLabelsTimer, this.jComboBoxLanguage);
        }
        
        this.apiManager.homeWorldsReload((String) this.jComboBoxLanguage.getSelectedItem());
        this.apiManager.allEventsReload((String) this.jComboBoxLanguage.getSelectedItem());
    }//GEN-LAST:event_jComboBoxLanguageActionPerformed

    private void jComboBoxHomeWorldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxHomeWorldActionPerformed

        /*
        this.apiManager.eventReaderStop();
        
        String homeWorldSelected = (String) this.homeWorlds.get((String) this.jComboBoxHomeWorld.getSelectedItem());
        
        this.apiManager.eventReaderStart((Integer)this.jSpinnerRefreshTime.getValue(),
                this.jCheckBoxAutoRefresh.isSelected(), homeWorldSelected);*/
    }//GEN-LAST:event_jComboBoxHomeWorldActionPerformed

    private void jCheckBoxAutoRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxAutoRefreshActionPerformed
        
        this.apiManager.eventReaderStop();
        
        this.jLabelWorking.setVisible(true);
        this.jCheckBoxAutoRefresh.setEnabled(false);
        this.jButtonRefresh.setEnabled(false);
        
        String homeWorldSelected = (String) this.homeWorlds.get((String) this.jComboBoxHomeWorld.getSelectedItem());
        
        this.apiManager.setRefreshTime((Integer)this.jSpinnerRefreshTime.getValue());
        
        this.apiManager.eventReaderStart((Integer)this.jSpinnerRefreshTime.getValue(),
                this.jCheckBoxAutoRefresh.isSelected(), homeWorldSelected);
        
        this.resetLabels();
    }//GEN-LAST:event_jCheckBoxAutoRefreshActionPerformed

    private void jCheckBoxPlaySoundsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxPlaySoundsActionPerformed
        
        this.apiManager.setPlaySounds(this.jCheckBoxPlaySounds.isSelected());
    }//GEN-LAST:event_jCheckBoxPlaySoundsActionPerformed

    private void jCheckBoxSystemSleepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxSystemSleepActionPerformed

        this.preventSystemSleep = this.jCheckBoxSystemSleep.isSelected();
    }//GEN-LAST:event_jCheckBoxSystemSleepActionPerformed

    private void labelEvent1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelEvent1MouseClicked

        this.showSoundSelector(1);
    }//GEN-LAST:event_labelEvent1MouseClicked

    private void labelEvent2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelEvent2MouseClicked

        this.showSoundSelector(2);
    }//GEN-LAST:event_labelEvent2MouseClicked

    private void labelEvent3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelEvent3MouseClicked

        this.showSoundSelector(3);
    }//GEN-LAST:event_labelEvent3MouseClicked

    private void labelEvent4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelEvent4MouseClicked

        this.showSoundSelector(4);
    }//GEN-LAST:event_labelEvent4MouseClicked

    private void labelEvent5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelEvent5MouseClicked

        this.showSoundSelector(5);
    }//GEN-LAST:event_labelEvent5MouseClicked

    private void labelEvent6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelEvent6MouseClicked

        this.showSoundSelector(6);
    }//GEN-LAST:event_labelEvent6MouseClicked

    private void labelEvent7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelEvent7MouseClicked

        this.showSoundSelector(7);
    }//GEN-LAST:event_labelEvent7MouseClicked

    private void labelEvent8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelEvent8MouseClicked

        this.showSoundSelector(8);
    }//GEN-LAST:event_labelEvent8MouseClicked

    private void labelEvent9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelEvent9MouseClicked

        this.showSoundSelector(9);
    }//GEN-LAST:event_labelEvent9MouseClicked

    private void labelEvent10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelEvent10MouseClicked

        this.showSoundSelector(10);
    }//GEN-LAST:event_labelEvent10MouseClicked

    private void labelEvent14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelEvent14MouseClicked

        this.showSoundSelector(14);
    }//GEN-LAST:event_labelEvent14MouseClicked

    private void labelEvent11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelEvent11MouseClicked

        this.showSoundSelector(11);
    }//GEN-LAST:event_labelEvent11MouseClicked

    private void labelEvent12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelEvent12MouseClicked

        this.showSoundSelector(12);
    }//GEN-LAST:event_labelEvent12MouseClicked

    private void labelEvent13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelEvent13MouseClicked

        this.showSoundSelector(13);
    }//GEN-LAST:event_labelEvent13MouseClicked

    private void labelEvent15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelEvent15MouseClicked

        this.showSoundSelector(15);
    }//GEN-LAST:event_labelEvent15MouseClicked

    private void labelEvent16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelEvent16MouseClicked

        this.showSoundSelector(16);
    }//GEN-LAST:event_labelEvent16MouseClicked

    private void labelEvent17MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelEvent17MouseClicked

        this.showSoundSelector(17);
    }//GEN-LAST:event_labelEvent17MouseClicked

    private void labelEvent18MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelEvent18MouseClicked

        this.showSoundSelector(18);
    }//GEN-LAST:event_labelEvent18MouseClicked

    private void labelEvent19MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelEvent19MouseClicked

        this.showSoundSelector(19);
    }//GEN-LAST:event_labelEvent19MouseClicked

    private void labelEvent20MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelEvent20MouseClicked

        this.showSoundSelector(20);
    }//GEN-LAST:event_labelEvent20MouseClicked

    private void labelEvent21MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelEvent21MouseClicked

        this.showSoundSelector(21);
    }//GEN-LAST:event_labelEvent21MouseClicked

    private void labelEvent22MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelEvent22MouseClicked

        this.showSoundSelector(22);
    }//GEN-LAST:event_labelEvent22MouseClicked

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked

        InfoGui dialog = new InfoGui(this, true); 
        
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);
        dialog.setIconImage(guiIcon);
        
        dialog.setVisible(true);
    }//GEN-LAST:event_jLabel1MouseClicked

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked

        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(new URI("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=R9A5ZF7U7G7LC"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_jLabel3MouseClicked

    private void labelEvent23MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelEvent23MouseClicked
        
        this.showSoundSelector(23);
    }//GEN-LAST:event_labelEvent23MouseClicked

    private void resetLabels() {
        
        for (int i = 0; i < this.eventLabels.size(); i++) {
            
            ((JLabel) this.eventLabels.get(i)).setEnabled(false);
            ((JLabel) this.eventLabels.get(i)).setText("x");
            ((JLabel) this.eventLabels.get(i)).setToolTipText("");
            
            ((JLabel) this.eventLabelsTimer.get(i)).setVisible(false);
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            /*
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }*/
            
            javax.swing.UIManager.setLookAndFeel(
                    javax.swing.UIManager.getSystemLookAndFeelClassName());
            
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GW2EventerGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GW2EventerGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GW2EventerGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GW2EventerGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GW2EventerGui().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel backGround;
    private javax.swing.JButton jButtonRefresh;
    private javax.swing.JCheckBox jCheckBoxAutoRefresh;
    private javax.swing.JCheckBox jCheckBoxPlaySounds;
    private javax.swing.JCheckBox jCheckBoxSystemSleep;
    private javax.swing.JComboBox jComboBoxHomeWorld;
    private javax.swing.JComboBox jComboBoxLanguage;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabelSeconds;
    private javax.swing.JLabel jLabelServer;
    private javax.swing.JLabel jLabelTips;
    private javax.swing.JLabel jLabelWorking;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JSpinner jSpinnerRefreshTime;
    private javax.swing.JLabel labelEvent1;
    private javax.swing.JLabel labelEvent10;
    private javax.swing.JLabel labelEvent11;
    private javax.swing.JLabel labelEvent12;
    private javax.swing.JLabel labelEvent13;
    private javax.swing.JLabel labelEvent14;
    private javax.swing.JLabel labelEvent15;
    private javax.swing.JLabel labelEvent16;
    private javax.swing.JLabel labelEvent17;
    private javax.swing.JLabel labelEvent18;
    private javax.swing.JLabel labelEvent19;
    private javax.swing.JLabel labelEvent2;
    private javax.swing.JLabel labelEvent20;
    private javax.swing.JLabel labelEvent21;
    private javax.swing.JLabel labelEvent22;
    private javax.swing.JLabel labelEvent23;
    private javax.swing.JLabel labelEvent3;
    private javax.swing.JLabel labelEvent4;
    private javax.swing.JLabel labelEvent5;
    private javax.swing.JLabel labelEvent6;
    private javax.swing.JLabel labelEvent7;
    private javax.swing.JLabel labelEvent8;
    private javax.swing.JLabel labelEvent9;
    private javax.swing.JLabel labelTimer1;
    private javax.swing.JLabel labelTimer10;
    private javax.swing.JLabel labelTimer11;
    private javax.swing.JLabel labelTimer12;
    private javax.swing.JLabel labelTimer13;
    private javax.swing.JLabel labelTimer14;
    private javax.swing.JLabel labelTimer15;
    private javax.swing.JLabel labelTimer16;
    private javax.swing.JLabel labelTimer17;
    private javax.swing.JLabel labelTimer18;
    private javax.swing.JLabel labelTimer19;
    private javax.swing.JLabel labelTimer2;
    private javax.swing.JLabel labelTimer20;
    private javax.swing.JLabel labelTimer21;
    private javax.swing.JLabel labelTimer22;
    private javax.swing.JLabel labelTimer23;
    private javax.swing.JLabel labelTimer3;
    private javax.swing.JLabel labelTimer4;
    private javax.swing.JLabel labelTimer5;
    private javax.swing.JLabel labelTimer6;
    private javax.swing.JLabel labelTimer7;
    private javax.swing.JLabel labelTimer8;
    private javax.swing.JLabel labelTimer9;
    // End of variables declaration//GEN-END:variables
}
