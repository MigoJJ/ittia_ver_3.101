package je.panse.doro.samsara.EMR_OBJ_XrayGFS;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import je.panse.doro.GDSEMR_frame;

public class EMR_DEXA extends JFrame implements ActionListener {

    private JTextField scoreField = new JTextField(10), ageField = new JTextField(10);
    private JComboBox<String> genderComboBox = new JComboBox<>(new String[]{"Female", "Male"});
    private JTextArea outputTextArea = new JTextArea(10, 40);
    private JCheckBox fragilityFractureCheckBox = new JCheckBox(), menopauseCheckBox = new JCheckBox();
    private ButtonGroup scoreTypeButtonGroup = new ButtonGroup();
    private JRadioButton tScoreRadioButton = new JRadioButton("T-Score", true);
    private JRadioButton zScoreRadioButton = new JRadioButton("Z-Score");

    public EMR_DEXA() {
        setTitle("Osteoporosis Risk Assessment");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        initComponents();
        createLayout();
        addListeners();
        pack();  
        setLocationRelativeTo(null);  
        setVisible(true);  
        scoreField.requestFocusInWindow();  // Set focus to scoreField at startup
    }

    private void initComponents() {
        Font boldFont = new Font("SansSerif", Font.BOLD, 14); // Bold font

        // Apply bold font and center alignment to text fields
        scoreField.setFont(boldFont);
        ageField.setFont(boldFont);
        scoreField.setHorizontalAlignment(JTextField.CENTER);
        ageField.setHorizontalAlignment(JTextField.CENTER);

        scoreTypeButtonGroup.add(tScoreRadioButton);
        scoreTypeButtonGroup.add(zScoreRadioButton);
        outputTextArea.setEditable(false);
    }

    private void createLayout() {
        // WEST Panel (Z-Score Explanation)
        JTextArea zScoreInfo = new JTextArea("Z-Score:--------\n- Children/adolescents\n- Premenopausal women (<50)\n- Men under 50\n"
           + "Key Tests:-------- \n"
           + "* Calcium & Phosphorus \n"
           + "* Vitamin D (regularly, especially if supplemented) \n"
           + "* Kidney Function (eGFR, Cr) \n"
           + "* Bone Turnover Markers (CTX/NTX, BSAP)\n "
           + "   - 3-6 months after new medication, then annually.");
        zScoreInfo.setEditable(false);
        zScoreInfo.setBackground(new Color(205, 206, 250)); // Light Sky Blue
        JPanel westPanel = new JPanel(new BorderLayout());
        westPanel.setBorder(BorderFactory.createTitledBorder("Z-Score Explanation"));
        westPanel.add(zScoreInfo, BorderLayout.CENTER);
        westPanel.setPreferredSize(new Dimension(300, 100)); // Increased width of WEST panel
        add(westPanel, BorderLayout.WEST);

        // CENTER Panel (Input Form)
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Input Data"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.6; // Reduce width ratio (Previously 1.0)

        add(inputPanel, BorderLayout.CENTER);

        Font boldItalicFont = new Font("SansSerif", Font.BOLD | Font.ITALIC, 12);

        // Set text field background color (Very Light Brown)
        Color veryLightBrown = new Color(210, 180, 140);  // Light brown shade

        scoreField.setBackground(veryLightBrown);
        ageField.setBackground(veryLightBrown);
        
        addField(inputPanel, gbc, new JLabel("Score:", JLabel.TRAILING), boldItalicFont, scoreField, 0);
        addField(inputPanel, gbc, new JLabel("Age:", JLabel.TRAILING), boldItalicFont, ageField, 1);
        addField(inputPanel, gbc, new JLabel("Gender:", JLabel.TRAILING), boldItalicFont, genderComboBox, 2);
        addField(inputPanel, gbc, new JLabel("Menopause (For Females):", JLabel.TRAILING), boldItalicFont, menopauseCheckBox, 3);
        addField(inputPanel, gbc, new JLabel("Fracture:", JLabel.TRAILING), boldItalicFont, fragilityFractureCheckBox, 4);
        addField(inputPanel, gbc, new JLabel("Score Type:", JLabel.TRAILING), boldItalicFont, tScoreRadioButton, 5);
        gbc.gridx = 2;
        inputPanel.add(zScoreRadioButton, gbc);
        add(inputPanel, BorderLayout.CENTER);

        // South Panel (Buttons)
        JPanel buttonPanel = new JPanel();
        String[] buttonNames = {"Assess Risk", "Clear", "Save", "Quit"};
        for (String name : buttonNames) {
            JButton button = new JButton(name);
            button.addActionListener(this);
            buttonPanel.add(button);
        }
        add(buttonPanel, BorderLayout.SOUTH);

        // Output Area
        outputTextArea.setBackground(new Color(250, 250, 250)); // Light Sky Blue
        add(new JScrollPane(outputTextArea), BorderLayout.NORTH);
    }

