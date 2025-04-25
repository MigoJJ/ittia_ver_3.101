package je.panse.doro.soap.assessment.kcd5;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer; // Import added
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import je.panse.doro.entry.EntryDir;
import java.awt.*;
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
    // Define the color for Etitle
    private static final String ETITLE_COLOR_HEX = "#00008B"; // Deep Blue hex code

    public Kcd5Viewer() {
        super("KCD5 Code Viewer");
        initComponents();
        loadData(CSV_FILE_PATH);
        setupFiltering();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        tableModel = new DefaultTableModel(new String[]{"Code"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        // table.setFont(KOREAN_FONT); // Font is now set in the renderer
        sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);
        table.setFillsViewportHeight(true);
        table.setRowHeight(20); // Adjust row height if needed due to formatting

        // Set the custom renderer for the column
        table.getColumnModel().getColumn(0).setCellRenderer(new KcdCellRenderer());
        table.getColumnModel().getColumn(0).setPreferredWidth(600);


        JScrollPane scrollPane = new JScrollPane(table);

        filterTextField = new JTextField(30);
        filterTextField.setFont(KOREAN_FONT); // Keep font for input field
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel filterLabel = new JLabel("Filter:");
        filterLabel.setFont(KOREAN_FONT); // Keep font for label
        topPanel.add(filterLabel);
        topPanel.add(filterTextField);

        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

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
            reader.close();
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
        // Original more strict validation:
        // return headerParts.length >= 3 &&
        //        headerParts[0].trim().equalsIgnoreCase("Code") &&
        //        headerParts[1].trim().equalsIgnoreCase("Ktitle") &&
        //        headerParts[2].trim().equalsIgnoreCase("Etitle");
    }

    private boolean validateDataRow(String[] data) {
         // Basic validation: Must have at least one part (the code) and it shouldn't be empty
        return data != null && data.length >= 1 && data[0] != null && !data[0].trim().isEmpty();
    }

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
                    //                         Special regex characters in the input won't cause errors.
                    // RowFilter.regexFilter(...) : Creates a filter that checks if the specified regex pattern
                    //                              (the literal word) exists *ANYWHERE* within the cell's data.
                    //                              This inherently allows partial matches (substring matching).
                    //                              Example: regexFilter("(?i)"+Pattern.quote("dia")) will match "diabetes".
                    filters.add(RowFilter.regexFilter("(?i)" + Pattern.quote(word)));
                }
            }

            if (!filters.isEmpty()) {
                 // RowFilter.andFilter(filters) : Combines all the individual word filters.
                 //                              A row will only be shown if it matches *ALL* the word filters.
                 //                              The order in which the words appear in the cell data doesn't matter,
                 //                              as long as *all* the searched words are present somewhere.
                 //                              Example: Searching "mellitus diabetes" creates filters for "mellitus" and "diabetes".
                 //                                       A row containing "Non-insulin-dependent diabetes mellitus" will match
                 //                                       because both "diabetes" and "mellitus" are present.
                sorter.setRowFilter(RowFilter.andFilter(filters));
            } else {
                 // If the input contained only spaces, clear the filter
                sorter.setRowFilter(null);
            }
        } catch (PatternSyntaxException e) {
            // Should not happen with Pattern.quote, but good practice to keep the catch block
            System.err.println("Invalid regex pattern: " + e.getMessage());
            sorter.setRowFilter(null); // Don't apply filter if regex is somehow invalid
        }
    }


    private void showError(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
    }

    // --- Custom Cell Renderer ---
    private static class KcdCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                     boolean isSelected, boolean hasFocus,
                                                     int row, int column) {
            // Let the default renderer set up background, selection etc.
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            if (value instanceof String) {
                String combinedText = (String) value;
                 // Attempt to parse the combined string back into parts
                 // This assumes the format "Code, Ktitle, Etitle" stored in the model
                 // Find the first and last comma to isolate the parts
                int firstComma = combinedText.indexOf(',');
                int lastComma = combinedText.lastIndexOf(',');

                String codePart = "";
                String kTitlePart = "";
                String eTitlePart = "";

                if (firstComma != -1 && lastComma != -1 && firstComma != lastComma) {
                    // Found two distinct commas, likely Code, Ktitle, Etitle
                    codePart = combinedText.substring(0, firstComma).trim();
                    kTitlePart = combinedText.substring(firstComma + 1, lastComma).trim();
                    eTitlePart = combinedText.substring(lastComma + 1).trim();
                } else if (firstComma != -1) {
                     // Found only one comma, assume Code, Title (either K or E?)
                     // Let's assume it might be Code, Ktitle and Etitle is missing
                    codePart = combinedText.substring(0, firstComma).trim();
                    kTitlePart = combinedText.substring(firstComma + 1).trim();
                    // eTitlePart remains ""
                 } else {
                    // No comma found, assume it's just the code
                    codePart = combinedText.trim();
                    // kTitlePart and eTitlePart remain ""
                }


                // Build the HTML string for display
                StringBuilder htmlBuilder = new StringBuilder("<html><body style='font-family: NanumGothic; font-size: 10pt;'>"); // Use body for better style control
                htmlBuilder.append(escapeHtml(codePart)); // Use plain text for code
                htmlBuilder.append(", ");
                htmlBuilder.append(escapeHtml(kTitlePart)); // Use plain text for Ktitle
                htmlBuilder.append(", ");
                // Apply formatting to Etitle
                htmlBuilder.append("<b><i><font color=\"").append(ETITLE_COLOR_HEX).append("\">");
                htmlBuilder.append(escapeHtml(eTitlePart)); // Apply styles to Etitle
                htmlBuilder.append("</font></i></b>");

                htmlBuilder.append("</body></html>");

                // Set the text of the renderer (which is a JLabel)
                setText(htmlBuilder.toString());

            } else {
                // Handle non-string values if any (optional)
                setText(value != null ? value.toString() : "");
            }
             // Ensure the base font is set if not using HTML or for overall component
             // The HTML style should override this for the content where specified.
            setFont(KOREAN_FONT);

            return c; // Return the configured renderer component
        }

         // Basic HTML escaping to prevent issues if titles contain <, >, &
        private String escapeHtml(String text) {
             if (text == null) return "";
             return text.replace("&", "&amp;")
                        .replace("<", "&lt;")
                        .replace(">", "&gt;");
        }
    }
    // --- End Custom Cell Renderer ---

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Kcd5Viewer viewer = new Kcd5Viewer();
            viewer.setVisible(true);
        });
    }
}