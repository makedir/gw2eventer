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

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
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
public class EventReader extends Thread {
    
    private int sleepTime;
    private boolean autoRefresh;
    
    private ArrayList result;
    
    private HashMap events;
    private HashMap allEvents;
    
    private ArrayList eventLabels;
    
    private String worldID;
    private String worldName;
    
    private JLabel labelServer;
    
    private JComboBox jComboBoxHomeWorlds;
    private JComboBox jComboBoxLanguage;
    
    private JLabel labelWorking;
    
    private boolean playSounds;
    
    public HashMap playSoundsList;
    public URL playSoundsCurrent;
    
    private JButton workingButton;
    private JCheckBox refreshSelector;
    
    private boolean[][] eventPlaySounds;
    
    private ArrayList eventLabelsTimer;
    
    private Date[] timerStamps;
    
    private Thread playThread;
    
    private String workingString;
    
    private boolean[] markedBs;
    
    private OverlayGui overlayGui;
    
    public EventReader(int refreshTime, boolean autoRefresh, String worldID,
            String worldName, boolean playSounds, HashMap allEvents,
            JButton workingButton, JCheckBox refreshSelector, OverlayGui overlayGui) {

        //setDaemon(true); //??
        
        this.overlayGui = overlayGui;
        
        this.playThread = null;
        
        this.timerStamps = new Date[GW2EventerGui.EVENT_COUNT];
        
        this.playSoundsList = new HashMap();
        
        this.workingButton = workingButton;
        this.refreshSelector = refreshSelector;
        
        this.autoRefresh = autoRefresh;
        this.sleepTime = refreshTime;
        this.worldID = worldID;
        this.worldName = worldName;
        this.playSounds = playSounds;
        this.allEvents = allEvents;
        
        this.markedBs = new boolean[GW2EventerGui.EVENT_COUNT];
        Arrays.fill(this.markedBs, Boolean.FALSE);
    }

    public void setArrayList(ArrayList arrayList, HashMap events,
            ArrayList eventLabels, JLabel labelServer,
            JComboBox jComboBoxHomeWorlds, JLabel labelWorking,
            boolean[][] eventPlaySounds, ArrayList eventLabelsTimer,
            JComboBox jComboBoxLanguage) {
                
        this.eventLabels = eventLabels;
        
        this.eventLabelsTimer = eventLabelsTimer;
        
        this.labelServer = labelServer;
        
        this.jComboBoxHomeWorlds = jComboBoxHomeWorlds;
        this.jComboBoxLanguage = jComboBoxLanguage;
        
        this.labelWorking = labelWorking;
        
        this.workingString = this.labelWorking.getText();
        
        this.result = arrayList;
        //this.jListActiveList = jListActiveList;
        
        this.events = events;
        this.eventPlaySounds = eventPlaySounds;
    }
    
    public void setPlaySounds(boolean playSounds) {
        
        this.playSounds = playSounds;
    }
    
    public void setSleepTime(int sleepTime) {
        
        this.sleepTime = sleepTime;
    }
    
    @Override
    public void interrupt() {
        
        if (this.playThread != null) {
            this.playThread.interrupt();
        }
        
        super.interrupt();
   }
    
