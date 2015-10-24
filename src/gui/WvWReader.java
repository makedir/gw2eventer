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
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class WvWReader extends Thread {
    
    //private HashMap result;
    
    private HashMap resultMapRed;
    private HashMap resultMapBlue;
    private HashMap resultMapGreen;
    private HashMap resultMapCenter;
    private HashMap resultMapScores;
    
    private String matchId;
    
    private WvWOverlayGui wvwOverlayGui;
    
    private int timeDifference;
    
    public WvWReader(WvWOverlayGui wvwOverlayGui) {
        
        this.wvwOverlayGui = wvwOverlayGui;
        this.timeDifference = 0;
        
        /*
        this.resultMapRed = new HashMap();
        this.resultMapBlue = new HashMap();
        this.resultMapGreen = new HashMap();
        this.resultMapCenter = new HashMap();
        this.resultMapScores = new HashMap();*/
    }
    
    /*
    public void setResult(HashMap hashMap) {
                
        this.result = hashMap;
    }*/
    
    public void setResultMaps(HashMap red, HashMap blue, HashMap green, HashMap center, HashMap scores) {
                
        this.resultMapRed = red;
        this.resultMapBlue = blue;
        this.resultMapGreen = green;
        this.resultMapCenter = center;
        this.resultMapScores = scores;
    }
    
    public String[] getScores(String type) {
        
        return (String[]) this.resultMapScores.get(type);
    }
    
    public String getMapFromId(String id, String home) {
               
        if (home.equals("RedHome")) {
            return (String) this.resultMapRed.get(id);
        } else if (home.equals("BlueHome")) {
            return (String) this.resultMapBlue.get(id);
        } else if (home.equals("GreenHome")) {
            return (String) this.resultMapGreen.get(id);
        } else {
            return (String) this.resultMapCenter.get(id);
        }
    }
    
    public void setMatchId(String matchId) {
                
        this.matchId = matchId;
    }
    
    @Override
    public void run() {

        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(10 * 1000).build();
        HttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
        //HttpClient client = new DefaultHttpClient();

        HttpGet request = new HttpGet(
                "https://api.guildwars2.com/v1/wvw/match_details.json?match_id="
                        + this.matchId);
        
        HttpResponse response;

        String line = "";
        String out = "";

        this.timeDifference = 0;
        
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

                    //this.result.clear();
                    this.resultMapRed.clear();
                    this.resultMapBlue.clear();
                    this.resultMapGreen.clear();
                    this.resultMapCenter.clear();
                    this.resultMapScores.clear();

                    try {

                        obj = parser.parse(out);

                        JSONObject obj2 = (JSONObject) obj;
                        JSONArray data = (JSONArray) obj2.get("maps");
                        JSONArray scores = (JSONArray) obj2.get("scores");
                        
                        /*
                        this.result.put("0", scores.get(0) + ","
                                + scores.get(1) + "," + scores.get(2));*/
                        
                        this.resultMapScores.put("all", new String[]{"" + scores.get(0), "" + scores.get(1), "" + scores.get(2)});
                        
                        for (int i = 0; i < data.size(); i++) {

                            obj2 = (JSONObject) data.get(i);
                            String type = (String) obj2.get("type");
                            scores = (JSONArray) obj2.get("scores");
                            JSONArray innerData = (JSONArray) obj2.get("objectives");
                            
                            this.resultMapScores.put(type, new String[]{"" + scores.get(0), "" + scores.get(1), "" + scores.get(2)});
                            
                            for (int j = 0; j < innerData.size(); j++) {
                                
                                JSONObject innerObj = (JSONObject) innerData.get(j);
                                
                                //this.result.put("" + innerObj.get("id"), "" + innerObj.get("owner"));
                                
                                if (type.equals("RedHome")) {
                                    this.resultMapRed.put("" + innerObj.get("id"), innerObj.get("owner"));
                                } else if (type.equals("BlueHome")) {
                                    this.resultMapBlue.put("" + innerObj.get("id"), innerObj.get("owner"));
                                } else if (type.equals("GreenHome")) {
                                    this.resultMapGreen.put("" + innerObj.get("id"), innerObj.get("owner"));
                                } else {
                                    this.resultMapCenter.put("" + innerObj.get("id"), innerObj.get("owner"));
                                }
                                
                                //this.result.put("" + innerObj.get("id"), "" + innerObj.get("owner") + "," + type);
                                //System.out.println(result);
                            }
                        }
                        
                        try {
                            
                            request.releaseConnection();
                            this.wvwOverlayGui.refresh(this.timeDifference);
                            
                            this.timeDifference = 0;
                            
                            Thread.sleep(10000);
                        } catch (InterruptedException ex) {
                            
                            Logger.getLogger(WvWReader.class.getName()).log(Level.SEVERE, null, ex);

                            this.interrupt();
                        }
                    } catch (ParseException ex) {
                        
                        try {
                            
                            Logger.getLogger(ApiManager.class.getName()).log(
                                    Level.SEVERE, null, ex);
                            
                            request.releaseConnection();
                            //this.wvwOverlayGui.refresh();
                            
                            if (this.timeDifference < 291) {
                                this.timeDifference = this.timeDifference + 3;
                            } else {
                                this.timeDifference = 3;
                            }
                            
                            Thread.sleep(3000);
                        } catch (InterruptedException ex1) {
                            
                            Logger.getLogger(WvWReader.class.getName()).log(Level.SEVERE, null, ex1);
                            
                            this.interrupt();
                        }
                    }
                } else {
                    
                    try {
                        
                        Logger.getLogger(EventReader.class.getName()).log(
                            Level.SEVERE, null, "Connection error.");
                        
                        request.releaseConnection();
                        //this.wvwOverlayGui.refresh();
                        
                        if (this.timeDifference < 286) {
                            this.timeDifference = this.timeDifference + 13;
                        } else {
                            this.timeDifference = 13;
                        }
                        
                        Thread.sleep(3000);
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
                    //this.wvwOverlayGui.refresh();
                    
                    if (this.timeDifference < 286) {
                        this.timeDifference = this.timeDifference + 13;
                    } else {
                        this.timeDifference = 13;
                    }
                    
                    Thread.sleep(3000);
                } catch (InterruptedException ex1) {
                    
                    Logger.getLogger(EventAllReader.class.getName()).log(Level.SEVERE, null, ex1);
                    
                    this.interrupt();
                }
            }
        }
    }
}
