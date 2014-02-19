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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.Clip;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;

/**
 *
 * @author mkdr <makedir@gmail.com>
 */
public class ApiManager {

    private EventReader eventReader;
    private HashMap events;

    private final HashMap homeWorlds;
    public static HashMap allEvents;
    public static ArrayList eventLabels;
    public static ArrayList activeEvents;

    public String language;
    public String worldID;
    
    private JComboBox jComboBoxHomeWorlds;
    private JComboBox jComboBoxLanguage;
    
    public JLabel labelServer;
    
    public JLabel labelWorking;
    
    private boolean playSounds;
    
    private JButton workingButton;
    private JCheckBox refreshSelector;
    
    private Clip audioClips;
    
    private boolean[][] eventPlaySounds;

    int refreshTime;
    
    public static JSpinner refreshSpinner;
    
    private ArrayList eventLabelsTimer;
    
    private GW2EventerGui gui;
    
    public ApiManager(GW2EventerGui gui, JSpinner refreshSpinner,
            boolean autoStart, ArrayList eventLabels,
            String language, String worldID, HashMap homeWorlds,
            JComboBox jComboBoxHomeWorlds, JLabel labelServer,
            JLabel labelWorking, boolean playSounds, JButton workingButton,
            JCheckBox refreshSelector, ArrayList eventLabelsTimer,
            JComboBox jComboBoxLanguage) {

        this.gui = gui;
        
        this.refreshSpinner = refreshSpinner;
        this.refreshTime = (Integer)this.refreshSpinner.getValue();
        
        this.eventLabelsTimer = eventLabelsTimer;
        
        this.eventLabels = eventLabels;
        
        this.allEvents = new HashMap();
        this.homeWorlds = homeWorlds;
        
        this.events = new HashMap();
        this.activeEvents = new ArrayList();
        
        this.language = language;
        this.worldID = worldID;
        
        this.jComboBoxHomeWorlds = jComboBoxHomeWorlds;
        this.jComboBoxLanguage = jComboBoxLanguage;
        
        this.labelServer = labelServer;
        this.labelWorking = labelWorking;
        
        this.playSounds = playSounds;
        
        this.workingButton = workingButton;
        this.refreshSelector = refreshSelector;
        
        //this.labelServer.setText(worldID);
        //this.jComboBoxHomeWorlds.setSelectedItem((String) homeWorlds.get(worldID));
        
        this.events.put("36330140-7A61-4708-99EB-010B10420E39", new String[]{"1","1/2","behemoth_pre"});
        this.events.put("31CEBA08-E44D-472F-81B0-7143D73797F5", new String[]{"1","B","behemoth"});
        this.events.put("5E4E9CD9-DD7C-49DB-8392-C99E1EF4E7DF", new String[]{"2","1/3","elemental_pre"});
        this.events.put("2C833C11-5CD5-4D96-A4CE-A74C04C9A278", new String[]{"2","2/3","elemental_pre"});
        this.events.put("33F76E9E-0BB6-46D0-A3A9-BE4CDFC4A3A4", new String[]{"2","B","elemental"});
        this.events.put("613A7660-8F3A-4897-8FAC-8747C12E42F8", new String[]{"3","1/6","junglewurm_pre"});
        this.events.put("CF6F0BB2-BD6C-4210-9216-F0A9810AA2BD", new String[]{"3","2/6","junglewurm_pre"});
        this.events.put("456DD563-9FDA-4411-B8C7-4525F0AC4A6F", new String[]{"3","3/6","junglewurm_pre"});
        this.events.put("1DCFE4AA-A2BD-44AC-8655-BBD508C505D1", new String[]{"3","4/6","junglewurm_pre"});
        this.events.put("61BA7299-6213-4569-948B-864100F35E16", new String[]{"3","5/6","junglewurm_pre"});
        this.events.put("C5972F64-B894-45B4-BC31-2DEEA6B7C033", new String[]{"3","B","junglewurm"});
        this.events.put("3ED4FEB4-A976-4597-94E8-8BFD9053522F", new String[]{"4","1/2","golem_pre"});
        this.events.put("9AA133DC-F630-4A0E-BB5D-EE34A2B306C2", new String[]{"4","B","golem"});
        this.events.put("90B241F5-9E59-46E8-B608-2507F8810E00", new String[]{"5","1/3","frozenmaw_pre"});
        this.events.put("374FC8CB-7AB7-4381-AC71-14BFB30D3019", new String[]{"5","2/3","frozenmaw_pre"});
        this.events.put("F7D9D427-5E54-4F12-977A-9809B23FBA99", new String[]{"5","B","frozenmaw"});
        this.events.put("D9F1CF48-B1CB-49F5-BFAF-4CEC5E68C9CF", new String[]{"6","1/3","ogerwars_pre"});
        this.events.put("4B478454-8CD2-4B44-808C-A35918FA86AA", new String[]{"6","2/3","ogerwars_pre"});
        this.events.put("B4E6588F-232C-4F68-9D58-8803D67E564D", new String[]{"6","B","ogerwars"});
        this.events.put("DA465AE1-4D89-4972-AD66-A9BE3C5A1823", new String[]{"7","1/2","modniirulgoth_pre"});
        this.events.put("E6872A86-E434-4FC1-B803-89921FF0F6D6", new String[]{"7","B","modniirulgoth"});
        this.events.put("95CA969B-0CC6-4604-B166-DBCCE125864F", new String[]{"8","B","dredgecommissar"});
        this.events.put("B6B7EE2A-AD6E-451B-9FE5-D5B0AD125BB2", new String[]{"9","1/4","taidha_pre"});
        this.events.put("189E7ABE-1413-4F47-858E-4612D40BF711", new String[]{"9","2/4","taidha_pre"});
        this.events.put("0E0801AF-28CF-4FF7-8064-BB2F4A816D23", new String[]{"9","3/4","taidha_pre"});
        this.events.put("242BD241-E360-48F1-A8D9-57180E146789", new String[]{"9","B","taidha"});
        this.events.put("36E81760-7D92-458E-AA22-7CDE94112B8F", new String[]{"10","1/2","megadestroyer_pre"});
        this.events.put("C876757A-EF3E-4FBE-A484-07FF790D9B05", new String[]{"10","B","megadestroyer"});
        this.events.put("295E8D3B-8823-4960-A627-23E07575ED96", new String[]{"11","B","fireshaman"});
        this.events.put("A0796EC5-191D-4389-9C09-E48829D1FDB2", new String[]{"12","B","eyeofzaithan"});
        this.events.put("E1CC6E63-EFFE-4986-A321-95C89EA58C07", new String[]{"13","B","karkaqueen"});
        this.events.put("F479B4CF-2E11-457A-B279-90822511B53B", new String[]{"13","B","karkaqueen"});
        this.events.put("5282B66A-126F-4DA4-8E9D-0D9802227B6D", new String[]{"13","B","karkaqueen"});
        this.events.put("4CF7AA6E-4D84-48A6-A3D1-A91B94CCAD56", new String[]{"13","B","karkaqueen"});
        this.events.put("8E064416-64B5-4749-B9E2-31971AB41783", new String[]{"14","1/3","shatterer_pre"});
        this.events.put("580A44EE-BAED-429A-B8BE-907A18E36189", new String[]{"14","2/3","shatterer_pre"});
        this.events.put("03BF176A-D59F-49CA-A311-39FC6F533F2F", new String[]{"14","B","shatterer"});
        this.events.put("568A30CF-8512-462F-9D67-647D69BEFAED", new String[]{"15","B","tequatl"});
        this.events.put("429D6F3E-079C-4DE0-8F9D-8F75A222DB36", new String[]{"16","1/6","jormag_pre"});
        this.events.put("C957AD99-25E1-4DB0-9938-F54D9F23587B", new String[]{"16","2/6","jormag_pre"});
        this.events.put("96D736C4-D2C6-4392-982F-AC6B8EF3B1C8", new String[]{"16","3/6","jormag_pre"});
        this.events.put("0CA3A7E3-5F66-4651-B0CB-C45D3F0CAD95", new String[]{"16","4/6","jormag_pre"});
        this.events.put("BFD87D5B-6419-4637-AFC5-35357932AD2C", new String[]{"16","5/6","jormag_pre"});
        this.events.put("0464CB9E-1848-4AAA-BA31-4779A959DD71", new String[]{"16","B","jormag"});
        this.events.put("D7246CA2-DD85-42B3-A8D3-D2A1FE464ECF", new String[]{"17","1/8","arah_pre"});
        this.events.put("80F7CC11-3116-42B5-A7C3-965EE5A69E51", new String[]{"17","2/8","arah_pre"});
        this.events.put("9DA0E1E8-1A44-4A3C-9FCC-257350978CE9", new String[]{"17","3/8","arah_pre"});
        this.events.put("6B5C8659-F3AF-4DFC-A6F5-CD6620E3BE11", new String[]{"17","4/8","arah_pre"});
        this.events.put("7EA1BE90-C3CB-4598-A2DD-D56764785F7D", new String[]{"17","5/8","arah_pre"});
        this.events.put("E87A021D-4E7C-4A50-BEDB-6F5A54C90A9A", new String[]{"17","6/8","arah_pre"});
        this.events.put("B1B94EFD-4F67-4716-97C2-880CD16F1297", new String[]{"17","7/8","arah_pre"});
        this.events.put("02DECBE6-A0BA-47CC-9256-A6D59881D92A", new String[]{"17","B","arah"});
        this.events.put("D0ECDACE-41F8-46BD-BB17-8762EF29868C", new String[]{"18","1/3","balthazar_pre"});
        this.events.put("7B7D6D27-67A0-44EF-85EA-7460FFA621A1", new String[]{"18","2/3","balthazar_pre"});
        this.events.put("2555EFCB-2927-4589-AB61-1957D9CC70C8", new String[]{"18","B","balthazar"});
        this.events.put("F531683F-FC09-467F-9661-6741E8382E24", new String[]{"19","1/4","dwayna_pre"});
        this.events.put("7EF31D63-DB2A-4FEB-A6C6-478F382BFBCB", new String[]{"19","2/4","dwayna_pre"});
        this.events.put("526732A0-E7F2-4E7E-84C9-7CDED1962000", new String[]{"19","3/4","dwayna_pre"});
        this.events.put("6A6FD312-E75C-4ABF-8EA1-7AE31E469ABA", new String[]{"19","B","dwayna"});
        this.events.put("C2AB5C4C-5FAA-449B-985C-93F8E2D579C8", new String[]{"20","1/7","grenth_pre"});
        this.events.put("B41C90F8-AF33-400E-9AD3-3DB0AFCEDC6C", new String[]{"20","2/7","grenth_pre"});
        this.events.put("4B612C93-3700-43B8-B3C1-CBC64FEC0566", new String[]{"20","3/7","grenth_pre"});
        this.events.put("1D1BE3D6-2F0D-4D1C-8233-812AAF261CFF", new String[]{"20","4/7","grenth_pre"});
        this.events.put("C8139970-BE46-419B-B026-485A14002D44", new String[]{"20","5/7","grenth_pre"});
        this.events.put("E16113B1-CE68-45BB-9C24-91523A663BCB", new String[]{"20","6/7","grenth_pre"});
        this.events.put("99254BA6-F5AE-4B07-91F1-61A9E7C51A51", new String[]{"20","B","grenth"});
        this.events.put("F66922B5-B4BD-461F-8EC5-03327BD2B558", new String[]{"21","1/10","lyssa_pre"});
        this.events.put("20422E4E-B7C8-46BB-82CD-C0C320E3BD7E", new String[]{"21","2/10","lyssa_pre"});
        this.events.put("A3BEF1D9-10B0-44C7-8B4B-600BEC0F0316", new String[]{"21","3/10","lyssa_pre"});
        this.events.put("35997B10-179B-4E39-AD7F-54E131ECDD57", new String[]{"21","4/10","lyssa_pre"});
        this.events.put("F5436671-8934-4BD4-AEF7-4F3741A9CDA4", new String[]{"21","5/10","lyssa_pre"});
        this.events.put("ADC3AA4C-0212-4AE6-98FA-4F59F3C9BCFA", new String[]{"21","6/10","lyssa_pre"});
        this.events.put("590364E0-0053-4933-945E-21D396B10B20", new String[]{"21","7/10","lyssa_pre"});
        this.events.put("B6D6D060-4974-4385-AB08-F641B6F32823", new String[]{"21","8/10","lyssa_pre"});
        this.events.put("2F3955DB-5CAD-480E-AACB-4A9D318AA9A8", new String[]{"21","9/10","lyssa_pre"});
        this.events.put("0372874E-59B7-4A8F-B535-2CF57B8E67E4", new String[]{"21","B","lyssa"});
        this.events.put("C15950B3-7EA6-4976-9DD3-97C88354EE0C", new String[]{"22","1/9","melandru_pre"});
        this.events.put("C39CA0D3-E00D-498F-9F9A-CCFB715896F4", new String[]{"22","2/9","melandru_pre"});
        this.events.put("3D333172-24CE-47BA-8F1A-1AD47E7B69E4", new String[]{"22","3/9","melandru_pre"});
        this.events.put("E7563D8D-838D-4AF4-80CD-1D3A25B6F6AB", new String[]{"22","4/9","melandru_pre"});
        this.events.put("F0CE1E71-4B96-48C6-809D-E1941AF40B1D", new String[]{"22","5/9","melandru_pre"});
        this.events.put("351F7480-2B1C-4846-B03B-ED1B8556F3D7", new String[]{"22","6/9","melandru_pre"});
        this.events.put("7E24F244-52AF-49D8-A1D7-8A1EE18265E0", new String[]{"22","7/9","melandru_pre"});
        this.events.put("04902E61-A102-4D32-860D-C14B150BD4F5", new String[]{"22","8/9","melandru"});
        this.events.put("A5B5C2AF-22B1-4619-884D-F231A0EE0877", new String[]{"22","B","melandru"});
        this.events.put("B081F000-5928-4B69-8980-20AD93827B6C", new String[]{"23","1/4","threehworm_pre"});
        this.events.put("743B0A35-118E-43D7-ACCE-10FDF00139BD", new String[]{"23","2/4","threehworm_pre"});
        this.events.put("96E9213C-54FD-4D66-B546-EF02FACEACEB", new String[]{"23","3/4","threehworm_pre"});
        this.events.put("5F91ED03-6A1B-4A54-9BB4-6A94AE97FC4F", new String[]{"23","B","threehworm"});

        this.eventPlaySounds = new boolean[GW2EventerGui.EVENT_COUNT][];
        
        this.eventPlaySounds[0] = new boolean[]{true, true, false};
        this.eventPlaySounds[1] = new boolean[]{true, true, false};
        this.eventPlaySounds[2] = new boolean[]{true, true, false};
        this.eventPlaySounds[3] = new boolean[]{false, true, false};
        this.eventPlaySounds[4] = new boolean[]{false, true, false};
        this.eventPlaySounds[5] = new boolean[]{false, true, false};
        this.eventPlaySounds[6] = new boolean[]{false, true, false};
        this.eventPlaySounds[7] = new boolean[]{false, true, false};
        this.eventPlaySounds[8] = new boolean[]{false, true, false};
        this.eventPlaySounds[9] = new boolean[]{false, true, false};
        this.eventPlaySounds[10] = new boolean[]{false, true, false};
        this.eventPlaySounds[11] = new boolean[]{false, true, false};
        this.eventPlaySounds[12] = new boolean[]{false, true, false};
        this.eventPlaySounds[13] = new boolean[]{false, true, false};
        this.eventPlaySounds[14] = new boolean[]{false, true, false};
        this.eventPlaySounds[15] = new boolean[]{false, true, false};
        this.eventPlaySounds[16] = new boolean[]{false, true, false};
        this.eventPlaySounds[17] = new boolean[]{false, true, false};
        this.eventPlaySounds[18] = new boolean[]{false, true, false};
        this.eventPlaySounds[19] = new boolean[]{false, true, false};
        this.eventPlaySounds[20] = new boolean[]{false, true, false};
        this.eventPlaySounds[21] = new boolean[]{false, true, false};
        this.eventPlaySounds[22] = new boolean[]{true, true, false};
        
        this.loadSettingsFromFile();
        
        this.resetLooted();
        
        //this.cacheAudioFiles();
        
        /*
        EventAllReader allEventReader = new EventAllReader();

        allEventReader.setHashMap(allEvents);
        allEventReader.setLanguage(this.language);
        allEventReader.start();

        HomeWorldAllReader allServerReader = new HomeWorldAllReader(this);

        allServerReader.setHashMap(this.homeWorlds, this.jComboBoxHomeWorlds,
                this.labelWorking, this.workingButton, this.refreshSelector);
        
        allServerReader.setLanguage(this.language);
        allServerReader.start();
        */
        
        this.eventReader = new EventReader(refreshTime, true, this.worldID,
                (String) homeWorlds.get(worldID), this.playSounds,
                this.allEvents, this.workingButton, this.refreshSelector);
        
        this.eventReader.setArrayList(this.activeEvents, this.events,
                this.eventLabels, this.labelServer, this.jComboBoxHomeWorlds,
                this.labelWorking, this.eventPlaySounds, this.eventLabelsTimer,
                this.jComboBoxLanguage);

        if (autoStart) {

            this.eventReader.start();
        }
    }

