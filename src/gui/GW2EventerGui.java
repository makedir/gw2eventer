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
import java.util.Iterator;
import java.util.Map;
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
    
    private static final String LANG_AUTO_REFRESH_DE = "Auto refresh";
    private static final String LANG_AUTO_REFRESH_EN = "Auto refresh";
    private static final String LANG_AUTO_REFRESH_ES = "Auto refresh";
    private static final String LANG_AUTO_REFRESH_FR = "Auto refresh";
    
    private static final String LANG_PLAY_SOUNDS_DE = "Sounds abspielen";
    private static final String LANG_PLAY_SOUNDS_EN = "Play sounds";
    private static final String LANG_PLAY_SOUNDS_ES = "Play sounds";
    private static final String LANG_PLAY_SOUNDS_FR = "Play sounds";
    
    private static final String LANG_PREVENT_SLEEP_DE = "Standby verhindern";
    private static final String LANG_PREVENT_SLEEP_EN = "Prevent standby";
    private static final String LANG_PREVENT_SLEEP_ES = "Prevent standby";
    private static final String LANG_PREVENT_SLEEP_FR = "Prevent standby";
    
    private static final String LANG_RELOAD_BTN_DE = "Setzen/Start";
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
    
    private static final String LANG_DONATE1_DE = "Ich weise darauf hin, dass PayPal folgenden Betrag von jeder Spende abzieht:";
    private static final String LANG_DONATE1_EN = "Please know, that PayPal takes away the following amount of each donation:";
    private static final String LANG_DONATE1_ES = "Please know, that PayPal takes away the following amount of each donation:";
    private static final String LANG_DONATE1_FR = "Please know, that PayPal takes away the following amount of each donation:";
    
    private static final String LANG_DONATE2_DE = "<html>Ich freue mich über jede Spende. Aber bitte spenden Sie keine<br>Beträge unter 1€, da PayPal sonst fast 37% Ihres gespendeten Geldes behält.<br>Oder noch mehr.<br><br>Wenn Sie z.B. 35cent spenden, würde ich nichts von Ihrem Geld erhalten<br>und PayPal 100%.<br><br>Vielen Dank.</html>";
    private static final String LANG_DONATE2_EN = "<html>I appreciate every single donation. But please don't donate under 1€,<br>otherwise PayPal would take away almost 37% of your donated money.<br>Or even more.<br><br>If you donate for example 35cent, I would get nothing of your donation<br>and PayPal would take 100% of it.<br><br>Thank you.</html>";
    private static final String LANG_DONATE2_ES = "<html>I appreciate every single donation. But please don't donate under 1€,<br>otherwise PayPal would take away almost 37% of your donated money.<br>Or even more.<br><br>If you donate for example 35cent, I would get nothing of your donation<br>and PayPal would take 100% of it.<br><br>Thank you.</html>";
    private static final String LANG_DONATE2_FR = "<html>I appreciate every single donation. But please don't donate under 1€,<br>otherwise PayPal would take away almost 37% of your donated money.<br>Or even more.<br><br>If you donate for example 35cent, I would get nothing of your donation<br>and PayPal would take 100% of it.<br><br>Thank you.</html>";
    
    private static final String LANG_DONATE3_DE = "Sie können auch einfach die Geld-Senden Funktion von PayPal nutzen:";
    private static final String LANG_DONATE3_EN = "Another way is to send me a gift just via normal PayPal sending money:";
    private static final String LANG_DONATE3_ES = "Another way is to send me a gift just via normal PayPal sending money:";
    private static final String LANG_DONATE3_FR = "Another way is to send me a gift just via normal PayPal sending money:";
    
    private static final String LANG_COPY_CLIP_DE = "In Zwischenablage kopieren";
    private static final String LANG_COPY_CLIP_EN = "Copy to clipboard";
    private static final String LANG_COPY_CLIP_ES = "Copy to clipboard";
    private static final String LANG_COPY_CLIP_FR = "Copy to clipboard";
    
    private static final String LANG_FEEDBACK_FEEDBACK_DE = "Teilen Sie Ihre Meinung mit";
    private static final String LANG_FEEDBACK_FEEDBACK_EN = "Leave a feedback";
    private static final String LANG_FEEDBACK_FEEDBACK_ES = "Leave a feedback";
    private static final String LANG_FEEDBACK_FEEDBACK_FR = "Leave a feedback";
    
    private static final String LANG_FEEDBACK_TITLE_DE = "Feedback/bug report";
    private static final String LANG_FEEDBACK_TITLE_EN = "Feedback/bug report";
    private static final String LANG_FEEDBACK_TITLE_ES = "Feedback/bug report";
    private static final String LANG_FEEDBACK_TITLE_FR = "Feedback/bug report";
    
    private static final String LANG_FEEDBACK_FROM_DE = "eMail/Von";
    private static final String LANG_FEEDBACK_FROM_EN = "e-Mail/From";
    private static final String LANG_FEEDBACK_FROM_ES = "e-Mail/From";
    private static final String LANG_FEEDBACK_FROM_FR = "e-Mail/From";
    
    private static final String LANG_FEEDBACK_SUBJECT_DE = "Betreff";
    private static final String LANG_FEEDBACK_SUBJECT_EN = "Subject";
    private static final String LANG_FEEDBACK_SUBJECT_ES = "Subject";
    private static final String LANG_FEEDBACK_SUBJECT_FR = "Subject";
    
    private static final String LANG_FEEDBACK_MESSAGE_DE = "Nachricht";
    private static final String LANG_FEEDBACK_MESSAGE_EN = "Message";
    private static final String LANG_FEEDBACK_MESSAGE_ES = "Message";
    private static final String LANG_FEEDBACK_MESSAGE_FR = "Message";
    
    private static final String LANG_SEND_BTN_DE = "Senden";
    private static final String LANG_SEND_BTN_EN = "Send";
    private static final String LANG_SEND_BTN_ES = "Send";
    private static final String LANG_SEND_BTN_FR = "Send";
    
    private static final String LANG_CANCLE_BTN_DE = "Abbrechen";
    private static final String LANG_CANCLE_BTN_EN = "Cancel";
    private static final String LANG_CANCLE_BTN_ES = "Cancel";
    private static final String LANG_CANCLE_BTN_FR = "Cancel";
    
    private static final String LANG_SEND_ERROR_TITLE_DE = "Verbindungs Fehler";
    private static final String LANG_SEND_ERROR_TITLE_EN = "Connection error";
    private static final String LANG_SEND_ERROR_TITLE_ES = "Connection error";
    private static final String LANG_SEND_ERROR_TITLE_FR = "Connection error";
    
    private static final String LANG_SEND_ERROR_MSG_DE = "Nachricht konnte nicht gesendet werden.";
    private static final String LANG_SEND_ERROR_MSG_EN = "There was an error sending the message.";
    private static final String LANG_SEND_ERROR_MSG_ES = "There was an error sending the message.";
    private static final String LANG_SEND_ERROR_MSG_FR = "There was an error sending the message.";
    
    private static final String LANG_INPUT_ERROR_TITLE_DE = "Eingabe Fehler";
    private static final String LANG_INPUT_ERROR_TITLE_EN = "Input error";
    private static final String LANG_INPUT_ERROR_TITLE_ES = "Input error";
    private static final String LANG_INPUT_ERROR_TITLE_FR = "Input error";
    
    private static final String LANG_INPUT_ERROR_FROM_DE = "Von-Feld darf nicht leer bleiben.";
    private static final String LANG_INPUT_ERROR_FROM_EN = "From can't be left empty.";
    private static final String LANG_INPUT_ERROR_FROM_ES = "From can't be left empty.";
    private static final String LANG_INPUT_ERROR_FROM_FR = "From can't be left empty.";
    
    private static final String LANG_INPUT_ERROR_MSG_DE = "Nachricht-Feld darf nicht leer bleiben.";
    private static final String LANG_INPUT_ERROR_MSG_EN = "Message can't be left empty.";
    private static final String LANG_INPUT_ERROR_MSG_ES = "Message can't be left empty.";
    private static final String LANG_INPUT_ERROR_MSG_FR = "Message can't be left empty.";
    
    private static final String LANG_NEWVERSION_DE = "Neue Version raus! Lade sie hier.";
    private static final String LANG_NEWVERSION_EN = "New version is out! Get it here.";
    private static final String LANG_NEWVERSION_ES = "New version is out! Get it here.";
    private static final String LANG_NEWVERSION_FR = "New version is out! Get it here.";
    
    private static final String LANG_OVERLAY_B_ACTIVE_DE = "Aktive Boss Events:";
    private static final String LANG_OVERLAY_B_ACTIVE_EN = "Active boss events:";
    private static final String LANG_OVERLAY_B_ACTIVE_ES = "Active boss events:";
    private static final String LANG_OVERLAY_B_ACTIVE_FR = "Active boss events:";
    
    private static final String LANG_OVERLAY_PRE_ACTIVE_DE = "Aktive Pre Events:";
    private static final String LANG_OVERLAY_PRE_ACTIVE_EN = "Active pre events:";
    private static final String LANG_OVERLAY_PRE_ACTIVE_ES = "Active pre events:";
    private static final String LANG_OVERLAY_PRE_ACTIVE_FR = "Active pre events:";
    
    private static final String LANG_OVERLAY_WVW_COHERENT_DE = "Zeit bis alle Daten koherent: ";
    private static final String LANG_OVERLAY_WVW_COHERENT_EN = "Time until data is coherent: ";
    private static final String LANG_OVERLAY_WVW_COHERENT_ES = "Time until data is coherent: ";
    private static final String LANG_OVERLAY_WVW_COHERENT_FR = "Time until data is coherent: ";
    
    private static final String LANG_GREEN_DE = "Grün";
    private static final String LANG_GREEN_EN = "Green";
    private static final String LANG_GREEN_ES = "Green";
    private static final String LANG_GREEN_FR = "Green";
    
    private static final String LANG_RED_DE = "Rot";
    private static final String LANG_RED_EN = "Red";
    private static final String LANG_RED_ES = "Red";
    private static final String LANG_RED_FR = "Red";
    
    private static final String LANG_BLUE_DE = "Blau";
    private static final String LANG_BLUE_EN = "Blue";
    private static final String LANG_BLUE_ES = "Blue";
    private static final String LANG_BLUE_FR = "Blue";
    
    private static final String LANG_ETERNAL_DE = "Ewige";
    private static final String LANG_ETERNAL_EN = "Eternal";
    private static final String LANG_ETERNAL_ES = "Eternal";
    private static final String LANG_ETERNAL_FR = "Eternal";
    
    public static final int EVENT_COUNT = 23;
    
    private static final String VERSION = "1.77";
    
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
    private DonateGui donateGui;
    private InfoGui infoGui;
    private FeedbackGui feedbackGui;
    
    private Date lastPush;
    
    private boolean updateInformed;
    
    private OverlayGui overlayGui;
    private WvWOverlayGui wvwOverlayGui;
    private SettingsOverlayGui settingsOverlayGui;
    
    private int overlayX;
    private int overlayY;
    
    private int wvwOverlayX;
    private int wvwOverlayY;
    
    private int settingsOverlayX;
    private int settingsOverlayY;
    
    private String matchId;
    private String matchIdColor;
    
    private WvWMatchReader wvwMatchReader;
    private HashMap matchIds;
    
    /**
     * Creates new form GW2EventerGui
     */
    public GW2EventerGui() {
       
        this.guiIcon = new ImageIcon(
                ClassLoader.getSystemResource("media/icon.png")).getImage();
        
        initComponents();
        
        this.matchIds = new HashMap();
        this.matchId = "2-6";
        this.matchIdColor = "green";
        
        this.jLabelNewVersion.setVisible(false);
        this.updateInformed = false;
        
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

        /*
        jsEditor = (JSpinner.NumberEditor)this.jSpinnerOverlayX.getEditor();
        formatter = (DefaultFormatter) jsEditor.getTextField().getFormatter();
        formatter.setAllowsInvalid(false);
        
        jsEditor = (JSpinner.NumberEditor)this.jSpinnerOverlayY.getEditor();
        formatter = (DefaultFormatter) jsEditor.getTextField().getFormatter();
        formatter.setAllowsInvalid(false);
        */
        
        this.workingButton = this.jButtonRefresh;
        this.refreshSelector = this.jCheckBoxAutoRefresh;
        
        this.addWindowListener(new WindowAdapter() {
            
            @Override
            public void windowClosing(WindowEvent e) {
                
                apiManager.saveSettingstoFile();
                System.exit(0);
            }
        });
        
        this.pushGui = new PushGui(this, true, "", "");
        this.pushGui.setIconImage(guiIcon);
        
        this.donateGui = new DonateGui(this, true);
        this.donateGui.setIconImage(guiIcon);
        
        this.infoGui = new InfoGui(this, true);
        this.infoGui.setIconImage(guiIcon);
        
        this.feedbackGui = new FeedbackGui(this, true);
        this.feedbackGui.setIconImage(guiIcon);
        
        this.overlayGui = new OverlayGui(this);
        this.initOverlayGui();
        
        this.settingsOverlayGui = new SettingsOverlayGui(this);
        this.initSettingsOverlayGui();
        
        this.wvwOverlayGui = new WvWOverlayGui(this);
        this.initWvwOverlayGui();
        
        this.language = "en";
        this.worldID = "2206"; //Millersund [DE]
        
        this.setTranslations();
        
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
                
                //int width2 = l.getX();
                //int height2 = l.getY();
                //System.out.println("$coords .= \"{\\\"x\\\": \\\""+ width2 + "\\\", \\\"y\\\": \\\""+ height2 + "\\\"},\\n\";");
                
                this.eventLabels.add(l);
                
                final int ii = i;
                
                l.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mousePressed(java.awt.event.MouseEvent evt) {
                            showSoundSelector(ii);
                        }
                });
                
                f = getClass().getDeclaredField("labelTimer" + i);
                l = (JLabel) f.get(this);
                l.setEnabled(true);
                l.setVisible(false);
                l.setForeground(Color.green);
                
                //int width2 = l.getX();
                //int height2 = l.getY();
                //System.out.println("$coords2 .= \"{\\\"x\\\": \\\""+ width2 + "\\\", \\\"y\\\": \\\""+ height2 + "\\\"},\\n\";");
                
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
                this.refreshSelector, this.eventLabelsTimer,
                this.jComboBoxLanguage, this.overlayGui, this.jCheckBoxWvWOverlay);
        }
        
        //this.wvwMatchReader = new WvWMatchReader(this.matchIds, this.jCheckBoxWvW);
        //this.wvwMatchReader.start();
        
        this.preventSleepMode();
        this.runUpdateService();
        //this.runPushService();
        this.runTips();
        //this.runTest();
    }

    public void setLooted(int index, boolean looted) {
        
        this.apiManager.setLooted(index, looted);
    }
    
    public void reloadMatchIds() {
        
        this.matchIds = new HashMap();
        
        this.wvwMatchReader = new WvWMatchReader(this.matchIds, this.jCheckBoxWvWOverlay);
        this.wvwMatchReader.start();
        
        try {
            this.wvwMatchReader.join();
            this.wvwOverlayGui.deactivateGui();
            this.wvwOverlayGui.startGui();
        } catch (InterruptedException ex) {
            Logger.getLogger(GW2EventerGui.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setOverlayVisible(boolean visible) {
        
        this.jCheckBoxOverlay.setSelected(visible);
        this.overlayGui.setVisible(visible);
        
        this.settingsOverlayGui.setEvents(visible);
    }
    
    public void setSettingsOverlayVisible(boolean visible) {
        
        this.jCheckBoxSettingsOverlay.setSelected(visible);
        this.settingsOverlayGui.setVisible(visible);
    }
    
    public void setWvWOverlayVisible(boolean visible) {
        
        if (visible) {
            this.reloadMatchIds();
            this.jCheckBoxWvWOverlay.setSelected(true);
        } else {
            this.wvwOverlayGui.deactivateGui();
            this.jCheckBoxWvWOverlay.setSelected(false);
        }
        
        this.settingsOverlayGui.setWvW(visible);
    }
    
    public void setWvWOverlayX(int newX) {
        
        //this.wvwOverlayX = newX;
        this.wvwOverlayGui.setLocation(newX, this.wvwOverlayGui.getY());
    }
    
    public void setWvWOverlayY(int newY) {
        
        //this.wvwOverlayY = newY;
        this.wvwOverlayGui.setLocation(this.wvwOverlayGui.getX(), newY);
    }
    
    public int getWvWOverlayX() {
        
        return this.wvwOverlayGui.getX();
    }
    
    public int getWvWOverlayY() {
        
        return this.wvwOverlayGui.getY();
    }
    
    public void setOverlayX(int newX) {
        
        //this.jSpinnerOverlayX.setValue(newX);
        //this.overlayX = newX;
        this.overlayGui.setLocation(newX, this.overlayGui.getY());
    }
    
    public void setOverlayY(int newY) {
        
        //this.jSpinnerOverlayY.setValue(newY);
        //this.overlayY = newY;
        this.overlayGui.setLocation(this.overlayGui.getX(), newY);
    }
    
    public int getOverlayX() {
        
        //return (Integer) this.jSpinnerOverlayX.getValue();
        return this.overlayGui.getX();
    }
    
    public int getOverlayY() {
        
        //return (Integer) this.jSpinnerOverlayY.getValue();
        return this.overlayGui.getY();
    }
    
    public void setSettingsOverlayX(int newX) {
        
        //this.settingsOverlayX = newX;
        this.settingsOverlayGui.setLocation(newX, this.settingsOverlayGui.getY());
    }
    
    public void setSettingsOverlayY(int newY) {
        
        //this.settingsOverlayY = newY;
        this.settingsOverlayGui.setLocation(this.settingsOverlayGui.getX(), newY);
    }
    
    public int getSettingsOverlayX() {
        
        return this.settingsOverlayGui.getX();
    }
    
    public int getSettingsOverlayY() {
        
        return this.settingsOverlayGui.getY();
    }
    
    public void setSoundPlaying(boolean play) {
        
        this.apiManager.setPlaySounds(play);
        this.jCheckBoxPlaySounds.setSelected(play);
    }
    
    public void setEventOverlay(boolean play) {
        
        this.apiManager.setPlaySounds(play);
        this.jCheckBoxPlaySounds.setSelected(play);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

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
        jSeparator1 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        jCheckBoxOverlay = new javax.swing.JCheckBox();
        jCheckBoxWvWOverlay = new javax.swing.JCheckBox();
        jCheckBoxSettingsOverlay = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        backgroundPanel1 = new gui.BackgroundPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabelNewVersion = new javax.swing.JLabel();
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
        setIconImage(this.guiIcon);
        setMinimumSize(new java.awt.Dimension(1100, 600));

        jPanel1.setMaximumSize(new java.awt.Dimension(1280, 33));
        jPanel1.setMinimumSize(new java.awt.Dimension(1280, 33));
        jPanel1.setPreferredSize(new java.awt.Dimension(1280, 33));
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));

        jPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jComboBoxHomeWorld.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Riverside" }));
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

        jSpinnerRefreshTime.setModel(new javax.swing.SpinnerNumberModel(40, 20, 300, 20));
        jSpinnerRefreshTime.setPreferredSize(new java.awt.Dimension(50, 22));
        jSpinnerRefreshTime.setValue(40);
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

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator1.setToolTipText("");
        jSeparator1.setPreferredSize(new java.awt.Dimension(2, 20));
        jPanel3.add(jSeparator1);

        jLabel4.setText("Overlay:");
        jPanel3.add(jLabel4);

        jCheckBoxOverlay.setText("Events");
        jCheckBoxOverlay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxOverlayActionPerformed(evt);
            }
        });
        jPanel3.add(jCheckBoxOverlay);

        jCheckBoxWvWOverlay.setText("WvW");
        jCheckBoxWvWOverlay.setEnabled(false);
        jCheckBoxWvWOverlay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxWvWOverlayActionPerformed(evt);
            }
        });
        jCheckBoxWvWOverlay.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jCheckBoxWvWOverlayPropertyChange(evt);
            }
        });
        jPanel3.add(jCheckBoxWvWOverlay);

        jCheckBoxSettingsOverlay.setText("Settings");
        jCheckBoxSettingsOverlay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxSettingsOverlayActionPerformed(evt);
            }
        });
        jPanel3.add(jCheckBoxSettingsOverlay);

        jPanel1.add(jPanel3);

        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/paypal-button.png"))); // NOI18N
        jLabel3.setToolTipText("Buy me a coffee :-)");
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel3MousePressed(evt);
            }
        });
        jPanel2.add(jLabel3);

        jLabel2.setText("Feedback");
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel2MousePressed(evt);
            }
        });
        jPanel2.add(jLabel2);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("?");
        jLabel1.setToolTipText("Info");
        jLabel1.setPreferredSize(new java.awt.Dimension(20, 21));
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel1MousePressed(evt);
            }
        });
        jPanel2.add(jLabel1);

        jPanel1.add(jPanel2);

        getContentPane().add(jPanel1, java.awt.BorderLayout.NORTH);

        backgroundPanel1.setLayout(new java.awt.GridBagLayout());

        jPanel5.setLayout(new java.awt.BorderLayout());

        jPanel4.setBackground(new java.awt.Color(51, 51, 51));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelNewVersion.setBackground(new java.awt.Color(51, 51, 51));
        jLabelNewVersion.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabelNewVersion.setForeground(new java.awt.Color(255, 255, 255));
        jLabelNewVersion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelNewVersion.setText("New version is out! Get it here.");
        jLabelNewVersion.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabelNewVersion.setOpaque(true);
        jLabelNewVersion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabelNewVersionMousePressed(evt);
            }
        });
        jPanel4.add(jLabelNewVersion, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 0, 320, -1));

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
        jPanel4.add(labelEvent1, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 160, -1, -1));

        labelEvent2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        labelEvent2.setForeground(new java.awt.Color(255, 51, 0));
        labelEvent2.setText("x");
        labelEvent2.setToolTipText("123");
        labelEvent2.setEnabled(false);
        jPanel4.add(labelEvent2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 350, -1, -1));

        labelEvent3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        labelEvent3.setForeground(new java.awt.Color(255, 51, 0));
        labelEvent3.setText("x");
        labelEvent3.setToolTipText("123");
        labelEvent3.setEnabled(false);
        jPanel4.add(labelEvent3, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 330, -1, -1));

        labelEvent4.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        labelEvent4.setForeground(new java.awt.Color(255, 51, 0));
        labelEvent4.setText("x");
        labelEvent4.setToolTipText("123");
        labelEvent4.setEnabled(false);
        jPanel4.add(labelEvent4, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 580, -1, -1));

        labelEvent5.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        labelEvent5.setForeground(new java.awt.Color(255, 51, 0));
        labelEvent5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelEvent5.setText("x");
        labelEvent5.setToolTipText("123");
        labelEvent5.setEnabled(false);
        jPanel4.add(labelEvent5, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 130, -1, -1));

        labelEvent6.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        labelEvent6.setForeground(new java.awt.Color(255, 51, 0));
        labelEvent6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelEvent6.setText("x");
        labelEvent6.setToolTipText("123");
        labelEvent6.setEnabled(false);
        labelEvent6.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jPanel4.add(labelEvent6, new org.netbeans.lib.awtextra.AbsoluteConstraints(1200, 410, -1, -1));

        labelEvent7.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        labelEvent7.setForeground(new java.awt.Color(255, 51, 0));
        labelEvent7.setText("x");
        labelEvent7.setToolTipText("123");
        labelEvent7.setEnabled(false);
        jPanel4.add(labelEvent7, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 40, -1, -1));

        labelEvent8.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        labelEvent8.setForeground(new java.awt.Color(255, 51, 0));
        labelEvent8.setText("x");
        labelEvent8.setToolTipText("123");
        labelEvent8.setEnabled(false);
        jPanel4.add(labelEvent8, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 410, -1, -1));

        labelEvent9.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        labelEvent9.setForeground(new java.awt.Color(255, 51, 0));
        labelEvent9.setText("x");
        labelEvent9.setToolTipText("123");
        labelEvent9.setEnabled(false);
        jPanel4.add(labelEvent9, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 340, -1, -1));

        labelEvent10.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        labelEvent10.setForeground(new java.awt.Color(255, 51, 0));
        labelEvent10.setText("x");
        labelEvent10.setToolTipText("123");
        labelEvent10.setEnabled(false);
        jPanel4.add(labelEvent10, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 610, -1, -1));

        labelEvent11.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        labelEvent11.setForeground(new java.awt.Color(255, 51, 0));
        labelEvent11.setText("x");
        labelEvent11.setToolTipText("123");
        labelEvent11.setEnabled(false);
        jPanel4.add(labelEvent11, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 40, -1, -1));

        labelEvent12.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        labelEvent12.setForeground(new java.awt.Color(255, 51, 0));
        labelEvent12.setText("x");
        labelEvent12.setToolTipText("123");
        labelEvent12.setEnabled(false);
        jPanel4.add(labelEvent12, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 625, -1, -1));

        labelEvent13.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        labelEvent13.setForeground(new java.awt.Color(255, 51, 0));
        labelEvent13.setText("x");
        labelEvent13.setToolTipText("123");
        labelEvent13.setEnabled(false);
        jPanel4.add(labelEvent13, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 480, -1, -1));

        labelEvent14.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        labelEvent14.setForeground(new java.awt.Color(255, 51, 0));
        labelEvent14.setText("x");
        labelEvent14.setToolTipText("123");
        labelEvent14.setEnabled(false);
        jPanel4.add(labelEvent14, new org.netbeans.lib.awtextra.AbsoluteConstraints(1210, 160, -1, -1));

        labelEvent15.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        labelEvent15.setForeground(new java.awt.Color(255, 51, 0));
        labelEvent15.setText("x");
        labelEvent15.setToolTipText("123");
        labelEvent15.setEnabled(false);
        jPanel4.add(labelEvent15, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 560, -1, -1));

        labelEvent16.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        labelEvent16.setForeground(new java.awt.Color(255, 51, 0));
        labelEvent16.setText("x");
        labelEvent16.setToolTipText("123");
        labelEvent16.setEnabled(false);
        labelEvent16.setPreferredSize(new java.awt.Dimension(15, 28));
        jPanel4.add(labelEvent16, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 40, -1, -1));

        labelEvent17.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        labelEvent17.setForeground(new java.awt.Color(255, 51, 0));
        labelEvent17.setText("x");
        labelEvent17.setToolTipText("123");
        labelEvent17.setEnabled(false);
        jPanel4.add(labelEvent17, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 690, -1, -1));

        labelEvent18.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        labelEvent18.setForeground(new java.awt.Color(255, 51, 0));
        labelEvent18.setText("x");
        labelEvent18.setToolTipText("123");
        labelEvent18.setEnabled(false);
        jPanel4.add(labelEvent18, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 620, -1, -1));

        labelEvent19.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        labelEvent19.setForeground(new java.awt.Color(255, 51, 0));
        labelEvent19.setText("x");
        labelEvent19.setToolTipText("123");
        labelEvent19.setEnabled(false);
        jPanel4.add(labelEvent19, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 600, -1, -1));

        labelEvent20.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        labelEvent20.setForeground(new java.awt.Color(255, 51, 0));
        labelEvent20.setText("x");
        labelEvent20.setToolTipText("123");
        labelEvent20.setEnabled(false);
        jPanel4.add(labelEvent20, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 690, -1, -1));

        labelEvent21.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        labelEvent21.setForeground(new java.awt.Color(255, 51, 0));
        labelEvent21.setText("x");
        labelEvent21.setToolTipText("123");
        labelEvent21.setEnabled(false);
        jPanel4.add(labelEvent21, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 580, -1, -1));

        labelEvent22.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        labelEvent22.setForeground(new java.awt.Color(255, 51, 0));
        labelEvent22.setText("x");
        labelEvent22.setToolTipText("123");
        labelEvent22.setEnabled(false);
        jPanel4.add(labelEvent22, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 660, -1, -1));

        labelEvent23.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        labelEvent23.setForeground(new java.awt.Color(255, 51, 0));
        labelEvent23.setText("x");
        labelEvent23.setToolTipText("123");
        labelEvent23.setEnabled(false);
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

        jPanel5.add(jPanel4, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        backgroundPanel1.add(jPanel5, gridBagConstraints);

        getContentPane().add(backgroundPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jCheckBoxSystemSleepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxSystemSleepActionPerformed

        this.preventSystemSleep = this.jCheckBoxSystemSleep.isSelected();
    }//GEN-LAST:event_jCheckBoxSystemSleepActionPerformed

    private void jCheckBoxPlaySoundsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxPlaySoundsActionPerformed

        this.setSoundPlaying(this.jCheckBoxPlaySounds.isSelected());
        this.settingsOverlayGui.setSound(this.jCheckBoxPlaySounds.isSelected());
    }//GEN-LAST:event_jCheckBoxPlaySoundsActionPerformed

    private void jCheckBoxAutoRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxAutoRefreshActionPerformed

        if (this.jCheckBoxAutoRefresh.isSelected()) {
            
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
        } else {
            this.apiManager.eventReaderStop();
        }
    }//GEN-LAST:event_jCheckBoxAutoRefreshActionPerformed

    private void jButtonRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRefreshActionPerformed

        this.apiManager.resetLooted();

        this.apiManager.eventReaderStop();

        this.jCheckBoxAutoRefresh.setSelected(true);
        
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

        this.setTranslations();

        if (this.apiManager == null) {
            
            this.apiManager = new ApiManager(this, this.jSpinnerRefreshTime,
                this.jCheckBoxAutoRefresh.isSelected(), this.eventLabels,
                this.language, this.worldID, this.homeWorlds,
                this.jComboBoxHomeWorld, this.jLabelServer, this.jLabelWorking,
                this.jCheckBoxPlaySounds.isSelected(), this.workingButton,
                this.refreshSelector, this.eventLabelsTimer,
                this.jComboBoxLanguage, this.overlayGui, this.jCheckBoxWvWOverlay);
        }

        this.apiManager.homeWorldsReload((String) this.jComboBoxLanguage.getSelectedItem());
        this.apiManager.allEventsReload((String) this.jComboBoxLanguage.getSelectedItem());
    }//GEN-LAST:event_jComboBoxLanguageActionPerformed

    private void jComboBoxHomeWorldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxHomeWorldActionPerformed

        /*
        if (this.jComboBoxHomeWorld.getSelectedItem() != null) {
            if (this.matchIds.size() > 0) {
                this.setMatchId();
            }
        }*/
    }//GEN-LAST:event_jComboBoxHomeWorldActionPerformed

    private void jLabelNewVersionMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelNewVersionMousePressed

        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(new URI("https://sourceforge.net/projects/gw2eventer/files/latest/download"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_jLabelNewVersionMousePressed

    private void jLabel3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MousePressed

        this.showDonateGui();
    }//GEN-LAST:event_jLabel3MousePressed

    private void jLabel1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MousePressed

        this.infoGui.setLocationRelativeTo(this);
        this.infoGui.setResizable(false);
        this.infoGui.setSize(305, 330);
        //this.infoGui.pack();
        this.infoGui.setVisible(true);
    }//GEN-LAST:event_jLabel1MousePressed

    private void jLabel2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MousePressed

        this.showFeedbackGui();
    }//GEN-LAST:event_jLabel2MousePressed

    private void jCheckBoxOverlayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxOverlayActionPerformed

        this.setOverlayVisible(this.jCheckBoxOverlay.isSelected());
        this.settingsOverlayGui.setEvents(this.jCheckBoxOverlay.isSelected());
    }//GEN-LAST:event_jCheckBoxOverlayActionPerformed

    private void jCheckBoxWvWOverlayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxWvWOverlayActionPerformed

        this.setWvWOverlayVisible(this.jCheckBoxWvWOverlay.isSelected());
        this.settingsOverlayGui.setWvW(this.jCheckBoxWvWOverlay.isSelected());
    }//GEN-LAST:event_jCheckBoxWvWOverlayActionPerformed

    private void jCheckBoxSettingsOverlayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxSettingsOverlayActionPerformed

        this.settingsOverlayGui.setVisible(this.jCheckBoxSettingsOverlay.isSelected());
    }//GEN-LAST:event_jCheckBoxSettingsOverlayActionPerformed

    private void jCheckBoxWvWOverlayPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jCheckBoxWvWOverlayPropertyChange

        if (this.jCheckBoxWvWOverlay.isEnabled()) {
            this.settingsOverlayGui.setWvWEnabled(true);
        }
    }//GEN-LAST:event_jCheckBoxWvWOverlayPropertyChange
    
    private void setTranslations() {
        
        String selectedLang = (String) this.jComboBoxLanguage.getModel().getSelectedItem();
        
        try {

            this.jButtonRefresh.setText((String) getClass().getDeclaredField("LANG_RELOAD_BTN_" + selectedLang).get(null));
            this.jCheckBoxAutoRefresh.setText((String) getClass().getDeclaredField("LANG_AUTO_REFRESH_" + selectedLang).get(null));
            this.jCheckBoxPlaySounds.setText((String) getClass().getDeclaredField("LANG_PLAY_SOUNDS_" + selectedLang).get(null));
            this.jCheckBoxSystemSleep.setText((String) getClass().getDeclaredField("LANG_PREVENT_SLEEP_" + selectedLang).get(null));
            this.jLabelServer.setText((String) getClass().getDeclaredField("LANG_NOT_RUNNING_" + selectedLang).get(null));
            this.jLabelWorking.setText((String) getClass().getDeclaredField("LANG_WORKING_" + selectedLang).get(null));
            this.jLabelTips.setText((String) getClass().getDeclaredField("LANG_TIP1_" + selectedLang).get(null));
            this.jLabelNewVersion.setText((String) getClass().getDeclaredField("LANG_NEWVERSION_" + selectedLang).get(null));
            
            this.donateGui.setTranslations((String) getClass().getDeclaredField("LANG_DONATE1_" + selectedLang).get(null),
                    (String) getClass().getDeclaredField("LANG_DONATE2_" + selectedLang).get(null),
                    (String) getClass().getDeclaredField("LANG_DONATE3_" + selectedLang).get(null),
                    (String) getClass().getDeclaredField("LANG_COPY_CLIP_" + selectedLang).get(null),
                    (String) getClass().getDeclaredField("LANG_CANCLE_BTN_" + selectedLang).get(null));
            
            this.feedbackGui.setTranslations((String) getClass().getDeclaredField("LANG_FEEDBACK_TITLE_" + selectedLang).get(null),
                    (String) getClass().getDeclaredField("LANG_FEEDBACK_FEEDBACK_" + selectedLang).get(null),
                    (String) getClass().getDeclaredField("LANG_FEEDBACK_FROM_" + selectedLang).get(null),
                    (String) getClass().getDeclaredField("LANG_FEEDBACK_SUBJECT_" + selectedLang).get(null),
                    (String) getClass().getDeclaredField("LANG_FEEDBACK_MESSAGE_" + selectedLang).get(null),
                    (String) getClass().getDeclaredField("LANG_SEND_BTN_" + selectedLang).get(null),
                    (String) getClass().getDeclaredField("LANG_CANCLE_BTN_" + selectedLang).get(null),
                    (String) getClass().getDeclaredField("LANG_SEND_ERROR_TITLE_" + selectedLang).get(null),
                    (String) getClass().getDeclaredField("LANG_SEND_ERROR_MSG_" + selectedLang).get(null),
                    (String) getClass().getDeclaredField("LANG_INPUT_ERROR_TITLE_" + selectedLang).get(null),
                    (String) getClass().getDeclaredField("LANG_INPUT_ERROR_FROM_" + selectedLang).get(null),
                    (String) getClass().getDeclaredField("LANG_INPUT_ERROR_MSG_" + selectedLang).get(null));
            
            this.overlayGui.setTranslations((String) getClass().getDeclaredField("LANG_OVERLAY_B_ACTIVE_" + selectedLang).get(null),
                    (String) getClass().getDeclaredField("LANG_OVERLAY_PRE_ACTIVE_" + selectedLang).get(null));
            
            this.wvwOverlayGui.setTranslations((String) getClass().getDeclaredField("LANG_OVERLAY_WVW_COHERENT_" + selectedLang).get(null),
                    (String) getClass().getDeclaredField("LANG_ETERNAL_" + selectedLang).get(null),
                    (String) getClass().getDeclaredField("LANG_GREEN_" + selectedLang).get(null),
                    (String) getClass().getDeclaredField("LANG_RED_" + selectedLang).get(null),
                    (String) getClass().getDeclaredField("LANG_BLUE_" + selectedLang).get(null));
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
            Logger.getLogger(GW2EventerGui.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setLastPushDate(Date lastPush) {
        
        this.lastPush = lastPush;
    }
    
    public Date getLastPushDate() {
        
        return this.lastPush;
    }
    
    public void setMatchId() {
        
        Iterator it = this.matchIds.entrySet().iterator();
            
        while (it.hasNext()) {

            Map.Entry pairs = (Map.Entry) it.next();

            String matchId = (String) pairs.getKey();
            
            String[] redServers = (String[]) pairs.getValue();
            String redServerID = redServers[0];
            String blueServerId = redServers[1];
            String greenServerId = redServers[2];
            
            String homwWorldId = (String) this.homeWorlds.get((String) this.jComboBoxHomeWorld.getSelectedItem());
            
            if (homwWorldId.equals(redServerID)) {
                this.matchId = matchId;
                this.matchIdColor = "red";
                break;
            } else if (homwWorldId.equals(blueServerId)) {
                this.matchId = matchId;
                this.matchIdColor = "blue";
                break;
            } else if (homwWorldId.equals(greenServerId)) {
                this.matchId = matchId;
                this.matchIdColor = "green";
                break;
            }
        }
    }
    
    public String getMatchId() {
        
        return this.matchId;
    }
    
    public String getMatchIdColor() {
        
        return this.matchIdColor;
    }
    
    private void initSettingsOverlayGui() {
        
        this.settingsOverlayGui.setIconImage(guiIcon);
        this.settingsOverlayGui.setSize(350, 44);
        this.settingsOverlayGui.setVisible(false);
        
        this.settingsOverlayGui.setLocation(20, 220);
        
        this.settingsOverlayGui.setBackground(new Color(0, 0, 0, 0));
        this.settingsOverlayGui.setAlwaysOnTop(true);
    }
    
    private void initOverlayGui() {
        
        this.overlayGui.setIconImage(guiIcon);
        this.overlayGui.setSize(250, 600);
        this.overlayGui.setVisible(false);
        
        //this.overlayGui.setLocationRelativeTo(null);
        //this.overlayGui.setLocation(0, 200);
        
        this.overlayGui.setLocation(20, 120);
        
        this.overlayGui.setBackground(new Color(0, 0, 0, 0));
        this.overlayGui.setAlwaysOnTop(true);
        //this.overlayGui.getRootPane().putClientProperty("apple.awt.draggableWindowBackground", false);

        //this.overlayGui.getContentPane().setLayout(new java.awt.BorderLayout());
        //this.overlayGui.getContentPane().add(new JTextField("text field north"), java.awt.BorderLayout.NORTH);
        //this.overlayGui.getContentPane().add(new JTextField("text field south"), java.awt.BorderLayout.SOUTH);
    }
    
    private void initWvwOverlayGui() {
        
        this.wvwOverlayGui.setIconImage(guiIcon);
        this.wvwOverlayGui.setSize(320, 500);
        this.wvwOverlayGui.setVisible(false);
        
        this.wvwOverlayGui.setLocation(20, 270);
        
        this.wvwOverlayGui.setBackground(new Color(0, 0, 0, 0));
    }
    
    private void runTest() {
        
        Thread t = new Thread() {
            
          @Override public void run() {
              
                RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(10 * 1000).build();
                HttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();

                HttpGet request = new HttpGet("http://gw2eventer.sourceforge.net/event_data");

                HttpResponse response;

                String line = "";
                String out = "";
              
                String version = "";
                
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
                                
                                JSONObject obj2 = (JSONObject) array.get(0);
                                version = (String) obj2.get("version");
                                
                                JSONArray data = (JSONArray) obj2.get("data");
                                obj2 = (JSONObject) data.get(0);
                                
                                //System.out.println(data.hashCode());
                                
                                JSONArray events = (JSONArray) obj2.get("events");
                                JSONArray playdata = (JSONArray) obj2.get("playdata");
                                JSONArray coords = (JSONArray) obj2.get("coords");
                                
                                for (int i = 0; i < events.size(); i++) {
                                    
                                    obj2 = (JSONObject) events.get(i);
                                    
                                    String id = (String) obj2.get("id");
                                    JSONArray data2 = (JSONArray) obj2.get("data");
                                    
                                    //System.out.println(id);
                                    
                                    for (int j = 0; j < data2.size(); j++) {
                                        //System.out.println((String) data2.get(j));
                                    }
                                }
                                
                                this.interrupt();
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

                HttpGet request = new HttpGet("http://gw2eventer.sourceforge.net/gw2/push");

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
                                            showPushGui(title, message, 100);
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

                HttpGet request = new HttpGet("http://gw2eventer.sourceforge.net/version");

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
                                        
                                        jLabelNewVersion.setVisible(true);
                                        
                                        if (updateInformed == false) {
                                            
                                            String mesTmp = "<html>Version: " + version
                                                + "<p>Get it at http://gw2eventer.com</p>"
                                                + "</html>";
                                        
                                        
                                            showPushGui("New version is out", mesTmp, 110);
                                        }
                                        
                                        updateInformed = true;
                                    }
                                }
                                
                                try {
                                    request.releaseConnection();
                                    Thread.sleep(60000 * 45);
                                } catch (InterruptedException ex) {
                                    Logger.getLogger(GW2EventerGui.class.getName()).log(Level.SEVERE, null, ex);
                                    
                                    this.interrupt();
                                }
                            } catch (ParseException ex) {
                                try {
                                    Logger.getLogger(ApiManager.class.getName()).log(
                                            Level.SEVERE, null, ex);
                                    
                                    request.releaseConnection();
                                    Thread.sleep(30000);
                                } catch (InterruptedException ex1) {
                                    Logger.getLogger(GW2EventerGui.class.getName()).log(Level.SEVERE, null, ex1);
                                    
                                    this.interrupt();
                                }
                            }
                        } else {
                            try {
                                request.releaseConnection();
                                Thread.sleep(30000);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(EventAllReader.class.getName()).log(Level.SEVERE, null, ex);
                                
                                this.interrupt();
                            }
                        }
                    } catch (IOException | IllegalStateException ex) {
                        try {
                            Logger.getLogger(EventReader.class.getName()).log(
                                    Level.SEVERE, null, ex);
                            
                            request.releaseConnection();
                            Thread.sleep(30000);
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
    
    public void showDonateGui() {
        
        this.donateGui.setLocationRelativeTo(this);
        this.donateGui.setResizable(false);
        //this.donateGui.pack();
        this.donateGui.setVisible(true);
    }  
    
    private void showPushGui(String title, String content, int height) {
        
        this.pushGui.setNewTitle(title);
        this.pushGui.setContent(content);
        this.pushGui.setLocationRelativeTo(null);
        this.pushGui.setResizable(false);
        this.pushGui.setPreferredSize(new Dimension(300, height));
        //this.pushGui.pack();
        this.pushGui.setVisible(true);
    }  
    
    private void showFeedbackGui() {
        
        this.feedbackGui.setLocationRelativeTo(this);
        //this.feedbackGui.pack();
        
        this.feedbackGui.showGui();
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
            
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
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
    private gui.BackgroundPanel backgroundPanel1;
    private javax.swing.JButton jButtonRefresh;
    private javax.swing.JCheckBox jCheckBoxAutoRefresh;
    private javax.swing.JCheckBox jCheckBoxOverlay;
    private javax.swing.JCheckBox jCheckBoxPlaySounds;
    private javax.swing.JCheckBox jCheckBoxSettingsOverlay;
    private javax.swing.JCheckBox jCheckBoxSystemSleep;
    private javax.swing.JCheckBox jCheckBoxWvWOverlay;
    private javax.swing.JComboBox jComboBoxHomeWorld;
    private javax.swing.JComboBox jComboBoxLanguage;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabelNewVersion;
    private javax.swing.JLabel jLabelSeconds;
    private javax.swing.JLabel jLabelServer;
    private javax.swing.JLabel jLabelTips;
    private javax.swing.JLabel jLabelWorking;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JSeparator jSeparator1;
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
