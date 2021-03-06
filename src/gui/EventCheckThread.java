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

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JList;

/**
 *
 * @author mkdr <makedir@gmail.com>
 */
public class EventCheckThread extends Thread {
    
    private JList jListActiveList;
    private ArrayList activeList;
    
    public EventCheckThread(JList jListActiveList, ArrayList activeList) {

        setDaemon(true);
        
        this.jListActiveList = jListActiveList;
        this.activeList = activeList;
    }
    
    @Override
    public void run() {
        
        while (!isInterrupted()) {
            
            String[] newList = new String[this.activeList.size()];
            this.activeList.toArray(newList);
            
            this.jListActiveList.setListData(newList);
            
            try {

                Thread.sleep(3000);
            } catch (InterruptedException ex) {

                interrupt();
            }
        }
    }
}
