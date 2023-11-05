package je.panse.doro.fourgate.emrgdsfu;


import javax.swing.*;

import je.panse.doro.entry.EntryDir;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class EMRGDS_FU_csvgeneration {
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("CSV Generator");
            frame.setSize(300, 150);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
            JPanel panel = new JPanel();
            JButton generateButton = new JButton("Generate CSV");
            
            generateButton.addActionListener(e -> {
                try {
                    generateCSV();
                    JOptionPane.showMessageDialog(frame, "CSV Generated Successfully");
                } catch (IOException ioException) {
                    JOptionPane.showMessageDialog(frame, "Error generating CSV: " + ioException.getMessage());
                }
            });
            
            panel.add(generateButton);
            frame.add(panel);
            frame.setVisible(true);
        });
    }
    
    private static void generateCSV() throws IOException {
        File file = new File(EntryDir.homeDir +"/fourgate/emrgdsfu/output.csv");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            String[] columns = {"DM", "Thyroid", "Hypertension", "Hypercholesterolemia", "URI", "AtypicalChest Pain"};
            String[] rows = {"CC>", "PI>", "ROS>", "PMH>", "S>", "O>", "Physical Exam>", "A>", "P>", "Comment>"};
            
            // Writing the header
            writer.write("Row,");
            for (int i = 0; i < columns.length; i++) {
                writer.write(columns[i]);
                if (i < columns.length - 1) {
                    writer.write(",");
                }
            }
            writer.newLine();
            
            // Writing the data
            for (String row : rows) {
                writer.write(row + ",");
                // Empty columns for this row
                for (int i = 0; i < columns.length - 1; i++) {
                    writer.write(",");
                }
                writer.newLine();
            }
        }
    }
}
