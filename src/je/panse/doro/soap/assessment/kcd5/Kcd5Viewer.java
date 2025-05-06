package je.panse.doro.soap.assessment.kcd5;

import javax.swing.*;	
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import je.panse.doro.entry.EntryDir;
// Import the class containing the static method (adjust package if necessary)
import je.panse.doro.GDSEMR_frame; // ***** ADD THIS IMPORT *****
import java.awt.*;
import java.awt.event.MouseAdapter; // ***** ADD THIS IMPORT *****
import java.awt.event.MouseEvent;   // ***** ADD THIS IMPORT *****
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class Kcd5Viewer extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField filterTextField;
    private TableRowSorter<DefaultTableModel> sorter;
    private static final String CSV_FILE_PATH = EntryDir.homeDir + "/soap/assessment/kcd5/KCD5.csv";
    private static final Font KOREAN_FONT = new Font("NanumGothic", Font.PLAIN, 14);
    private static final String ETITLE_COLOR_HEX = "#00008B"; // Deep Blue hex code

    public Kcd5Viewer() {
        super("KCD5 Code Viewer");
        initComponents();
        loadData(CSV_FILE_PATH);
        setupFiltering();

        // Make the viewer dispose on close instead of exiting the whole application
        // This is often better if it's a secondary window.
        // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // ***** CHANGED THIS LINE *****
        setSize(800, 600);
        setLocationRelativeTo(null); // Center on screen
    }

    private void initComponents() {
        tableModel = new DefaultTableModel(new String[]{"Code"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Cells are not editable
            }
        };
        table = new JTable(tableModel);
        sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);
        table.setFillsViewportHeight(true);
        table.setRowHeight(20);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Allow only single row selection

        // ***** START: ADD MOUSE LISTENER FOR TABLE CLICK *****
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Respond to single clicks (can change to e.getClickCount() == 2 for double-click)
                if (e.getClickCount() == 1) {
                    int viewRow = table.rowAtPoint(e.getPoint()); // Get row index in the view
                    if (viewRow >= 0) { // Check if the click was on a valid row
                        // Convert view row index to model row index (important for sorting/filtering)
                        int modelRow = table.convertRowIndexToModel(viewRow);
                        Object rowData = tableModel.getValueAt(modelRow, 0); // Get data from the model

                        if (rowData instanceof String) {
                            String combinedData = (String) rowData;
                            try {
                                // Extract the Code, Ktitle, and Etitle
                                String[] parts = combinedData.split(",", 3); // split into 3 parts
                                if (parts.length >= 3) {
                                    String code = parts[0].trim();
                                    String ktitle = parts[1].trim();
                                    String etitle = parts[2].trim();

                                    if (!code.isEmpty()) {
                                        // Format the string more clearly
                                        String textToAppend = String.format("\n # %s - %s\n    (%s)", code, ktitle, etitle);

                                        // Call the static method from GDSEMR_frame class
                                        GDSEMR_frame.setTextAreaText(7, "\n"+ textToAppend);
                                    } else {
                                        System.err.println("Extracted code is empty. Row data: [" + combinedData + "]");
                                    }
                                } else {
                                    System.err.println("Could not extract enough parts (code, ktitle, etitle). Row data: [" + combinedData + "]");
                                }
                            } catch (Exception ex) {
                                System.err.println("Error processing table click or appending text: " + ex.getMessage());
                                ex.printStackTrace();
                                showError("Action Error", "Could not process the selection or update the text area.\nError: " + ex.getMessage());
                            }
                        }

                    }
                }
            }
        });
        // ***** END: ADD MOUSE LISTENER FOR TABLE CLICK *****

        // Set the custom renderer for the column
        table.getColumnModel().getColumn(0).setCellRenderer(new KcdCellRenderer());
        table.getColumnModel().getColumn(0).setPreferredWidth(600);

        JScrollPane scrollPane = new JScrollPane(table);

        filterTextField = new JTextField(30);
        filterTextField.setFont(KOREAN_FONT);
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel filterLabel = new JLabel("Filter:");
        filterLabel.setFont(KOREAN_FONT);
        topPanel.add(filterLabel);
        topPanel.add(filterTextField);

        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    // --- loadData, processDataRow, validateHeader, validateDataRow remain the same ---
    private void loadData(String filePath) {
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            showError("File Not Found", "KCD5 file not found: " + filePath);
            return;
        }

        Charset[] charsets = {Charset.forName("UTF-8"), Charset.forName("CP949"), Charset.forName("EUC-KR")};
        BufferedReader reader = null;
        Charset selectedCharset = null;

        for (Charset charset : charsets) {
            try {
                reader = Files.newBufferedReader(path, charset);
                reader.mark(1024); // Mark the beginning
                 // Try reading a line to check encoding, might throw MalformedInputException
                String testLine = reader.readLine();
                if (testLine == null) { // Handle empty file case
                    reader.close();
                    continue;
                }
                reader.reset(); // Go back to the beginning
                selectedCharset = charset;
                System.out.println("Using encoding: " + charset.name());
                break;
            } catch (IOException e) {
                System.err.println("Failed to read or reset with " + charset.name() + ": " + e.getMessage());
                 if (reader != null) {
                    try { reader.close(); } catch (IOException ioex) { /* ignore */ }
                }
                reader = null; // Ensure reader is null if this charset failed
            } catch (Exception e) { // Catch other potential errors during read/reset
                 System.err.println("Error testing encoding " + charset.name() + ": " + e.getMessage());
                  if (reader != null) {
                    try { reader.close(); } catch (IOException ioex) { /* ignore */ }
                }
                 reader = null;
            }
        }


        if (reader == null) {
            showError("Error Reading File", "Could not read KCD5 file with UTF-8, CP949, or EUC-KR encoding, or file is empty/corrupt.");
            return;
        }

        try {
            String line;
            boolean isFirstLine = true;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (isFirstLine) {
                    isFirstLine = false;
                    System.out.println("Header line: [" + line + "]");
                    if (!validateHeader(line)) {
                         // Allow processing if it looks like data, even if header is missing/wrong
                        if (line.matches("^[A-Z][0-9]{2}(\\.[0-9]*)?.*")) {
                            System.out.println("Treating first line as data (header validation failed or missing).");
                            processDataRow(line, lineNumber);
                        } else {
                            // Show error only if it doesn't look like data either
                            showError("Invalid File Format", "File header does not match expected format (Code,Ktitle,Etitle) and first line doesn't appear to be data. Header found: [" + line + "]");
                            reader.close();
                            return;
                        }
                    }
                    // If header was valid, continue to next line
                    continue;
                }
                 if (line.trim().isEmpty()) {
                    continue; // Skip empty lines
                }
                processDataRow(line, lineNumber);
            }
        } catch (IOException e) {
            showError("Error Reading File", "Could not read KCD5 data: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

     private void processDataRow(String line, int lineNumber) {
        try {
            // Split by comma, limit parts to handle commas in titles correctly
            // We assume the structure Code,Ktitle,Etitle
            // Split into max 3 parts: Code, Ktitle, rest (Etitle)
            String[] data = line.split(",", 3);

            if (!validateDataRow(data)) {
                System.err.println("Skipping invalid row at line " + lineNumber + ": " + line + " (Expected at least a non-empty code)");
                return;
            }

            String code = data[0].trim();
            // If split produced fewer than 3 parts, assign empty strings
            String kTitle = data.length > 1 ? data[1].trim() : "";
            String eTitle = data.length > 2 ? data[2].trim() : "";

            // Store the combined *plain text* data in the model
            // The renderer will handle the formatting
            String combined = code + ", " + kTitle + ", " + eTitle;
            tableModel.addRow(new Object[]{combined});

        } catch (Exception e) {
             // Catch broader exceptions during parsing/processing
            System.err.println("Error processing line " + lineNumber + ": [" + line + "]. Error: " + e.getMessage());
            e.printStackTrace(); // Print stack trace for detailed debugging
        }
    }

    private boolean validateHeader(String header) {
        if (header == null || header.trim().isEmpty()) {
            return false;
        }
        // Be slightly flexible with header names, ignore case
        String[] headerParts = header.split(",", -1); // Split, keeping trailing empty strings
        return headerParts.length >= 1 && headerParts[0].trim().equalsIgnoreCase("Code");
    }

    private boolean validateDataRow(String[] data) {
         // Basic validation: Must have at least one part (the code) and it shouldn't be empty
        return data != null && data.length >= 1 && data[0] != null && !data[0].trim().isEmpty();
    }

    // --- setupFiltering, filterTable remain the same ---
    private void setupFiltering() {
        filterTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { filterTable(); }
            @Override public void removeUpdate(DocumentEvent e) { filterTable(); }
            @Override public void changedUpdate(DocumentEvent e) { filterTable(); }
        });
    }

     private void filterTable() {
        String text = filterTextField.getText().trim();
         // The RowFilter operates on the *model data* (plain text stored),
         // not the formatted HTML shown by the renderer.

        if (text.isEmpty()) {
            sorter.setRowFilter(null); // No filter if input is empty
            return;
        }

        // Split the user's input into individual words based on spaces
        String[] words = text.split("\\s+");
        try {
            List<RowFilter<Object, Object>> filters = new ArrayList<>();

            // Create a filter for each word entered by the user
            for (String word : words) {
                if (!word.isEmpty()) {
                    // (?i) : Makes the search case-insensitive.
                    // Pattern.quote(word) : Treats the user's input word as a literal sequence of characters.
                    // RowFilter.regexFilter(...) : Creates a filter that checks if the specified regex pattern
                    //                              (the literal word) exists *ANYWHERE* within the cell's data.
                     filters.add(RowFilter.regexFilter("(?i)" + Pattern.quote(word)));
                }
            }

            if (!filters.isEmpty()) {
                 // RowFilter.andFilter(filters) : Combines all the individual word filters.
                 //                              A row will only be shown if it matches *ALL* the word filters.
                 sorter.setRowFilter(RowFilter.andFilter(filters));
            } else {
                 // If the input contained only spaces, clear the filter
                sorter.setRowFilter(null);
            }
        } catch (PatternSyntaxException e) {
            System.err.println("Invalid regex pattern: " + e.getMessage());
            sorter.setRowFilter(null); // Don't apply filter if regex is somehow invalid
        }
    }


    // --- showError, KcdCellRenderer remain the same ---
    private void showError(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
    }

    private static class KcdCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                     boolean isSelected, boolean hasFocus,
                                                     int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            if (value instanceof String) {
                String combinedText = (String) value;
                int firstComma = combinedText.indexOf(',');
                int lastComma = combinedText.lastIndexOf(',');

                String codePart = "";
                String kTitlePart = "";
                String eTitlePart = "";

                if (firstComma != -1 && lastComma != -1 && firstComma != lastComma) {
                     codePart = combinedText.substring(0, firstComma).trim();
                    kTitlePart = combinedText.substring(firstComma + 1, lastComma).trim();
                    eTitlePart = combinedText.substring(lastComma + 1).trim();
                } else if (firstComma != -1) {
                    codePart = combinedText.substring(0, firstComma).trim();
                    kTitlePart = combinedText.substring(firstComma + 1).trim();
                 } else {
                    codePart = combinedText.trim();
                 }

                StringBuilder htmlBuilder = new StringBuilder("<html><body style='font-family: NanumGothic; font-size: 10pt;'>");
                htmlBuilder.append(escapeHtml(codePart));
                htmlBuilder.append(", ");
                htmlBuilder.append(escapeHtml(kTitlePart));
                htmlBuilder.append(", ");
                htmlBuilder.append("<b><i><font color=\"").append(ETITLE_COLOR_HEX).append("\">");
                htmlBuilder.append(escapeHtml(eTitlePart));
                htmlBuilder.append("</font></i></b>");
                htmlBuilder.append("</body></html>");

                setText(htmlBuilder.toString());
            } else {
                setText(value != null ? value.toString() : "");
            }
            setFont(KOREAN_FONT);
            return c;
        }

        private String escapeHtml(String text) {
             if (text == null) return "";
             return text.replace("&", "&amp;")
                        .replace("<", "&lt;")
                        .replace(">", "&gt;");
        }
    }

    // --- main method remains the same ---
     public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Kcd5Viewer viewer = new Kcd5Viewer();
            viewer.setVisible(true);
        });
    }
}