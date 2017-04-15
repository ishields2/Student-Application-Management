/**
 * This class is not original. Obtained from: http://www.codeproject.com/Articles/114959/Rounded-Border-JPanel-JPanel-graphics-improvements
 * 
 * The class is used to make graphical changes to JPanels.
 */
package com.sam.client;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class RoundedPanel extends JPanel {
	
     int strokeSize = 1;
     Color shadowColor = Color.black;
     boolean shady = true;
     boolean highQuality = true;
     Dimension arcs = new Dimension(20, 20);
     int shadowGap = 5;
     int shadowOffset = 4;
     int shadowAlpha = 150;

    
    public RoundedPanel() {
        super();
        setOpaque(false);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();
        int shadowGap = this.shadowGap;
        Color shadowColorA = new Color(shadowColor.getRed(), 
	shadowColor.getGreen(), shadowColor.getBlue(), shadowAlpha);
        Graphics2D graphics = (Graphics2D) g;

        
        if (highQuality) {
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
			RenderingHints.VALUE_ANTIALIAS_ON);
        }

        
        if (shady) {
            graphics.setColor(shadowColorA);
            graphics.fillRoundRect(
                    shadowOffset,
                    shadowOffset,
                    width - strokeSize - shadowOffset, 
                    height - strokeSize - shadowOffset, 
                    arcs.width, arcs.height);
        } else {
            shadowGap = 1;
        }

        
        graphics.setColor(getBackground());
        graphics.fillRoundRect(0, 0, width - shadowGap, 
		height - shadowGap, arcs.width, arcs.height);
        graphics.setColor(getForeground());
        graphics.setStroke(new BasicStroke(strokeSize));
        graphics.drawRoundRect(0, 0, width - shadowGap, 
		height - shadowGap, arcs.width, arcs.height);

        
        graphics.setStroke(new BasicStroke());
    }

}