    public void resetLooted() {
        
        for (int i = 0; i < this.eventPlaySounds.length; i++) {
            
            this.eventPlaySounds[i][2] = false;
            ((JLabel) this.eventLabels.get(i)).setForeground(Color.green);
        }
    }
    
    public void saveSettingstoFile() {
        
        ObjectOutputStream oos = null;
        FileOutputStream fout = null;
        
        String path = System.getProperty("user.dir");
        
        try {
            
            fout = new FileOutputStream(path + "\\gw2eve.dat", false);
            oos = new ObjectOutputStream(fout);

            oos.writeObject(this.jComboBoxHomeWorlds.getSelectedItem());
            oos.writeObject(this.jComboBoxLanguage.getSelectedItem());
            oos.writeObject(this.eventPlaySounds);
            oos.writeObject("" + this.refreshTime);
            oos.writeObject(this.gui.getLastPushDate());
        } catch (Exception ex) {
            
            ex.printStackTrace();
        } finally {
            
            if (oos != null){

                try {
                    oos.close();
                } catch (IOException ex) {
                    Logger.getLogger(ApiManager.class.getName()).log(Level.SEVERE, null, ex);
                }
             } 
        }
    }
    
    private void loadSettingsFromFile() {
        
        ObjectInputStream objectinputstream = null;
        FileInputStream streamIn = null;
        
        String path = System.getProperty("user.dir");
        
        try {
            
                File f = new File(path + "\\gw2eve.dat");
               
                if (f.exists() && !f.isDirectory()) {
                  
                    streamIn = new FileInputStream(path + "\\gw2eve.dat");
                    objectinputstream = new ObjectInputStream(streamIn);

                    Object readCase = null;
                    readCase = (String) objectinputstream.readObject(); //home

                    readCase = null;
                    readCase = (String) objectinputstream.readObject();
                    
                    if (readCase != null) {
                        
                        this.gui.setNewApiManager(this);
                        
                        this.jComboBoxLanguage.setSelectedItem((String) readCase);
                        this.jComboBoxLanguage.setVisible(false);
                        this.jComboBoxLanguage.setVisible(true);
                        
                        this.setLanguage((String) readCase);
                    }
                    
                    readCase = null;
                    readCase = (boolean[][]) objectinputstream.readObject();

                    if (readCase != null) {
                        
                        this.eventPlaySounds = (boolean[][]) readCase;
                    }
                    
                    readCase = null;
                    readCase = (String) objectinputstream.readObject();
                    
                    if (readCase != null) {
                        //System.out.println(readCase);
                        int intWert = Integer.parseInt((String) readCase);
                        this.refreshTime = intWert;
                        this.refreshSpinner.setValue(intWert);
                    }
                    
                    readCase = null;
                    readCase = (Date) objectinputstream.readObject();
                    
                    if (readCase != null) {
                        this.gui.setLastPushDate((Date) readCase);
                    }
                    System.out.println(this.gui.getLastPushDate());
                } else {
                    
                    this.saveSettingstoFile();
                    this.loadSettingsFromFile();
                }
          } catch (Exception ex) {

               ex.printStackTrace();
          } finally {
                  
               if (objectinputstream != null) {
                   
                   try {
                       objectinputstream .close();
                   } catch (IOException ex) {
                       Logger.getLogger(ApiManager.class.getName()).log(Level.SEVERE, null, ex);
                   }
                } 
          }
    }
    
