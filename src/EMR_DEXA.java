import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;

public class EMR_DEXA extends JFrame implements ActionListener {

    private JTextField scoreField, ageField;
    private JComboBox<String> genderComboBox;
    private JTextArea outputTextArea;
    private JCheckBox fragilityFractureCheckBox, menopauseCheckBox;
    private ButtonGroup scoreTypeButtonGroup;

    public EMR_DEXA() {
        setTitle("Osteoporosis Risk Assessment");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize components
        scoreField = new JTextField(10);
        ageField = new JTextField(10);
        genderComboBox = new JComboBox<>(new String[]{"Male", "Female"});
        fragilityFractureCheckBox = new JCheckBox();
        menopauseCheckBox = new JCheckBox(); // New Menopause Checkbox
        outputTextArea = new JTextArea(10, 45);
        outputTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputTextArea);

        // Radio buttons for score type
        scoreTypeButtonGroup = new ButtonGroup();
        JRadioButton tScoreRadioButton = new JRadioButton("T-Score");
        JRadioButton zScoreRadioButton = new JRadioButton("Z-Score");
        tScoreRadioButton.setSelected(true);
        scoreTypeButtonGroup.add(tScoreRadioButton);
        scoreTypeButtonGroup.add(zScoreRadioButton);

        // Z-Score explanation (West Panel)
        JTextArea zScoreInfo = new JTextArea(
                "Z-Score:\n" +
                "- Children and adolescents\n" +
                "- Premenopausal women (<50 years old)\n" +
                "- Men under 50 years old"
        );
        zScoreInfo.setEditable(false);
        zScoreInfo.setLineWrap(true);
        zScoreInfo.setWrapStyleWord(true);
        JPanel westPanel = new JPanel(new BorderLayout());
        westPanel.setBorder(BorderFactory.createTitledBorder("Z-Score Explanation"));
        westPanel.add(zScoreInfo, BorderLayout.CENTER);
        add(westPanel, BorderLayout.WEST);

     // Central Panel (Input Form) with GridBagLayout
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL; // Allow components to expand
        gbc.weightx = 1.0; // Enable horizontal expansion

        addComponent(inputPanel, gbc, new JLabel("Score:"), 0, 0, 1, 1);
        addComponent(inputPanel, gbc, scoreField, 1, 0, 2, 1); // Wider input field

        addComponent(inputPanel, gbc, new JLabel("Age:"), 0, 1, 1, 1);
        addComponent(inputPanel, gbc, ageField, 1, 1, 2, 1); // Wider input field

        addComponent(inputPanel, gbc, new JLabel("Gender:"), 0, 2, 1, 1);
        addComponent(inputPanel, gbc, genderComboBox, 1, 2, 2, 1); // Wider dropdown

        addComponent(inputPanel, gbc, new JLabel("Menopause (For Females):"), 0, 3, 1, 1);
        addComponent(inputPanel, gbc, menopauseCheckBox, 1, 3, 2, 1); // Wider checkbox

        addComponent(inputPanel, gbc, new JLabel("Fracture:"), 0, 4, 1, 1);
        addComponent(inputPanel, gbc, fragilityFractureCheckBox, 1, 4, 2, 1); // Wider checkbox

        addComponent(inputPanel, gbc, new JLabel("Score Type:"), 0, 5, 1, 1);
        addComponent(inputPanel, gbc, tScoreRadioButton, 1, 5, 1, 1);
        addComponent(inputPanel, gbc, zScoreRadioButton, 2, 5, 1, 1);

        add(inputPanel, BorderLayout.CENTER);


        // South Panel (Buttons)
        JPanel buttonPanel = new JPanel();
        JButton assessButton = new JButton("Assess Risk");
        JButton clearButton = new JButton("Clear");
        JButton quitButton = new JButton("Quit");

        assessButton.addActionListener(this);
        clearButton.addActionListener(e -> clearFields());
        quitButton.addActionListener(e -> System.exit(0));

