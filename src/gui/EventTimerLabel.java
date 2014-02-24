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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.Timer;

/**
 *
 * @author mkdr <makedir@gmail.com>
 */
public class EventTimerLabel extends JLabel implements ActionListener {
    
    private Timer timer;
    private int counter;
    
    private String customText;
    
    private static final int COUNTER_SECONDS = 295;
    
    public EventTimerLabel() {
        
        super();
        
        this.customText = "";
        
        this.setForeground(Color.white);
        this.setFocusable(false);
        this.setOpaque(false);
        this.setVisible(false);
        
        this.counter = COUNTER_SECONDS;
        this.timer = new Timer(1000, this);
    }

    public void setCustomText(String customText) {
        
        this.customText = customText;
    }
    
    public boolean isTicking() {
        
        return this.timer.isRunning();
    }
    
    public void setCounter(int counter) {
        
        this.counter = counter;
    }
    
    public void startTimer() {
        
        if (this.counter > 0) {
            
            this.timer.start();
            //this.setVisible(true);
        }
    }
    
    public void resetTimer() {
        
        this.timer.stop();
        this.counter = COUNTER_SECONDS;
        this.setVisible(false);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        this.counter--;
        
        this.setText(customText + this.getDurationString(this.counter));
        
        if (this.counter == 0) {
            
            this.resetTimer();
        }
    }
    
    private String getDurationString(int seconds) {

        int minutes = (seconds % 3600) / 60;
        seconds = seconds % 60;

        return this.twoDigitString(minutes) + ":" + this.twoDigitString(seconds);
    }

    private String twoDigitString(int number) {

        if (number == 0) {
            return "00";
        }

        if (number / 10 == 0) {
            return "0" + number;
        }

        return String.valueOf(number);
    }
    
}