    public void loadHomeWorldFromFile() {
        
        ObjectInputStream objectinputstream = null;
        FileInputStream streamIn = null;
        
        String path = System.getProperty("user.dir");
        
        try {
            
                File f = new File(path + "\\gw2eve.dat");
               
                if (f.exists() && !f.isDirectory()) {
                   
                    streamIn = new FileInputStream(path + "\\gw2eve.dat");
                    objectinputstream = new ObjectInputStream(streamIn);

                    Object readCase = null;
                    readCase = (String) objectinputstream.readObject();

                    if (readCase != null) {
                        //System.out.println(readCase);
                        this.jComboBoxHomeWorlds.setSelectedItem((String) readCase);
                        //this.jComboBoxHomeWorlds.setSelectedIndex(1);
                        this.jComboBoxHomeWorlds.setVisible(false);
                        this.jComboBoxHomeWorlds.setVisible(true);
                    }
                } else {
                    
                    //this.saveSettingstoFile();
                }
          } catch (Exception ex) {

               ex.printStackTrace();
          } finally {
                  
               if (objectinputstream != null) {
                   
                   try {
                       objectinputstream .close();
                   } catch (IOException ex) {
                       Logger.getLogger(ApiManager.class.getName()).log(Level.SEVERE, null, ex);
                   }
                } 
          }
    }
    
