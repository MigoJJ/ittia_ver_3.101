package je.panse.doro.soap.assessment;

import java.awt.*;
import javax.swing.*;

import je.panse.doro.GDSEMR_frame;

import java.awt.event.*;

public class AssessmentSupport {
    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 600;
    
    private JTextArea textArea;
    private JPanel centerPanel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AssessmentSupport::new);
    }

    public AssessmentSupport() {
        JFrame frame = createMainFrame();
        frame.add(createTextArea(), BorderLayout.NORTH);
        frame.add(createComboBoxPanel(), BorderLayout.CENTER);
        frame.add(createButtonPanel(), BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private JFrame createMainFrame() {
        JFrame frame = new JFrame("Assessment Support");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        return frame;
    }

    private JScrollPane createTextArea() {
        textArea = new JTextArea(5, 40);
        return new JScrollPane(textArea);
    }

    private JPanel createComboBoxPanel() {
        centerPanel = new JPanel(new GridLayout(10, 1));
        String[][] comboBoxOptions = getComboBoxOptions();

        for (String[] comboBoxOption : comboBoxOptions) {
            JComboBox<String> comboBox = new JComboBox<>(comboBoxOption);
            comboBox.addItemListener(e -> {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    textArea.append(e.getItem().toString() + "\n");
                }
            });
            centerPanel.add(comboBox);
        }

        // Fill with generic options if less than 10 arrays
        for (int i = comboBoxOptions.length; i < 10; i++) {
            JComboBox<String> comboBox = new JComboBox<>(new String[]{"Option 1", "Option 2", "Option 3"});
            centerPanel.add(comboBox);
        }
        return centerPanel;
    }

    private JPanel createButtonPanel() {
        JPanel southPanel = new JPanel();

        JButton clearButton = new JButton("Clear");
	        clearButton.addActionListener(e -> {
	            for (Component innerComp : centerPanel.getComponents()) {
	                if (innerComp instanceof JComboBox) {
	                    ((JComboBox<?>) innerComp).setSelectedIndex(0);
	                }
	            }
	        textArea.setText("");
	    });

        JButton saveButton = new JButton("Save");
        	saveButton.addActionListener(e -> {
        		String assess = textArea.getText();
        		GDSEMR_frame.setTextAreaText(7, "\n");
        		GDSEMR_frame.setTextAreaText(7, assess);
        });

        JButton quitButton = new JButton("Quit");
	        quitButton.addActionListener(e -> {
	            JFrame frame = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, (Component) e.getSource());
	            frame.dispose();
        });

        southPanel.add(clearButton);
        southPanel.add(saveButton);
        southPanel.add(quitButton);

        return southPanel;
    }

    private String[][] getComboBoxOptions() {
        return new String[][]  {
        			 {
							 "  # diabetes mellitus",
							 "  # Prediabetes",
							 "  # Gestational Diabetes Mellitus",
							 "  # Diabetes mellitus F/U",
							 
							 "  # DM without complications",
							 "  # DM with Retinopathy",
							 "  # DM with Retinopathy : Non-proliferative diabetic retinopathy",
							 "  # DM with Retinopathy : Proliferative diabetic retinopathy",
							 "  # DM with Retinopathy : Macular edema",
							 "  # DM with Nephropathy",
							 "  # DM with Nephropathy with microalbuminuria",
							 "  # DM with Nephropathy with CRF",
							 "  # DM with Peripheral Neuropathy",
							 "  # DM with Autonomic Neuropathy",
        	            },
        	            {
        	            	"  # Hyperthyroidism",
        	            	"  # Hyperthyroidism : Graves' disease",
        	            	"  # Hyperthyroidism F/U",
        	            	"  # Hyperthyroidism with Pregnancy [ ] weeks F/U",
        	            	"  # Hypothyroidism",
        	            	"  # Hypothyroidism : Hashimoto's thyroiditis",
        	            	"  # Hypothyroidism F/U",
        	            	"  # Hypothyroidism with Pregnancy [ ] weeks F/U",
        	            	
        	            	"  # Thyroid nodule",
        	            	"  # Thyroid cyst",
        	            	"  # Simple Goiter",
        	            	"  # Subacute Thyroiditis",
        	            	"  # c/w Chronic Thyroiditis on USG",
        	            	"  # Non-Thyroidal Illness F/U",
        	            	
        	            	"  # Papillary Thyroid Cancer OP(+) with tHypothyroidism",
        	            	"  # Papillary Thyroid Cancer OP(+) RAI Tx(+) withypothyroidism",
        	            },
        	            {
        	            	"  # GERD",
        	            	"  # Chronic Atrophic Gastritis",
        	            	"  # Chronic Superficial Gastritis",
        	            	"  # r/o Erosive Gastritis",
        	            	"  # r/o Irritable Bowel Syndrome",
        	            	"  # Severe Constipation",
        	            	"  # Colonic Polyp",
        	            	"  # Colonic diverticulum",
        	            	"  # Gastric Polyp",
        	            	"  # Duodenal Polyp",
        	            	"  # Duodenal Ulcer",
        	            	"  # CLO[ + ]",
        	            	
        	            	"  # Gilbert's syndrome",
        	            	"  # s/p Hepatitis A infection",
        	            	"  # HBsAg(+) Carrier",
        	            	"  # Hepatitis C virus (HCV) chronic infection",
        	            	"  # HCV-Ab(Positive) --> PCR(Negative) confirmed",
        	            	"  # Hepatic Hemangioma",
        	            	"  # Hepatic Cyst",
        	            	"  # Hepatic Nodule",
        	            	"  # Hepatic higher echoic nodule",
        	            	"  # Fatty Liver",
        	            	"  # Mild Fatty Liver",
        	            	"  # Moderate Fatty Liver",
        	            	"  # Severe Fatty Liver",
        	            	"  # GB polyp",
        	            	"  # GB stone" , 
        	    			        	            },
        	            {
        	            	"  # s/p Cholecystectomy d/t GB stone" ,         
        	            	"  # s/p Appendectomy",         
        	            	"  # s/p Colon cancer op(+)" ,        
        	            	"  # s/p Gastric cancer cancer op(+)",        
        	            	"  # s/p TAH : Total Abdominal Hysterectomy",        
        	            	"  # s/p TAH without BSO",   
        	            	"  # s/p Mymectomy",
        	            	"  # s/p Prostate cancer operation(+)",        
        	            	"  # s/p Cataract OP(+)",   

        	            	"  # s/p Papillary Thyroid Cancer OP(+) with Hypothyroidism",        
        	            	"  # s/p Papillary Thyroid Cancer OP(+) RAI Tx(+) with Hypothyroidism",   
        	            	"  # s/p Breast Cancer Operation",
        	            	"  # s/p Breast Cancer Operation+ ChemoTx(+)",        
        	            	"  # s/p Breast Cancer Operation + RT(+)",        
        	            	"  # s/p Breast Cancer Operation + ChemoTx(+) + RT(+)" 
        	            },
        	            // You can add more options arrays here if needed
        	        };        
    }
}
