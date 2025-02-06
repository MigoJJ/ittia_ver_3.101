package je.panse.doro.samsara.EMR_OBJ_XrayGFS;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import je.panse.doro.GDSEMR_frame;
import je.panse.doro.chartplate.filecontrol.datetime.Date_current;

public class EMR_DEXA extends JFrame implements ActionListener {
    
    private JTextField ageTextField = new JTextField(), totTextField = new JTextField();
    private JComboBox<String> genderComboBox = new JComboBox<>(new String[]{"Female", "Male", "TransGender"});
    private JCheckBox fractureCheckBox = new JCheckBox(), menopauseCheckBox = new JCheckBox();
    private JTextArea resultTextArea = new JTextArea(3, 20);
    private JButton calculateButton = new JButton("Calculate"), resetButton = new JButton("Reset"), quitButton = new JButton("Quit");

    public EMR_DEXA() {
        setTitle("EMR DEXA Interface");
        setSize(500, 650);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Labels for input fields
        String[] labelNames = {"Age:", "Gender:", "T-score / Z-Score:", "Fracture History:", "Menopause History:", "Z-score used in:"};
        JLabel zscoreLabel = new JLabel("<html>- Pediatric patients:<br>- Premenopausal women:<br>- Men under 50 years:<br>- Monitoring changes:</html>");

        JPanel mainPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (int i = 0; i < labelNames.length; i++) {
            JLabel label = new JLabel(labelNames[i], SwingConstants.RIGHT);
            mainPanel.add(label);
            mainPanel.add(switch (i) {
                case 0 -> ageTextField;
                case 1 -> genderComboBox;
                case 2 -> totTextField;
                case 3 -> fractureCheckBox;
                case 4 -> menopauseCheckBox;
                case 5 -> zscoreLabel;
                default -> new JLabel();
            });
        }

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        for (JButton button : new JButton[]{calculateButton, resetButton, quitButton}) {
            button.addActionListener(this);
            buttonPanel.add(button);
        }

        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        add(resultTextArea, BorderLayout.NORTH);
        setVisible(true);

        // Keyboard focus manager
        ageTextField.addActionListener(e -> genderComboBox.requestFocus());
        genderComboBox.addActionListener(e -> totTextField.requestFocus());
        totTextField.addActionListener(e -> fractureCheckBox.requestFocus());
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == calculateButton) calculateDEXA();
        else if (e.getSource() == resetButton) resetFields();
        else dispose();
    }

    private void calculateDEXA() {
        int age = Integer.parseInt(ageTextField.getText());
        String gender = genderComboBox.getSelectedItem().toString();
        double totZScore = Double.parseDouble(totTextField.getText());
        boolean fractureHistory = fractureCheckBox.isSelected();
        boolean menopauseHistory = menopauseCheckBox.isSelected();

        String diagnosis = determineDiagnosis(age, gender, totZScore, fractureHistory, menopauseHistory);
        updateResults(diagnosis, age, gender, fractureHistory);
        dispose();
    }

    private void resetFields() {
        ageTextField.setText(""); totTextField.setText("");
        genderComboBox.setSelectedIndex(0);
        fractureCheckBox.setSelected(false); menopauseCheckBox.setSelected(false);
        resultTextArea.setText("");
    }

    private void updateResults(String diagnosis, int age, String gender, boolean fractureHistory) {
        String date = Date_current.main("d");
        String fractureStatus = fractureHistory ? "[+]" : "none";

        resultTextArea.setText(diagnosis);
        GDSEMR_frame.setTextAreaText(5, "\n< DEXA >\n\t" + diagnosis);
        GDSEMR_frame.setTextAreaText(5, String.format("\n\tAge: [%d]  Gender: [%s]  Fracture: %s", age, gender, fractureStatus));
        GDSEMR_frame.setTextAreaText(9, "\n# " + diagnosis + " " + date);
    }

    private String determineDiagnosis(int age, String gender, double score, boolean fracture, boolean menopause) {
        return (age > 50 && gender.equalsIgnoreCase("Male")) || (gender.equalsIgnoreCase("Female") && menopause)
                ? diagnoseByTScore(score) : diagnoseByZScore(score);
    }

    private String diagnoseByTScore(double score) {
        return (score <= -2.5) ? "Osteoporosis" :
               (score < -1.0) ? "Low bone mass / Osteopenia" : "Normal bone density"
               + " T-score [" + score + "]";
    }

    private String diagnoseByZScore(double score) {
        return (score <= -2.0) ? "Osteoporosis" :
               (score < 0.0) ? "Low bone mass / Osteopenia" : "Normal bone density"
               + " Z-score [" + score + "]";
    }

    public static void main(String[] args) {
        new EMR_DEXA();
    }
}