    @Override
    public void run() {

        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(10 * 1000).build();
        HttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
        //HttpClient client = new DefaultHttpClient();
        
        HttpGet request = new HttpGet(
                //"https://api.guildwars2.com/v1/events.json?world_id="
                "http://gw2eventer.sourceforge.net/api/events.php?world_id="
                        + this.worldID);
        
        HttpResponse response;
        
        String line = "";
        String out = "";

        while (!isInterrupted()) {

            try {

                this.labelWorking.setText(this.workingString);
                
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
                    } else {
                        // http error
                        request.releaseConnection();
                        
                        this.refreshSelector.setEnabled(false);
                        this.workingButton.setEnabled(false);
                        this.jComboBoxLanguage.setEnabled(false);
                        this.labelWorking.setText("connection error. retrying in... 10.");
                        this.labelWorking.setVisible(true);
                        
                        Thread.sleep(10000);
                        continue;
                    }
                } catch (IOException | IllegalStateException ex) {

                    Logger.getLogger(EventReader.class.getName()).log(
                            Level.SEVERE, null, ex);

                    request.releaseConnection();
                    
                    this.refreshSelector.setEnabled(false);
                    this.workingButton.setEnabled(false);
                    this.jComboBoxLanguage.setEnabled(false);
                    this.labelWorking.setText("connection error. retrying in... 10.");
                    this.labelWorking.setVisible(true);
                    
                    Thread.sleep(10000);
                    continue;
                    //this.interrupt();
                }
                
                request.releaseConnection();
                
                JSONParser parser = new JSONParser();

                Object obj;

                this.result.clear();
                
                HashMap mehrfachEvents = new HashMap();
                playSoundsList.clear();
                
                this.overlayGui.clearActive();
                String toolTip;
                
                try {
                    
                    obj = parser.parse(out);
                    
                    for (int i = 0; i < this.eventLabels.size(); i++) {
                        
                        JLabel iter = (JLabel) this.eventLabels.get(i);
                        iter.setEnabled(false);
                        iter.setForeground(Color.green);
                    }
                    
                    for (Iterator iterator = ((JSONObject) obj).values().iterator(); iterator.hasNext();) {
                        
                        JSONArray arrayNew = (JSONArray) iterator.next();
                        
                        for (int i = 0; i < arrayNew.size(); i++) {

                            JSONObject obj2 = (JSONObject) arrayNew.get(i);
                            
                            if (obj2.get("event_id") != null) {
                                
                                String event = (String) obj2.get("event_id");

                                if (this.events.containsKey(event)) {

                                    //System.out.println("debug: " + event + "\n");
                                    this.result.add(obj2.get("event_id"));

                                    int indexEvent = Integer.parseInt(((String[]) this.events.get(event))[0]);
                                    String eventPercent = ((String[]) this.events.get(event))[1];
                                    String eventWav = ((String[]) this.events.get(event))[2];
                                    String eventName = ((String[]) this.events.get(event))[3];

                                    JLabel activeLabel = (JLabel) this.eventLabels.get(indexEvent - 1);
                                    JLabel activeLabelTimer = (JLabel) this.eventLabelsTimer.get(indexEvent - 1);

                                    int activeLabelInt = indexEvent - 1;
                                    String tmpEventName = eventPercent.substring(0, 1);

                                    Date dateNow = new Date();
                                    long stampNow = dateNow.getTime();

                                    if (this.timerStamps[activeLabelInt] != null) {

                                        long oldTimestamp = this.timerStamps[activeLabelInt].getTime();
                                        long minsdiff = ((stampNow - oldTimestamp) / 1000 / 60);

                                        if (minsdiff >= 30) {
                                            activeLabelTimer.setEnabled(true);
                                        } else {
                                            activeLabelTimer.setEnabled(false);
                                        }

                                        if (minsdiff >= 60) {
                                            activeLabelTimer.setForeground(Color.red);
                                        } else {
                                            activeLabelTimer.setForeground(Color.green);
                                        }

                                        activeLabelTimer.setText(minsdiff + " mins (B)");
                                    }

                                    if (activeLabel != null) {
                                        if (activeLabel.getToolTipText() != null) {
                                            if (activeLabel.getToolTipText().equals("")) { // null pointer??
                                                activeLabel.setToolTipText((String) this.allEvents.get(obj2.get("event_id")));
                                            }
                                        }
                                    }

                                    if (obj2.get("state").equals("Active")) {

                                        activeLabel.setEnabled(true);

                                        activeLabel.setToolTipText((String) this.allEvents.get(obj2.get("event_id")));

                                        toolTip = activeLabel.getToolTipText();

                                        if (toolTip != null) {
                                            
                                            if (toolTip.length() > 35) {
                                                toolTip = toolTip.substring(0, 35) + "...";
                                            }
                                        } else {
                                            toolTip = "";
                                        }
                                        
                                        if (tmpEventName.equals("B")) {

                                            this.markedBs[activeLabelInt] = true;
                                            activeLabelTimer.setVisible(false);
                                            this.timerStamps[activeLabelInt] = null;

                                            if (this.eventPlaySounds[activeLabelInt][2]) {
                                                if (!this.overlayGui.containsActiveB(eventName)) {
                                                    this.overlayGui.addActiveB(eventName, "yellow", activeLabelInt);
                                                }
                                            } else {
                                                if (!this.overlayGui.containsActiveB(eventName)) {
                                                    this.overlayGui.addActiveB(eventName, "green", activeLabelInt);
                                                }
                                            }
                                        } else {

                                            if (this.eventPlaySounds[activeLabelInt][2]) {
                                                if (!this.overlayGui.containsActivePre(eventName)) {
                                                    this.overlayGui.addActivePreEvent(eventName, "yellow", activeLabelInt);
                                                }
                                            } else {
                                                if (!this.overlayGui.containsActivePre(eventName)) {
                                                    this.overlayGui.addActivePreEvent(eventName, "green", activeLabelInt);
                                                }
                                            }
                                        }

                                        //activeLabel.setSize(100, activeLabel.getSize().height);
                                        //activeLabel.setText(eventPercent);

                                        URL url = this.getClass().getClassLoader().getResource("media/sounds/" + eventWav + ".wav");

                                        if (!playSoundsList.containsKey(url)) {

                                            if (!this.eventPlaySounds[activeLabelInt][2]) {
                                                if (tmpEventName.equals("B")) {
                                                   if (this.eventPlaySounds[activeLabelInt][1]) {

                                                       playSoundsList.put(url, activeLabel);
                                                   }
                                                } else {
                                                    if (this.eventPlaySounds[activeLabelInt][0]) {

                                                       playSoundsList.put(url, activeLabel);
                                                    }
                                                }
                                            } else {
                                                activeLabel.setForeground(Color.YELLOW);
                                            }
                                         }

                                        if (mehrfachEvents.containsKey(activeLabel)) {
                                            ((ArrayList) mehrfachEvents.get(activeLabel)).add(tmpEventName);
                                        } else {
                                            ArrayList tmpListe = new ArrayList();
                                            tmpListe.add(tmpEventName);
                                            mehrfachEvents.put(activeLabel, tmpListe);
                                        }                                    
                                    } else {

                                        if (tmpEventName.equals("B")) {
                                            if (this.markedBs[activeLabelInt]) {

                                                this.timerStamps[activeLabelInt] = dateNow;
                                                this.markedBs[activeLabelInt] = false;

                                                activeLabelTimer.setVisible(true);
                                                activeLabelTimer.setText("0 mins (B)");
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    
                    Iterator it = mehrfachEvents.entrySet().iterator();
                    
                    while (it.hasNext()) {
                        
                        Map.Entry pairs = (Map.Entry)it.next();
                        
                        JLabel label = (JLabel) pairs.getKey();
                        ArrayList liste = (ArrayList) pairs.getValue();
                        String outString = null;
                        
                        Collections.sort(liste,
                        new Comparator<String>()
                        {
                            public int compare(String f1, String f2)
                            {
                                return -f1.toString().compareTo(f2.toString());
                            }        
                        });
                        
                        for (int i = 0; i < liste.size(); i++) {
                            
                            if (outString == null) {
                                outString = (String) liste.get(i);
                            } else {
                                outString += ", " + (String) liste.get(i);
                            }
                        }
                        
                        label.setText(outString);
                        
                        it.remove();
                    }
                    
                    this.labelServer.setOpaque(true);
                    this.labelServer.setOpaque(false);
                    this.labelServer.setEnabled(false);
                    this.labelServer.setText("");
                    this.labelServer.setText(this.worldName);
                    
                    this.labelWorking.setVisible(false);
                    this.refreshSelector.setEnabled(true);
                    this.workingButton.setEnabled(true);
                    this.jComboBoxLanguage.setEnabled(true);
                    
                    this.overlayGui.renderActive();
                    
                    if (playSounds) {
                    
                        this.playThread = new Thread() {
                            
                            @Override public void run() {

                                Iterator it = playSoundsList.entrySet().iterator();
                    
                                while (it.hasNext() && !isInterrupted()) {

                                    AudioInputStream audioIn;
                                    
                                    Map.Entry pairs = (Map.Entry)it.next();

                                    JLabel label = (JLabel) pairs.getValue();
                                    
                                    try {

                                        playSoundsCurrent = (URL) pairs.getKey();
                                        audioIn = AudioSystem.getAudioInputStream(playSoundsCurrent);

                                        Clip clip = null;
                                        String tmp = label.getText();

                                        try {
                                            //label.setText(">" + tmp);
                                            //label.setText("<HTML><U>" + tmp + "<U><HTML>");
                                            label.setForeground(Color.red);
                                            
                                            //Font font = label.getFont();
                                            //Map attributes = font.getAttributes();
                                            //attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                                            //label.setFont(font.deriveFont(attributes));
                                            
                                            clip = AudioSystem.getClip();
                                            clip.open(audioIn);

                                            clip.start();
                                        } catch (LineUnavailableException ex) {
                                            Logger.getLogger(EventReader.class.getName()).log(Level.SEVERE, null, ex);
                                        } finally {
                                            try {
                                                audioIn.close();
                                            } catch (IOException ex) {
                                                Logger.getLogger(EventReader.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                        }

                                        try {
                                            Thread.sleep(2000);
                                            //label.setText(tmp);
                                            label.setForeground(Color.green);
                                        } catch (InterruptedException ex) {
                                            Logger.getLogger(EventReader.class.getName()).log(Level.SEVERE, null, ex);
                                            this.interrupt(); //??
                                        }
                                    } catch (UnsupportedAudioFileException ex) {
                                        Logger.getLogger(EventReader.class.getName()).log(Level.SEVERE, null, ex);
                                    } catch (IOException ex) {
                                        Logger.getLogger(EventReader.class.getName()).log(Level.SEVERE, null, ex);
                                    }

                                    it.remove();
                                }
                            }
                        };

                        this.playThread.start();
                        this.playThread.join();
                    }
                
                } catch (ParseException ex) {
                    
                    Logger.getLogger(ApiManager.class.getName()).log(
                            Level.SEVERE, null, ex);
                    
                    this.refreshSelector.setEnabled(false);
                    this.workingButton.setEnabled(false);
                    this.jComboBoxLanguage.setEnabled(false);
                    this.labelWorking.setText("connection error. retrying in... 10.");
                    this.labelWorking.setVisible(true);
                    
                    Thread.sleep(10000);
                    continue;
                }
                
                if (this.autoRefresh) {
                    
                    Thread.sleep(this.sleepTime * 1000);
                } else {
                    
                    this.interrupt();
                }
            } catch (InterruptedException ex) {

                Logger.getLogger(ApiManager.class.getName()).log(
                            Level.SEVERE, null, ex);
                
                this.interrupt();
            }
        }
    }

}
