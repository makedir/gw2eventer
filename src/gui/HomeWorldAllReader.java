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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class HomeWorldAllReader extends Thread {
    
    private HashMap result;
    
    private String language;
    
    private JComboBox homeWorlds;
    private JComboBox jComboBoxLanguage;
    private JCheckBox jCheckBoxWvW;
    
    private JLabel labelWorking;
    
    private JButton workingButton;
    private JCheckBox refreshSelector;
    
    ApiManager apiManager;
    
    private ArrayList homeWorldsList;
    
    private String workingString;
    
    public HomeWorldAllReader(ApiManager apiManager) {
        
        this.apiManager = apiManager;
        this.language = "en";
        
        this.homeWorldsList = new ArrayList();
    }
    
    public void setHashMap(HashMap hashMap, JComboBox homeWorlds,
            JLabel labelWorking, JButton workingButton,
            JCheckBox refreshSelector, JComboBox jComboBoxLanguage,
            JCheckBox jCheckBoxWvW) {
                
        this.labelWorking = labelWorking;
        
        this.workingString = this.labelWorking.getText();
        
        this.workingButton = workingButton;
        this.refreshSelector = refreshSelector;
        
        this.result = hashMap;
        this.homeWorlds = homeWorlds;
        this.jComboBoxLanguage = jComboBoxLanguage;
        this.jCheckBoxWvW = jCheckBoxWvW;
    }
    
    public void setLanguage(String language) {
                
        this.language = language;
        this.workingString = this.labelWorking.getText();
    }
    
    @Override
    public void run() {

        HashMap servers = new HashMap();
        
        servers.put("1008", "Jade Quarry");
        servers.put("2006", "Underworld");
        servers.put("1023", "Devona’s Rest");
        servers.put("2105", "Arborstone [FR]");
        servers.put("1014", "Crystal Desert");
        servers.put("1022", "Kaineng");
        servers.put("2001", "Fissure of Woe");
        servers.put("1001", "Anvil Rock");
        servers.put("2003", "Gandara");
        servers.put("1003", "Yak’s Bend");
        servers.put("2007", "Far Shiverpeaks");
        servers.put("1011", "Stormbluff Isle");
        servers.put("2013", "Aurora Glade");
        servers.put("1016", "Sea of Sorrows");
        servers.put("2005", "Ring of Fire");
        servers.put("2012", "Piken Square");
        servers.put("1012", "Darkhaven");
        servers.put("1005", "Maguuma");
        servers.put("2204", "Abaddon’s Mouth [DE]");
        servers.put("2203", "Elona Reach [DE]");
        servers.put("2010", "Seafarer’s Rest");
        servers.put("2104", "Vizunah Square [FR]");
        servers.put("2207", "Dzagonur [DE]");
        servers.put("2009", "Ruins of Surmia");
        servers.put("1002", "Borlis Pass");
        servers.put("2002", "Desolation");
        servers.put("1010", "Ehmry Bay");
        servers.put("1024", "Eredon Terrace");
        servers.put("1004", "Henge of Denravi");
        servers.put("1007", "Gate of Madness");
        servers.put("2205", "Drakkar Lake [DE]");
        servers.put("2008", "Whiteside Ridge");
        servers.put("1017", "Tarnished Coast");
        servers.put("2101", "Jade Sea [FR]");
        servers.put("1013", "Sanctum of Rall");
        servers.put("2014", "Gunnar’s Hold");
        servers.put("1021", "Dragonbrand");
        servers.put("2301", "Baruch Bay [SP]");
        servers.put("2102", "Fort Ranik [FR]");
        servers.put("2103", "Augury Rock [FR]");
        servers.put("2201", "Kodash [DE]");
        servers.put("2202", "Riverside [DE]");
        servers.put("2206", "Miller’s Sound [DE]");
        servers.put("1018", "Northern Shiverpeaks");
        servers.put("1015", "Isle of Janthir");
        servers.put("2004", "Blacktide");
        servers.put("1006", "Sorrow’s Furnace");
        servers.put("2011", "Vabbi");
        servers.put("1009", "Fort Aspenwood");
        servers.put("1020", "Ferguson’s Crossing");
        servers.put("1019", "Blackgate");
        
        this.homeWorlds.removeAllItems();
        this.homeWorldsList.clear();
        this.result.clear();
        
        Iterator it = servers.entrySet().iterator();
        
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();
            
            String id = pairs.getKey() + "";
            String name = pairs.getValue() + "";
            
            this.result.put(id, name);
            this.result.put(name, id);

            this.homeWorldsList.add(name);
            
            it.remove();
        }


        Collections.sort(this.homeWorldsList);

        for (int i = 0; i < this.homeWorldsList.size(); i++) {
            this.homeWorlds.addItem(this.homeWorldsList.get(i));
        }
        
        this.labelWorking.setVisible(false);
        this.refreshSelector.setEnabled(true);
        this.workingButton.setEnabled(true);
        this.homeWorlds.setEnabled(true);
        this.jComboBoxLanguage.setEnabled(true);
        this.jCheckBoxWvW.setEnabled(true);

        this.apiManager.loadHomeWorldFromFile();

        this.interrupt();
        
        /*
        
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(10 * 1000).build();
        HttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
        //HttpClient client = new DefaultHttpClient();

        HttpGet request = new HttpGet(
                "https://api.guildwars2.com/v1/world_names.json?lang="
                        + this.language);
        
        HttpResponse response;

        String line = "";
        String out = "";

        while (!this.isInterrupted()) {
            
            try {

                this.labelWorking.setText(this.workingString);
                
                response = client.execute(request);
                
                if ((response.getStatusLine().toString().contains("200"))) {

                    BufferedReader rd = new BufferedReader(new InputStreamReader(
                        response.getEntity().getContent(), Charset.forName("UTF-8")));

                    line = "";
                    out = "";

                    while ((line = rd.readLine()) != null) {

                        out = out + line;
                    }

                    JSONParser parser = new JSONParser();

                    Object obj;

                    this.homeWorlds.removeAllItems();
                    this.homeWorldsList.clear();
                    this.result.clear();

                    try {

                        obj = parser.parse(out);

                        JSONArray array = (JSONArray) obj;

                        for (int i = 0; i < array.size(); i++) {

                            JSONObject obj2 = (JSONObject) array.get(i);
                            
                            if (obj2.get("name") != null) {
                                this.result.put((String) obj2.get("id"), (String) obj2.get("name"));
                                this.result.put((String) obj2.get("name"), (String) obj2.get("id"));

                                this.homeWorldsList.add(obj2.get("name"));
                            }
                        }
                        
                        Collections.sort(this.homeWorldsList);
                        
                        for (int i = 0; i < this.homeWorldsList.size(); i++) {
                            this.homeWorlds.addItem(this.homeWorldsList.get(i));
                        }
                        
                        this.labelWorking.setVisible(false);
                        this.refreshSelector.setEnabled(true);
                        this.workingButton.setEnabled(true);
                        this.homeWorlds.setEnabled(true);
                        this.jComboBoxLanguage.setEnabled(true);
                        this.jCheckBoxWvW.setEnabled(true);
                        
                        this.apiManager.loadHomeWorldFromFile();
                        
                        request.releaseConnection();
                        this.interrupt();
                    } catch (ParseException ex) {
                        try {
                            Logger.getLogger(ApiManager.class.getName()).log(
                                    Level.SEVERE, null, ex);
                            
                            request.releaseConnection();
                            
                            this.refreshSelector.setEnabled(false);
                            this.workingButton.setEnabled(false);
                            this.jComboBoxLanguage.setEnabled(false);
                            this.jCheckBoxWvW.setEnabled(false);
                            this.labelWorking.setText("connection error. retrying in 5...");
                            this.labelWorking.setVisible(true);
                            
                            Thread.sleep(5000);
                        } catch (InterruptedException ex1) {
                            Logger.getLogger(HomeWorldAllReader.class.getName()).log(Level.SEVERE, null, ex1);
                        }
                    }
                } else {
                    try {
                        request.releaseConnection();
                        
                        this.refreshSelector.setEnabled(false);
                        this.workingButton.setEnabled(false);
                        this.jComboBoxLanguage.setEnabled(false);
                        this.jCheckBoxWvW.setEnabled(false);
                        this.labelWorking.setText("connection error. retrying in 10...");
                        this.labelWorking.setVisible(true);
                        
                        Thread.sleep(5000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(HomeWorldAllReader.class.getName()).log(Level.SEVERE, null, ex);
                        
                        this.interrupt();
                    }
                }
            } catch (IOException | IllegalStateException ex) {
                try {
                    Logger.getLogger(EventReader.class.getName()).log(
                            Level.SEVERE, null, ex);
                    
                    request.releaseConnection();
                    
                    this.refreshSelector.setEnabled(false);
                    this.workingButton.setEnabled(false);
                    this.jComboBoxLanguage.setEnabled(false);
                    this.jCheckBoxWvW.setEnabled(false);
                    this.labelWorking.setText("connection error. retrying in 5...");
                    this.labelWorking.setVisible(true);
                    
                    Thread.sleep(5000);
                } catch (InterruptedException ex1) {
                    Logger.getLogger(HomeWorldAllReader.class.getName()).log(Level.SEVERE, null, ex1);
                    
                    this.interrupt();
                }
            }
        }
        
        */
    }
}