    private void addField(JPanel panel, GridBagConstraints gbc, JLabel label, Font font, Component comp, int y) {
        label.setFont(font);
        gbc.gridx = 0;
        gbc.gridy = y;
        panel.add(label, gbc);
        gbc.gridx = 1;
        panel.add(comp, gbc);
    }

    private void addListeners() {
        scoreField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) ageField.requestFocusInWindow();
            }
        });
    }

    private void clearFields() {
        scoreField.setText("");
        ageField.setText("");
        genderComboBox.setSelectedIndex(0);
        fragilityFractureCheckBox.setSelected(false);
        menopauseCheckBox.setSelected(false);
        tScoreRadioButton.setSelected(true); // Set default selection to T-Score
        outputTextArea.setText("");
    }

    private String calculateDEXADiagnosis(double score, String type, int age, String gender, boolean fracture, boolean menopause) {
        if (gender.equals("Female") && !menopause) { // Premenopausal women
            // Z-score interpretation
            return (score < -2.0) ? "Low Z-Score (BMD lower than expected)" : "Normal Z-Score (BMD within expected range)";

        } else { // Everyone else (including postmenopausal women and men of any age)
            // T-score interpretation
            if (type.equals("T-Score")) {
                return (score <= -2.5 && fracture) ? "Severe Osteoporosis" :
                        (score <= -2.5 ? "Osteoporosis" : (score < -1.0 ? "Osteopenia" : "Normal Bone Density"));
            } else { //If a Z-score is entered for a non-premenopausal female, still interpret it.
                return (score < -2.0) ? "Low Z-Score (BMD lower than expected)" : "Normal Z-Score (BMD within expected range)";
            }
        }
    }

    private String formatReport(double score, String scoreType, int age, String gender, boolean fracture, boolean menopause) {
        String diagnosis = calculateDEXADiagnosis(score, scoreType, age, gender, fracture, menopause); // Pass menopause to calculateDiagnosis
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));

        String report = "< DEXA >\n";
        report += String.format("\t%s %s [%.1f]\n", diagnosis, scoreType, score);
        report += String.format("\tAge: [%d]  Gender: [%s]\n", age, gender);

        if (gender.equals("Female")) {
            report += String.format("\tMenopause: [%s]  Fracture: [%s]\n", (menopause ? "Postmenopausal" : "Premenopausal"), fracture ? "+" : "-");
            if (!menopause && scoreType.equals("T-Score")) { // Add warning for T-Score in premenopausal women
                report += "\t**Warning: T-score is not typically used for premenopausal women. Consider using Z-score.**\n";
            }

        } else {
            report += String.format("\tFracture: [%s]\n", fracture ? "+" : "-");
        }

        report += "Comment>\n";
        report += String.format("# %s %s [%.1f]   %s", diagnosis.split("\\(")[0], scoreType, score, date);
        return report;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("Clear")) {
            clearFields();
        } else if (command.equals("Save")) {
//            JOptionPane.showMessageDialog(this, "Save functionality not yet implemented.");
            GDSEMR_frame.setTextAreaText(5, outputTextArea.getText());
//            GDSEMR_frame.setTextAreaText(7, "\n# " + outputTextArea.getText());
            
        } else if (command.equals("Quit")) {
            dispose();
        } else {
            try {
                double score = Double.parseDouble(scoreField.getText());
                int age = Integer.parseInt(ageField.getText());
                String gender = (String) genderComboBox.getSelectedItem();
                boolean fracture = fragilityFractureCheckBox.isSelected();
                boolean menopause = menopauseCheckBox.isSelected();
                String scoreType = tScoreRadioButton.isSelected() ? "T-Score" : zScoreRadioButton.isSelected() ? "Z-Score" : "";

                if (scoreType.isEmpty()) {
                    outputTextArea.setText("Error: Please select a score type.");
                    return;
                }
                outputTextArea.setText(formatReport(score, scoreType, age, gender, fracture, menopause));
            } catch (NumberFormatException ex) {
                outputTextArea.setText("Invalid input. Enter numeric values for score and age.");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EMR_DEXA::new);
    }
}