    public void showSoundSelectGui(JFrame suppe, int event) {
        
        SoundSelectGui dialog = new SoundSelectGui(suppe, true, this.eventPlaySounds[event-1]); 
        
        dialog.pack();
        //Dimension Size = Toolkit.getDefaultToolkit().getScreenSize();  
        //dialog.setLocation(new Double((Size.getWidth()/2) - (dialog.getWidth()/2)).intValue(), new Double((Size.getHeight()/2) - (dialog.getHeight()/2)).intValue());
        dialog.setLocationRelativeTo(suppe);
        dialog.setResizable(false);
        
        boolean[] result = dialog.showDialog();
        
        if (result != null) {
            
            this.eventPlaySounds[event-1] = result;
            
            if (result[2]) {
                ((JLabel) this.eventLabels.get(event-1)).setForeground(Color.yellow);
            } else {
                ((JLabel) this.eventLabels.get(event-1)).setForeground(Color.green);
            }
        }
        
        dialog.dispose();
    }
    
    public void setPlaySounds(boolean playSounds) {
        
        this.playSounds = playSounds;
        this.eventReader.setPlaySounds(playSounds);
    }
    
    public void updateToolTips() {
              
        ArrayList tmpIds = new ArrayList();
        String[] tooltips = new String[GW2EventerGui.EVENT_COUNT];
        int i = 0;
        
        Iterator it = this.events.entrySet().iterator();
                    
        while (it.hasNext()) {

            Map.Entry pairs = (Map.Entry)it.next();

            String id = (String) pairs.getKey();
            String[] values = (String[]) pairs.getValue();
            
            if (!tmpIds.contains(values[0])) {
                
                tooltips[Integer.parseInt(values[0]) - 1] = (String) this.allEvents.get(id);
                tmpIds.add(values[0]);
                i++;
            }
        }
        
        for (int j = 0; j < this.eventLabels.size(); j++) {
            
            JLabel label = (JLabel) this.eventLabels.get(j);
            label.setToolTipText(tooltips[j]);
        }
    }
    
