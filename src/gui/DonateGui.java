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

import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.net.URI;

/**
 *
 * @author mkdr <makedir@gmail.com>
 */
public class DonateGui extends javax.swing.JDialog {

    /**
     * Creates new form DonateGui
     */
    public DonateGui(java.awt.Frame parent, boolean modal) {
        
        super(parent, modal);
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabelSentence1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabelSentence2 = new javax.swing.JLabel();
        jTextFieldMail = new javax.swing.JTextField();
        jLabelSentence3 = new javax.swing.JLabel();
        jButtonClipboard = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabelDonate = new javax.swing.JLabel();
        jButtonCancle = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Buy me a coffee :-)");
        setPreferredSize(new java.awt.Dimension(450, 260));

        jLabelSentence1.setText("Please know, that PayPal takes away the following amount of each donation:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel3.setText("PayPal: 1,2% + 0,35€");

        jLabelSentence2.setText("<html>I appreciate every single donation. But please don't donate under 1€,<br>otherwise PayPal would take away almost 50% of your donated money.<br><br>Thank you.</html>");

        jTextFieldMail.setEditable(false);
        jTextFieldMail.setText("makedir@gmail.com");

        jLabelSentence3.setText("Another way is to send me a gift just via normal PayPal sending money:");

        jButtonClipboard.setText("Copy to clipboard");
        jButtonClipboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonClipboardActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelSentence1)
                    .addComponent(jLabel3)
                    .addComponent(jLabelSentence2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelSentence3)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jTextFieldMail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonClipboard)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelSentence1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabelSentence2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabelSentence3)
                .addGap(1, 1, 1)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldMail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonClipboard))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, java.awt.BorderLayout.LINE_START);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabelDonate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/paypal-button.png"))); // NOI18N
        jLabelDonate.setText(" ");
        jLabelDonate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelDonateMouseClicked(evt);
            }
        });
        jPanel1.add(jLabelDonate, new java.awt.GridBagConstraints());

        jButtonCancle.setText("Cancle");
        jButtonCancle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancleActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonCancle, new java.awt.GridBagConstraints());

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonCancleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancleActionPerformed

        this.setVisible(false);
    }//GEN-LAST:event_jButtonCancleActionPerformed

    public void setTranslations(String txt1, String txt2, String txt3, String saveClipboard, String cancle) {
        
        this.jLabelSentence1.setText(txt1);
        this.jLabelSentence2.setText(txt2);
        this.jLabelSentence3.setText(txt3);
        this.jButtonClipboard.setText(saveClipboard);
        this.jButtonCancle.setText(cancle);
    }
    
    private void jLabelDonateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelDonateMouseClicked

        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(new URI("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=R9A5ZF7U7G7LC"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        this.setVisible(false);
    }//GEN-LAST:event_jLabelDonateMouseClicked

    private void jButtonClipboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonClipboardActionPerformed

        StringSelection stringSelection = new StringSelection(this.jTextFieldMail.getText());
        Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
        clpbrd.setContents(stringSelection, null);
    }//GEN-LAST:event_jButtonClipboardActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCancle;
    private javax.swing.JButton jButtonClipboard;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabelDonate;
    private javax.swing.JLabel jLabelSentence1;
    private javax.swing.JLabel jLabelSentence2;
    private javax.swing.JLabel jLabelSentence3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField jTextFieldMail;
    // End of variables declaration//GEN-END:variables
}
