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

                            this.result.put((String) obj2.get("id"), (String) obj2.get("name"));
                            this.result.put((String) obj2.get("name"), (String) obj2.get("id"));

                            this.homeWorldsList.add(obj2.get("name"));
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
    }
}
