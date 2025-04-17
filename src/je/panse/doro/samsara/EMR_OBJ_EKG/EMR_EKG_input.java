package je.panse.doro.samsara.EMR_OBJ_EKG;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EMR_EKG_input extends JFrame {
    private JTextField prtAxisField, qtqtcField, sv1rv5Field;
    private JCheckBox[] leadCheckboxes;

    public EMR_EKG_input() {
        setTitle("EMR EKG Analysis");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // South Panel with buttons
        JPanel southPanel = new JPanel();
        JButton clearButton = new JButton("Clear All");
        JButton saveButton = new JButton("Save");
        JButton quitButton = new JButton("Quit");
        southPanel.add(clearButton);
        southPanel.add(saveButton);
        southPanel.add(quitButton);

        // East Panel with vertical checkboxes
        JPanel eastPanel = new JPanel(new BorderLayout());
        String[] leads = {
            "I", "II", "III", "aVR", "aVL", "aVF",
            "V1", "V2", "V3", "V4", "V5", "V6",
            "II III aVF", "V4-V6", "aVR V1"
        };
        
        JPanel checkboxPanel = new JPanel();
        checkboxPanel.setLayout(new GridLayout(0, 1)); // Vertical layout
        leadCheckboxes = new JCheckBox[leads.length];
        
        for(int i = 0; i < leads.length; i++) {
            leadCheckboxes[i] = new JCheckBox(leads[i]);
            leadCheckboxes[i].setHorizontalAlignment(SwingConstants.LEFT);
            checkboxPanel.add(leadCheckboxes[i]);
        }
        
        JScrollPane scrollPane = new JScrollPane(checkboxPanel);
        scrollPane.setPreferredSize(new Dimension(150, 0));
        eastPanel.add(scrollPane, BorderLayout.CENTER);

        // West Panel with input fields
        JPanel westPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        addLabelFieldPair(westPanel, gbc, "P-R-T Axis:", prtAxisField = new JTextField(10));
        addLabelFieldPair(westPanel, gbc, "QT / QTc:", qtqtcField = new JTextField(10));
        addLabelFieldPair(westPanel, gbc, "SV1 / RV5 / R+S:", sv1rv5Field = new JTextField(10));

     // Central Panel
        JPanel centralPanel = new JPanel(new BorderLayout());
        centralPanel.setBorder(BorderFactory.createTitledBorder("EKG Interpretation Input"));
        centralPanel.add(new EMR_EKG_inputformatPanel(), BorderLayout.CENTER);

        // Add components to frame
        add(southPanel, BorderLayout.SOUTH);
        add(eastPanel, BorderLayout.EAST);
        add(westPanel, BorderLayout.WEST);
        add(centralPanel, BorderLayout.CENTER);

        // Button actions
        clearButton.addActionListener(e -> clearFields());
        quitButton.addActionListener(e -> System.exit(0));
        saveButton.addActionListener(e -> saveData());
    }

    private void addLabelFieldPair(JPanel panel, GridBagConstraints gbc, String label, JTextField field) {
        panel.add(new JLabel(label), gbc);
        gbc.gridx++;
        panel.add(field, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
    }

    private void clearFields() {
        prtAxisField.setText("");
        qtqtcField.setText("");
        sv1rv5Field.setText("");
        for(JCheckBox cb : leadCheckboxes) {
            cb.setSelected(false);
        }
    }

    private void saveData() {
        // Implement actual save logic here
        StringBuilder sb = new StringBuilder("Selected Leads:\n");
        for(JCheckBox cb : leadCheckboxes) {
            if(cb.isSelected()) {
                sb.append("• ").append(cb.getText()).append("\n");
            }
        }
        sb.append("\nParameters:\n")
          .append("P-R-T Axis: ").append(prtAxisField.getText()).append("\n")
          .append("QT/QTc: ").append(qtqtcField.getText()).append("\n")
          .append("SV1/RV5/R+S: ").append(sv1rv5Field.getText());
        
        JOptionPane.showMessageDialog(this, sb.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EMR_EKG_input emrEkg = new EMR_EKG_input();
            emrEkg.setVisible(true);
        });
    }
}
