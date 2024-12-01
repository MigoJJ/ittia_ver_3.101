package je.panse.doro.samsara.EMR_OBJ_excute;

import java.awt.*;	
import java.awt.event.*;
import javax.swing.*;
import je.panse.doro.GDSEMR_frame;
import je.panse.doro.chartplate.filecontrol.datetime.Date_current;

public class EMR_BMI_calculator extends JFrame implements ActionListener {
    private JLabel[] labels = {
        new JLabel("Height (cm): "),
        new JLabel("Weight (kg): "),
        new JLabel("Waist (cm or inch): ")
    };
    private JTextField[] fields = new JTextField[3];
    private JButton SaveandQuit;

    public EMR_BMI_calculator() {
        setTitle("BMI Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 240);
        setResizable(true);
        positionFrameToTopRight();

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(5, 5, 5, 5);

        for (int i = 0; i < labels.length; i++) {
            constraints.gridx = 0;
            constraints.gridy = i;
            panel.add(labels[i], constraints);

            fields[i] = new JTextField(10);
            fields[i].setPreferredSize(new Dimension(15, 30));
            fields[i].setHorizontalAlignment(SwingConstants.CENTER);
            constraints.gridx = 1;
            panel.add(fields[i], constraints);

            addKeyListenerToField(fields[i], i);
        }

        SaveandQuit = new JButton("Save & Quit");
        SaveandQuit.addActionListener(this);
        constraints.gridx = 1;
        constraints.gridy = labels.length;
        constraints.gridwidth = 2;
        panel.add(SaveandQuit, constraints);

        add(panel);
        setVisible(true);
    }

    private void addKeyListenerToField(JTextField field, int index) {
        field.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (index < fields.length - 1) {
                        fields[index + 1].requestFocusInWindow();
                    } else {
                        calculateAndDisplayBMI();
                    }
                }
            }
        });
    }

    private void calculateAndDisplayBMI() {
        double height = Double.parseDouble(fields[0].getText());
        double weight = Double.parseDouble(fields[1].getText());
        double bmi = weight / (height / 100.0 * height / 100.0);

        String result = getBMICategory(bmi);
        result = String.format("%s : BMI: [ %.2f ]kg/m^2", result, bmi);
        String result1 = "\n" + result + "\nHeight : " + height + " cm   Weight : " + weight + " kg";

        if (!fields[2].getText().isEmpty()) {
            String waist = fields[2].getText();
            
            if (waist.contains("i")) {
                waist = waist.replace("i", "");
                double waistValue = Double.parseDouble(waist);
                waistValue *= 2.54;
                waist = String.format("%.1f", waistValue);
            }
            
            result1 += "   Waist: " + waist + " cm";
        }

        updateTextAreas(result, result1);
        dispose();
        EMR_BMI_calculator.main(null);
    }

    private String getBMICategory(double bmi) {
        if (bmi < 18.5) return "Underweight";
        if (bmi < 25) return "Healthy weight";
        if (bmi < 30) return "Overweight";
        return "Obese";
    }

    private void updateTextAreas(String result, String result1) {
        GDSEMR_frame.setTextAreaText(5, "\n< BMI >\n" + result1);
        String cdate = Date_current.main("m");
        GDSEMR_frame.setTextAreaText(7, "\n# " + result + "    " + cdate);
    }

    private void positionFrameToTopRight() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(screenSize.width - getWidth(), 60);
    }

    public static void main(String[] args) {
        new EMR_BMI_calculator();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == SaveandQuit) {
            dispose();
        }
    }
}