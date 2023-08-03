package je.panse.doro.samsara.EMR_OBJ_excute;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import je.panse.doro.GDSEMR_frame;

public class EMR_HbA1c extends JFrame {
    private JTextArea textArea = new JTextArea(4, 20);
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private String fpspp2timevalue = "FBS";
    private double hba1cvalue = 0.0;
    private Timer timer = new Timer();

    public EMR_HbA1c() {
        setTitle("EMR Glucose HbA1c");
        setSize(330, 250);
        setLocation(1460, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setupTextArea();
        setupTextFields();
        setupButtons();

        setVisible(true);
    }

    // Setup the text area and add a mouse listener for double-click events
    private void setupTextArea() {
        textArea.setEditable(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.NORTH);

        textArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    textArea.append("  at home SMBG");
                    textField1.requestFocus();
                }
            }
        });
    }

    // Setup the text fields for user input
    private void setupTextFields() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        textField1 = createTextField(" FBS / PP2 time", 2);
        textField1.addActionListener(e -> {
            textArea.setText("");
            fbspp2time(textField1.getText());
            textField2.requestFocus();
        });
        textField1.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                clear();
            }
        });

        textField2 = createTextField(" Glucose  mg/dL ", 2);
        textField2.addActionListener(e -> {
            fbspp2(textField2.getText());
            textField3.requestFocus();
        });

        textField3 = createTextField("HbA1c  %  ", 2);
        textField3.addActionListener(e -> {
            try {
                hba1cvalue = Double.parseDouble(textField3.getText()); 
                hba1c(hba1cvalue);
                save();
            } catch (NumberFormatException exception) {
                save();
                clear();
            }
        });

        panel.add(textField1);
        panel.add(textField2);
        panel.add(textField3);

        add(panel, BorderLayout.CENTER);
    }

    // Create a new text field with the specified text and number of rows
    private JTextField createTextField(String text, int rows) {
        JTextField textField = new JTextField(text, rows);
        textField.setHorizontalAlignment(JTextField.CENTER);
        return textField;
    }

    // Setup the buttons for the interface
    private void setupButtons() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> clear());

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> save());

        JButton saveQuitButton = new JButton("Save and Quit");
        saveQuitButton.addActionListener(e -> {
            save();
            dispose();
        });

        buttonPanel.add(clearButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(saveQuitButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Clear the text fields
    private void clear() {
        textField1.setText("");
        textField2.setText("");
        textField3.setText("");
    }

    // Save the user's inputs (to be implemented)
    private void save() {
        GDSEMR_frame.setTextAreaText(5, textArea.getText());
    }

    // Update the time value
    private void fbspp2time(String labtime) {
        if (!labtime.equals("0")) {
            fpspp2timevalue = "PP" + labtime;
        }
        textArea.append("\n"+ fpspp2timevalue);
    }

    // Update the fbspp2 value
    private void fbspp2(String labtime) {
        textArea.append(" [ " + labtime + " ]mg/dL");
    }

    // Update the hba1c value and calculate related metrics
    private void hba1c(double labhba1c) {
        if (labhba1c != 0.0) {
            textArea.append("    HbA1c [ " + labhba1c + " ]%\n");
            hba1cCalc(labhba1c);
            textField1.requestFocus();
        } else {
        		textField1.requestFocus();
        		save();
        }
    }

    // Calculate the HbA1c value and update the text area
    private void hba1cCalc(double hba1c_perc) {
        double ifcc_hba1c_mmolmol = (hba1c_perc - 2.15) * 10.929;
        double eag_mgdl = (28.7 * hba1c_perc) - 46.7;
        double eag_mmoll = eag_mgdl / 18.01559;

        String hba1cP = String.format("\n\tIFCC HbA1c: %.0f mmol/mol\n\teAG: %.0f mg/dL\n\teAG: %.1f mmol/l\n",
                ifcc_hba1c_mmolmol, eag_mgdl, eag_mmoll);
        textArea.append(hba1cP);
        getGlucoseControlStatus(hba1c_perc);
    }
    
    public static void getGlucoseControlStatus(double HbA1c) {
        String status;
        if (HbA1c > 9.0) {
            status = "Very poor";
        } else if (HbA1c >= 8.5 && HbA1c <= 9.0) {
            status = "Poor";
        } else if (HbA1c >= 7.5 && HbA1c < 8.5) {
            status = "Fair";
        } else if (HbA1c >= 6.5 && HbA1c < 7.5) {
            status = "Good";
        } else {
            status = "Excellent";
        }

        String message = String.format("\n...now [ %s ] controlled glucose status", status);
        GDSEMR_frame.setTextAreaText(8, message);
        System.out.println(message);
    }


    public static void main(String[] args) {
        new EMR_HbA1c();
    }
}
