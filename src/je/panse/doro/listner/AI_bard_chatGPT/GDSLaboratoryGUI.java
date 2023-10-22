package je.panse.doro.listner.AI_bard_chatGPT;

import java.awt.*;		
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class GDSLaboratoryGUI extends JFrame implements ActionListener {

    private static final JTextArea inputTextArea = new JTextArea(40, 35);
    private static final JTextArea outputTextArea = new JTextArea(40, 35);
    private static String[] centerButtonLabels = {"Modify List", "Modify Lab","Problem List","Modify ..."};
    private static String[] eastButtonLabels = {"Rescue","Copy to Clipboard", "Clear Input", "Clear Output", "Clear All", "Save and Quit"};
    private JButton[] centerButtons;
    private static final String bardorderlab = """
            make table
            if parameter does not exist -> remove the row;
            Parameter Value Unit 
            using value format 
            """;
    private static final String bardorderlist = """
			modify the data using format  like below data sample;
			format sample data starting-------------
			    #1  Neurological
			          - Pituitary nonfunctioning tumor (surgery performed) (2016)
			          - Optic nerve deterioration (2021)
			              > CT and MRI: normal
			              > MRI: CVA[+]
			    #2  disease category
			    #3  disease category
			format sample data finishing----------------
			
			organize and make summary list using format;
			the list sorted by disease category;

            """;
    
    private static final String bardorderpro = """
the dataset finished --------------------------

make problem list and comment;

PMH>	-> Past Medical history;
▣   ->  The Patient has suffered from
□   ->  The Patient has  not suffered from

▲     -> upper value for reference
▼     -> lower value for reference

if the problem list is "None" -> remove;



format will be required ;
indentation and prefix   "    # ."  and  "        -   . ";


problem sample list is;

starting------------------------------

***  Problem List   ***********************

    #1  Cardiovascular
          -  ... (2006-02-17 ~ Present)
      
    #2  Endocrinology
          -  ...   
          -  ...   
          -  ...   

    #2  Hepatic
         -  ...

    #3  Musculoskeletal
         -  ... (surgery performed 2019)

    #4  Substance Use
         -  ...

    #5  Neurological
         -  ... (2023-10)

    #6  Comment
          -  ...   
          -  ...   
          -  ...   
finishing-------------------------------

            """;
    
    public GDSLaboratoryGUI() {
        setupFrame();
        setupTextAreas();
        setupButtons();
        arrangeComponents();
    }

    private void setupFrame() {
        setTitle("GDS Laboratory Data");
        setSize(1200, 900);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Color backgroundColor = new Color(0xffdfba); // convert hex to Color object
        inputTextArea.setBackground(backgroundColor);
        outputTextArea.setBackground(backgroundColor);
        inputTextArea.setBorder(BorderFactory.createRaisedBevelBorder());
        outputTextArea.setBorder(BorderFactory.createRaisedBevelBorder());
    }

    private void setupTextAreas() {
        outputTextArea.setEditable(true);
    }

    public static void appendTextAreas(String value) {
        outputTextArea.append(value);      
    }

    private void setupButtons() {
        centerButtons = new JButton[centerButtonLabels.length];
        for (int i = 0; i < centerButtonLabels.length; i++) {
            centerButtons[i] = createButton(centerButtonLabels[i]);
        }
    }

    private void arrangeComponents() {
        JPanel eastButtonPanel = new JPanel();
        eastButtonPanel.setLayout(new BoxLayout(eastButtonPanel, BoxLayout.Y_AXIS));

        JPanel centerButtonPanel = new JPanel();
        centerButtonPanel.setLayout(new FlowLayout()); // or any other layout you prefer for the center buttons

        JButton[] centerButtons = new JButton[centerButtonLabels.length];

        for (int i = 0; i < centerButtonLabels.length; i++) {
            centerButtons[i] = createButton(centerButtonLabels[i]);
            centerButtonPanel.add(centerButtons[i]);
        }

        JButton[] eastButtons = new JButton[eastButtonLabels.length];

        for (int i = 0; i < eastButtonLabels.length; i++) {
            eastButtons[i] = createButton(eastButtonLabels[i]);
            eastButtonPanel.add(eastButtons[i]);
            if (i != eastButtonLabels.length - 1) { // Avoid adding a strut after the last button
                eastButtonPanel.add(Box.createVerticalStrut(5));
            }
        }

     // Determine the maximum width across all buttons
        int maxWidth = 0;
        for (JButton button : centerButtons) {
            maxWidth = Math.max(maxWidth, button.getPreferredSize().width);
            button.setBorder(BorderFactory.createLoweredBevelBorder());  // 버튼에 대해 Border 설정
        }
        for (JButton button : eastButtons) {
            maxWidth = Math.max(maxWidth, button.getPreferredSize().width);
            button.setBorder(BorderFactory.createLoweredBevelBorder());  // 버튼에 대해 Border 설정
        }

        // Set all buttons to the maximum width and fixed height
        int fixedHeight = 40; // Fixed height in pixels
        for (JButton button : centerButtons) {
            Dimension size = new Dimension(maxWidth, fixedHeight);
            button.setPreferredSize(size);
            button.setMaximumSize(size);
        }
        for (JButton button : eastButtons) {
            Dimension size = new Dimension(maxWidth, fixedHeight);
            button.setPreferredSize(size);
            button.setMaximumSize(size);
        }

        JPanel contentPanel = new JPanel(new GridBagLayout());
        // Adding JTextAreas and other components to the contentPanel
        addComponent(contentPanel, new JLabel("Input Data:"), 0, 0, GridBagConstraints.NORTH);
        addComponent(contentPanel, new JScrollPane(inputTextArea), 1, 0, GridBagConstraints.BOTH);
        addComponent(contentPanel, new JLabel("Output Data:"), 2, 0, GridBagConstraints.NORTH);
        addComponent(contentPanel, new JScrollPane(outputTextArea), 3, 0, GridBagConstraints.BOTH);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.gridx = 0;
        constraints.gridy = 4; // Changed the y coordinate to place the buttons below the JTextAreas
        constraints.gridwidth = 4;
        constraints.anchor = GridBagConstraints.CENTER;
        contentPanel.add(centerButtonPanel, constraints);

        // Using BorderLayout to add both the GridBagLayout and the east buttons
        setLayout(new BorderLayout());
        add(contentPanel, BorderLayout.CENTER);
        add(eastButtonPanel, BorderLayout.EAST);
    }

    private JButton createButton(String label) {
        JButton button = new JButton(label);
        button.addActionListener(this);
        button.setBackground(Color.decode("#ffffba"));
        return button;
    }

    private void addComponent(JPanel panel, Component comp, int x, int y, int fill) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.fill = fill;
        panel.add(comp, constraints);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
        	case "Modify List" -> modifyActionlist();
            case "Modify Lab" -> modifyActionlab();
            case "Problem List" -> modifyActionpro();
            case "Copy to Clipboard" -> copyToClipboardAction();
            case "Clear Input" -> inputTextArea.setText("");
            case "Clear Output" -> outputTextArea.setText("");
            case "Clear All" -> {
                inputTextArea.setText("");
                outputTextArea.setText("");
            }
            case "Save and Quit" -> {
	            inputTextArea.setText("");
	            outputTextArea.setText("");
	            dispose();
            }
        }
    }

    private void modifyActionlab() {
        String textFromInputArea = inputTextArea.getText();
        outputTextArea.append("\n" + bardorderlab);
        outputTextArea.append(""
        		+ "\nthe below contents are data --------------------------\n" 
        		+ textFromInputArea 
        		+ "\nthe dataset finished --------------------------\n"
        		+ "\nmerge parameters like below;\n"
				+ "\nthe row titles ;----------------------\n");

        GDSLaboratoryDataModify.main(textFromInputArea);
        copyToClipboardAction();
    }
    
    private void modifyActionlist() {
        String textFromInputArea = inputTextArea.getText();
        outputTextArea.append(""
        		+ "\nthe below contents are data --------------------------\n" 
        		+ textFromInputArea 
        		+ "\nthe dataset finished --------------------------\n");
        outputTextArea.append("\n" + bardorderlist);
        copyToClipboardAction();
    }

    private void modifyActionpro() {
        String textFromInputArea = inputTextArea.getText();
        outputTextArea.append(""
        		+ "\nthe below contents are data --------------------------\n" 
        		+ textFromInputArea 
        		+ "\n");
        outputTextArea.append("\n" + bardorderpro);
        copyToClipboardAction();
    }
    
    private void copyToClipboardAction() {
        String textToCopy = outputTextArea.getText();
        StringSelection selection = new StringSelection(textToCopy);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GDSLaboratoryGUI gui = new GDSLaboratoryGUI();
            gui.setVisible(true);
        });
    }
}