package je.panse.doro.fourgate.thyroid;

import javax.swing.*;

public class EMR_thyroid_PE extends JFrame {
    private JTextField goiterSizeField;
    private JCheckBox[] goiterRuledCheckBoxes;
    private JCheckBox[] noduleDetectionCheckBoxes;
    private JCheckBox[] consistencyCheckBoxes;
    private JCheckBox[] tendernessCheckBoxes;
    private JCheckBox[] bruitCheckBoxes;
    private JCheckBox[] dtrCheckBoxes;
    private JCheckBox[] tedCheckBoxes;
    
    public EMR_thyroid_PE() {
        setTitle("Thyroid Physical Exam");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        
        add(createGoiterSizePanel());
        add(createGoiterRuledPanel());
        add(createNoduleDetectionPanel());
        add(createConsistencyPanel());
        add(createTendernessPanel());
        add(createBruitPanel());
        add(createDTRPanel());
        add(createTEDPanel());
        
        setVisible(true);
    }
    
    private JPanel createGoiterSizePanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("Goiter size"));
        goiterSizeField = new JTextField(10);
        panel.add(goiterSizeField);
        return panel;
    }
    
    private JPanel createGoiterRuledPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Goiter Ruled"));
        
        String[] goiterRuledOptions = {"Goiter ruled out", "Goiter ruled in Diffuse Enlargement",
            "Goiter ruled in Nodular Enlargement", "Single Nodular Goiter", "Multiple Nodular Goiter"};
        goiterRuledCheckBoxes = new JCheckBox[goiterRuledOptions.length];
        
        for (int i = 0; i < goiterRuledOptions.length; i++) {
            goiterRuledCheckBoxes[i] = new JCheckBox(goiterRuledOptions[i]);
            panel.add(goiterRuledCheckBoxes[i]);
        }
        
        return panel;
    }
    
    private JPanel createNoduleDetectionPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Detect any nodules"));
        
        String[] noduleDetectionOptions = {"None", "Single nodule", "Multinodular Goiter"};
        noduleDetectionCheckBoxes = new JCheckBox[noduleDetectionOptions.length];
        
        for (int i = 0; i < noduleDetectionOptions.length; i++) {
            noduleDetectionCheckBoxes[i] = new JCheckBox(noduleDetectionOptions[i]);
            panel.add(noduleDetectionCheckBoxes[i]);
        }
        
        return panel;
    }
    
    private JPanel createConsistencyPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Thyroid gland consistency"));
        
        String[] consistencyOptions = {"Soft", "Soft to Firm", "Firm", "Cobble-stone", "Firm to Hard", "Hard"};
        consistencyCheckBoxes = new JCheckBox[consistencyOptions.length];
        
        for (int i = 0; i < consistencyOptions.length; i++) {
            consistencyCheckBoxes[i] = new JCheckBox(consistencyOptions[i]);
            panel.add(consistencyCheckBoxes[i]);
        }
        
        return panel;
    }
    
    private JPanel createTendernessPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Evaluate the thyroid gland for tenderness"));
        
        String[] tendernessOptions = {"Tender", "Non-tender"};
        tendernessCheckBoxes = new JCheckBox[tendernessOptions.length];
        
        for (int i = 0; i < tendernessOptions.length; i++) {
            tendernessCheckBoxes[i] = new JCheckBox(tendernessOptions[i]);
            panel.add(tendernessCheckBoxes[i]);
        }
        
        return panel;
    }
    
    private JPanel createBruitPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Systolic or continuous Bruit (y/n)"));
        
        String[] bruitOptions = {"Yes", "No"};
        bruitCheckBoxes = new JCheckBox[bruitOptions.length];
        
        for (int i = 0; i < bruitOptions.length; i++) {
            bruitCheckBoxes[i] = new JCheckBox(bruitOptions[i]);
            panel.add(bruitCheckBoxes[i]);
        }
        
        return panel;
    }
    
    private JPanel createDTRPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("DTR deep tendon reflex"));
        
        String[] dtrOptions = {"1+ = present but depressed", "2+ = normal / average", "3+ = increased", "4+ = clonus"};
        dtrCheckBoxes = new JCheckBox[dtrOptions.length];
        
        for (int i = 0; i < dtrOptions.length; i++) {
            dtrCheckBoxes[i] = new JCheckBox(dtrOptions[i]);
            panel.add(dtrCheckBoxes[i]);
        }
        
        return panel;
    }
    
    private JPanel createTEDPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("TED: Thyroid Eye Disease"));
        
        String[] tedOptions = {"Class 0: No signs or symptoms",
            "Class 1: Only signs (limited to upper lid retraction and stare, with or without lid lag)",
            "Class 2: Soft tissue involvement (oedema of conjunctivae and lids, conjunctival injection, etc.)",
            "Class 3: Proptosis",
            "Class 4: Extraocular muscle involvement (usually with diplopia)",
            "Class 5: Corneal involvement (primarily due to lagophthalmos)",
            "Class 6: Sight loss (due to optic nerve involvement)"};
        tedCheckBoxes = new JCheckBox[tedOptions.length];
        
        for (int i = 0; i < tedOptions.length; i++) {
            tedCheckBoxes[i] = new JCheckBox(tedOptions[i]);
            panel.add(tedCheckBoxes[i]);
        }
        
        return panel;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EMR_thyroid_PE());
    }
}
