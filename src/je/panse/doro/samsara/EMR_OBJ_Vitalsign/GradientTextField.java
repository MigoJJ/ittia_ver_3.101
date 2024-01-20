package je.panse.doro.samsara.EMR_OBJ_Vitalsign;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class GradientTextField extends JTextField {
    public GradientTextField(int columns) {
        super(columns);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Color lightSkyBlueStart = new Color(135, 206, 250); // Light sky blue start color
        Color lightSkyBlueEnd = new Color(176, 226, 255); // Light sky blue end color
        GradientPaint gp = new GradientPaint(0, 0, lightSkyBlueStart, 0, getHeight(), lightSkyBlueEnd);
        g2.setPaint(gp);
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.dispose();

        super.paintComponent(g);
    }
}