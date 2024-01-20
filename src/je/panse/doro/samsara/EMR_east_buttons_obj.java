package je.panse.doro.samsara;
import java.awt.Color;	
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;

import je.panse.doro.fourgate.diabetes.EMR_FU_retinopathy;
import je.panse.doro.fourgate.diabetes.EMR_pph_neuropathy;
import je.panse.doro.samsara.EMR_OBJ_XrayGFS.EMR_ChestPA;
import je.panse.doro.samsara.EMR_OBJ_XrayGFS.EMR_DEXA;
import je.panse.doro.samsara.EMR_OBJ_XrayGFS.EMR_EKG;
import je.panse.doro.samsara.EMR_OBJ_XrayGFS.EMR_endo_CFS;
import je.panse.doro.samsara.EMR_OBJ_XrayGFS.EMR_endo_GFS;
import je.panse.doro.samsara.EMR_OBJ_excute.EMR_BMI_calculator;
import je.panse.doro.samsara.EMR_OBJ_excute.EMR_CBC;
import je.panse.doro.samsara.EMR_OBJ_excute.EMR_HbA1c;
import je.panse.doro.samsara.EMR_OBJ_excute.EMR_LDL;
import je.panse.doro.samsara.EMR_OBJ_excute.EMR_LFT;
import je.panse.doro.samsara.EMR_OBJ_excute.EMR_Lab_enterresult;
import je.panse.doro.samsara.EMR_OBJ_excute.EMR_Lab_positive;
import je.panse.doro.samsara.EMR_OBJ_excute.EMR_LpaApoB;
import je.panse.doro.samsara.EMR_OBJ_excute.EMR_TFT;
import je.panse.doro.samsara.EMR_OBJ_excute.EMR_TFTout;
import je.panse.doro.samsara.EMR_OBJ_excute.EMR_eGFR;
import je.panse.doro.samsara.EMR_OBJ_Vitalsign.Vitalsign;
import je.panse.doro.samsara.EMR_PE.PhysicalExaminationAbdomen;

public class EMR_east_buttons_obj extends JFrame implements ActionListener {

    private ArrayList<JButton> buttons = new ArrayList<>();

    public EMR_east_buttons_obj(String position, String title) {
    	 setUndecorated(true);
        setSize(new Dimension(1850, 30));
        setLayout(new GridLayout(0, 20)); // Set 0 rows and 5 columns
        setBackground(new Color(240, 240, 240));
        setTitle(title);
        setLocation(1460, 0);

        String[] buttonNames = {"BMI", "BP", "HbA1c", "TFT", "TFTout",
        		"LDL", "LFT", "CBC", "eGFR", "Lp(a)",
        		"Etc.", "ChestPA", "EKG", "GFS", "CFS",
        		"DEXA","Abdomen","DM pph","DM reti",""
        };

        // Create buttons and add to array list
        for (String buttonName : buttonNames) {
            JButton button = new JButton(buttonName);
            button.setFont(new Font("Arial", Font.PLAIN, 14));
            button.addActionListener(this); // Add this as the action listener
            buttons.add(button);
            add(button);
        }

        // Change background color of buttons gradually
        for (int i = 0; i < buttons.size(); i++) {
            Color color = new Color(135, 206, 235); // Create a sky blue color object
            buttons.get(i).setBackground(color);
        }

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        pack();
        setVisible(true);
    }

    public static void main(String text) {
        new EMR_east_buttons_obj("east", "EMR Object Buttons");
    }

    public void actionPerformed(ActionEvent e) {
        Map<String, Runnable> buttonActions = createButtonActionsMap();

        // Get the button text from the event source
        String buttonText = ((JButton) e.getSource()).getText();

        // Get the corresponding action based on the button text and execute it
        Runnable action = buttonActions.get(buttonText);
        if (action != null) {
            action.run();
        }
    }

    private Map<String, Runnable> createButtonActionsMap() {
        Map<String, Runnable> buttonActions = new HashMap<>();

        buttonActions.put("BMI", () -> EMR_BMI_calculator.main(new String[0]));
        buttonActions.put("BP", () ->  Vitalsign.main(new String[0]));
        buttonActions.put("HbA1c", () -> EMR_HbA1c.main(new String[0]));
        buttonActions.put("LDL", () -> EMR_LDL.main(new String[0]));
        buttonActions.put("LFT", () -> EMR_LFT.main(new String[0]));
        buttonActions.put("eGFR", () -> EMR_eGFR.main(new String[0]));
        buttonActions.put("Lp(a)", () -> EMR_LpaApoB.main(new String[0]));
        buttonActions.put("TFT", () -> EMR_TFT.main(new String[0]));
        buttonActions.put("TFTout", () -> EMR_TFTout.main(new String[0]));
        buttonActions.put("CBC", () -> EMR_CBC.main(new String[0]));
        buttonActions.put("Etc.", () -> {
            EMR_Lab_enterresult.main(new String[0]);
            EMR_Lab_positive.main(new String[0]);
        });
        buttonActions.put("GFS", () -> EMR_endo_GFS.main(new String[0]));
        buttonActions.put("CFS", () -> EMR_endo_CFS.main(new String[0]));
        buttonActions.put("ChestPA", () -> EMR_ChestPA.main(new String[0]));
        buttonActions.put("EKG", () -> EMR_EKG.main(new String[0]));
        buttonActions.put("DEXA", () -> EMR_DEXA.main(new String[0]));
        buttonActions.put("Abdomen", () -> PhysicalExaminationAbdomen.main(new String[0]));
        buttonActions.put("DM pph", () -> EMR_pph_neuropathy.main(new String[0]));
        buttonActions.put("DM reti", () -> EMR_FU_retinopathy.main(new String[0]));

        
        return buttonActions;
    }

    
}