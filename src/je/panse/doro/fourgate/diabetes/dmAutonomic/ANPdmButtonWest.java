package je.panse.doro.fourgate.diabetes.dmAutonomic;

import java.awt.Component;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableModel;

import je.panse.doro.GDSEMR_frame;

public class ANPdmButtonWest {

    public static Component createButton(String name, JTextArea textArea, JTable table) {
        JButton button = new JButton(name);
        // Attach an action listener that performs actions based on the button name.
        button.addActionListener(e -> performAction(name, textArea, table.getModel(), ANPdm.frame));
        return button;
    }

    private static void performAction(String buttonName, JTextArea textArea, TableModel model, JFrame frame) {
        switch (buttonName) {
            case "Save":
                saveAction(textArea);
                break;
            case "Clear":
                clearAction(textArea, model);
                break;
            case "Copy":
                copyAction(textArea);
                break;
            case "Exit":
                exitAction(textArea, model, frame);
                break;
            case "SelectAll":
                selectAllAction(model);
                break;
            case "ClearAll":
                clearAllAction(model);
                break;
            default:
                System.out.println("Unknown button clicked: " + buttonName);
        }
    }

    // Implementation of the save action.
    private static void saveAction(JTextArea textArea) {
        System.out.println("Save action performed");
        GDSEMR_frame.setTextAreaText(1, textArea.getText());
    }

    // Clears the JTextArea and unchecks all checkboxes in the table model.
    private static void clearAction(JTextArea textArea, TableModel model) {
        textArea.setText("\n  < Autonomic Neuroapthy >\n");
        updateCheckboxes(model, false); // Uncheck all checkboxes
    }

    // Implementation of the copy action.
    private static void copyAction(JTextArea textArea) {
        System.out.println("Copy action performed");
        String text = textArea.getText(); // Get the text from the JTextArea
        StringSelection stringSelection = new StringSelection(text);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null); // Set the text to the system clipboard
    }

    // Exits the application by disposing of the frame.
    private static void exitAction(JTextArea textArea, TableModel model, JFrame frame) {
        clearAction(textArea, model); // Optionally clear data before exiting
//        System.exit(0);
        frame.dispose();
    }

    // Implementation of the select all action, specifically targeting column 1.
    private static void selectAllAction(TableModel model) {
        // Explicitly state the column to be affected for clarity.
        updateCheckboxesInColumn(model, true, 2); // Check all checkboxes in column 1 only
    }

    // Clears all checkboxes, regardless of the column.
    private static void clearAllAction(TableModel model) {
        updateCheckboxes(model, false); // Uncheck all checkboxes in the model
    }

    // Method to update checkboxes across all columns.
    private static void updateCheckboxes(TableModel model, boolean state) {
        if (model != null) {
            for (int i = 0; i < model.getRowCount(); i++) {
                for (int j = 0; j < model.getColumnCount(); j++) {
                    if (model.getColumnClass(j) == Boolean.class) {
                        model.setValueAt(state, i, j);
                    }
                }
            }
        }
    }

    // Method to update checkboxes in a specific column.
    private static void updateCheckboxesInColumn(TableModel model, boolean state, int targetColumn) {
        if (model != null && targetColumn >= 0 && targetColumn < model.getColumnCount()) {
            for (int i = 0; i < model.getRowCount(); i++) {
                // Check if the target column can hold Boolean values, then update accordingly.
                if (model.getColumnClass(targetColumn) == Boolean.class) {
                    model.setValueAt(state, i, targetColumn);
                  }
              }
          }
    }
}