package je.panse.doro.soap.fu;

import javax.swing.*;
import je.panse.doro.GDSEMR_frame;
import java.awt.*;
import java.awt.event.ActionEvent;

public class FUplan extends JFrame {
    private JTextField fuField;
    private JTextField medsCodeField;

    public FUplan() {
        super("FU Plan");
        setUndecorated(true);
        setLayout(new GridLayout(2, 2, 5, 5));

        Font fieldFont = new Font("Arial", Font.BOLD, 12);

        add(new JLabel("FU:"));
        fuField = new JTextField(10);
        fuField.setFont(fieldFont);
        fuField.setHorizontalAlignment(JTextField.CENTER);
        add(fuField);

        add(new JLabel("Meds Code:"));
        medsCodeField = new JTextField(10);
        medsCodeField.setFont(fieldFont);
        medsCodeField.setHorizontalAlignment(JTextField.CENTER);
        add(medsCodeField);

        // Action when Enter is pressed in FU field
        fuField.addActionListener((ActionEvent e) -> medsCodeField.requestFocusInWindow());

        // Action when Enter is pressed in Meds Code field
        medsCodeField.addActionListener((ActionEvent e) -> {
            String fu = parseFU(fuField.getText().trim());
            String meds = medsCodeField.getText().trim();
            String medsMessage = returnchangefield2(meds);

            System.out.printf("Saved: FU = %s, Meds Code = %s%n", fu, meds);
            System.out.println("***: " + fu);
            System.out.println("***: " + medsMessage);
            GDSEMR_frame.setTextAreaText(8, "\n***: " + fu + "\n***: " + medsMessage);

            // Clear text fields
            fuField.setText("");
            medsCodeField.setText("");
            fuField.requestFocusInWindow();
        });

        setSize(300, 60);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(screenSize.width - getWidth(), 60);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private String parseFU(String input) {
        if (input == null || input.isEmpty()) return "";
        String num = input.replaceAll("[^0-9]", "");
        if (input.toLowerCase().contains("w")) {
            return String.format("follow-up [ %s ] weeks later", num);
        } else {
            return String.format("follow-up [ %s ] months later", num);
        }
    }

    private String returnchangefield2(String meds) {
        String[] codes = {"5", "55", "6", "8", "2", "4", "0", "1"};
        String[] messages = {
            " |→  starting new medicine to treat ",
            " →|  discontinue current medication",
            " [ → ] advised the patient to continue with current medication",
            " [ ↗ ] increased the dose of current medication",
            " [ ↘ ] decreased the dose of current medication",
            " [ ⭯ ] changed the dose of current medication",
            " Observation & Follow-up without medication",
            " With conservative treatment"
        };

        for (int i = 0; i < codes.length; i++) {
            if (meds.equals(codes[i])) return messages[i];
        }
        return "(unknown code)";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FUplan::new);
    }
}
