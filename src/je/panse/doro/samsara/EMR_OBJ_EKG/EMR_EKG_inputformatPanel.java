package je.panse.doro.samsara.EMR_OBJ_EKG;

import javax.swing.*;	
import java.awt.*;

public class EMR_EKG_inputformatPanel extends JPanel {

    public EMR_EKG_inputformatPanel() {
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 6, 4, 6);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        int row = 0;

        // Section: Patient Information
        addSection(formPanel, gbc, "🩺 Patient Information", row++);
        addField(formPanel, gbc, "Name:", row++);
        addField(formPanel, gbc, "Date/Time:", row++);
        addField(formPanel, gbc, "Age/Sex:", row++);
        addField(formPanel, gbc, "Clinical Context:", row++);

        // Section: Rhythm
        addSection(formPanel, gbc, "1. Rhythm", row++);
        addCheckGroup(formPanel, gbc, new String[]{"Regular", "Irregular"}, row++);
        addCheckGroup(formPanel, gbc, new String[]{"Atrial firillation", "Atrail Flutter", "ectopy"}, row++);
        addField(formPanel, gbc, "R-R intervals:", row++);

        // Section: Heart Rate
        addSection(formPanel, gbc, "2. Heart Rate (HR)", row++);
        addCheckGroup(formPanel, gbc, new String[]{"1500 Method", "6-second Rule", "Count R-R with ruler"}, row++);
        addField(formPanel, gbc, "Rate (bpm):", row++);

        // Section: P Waves
        addSection(formPanel, gbc, "3. P Waves", row++);
        addCheckGroup(formPanel, gbc, new String[]{"Present before each QRS", "Morphology consistent", "Absent or abnormal"}, row++);

        // Section: PR Interval
        addSection(formPanel, gbc, "4. PR Interval", row++);
        addField(formPanel, gbc, "Measured PR Interval (sec):", row++);
        addCheckGroup(formPanel, gbc, new String[]{"Normal (0.12–0.20 sec)", "Prolonged → Suspect AV Block", "Shortened → Suspect pre-excitation"}, row++);

        // Section: QRS Duration
        addSection(formPanel, gbc, "5. QRS Duration", row++);
        addField(formPanel, gbc, "Measured QRS (sec):", row++);
        addCheckGroup(formPanel, gbc, new String[]{"Normal (< 0.12 sec)", "Prolonged → Consider BBB or ventricular rhythm"}, row++);

        // Section: Ectopic or Early Beats
        addSection(formPanel, gbc, "6. Ectopic or Early Beats", row++);
        addCheckGroup(formPanel, gbc, new String[]{"PACs", "PJCs", "PVCs"}, row++);
        addCheckGroup(formPanel, gbc, new String[]{"Bigeminy", "Trigeminy", "Couplets"}, row++);

        // Section: R Wave Progression
        addSection(formPanel, gbc, "7. R Wave Progression (V1–V6)", row++);
        addCheckGroup(formPanel, gbc, new String[]{"Normal", "Poor R wave progression"}, row++);
        addField(formPanel, gbc, "Transition zone (e.g., V3):", row++);

        // Section: ST Segment
        addSection(formPanel, gbc, "8. ST Segment", row++);
        addCheckGroup(formPanel, gbc, new String[]{"Normal"}, row++);
        addField(formPanel, gbc, "Elevated (leads):", row++);
        addField(formPanel, gbc, "Depressed (leads):", row++);
        addCheckGroup(formPanel, gbc, new String[]{"Concave", "Convex", "Horizontal"}, row++);

        // Section: Q Waves
        addSection(formPanel, gbc, "9. Q Waves", row++);
        addCheckGroup(formPanel, gbc, new String[]{"Normal", "Pathological"}, row++);
        addField(formPanel, gbc, "Leads Affected:", row++);
        // Section: Q Waves
        addSection(formPanel, gbc, "10. QT/QTc Interaval", row++);
        addField(formPanel, gbc, "mSec:", row++);

        // Section: T Waves
        addSection(formPanel, gbc, "11. T Waves", row++);
        addCheckGroup(formPanel, gbc, new String[]{"Upright in most leads", "Inverted", "Peaked", "Biphasic"}, row++);
        addField(formPanel, gbc, "Leads Affected:", row++);

        // Section: U Waves
        addSection(formPanel, gbc, "12. U Waves", row++);
        addCheckGroup(formPanel, gbc, new String[]{"Not visible", "Present", "Prominent in V2–V3"}, row++);
        addCheckGroup(formPanel, gbc, new String[]{"Consider: Hypokalemia", "Bradycardia"}, row++);

        // Section: Signs of Ischemia or Infarction
        addSection(formPanel, gbc, "13. Signs of Ischemia or Infarction", row++);
        addCheckGroup(formPanel, gbc, new String[]{"ST Depression (Ischemia)", "ST Elevation (Infarction)", "Q Waves (Old infarct)"}, row++);
        addCheckGroup(formPanel, gbc, new String[]{"Anterior", "Inferior", "Lateral", "Posterior"}, row++);

        // Section: Final Interpretation / Summary
        addSection(formPanel, gbc, "14. Final Interpretation / Summary", row++);
        addField(formPanel, gbc, "Rhythm:", row++);
        addField(formPanel, gbc, "Rate (bpm):", row++);
        addField(formPanel, gbc, "Axis:", row++);
        addField(formPanel, gbc, "PR:", row++);
        addField(formPanel, gbc, "QRS:", row++);
        addField(formPanel, gbc, "QT/QTc:", row++);
        addField(formPanel, gbc, "Abnormal Findings:", row++);
        addField(formPanel, gbc, "Possible Diagnosis:", row++);
        addField(formPanel, gbc, "Recommendations / Action:", row++);

        // Make the form scrollable
        JScrollPane scrollPane = new JScrollPane(formPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(scrollPane, BorderLayout.CENTER);
    }

    private void addSection(JPanel panel, GridBagConstraints gbc, String section, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        JLabel label = new JLabel(section);
        label.setFont(label.getFont().deriveFont(Font.BOLD, 15f));
        panel.add(label, gbc);
        gbc.gridwidth = 1;
    }

    private void addField(JPanel panel, GridBagConstraints gbc, String label, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel(label), gbc);
        gbc.gridx = 1;
        JTextField field = new JTextField(25);
        field.setHorizontalAlignment(JTextField.LEFT);
        panel.add(field, gbc);
    }

    private void addCheckGroup(JPanel panel, GridBagConstraints gbc, String[] labels, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        JPanel groupPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        for (String label : labels) {
            JCheckBox cb = new JCheckBox(label);
            groupPanel.add(cb);
        }
        panel.add(groupPanel, gbc);
        gbc.gridwidth = 1;
    }

    // For demonstration/testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("12-Lead EKG Interpretation Input Format");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(new EMR_EKG_inputformatPanel());
            frame.setSize(900, 700);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}