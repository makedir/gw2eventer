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

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author mkdr <makedir@gmail.com>
 */
public class BackgroundPanel extends JPanel {
    
    private Image bgImage;
    
    public BackgroundPanel() {
       
        try {
            
            this.bgImage = ImageIO.read(
                    ClassLoader.getSystemResource("media/bg_k.png"));
        } catch (IOException e) {
            //
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        
        super.paintComponent(g);

        if (this.bgImage != null) {
            
            int width = this.bgImage.getWidth(this);
            int height = this.bgImage.getHeight(this);
            
            if ((width > 0) && (height > 0)) {
                
                for (int x = 0; x < getWidth(); x += width) {
                    
                    for (int y = 0; y < getHeight(); y += height) {
                        
                        g.drawImage(this.bgImage, x, y, width, height, this);
                    }
                }
            }
        }
    }
    
}
