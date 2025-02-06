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
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import je.panse.doro.entry.EntryDir;
import je.panse.doro.fourgate.osteoporosis.EMR_Osteoporosis_meds;

public class OS_definition extends JFrame {
    public OS_definition(String osargs) {
        setTitle("Osteoporosis Viewer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);

        if (osargs.equals("Osteoporosis Definition")) {
            String imagePath = EntryDir.homeDir + "/support/EMR_support_Folder/Osteoporosis/OsteoporosisDiagnosis.png";
            try {
                ImageIcon icon = new ImageIcon(ImageIO.read(new File(imagePath)));
                JLabel label = new JLabel(icon);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                add(new JScrollPane(label), BorderLayout.CENTER);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error loading image: " + e.getMessage());
                dispose();
            }
        } else if (osargs.equals("Secondary Osteoporosis")) {
            String txtPath = EntryDir.homeDir + "/support/EMR_support_Folder/Osteoporosis/Causes of Secondary Osteoporosis.txt";
            try {
                String textContent = new String(java.nio.file.Files.readAllBytes(new File(txtPath).toPath()));
                JTextArea textArea = new JTextArea(textContent);
                textArea.setEditable(false);
                textArea.setWrapStyleWord(true);
                textArea.setLineWrap(true);
                add(new JScrollPane(textArea), BorderLayout.CENTER);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error loading text file: " + e.getMessage());
                dispose();
            }
        } else if (osargs.equals("Medications")) {
            EMR_Osteoporosis_meds.main(null);
            String imagePath = EntryDir.homeDir + "/support/EMR_support_Folder/Osteoporosis/Osteoporosis.jpg";
            try {
                ImageIcon icon = new ImageIcon(ImageIO.read(new File(imagePath)));
                JLabel label = new JLabel(icon);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                add(new JScrollPane(label), BorderLayout.CENTER);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error loading image: " + e.getMessage());
                dispose();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Invalid argument. Use 'Osteoporosis Definition', 'Secondary Osteoporosis', or 'Medications'.");
            dispose();
        }

        setVisible(true);
    }

    public static void main(String osargs) {
        SwingUtilities.invokeLater(() -> new OS_definition(osargs));
    }
}
