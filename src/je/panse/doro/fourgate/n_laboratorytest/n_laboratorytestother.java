package je.panse.doro.fourgate.n_laboratorytest;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import je.panse.doro.GDSEMR_frame;

public class n_laboratorytestother extends JFrame {
    private static final int FRAME_WIDTH = 950;
    private static final int FRAME_HEIGHT = 900;
    private static final int MAIN_PANEL_HEIGHT = 250;

    private String lastSelectedTestCode;
    private String lastSelectedTestName;

    private static final String[][] TEST_DATA = {
            {"Blood Type", "B2001, D1502003", "ABO blood type test"},
            {"Blood Type", "B2021, D1512003", "Rh type test"},
            {"Liver Function", "D1820003", "Direct bilirubin test"},
            {"Pancreatic Function", "C3430", "C-peptide test"},
            {"Pancreatic Function", "3410", "Insulin test"},
            {"Hormones", "C3480", "LH test"},
            {"Hormones", "C3500", "FSH test"},
            {"Hormones", "C3260", "E2 test"},
            {"Hormones", "D1640", "Progesterone test"},
            {"Hormones", "D3420005", "GH test"},
            {"Hormones", "C7351", "Prolactin test"},
            {"Electrolytes", "C3796", "Ionized-calcium test"},
            {"Hormones", "Cx232", "Free-testosterone test"},
            {"Hormones", "D3710050", "Free-testosterone test"},
            {"Hormones", "D3502143", "Free-Cortisol test"},
            {"Infectious Diseases", "C4872", "Anti HCV AB test"},
            {"Infectious Diseases", "C4854006", "HBV DNA test"},
            {"Infectious Diseases", "+hp4", "H. Pylori 4-regimen test"},
            {"Infectious Diseases", "052400041", "Prolia"},
            {"Infectious Diseases", "C6095006", "Influenza test"},
            {"Infectious Diseases", "D6620", "COVID-19 test"},
            {"Vaccines", "Td", "Tetanus-diphtheria vaccine"},
            {"Vaccines", "Menactra", "Meningococcal conjugate vaccine"},
            {"Vaccines", "650003220", "Shingrix vaccine"},
            {"Infectious Diseases", "14912", "HIV Ag/Ab (combo) test"},
            {"Infectious Diseases", "C4854006, B1810", "HBV DNA test [ B1810 ]"},
            {"Diseases", "N94.6", "Dysmenorrhea 월경통"},
            {"Diseases", "O224", "Gestational diabetes 임신성 당뇨병"},
            {"Diseases", "FZ811", "Libre 연속혈당측정기"},
            {"Diseases", "D126", "Colonic polyp 대장 용종"},
            {"Diseases", "E041", "Benign thyroid nodule"},
            {"Diseases", "B1810", "HBV infection"},
    };

    public n_laboratorytestother() {
        initializeFrame();
        addComponents();
        setVisible(true);
    }

    private void initializeFrame() {
        setTitle("Laboratory Test Overview");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void addComponents() {
        add(createSouthPanel(), BorderLayout.SOUTH);
        add(createMainPanel(), BorderLayout.CENTER);
    }

    private JPanel createSouthPanel() {
        JPanel southPanel = new JPanel(new FlowLayout());
        JButton executeButton = new JButton("Execute");
        JButton quitButton = new JButton("Quit");

        executeButton.addActionListener(e -> handleExecuteAction());
        quitButton.addActionListener(e -> dispose());

        southPanel.add(executeButton);
        southPanel.add(quitButton);
        return southPanel;
    }

    private JScrollPane createMainPanel() {
        JPanel mainPanel = new JPanel(new GridLayout(0, 2));
        Map<String, List<String[]>> groupedTests = groupTestsByCategory();

        for (Map.Entry<String, List<String[]>> group : groupedTests.entrySet()) {
            mainPanel.add(createCategoryPanel(group.getKey(), group.getValue()));
        }

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setPreferredSize(new Dimension(FRAME_WIDTH, MAIN_PANEL_HEIGHT));
        return scrollPane;
    }

    private Map<String, List<String[]>> groupTestsByCategory() {
        Map<String, List<String[]>> grouped = new LinkedHashMap<>();
        for (String[] entry : TEST_DATA) {
            grouped.computeIfAbsent(entry[0], k -> new ArrayList<>()).add(new String[]{entry[1], entry[2]});
        }
        return grouped;
    }

    private JPanel createCategoryPanel(String category, List<String[]> tests) {
        JPanel categoryPanel = new JPanel(new BorderLayout());
        categoryPanel.setBorder(BorderFactory.createTitledBorder(category));

        String[] columns = {"Test Code", "Test Name"};
        String[][] rows = tests.toArray(new String[0][]);
        JTable table = new JTable(rows, columns);
        configureTable(table);

        categoryPanel.add(new JScrollPane(table), BorderLayout.CENTER);
        return categoryPanel;
    }

    private void configureTable(JTable table) {
        table.setRowHeight(23);
        table.setFont(new Font("Consolas", Font.PLAIN, 12));
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                    int selectedRow = table.getSelectedRow();
                    lastSelectedTestCode = (String) table.getValueAt(selectedRow, 0);
                    lastSelectedTestName = (String) table.getValueAt(selectedRow, 1);
                }
            }
        });
    }

    private void handleExecuteAction() {
        if (lastSelectedTestCode != null) {
            try {
                GDSEMR_frame.setTextAreaText(8, "\n...Laboratory test [ :cd ]\n ..." + lastSelectedTestCode + "  " + lastSelectedTestName);
            } catch (Exception ex) {
                System.err.println("Error setting text in GDSEMR_frame: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "No test item selected!", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new n_laboratorytestother());
    }
}