        buttonPanel.add(assessButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(quitButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // North Panel (Output Text Area)
        add(scrollPane, BorderLayout.NORTH);

        pack();
        setVisible(true);
    }

    private void addComponent(JPanel panel, GridBagConstraints gbc, Component comp, int gridx, int gridy, int gridwidth, int gridheight) {
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.gridwidth = gridwidth;
        gbc.gridheight = gridheight;
        panel.add(comp, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            double score = Double.parseDouble(scoreField.getText());
            int age = Integer.parseInt(ageField.getText());
            String gender = (String) genderComboBox.getSelectedItem();
            boolean fracture = fragilityFractureCheckBox.isSelected();
            boolean menopause = menopauseCheckBox.isSelected(); // Get menopause status
            String scoreType = "";

            for (Enumeration<AbstractButton> buttons = scoreTypeButtonGroup.getElements(); buttons.hasMoreElements();) {
                AbstractButton button = buttons.nextElement();
                if (button.isSelected()) {
                    scoreType = button.getText();
                    break;
                }
            }

            if (scoreType.isEmpty()) {
                outputTextArea.setText("Error: Please select a score type (T-Score or Z-Score).");
                return;
            }

            String formattedReport = formatReport(score, scoreType, age, gender, fracture, menopause);
            outputTextArea.setText(formattedReport);
        } catch (NumberFormatException ex) {
            outputTextArea.setText("Invalid input. Please enter numeric values for score and age.");
        }
    }

    private String formatReport(double score, String scoreType, int age, String gender, boolean fracture, boolean menopause) {
        String diagnosis = calculateDEXADiagnosis(score, scoreType, age, gender, fracture);
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        String formattedDate = currentDate.format(formatter);
        String fractureSymbol = fracture ? "+" : "-";

        // Include Menopause only if Gender is Female
        String menopauseStatus = "";
        if (gender.equals("Female")) {
            menopauseStatus = "  Menopause: [" + (menopause ? "Postmenopausal" : "Premenopausal") + "]";
        }

        return "< DEXA >\n" +
                "\t" + diagnosis + " " + scoreType + " [" + score + "]\n" +
                "\tAge: [" + age + "]  Gender: [" + gender + "]" + menopauseStatus + "  Fracture: [" + fractureSymbol + "]\n" +
                "Comment>\n" +
                "# " + diagnosis.split("\\(")[0] + " " + formattedDate;
    }


    private String calculateDEXADiagnosis(double score, String scoreType, int age, String gender, boolean fracture) {
        if (scoreType.equalsIgnoreCase("T-Score")) {
            return calculateDEXADiagnosisT(score, fracture);
        } else if (scoreType.equalsIgnoreCase("Z-Score")) {
            return calculateDEXADiagnosisZ(score);
        } else {
            return "Invalid Score Type";
        }
    }

    private String calculateDEXADiagnosisT(double tScore, boolean fracture) {
        if (tScore <= -2.5 && fracture) {
            return "Severe Osteoporosis (T-score ≤ -2.5 with fragility fracture)";
        } else if (tScore <= -2.5) {
            return "Osteoporosis";
        } else if (tScore < -1.0) {
            return "Osteopenia (Low Bone Mass)";
        } else {
            return "Normal Bone Density";
        }
    }

    private String calculateDEXADiagnosisZ(double zScore) {
        return zScore < -2.0 ? "Low Z-Score (BMD lower than expected for age and sex)" : "Normal Z-Score (BMD within expected range for age and sex)";
    }

    private void clearFields() {
        scoreField.setText("");
        ageField.setText("");
        genderComboBox.setSelectedIndex(0);
        fragilityFractureCheckBox.setSelected(false);
        menopauseCheckBox.setSelected(false);
        scoreTypeButtonGroup.clearSelection();
        outputTextArea.setText("");
    }

    public static void main(String[] args) {
        new EMR_DEXA();
    }
}
