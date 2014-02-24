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
import javax.swing.JCheckBox;
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
public class WvWMatchReader extends Thread {
    
    private HashMap result;
    
    private JCheckBox wvwCheckBox;
    
    public WvWMatchReader(HashMap result, JCheckBox wvwCheckBox) {
        
        this.result = result;
        this.wvwCheckBox = wvwCheckBox;
    }
    
    @Override
    public void run() {

        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(10 * 1000).build();
        HttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
        //HttpClient client = new DefaultHttpClient();

        HttpGet request = new HttpGet(
                "https://api.guildwars2.com/v1/wvw/matches.json");
        
        HttpResponse response;

        String line = "";
        String out = "";

        while (!this.isInterrupted()) {
            
            try {
                
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

                    this.result.clear();

                    try {

                        obj = parser.parse(out);
                        
                        JSONObject obj2 = (JSONObject) obj;
                        JSONArray array = (JSONArray) obj2.get("wvw_matches");

                        for (int i = 0; i < array.size(); i++) {

                             obj2 = (JSONObject) array.get(i);

                            this.result.put("" + obj2.get("wvw_match_id"), new String[]{"" + obj2.get("red_world_id"), "" + obj2.get("blue_world_id"), "" + obj2.get("green_world_id")});
                        }
                    } catch (ParseException ex) {

                        Logger.getLogger(ApiManager.class.getName()).log(
                                Level.SEVERE, null, ex);
                    }
                    
                    request.releaseConnection();
                    
                    this.wvwCheckBox.setEnabled(true);
                    this.interrupt();
                } else {
                    try {
                        request.releaseConnection();
                        Thread.sleep(10000);
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
                    Thread.sleep(10000);
                } catch (InterruptedException ex1) {
                    Logger.getLogger(HomeWorldAllReader.class.getName()).log(Level.SEVERE, null, ex1);
                    
                    this.interrupt();
                }
            }
        }
    }
    
}
