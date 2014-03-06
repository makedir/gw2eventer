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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

/**
 *
 * @author mkdr <makedir@gmail.com>
 */
public class FeedbackGui extends javax.swing.JDialog {

    private String langErrorSendTitle;
    private String langErrorSendMessage;
    private String langErrorInputTitle;
    private String langErrorInputFrom;
    private String langErrorInputMessage;
    
    /**
     * Creates new form FeedbackGui
     */
    public FeedbackGui(java.awt.Frame parent, boolean modal) {
        
        super(parent, modal);
        initComponents();
        
        this.langErrorSendTitle = "";
        this.langErrorSendMessage = "";
        this.langErrorInputTitle = "";
        this.langErrorInputFrom = "";
        this.langErrorInputMessage = "";
    }

    public void showGui() {
        
        //this.jTextFieldFrom.setText("");
        this.jTextFieldSubject.setText("");
        this.jTextAreaMessage.setText("");
        
        this.setVisible(true);
    }
    
    public void setTranslations(String title, String feedback, String from,
            String subject, String message, String sendBtn, String cancleBtn,
            String errorSendTitle, String errorSendMessage, String errorInputTitle,
            String errorInputFrom, String errorInputMessage) {
        
        this.setTitle(title);
        
        this.jLabelFeedback.setText(feedback);
        this.jLabelFrom.setText(from);
        this.jLabelSubject.setText(subject);
        this.jLabelMessage.setText(message);
        this.jButtonSend.setText(sendBtn);
        this.jButtonCancle.setText(cancleBtn);
        
        this.langErrorSendTitle = errorSendTitle;
        this.langErrorSendMessage = errorSendMessage;
        this.langErrorInputTitle = errorInputTitle;
        this.langErrorInputFrom = errorInputFrom;
        this.langErrorInputMessage = errorInputMessage;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelFeedback = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabelFrom = new javax.swing.JLabel();
        jTextFieldFrom = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabelSubject = new javax.swing.JLabel();
        jTextFieldSubject = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabelMessage = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaMessage = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        jButtonSend = new javax.swing.JButton();
        jButtonCancle = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Leave a feedback/bug report");
        setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        setResizable(false);

        jLabelFeedback.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabelFeedback.setText("Leave a feedback");
        jLabelFeedback.setPreferredSize(new java.awt.Dimension(93, 33));
        getContentPane().add(jLabelFeedback, java.awt.BorderLayout.PAGE_START);

        jPanel2.setPreferredSize(new java.awt.Dimension(400, 200));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel5.setLayout(new java.awt.BorderLayout());

        jLabelFrom.setText("eMail/From:");
        jPanel5.add(jLabelFrom, java.awt.BorderLayout.PAGE_START);
        jPanel5.add(jTextFieldFrom, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel5, java.awt.BorderLayout.PAGE_START);

        jPanel4.setLayout(new java.awt.BorderLayout());

        jLabelSubject.setText("Subject:");
        jPanel4.add(jLabelSubject, java.awt.BorderLayout.CENTER);

        jTextFieldSubject.setPreferredSize(new java.awt.Dimension(100, 22));
        jPanel4.add(jTextFieldSubject, java.awt.BorderLayout.PAGE_END);

        jPanel2.add(jPanel4, java.awt.BorderLayout.CENTER);

        jPanel3.setPreferredSize(new java.awt.Dimension(200, 120));
        jPanel3.setLayout(new java.awt.BorderLayout());

        jLabelMessage.setText("Message:");
        jPanel3.add(jLabelMessage, java.awt.BorderLayout.CENTER);

        jTextAreaMessage.setColumns(20);
        jTextAreaMessage.setRows(5);
        jTextAreaMessage.setPreferredSize(null);
        jScrollPane1.setViewportView(jTextAreaMessage);

        jPanel3.add(jScrollPane1, java.awt.BorderLayout.PAGE_END);

        jPanel2.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(jPanel2, java.awt.BorderLayout.LINE_START);

        jButtonSend.setText("Send");
        jButtonSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSendActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonSend);

        jButtonCancle.setText("Cancle");
        jButtonCancle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancleActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonCancle);

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonCancleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancleActionPerformed

        this.setVisible(false);
    }//GEN-LAST:event_jButtonCancleActionPerformed

    private void jButtonSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSendActionPerformed

        boolean wasSent = false;
        
        if (this.jTextFieldFrom.getText().equals("")) {
            
            JOptionPane.showMessageDialog(this,
                this.langErrorInputFrom,
                this.langErrorInputTitle,
                JOptionPane.WARNING_MESSAGE);
        } else if (this.jTextAreaMessage.getText().equals("")) {
            
            JOptionPane.showMessageDialog(this,
                this.langErrorInputMessage,
                this.langErrorInputTitle,
                JOptionPane.WARNING_MESSAGE);
        } else {     
            
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(10 * 1000).build();
            HttpClient httpclient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
            HttpPost httppost = new HttpPost("http://gw2eventer.sourceforge.net/feedback/");

            // Request parameters and other properties.
            List<NameValuePair> params = new ArrayList<NameValuePair>(5);

            params.add(new BasicNameValuePair("key", "jhk3h238kfh8k32r88hfk2h"));
            params.add(new BasicNameValuePair("date", new Date().getTime() + ""));
            params.add(new BasicNameValuePair("from", this.jTextFieldFrom.getText()));
            params.add(new BasicNameValuePair("subject", this.jTextFieldSubject.getText()));
            params.add(new BasicNameValuePair("message", this.jTextAreaMessage.getText()));

            try {
                httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(FeedbackGui.class.getName()).log(Level.SEVERE, null, ex);
            }

            //Execute and get the response.
            HttpResponse response;

            try {
                
                response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();

                if (entity != null) {

                    InputStream instream = entity.getContent();

                    try {
                        wasSent = true;
                        
                        BufferedReader rd = new BufferedReader(new InputStreamReader(
                        response.getEntity().getContent(), Charset.forName("UTF-8")));

                        String line = "";
                        String out = "";

                        while ((line = rd.readLine()) != null) {

                            out = out + line;
                        }
                        
                        System.out.println(out);
                    } finally {
                        instream.close();
                    }
                }
            } catch (IOException | IllegalStateException ex) {
                Logger.getLogger(FeedbackGui.class.getName()).log(Level.SEVERE, null, ex);
                
                JOptionPane.showMessageDialog(this,
                    this.langErrorSendMessage,
                    this.langErrorSendTitle,
                    JOptionPane.WARNING_MESSAGE);
            } finally {
                
                if (wasSent) {
                    this.setVisible(false);
                }
            }
        }
    }//GEN-LAST:event_jButtonSendActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCancle;
    private javax.swing.JButton jButtonSend;
    private javax.swing.JLabel jLabelFeedback;
    private javax.swing.JLabel jLabelFrom;
    private javax.swing.JLabel jLabelMessage;
    private javax.swing.JLabel jLabelSubject;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextAreaMessage;
    private javax.swing.JTextField jTextFieldFrom;
    private javax.swing.JTextField jTextFieldSubject;
    // End of variables declaration//GEN-END:variables
}