    public void allEventsReload(String language) {
        
        EventAllReader allEventReader = new EventAllReader(this);

        this.language = language;
        
        allEventReader.setHashMap(allEvents);
        allEventReader.setLanguage(this.language);
        
        allEventReader.start();
    }
    
    public void homeWorldsReload(String language) {
        
        //this.resetLooted();
        
        HomeWorldAllReader allServerReader = new HomeWorldAllReader(this);

        this.language = language;
        
        allServerReader.setHashMap(this.homeWorlds, this.jComboBoxHomeWorlds,
                this.labelWorking, this.workingButton, this.refreshSelector, this.jComboBoxLanguage);
        
        allServerReader.setLanguage(this.language);
        allServerReader.start();
    }
    
    public void setLanguage(String language) {
        
        this.language = language;
    }
    
    public void setHomeWorld(String worldID) {
        
        this.worldID = worldID;
    }
    
    public void setRefreshTime(int newTime) {

        this.refreshTime = newTime;
        this.eventReader.setSleepTime(newTime);
    }

    public void eventReaderStop() {

        this.eventReader.interrupt();
        try {
            this.eventReader.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(ApiManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void eventReaderStart(int refreshTime, boolean autoRefresh, String worldID) {

        this.worldID = worldID;
        this.refreshTime = refreshTime;
        
        //this.labelServer.setText((String) homeWorlds.get(worldID));
        
        this.eventReader = new EventReader(refreshTime, autoRefresh,
                worldID, (String) homeWorlds.get(worldID), this.playSounds,
                this.allEvents, this.workingButton, this.refreshSelector);
        
        this.eventReader.setArrayList(this.activeEvents, this.events,
                this.eventLabels, this.labelServer, this.jComboBoxHomeWorlds,
                this.labelWorking, this.eventPlaySounds, this.eventLabelsTimer,
                this.jComboBoxLanguage);
        
        this.eventReader.start();
    }

    private void cacheAudioFiles() {
        
        Iterator it = events.entrySet().iterator();

        while (it.hasNext()) {

            Map.Entry pairs = (Map.Entry)it.next();

            String[] values = (String[]) pairs.getValue();
            
            it.remove();
        }
    }
}
