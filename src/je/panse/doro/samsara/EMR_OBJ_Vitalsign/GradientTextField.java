package je.panse.doro.samsara.EMR_OBJ_Vitalsign;

import javax.swing.JTextField;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GradientPaint;
import java.awt.Color;

public class GradientTextField extends JTextField {
    public GradientTextField(int columns) {
        super(columns);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        int width = getWidth();
        int height = getHeight();
        Color lightSkyBlue1 = new Color(175, 228, 255); // A lighter shade of light sky blue
        Color lightSkyBlue2 = new Color(200, 240, 255); // An even lighter shade

        GradientPaint gp = new GradientPaint(0, 0, lightSkyBlue1, 0, height, lightSkyBlue2);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, width, height);
        super.paintComponent(g2d);
        g2d.dispose();
    }
}
