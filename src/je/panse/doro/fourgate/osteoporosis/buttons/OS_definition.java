package je.panse.doro.fourgate.osteoporosis.buttons;

import java.awt.BorderLayout;		
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import je.panse.doro.entry.EntryDir;

public class OS_definition extends JFrame {
    public OS_definition() {
        // Set JFrame properties
        setTitle("Image Viewer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);

        // Load the image
        String imagePath = EntryDir.homeDir + "/support/EMR_support_Folder/Osteoporosis/OsteoporosisDiagnosis.png";
        try {
            ImageIcon icon = new ImageIcon(ImageIO.read(new File(imagePath)));
            JLabel label = new JLabel(icon);
            label.setHorizontalAlignment(SwingConstants.CENTER);

            // Add the label to the frame
            add(new JScrollPane(label), BorderLayout.CENTER);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading image: " + e.getMessage());
            System.exit(1);
        }

        // Display the frame
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(OS_definition::new);
    }
}
