package je.panse.doro.soap.assessment.icd_10;

import javax.swing.*;
import java.awt.*;

public class ButtonPanel {
    private JPanel panel;
    private JButton clearButton, findButton, addButton, deleteButton, saveButton, quitButton, appendIttiaButton;

    public ButtonPanel() {
        panel = new JPanel(new GridLayout(1, 7, 5, 5)); // 7 buttons
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        clearButton = new JButton("Clear");
        findButton = new JButton("Find");
        addButton = new JButton("Add");
        deleteButton = new JButton("Delete");
        saveButton = new JButton("Save");
        quitButton = new JButton("Quit");
        appendIttiaButton = new JButton("Append Ittia");

        // Add tooltips
        clearButton.setToolTipText("Clear all input fields");
        findButton.setToolTipText("Search records by text");
        addButton.setToolTipText("Add a new record");
        deleteButton.setToolTipText("Delete the selected record");
        saveButton.setToolTipText("Save changes to the selected record");
        quitButton.setToolTipText("Exit the application");
        appendIttiaButton.setToolTipText("Append data to Ittia (custom action)");

        // Add mnemonic keys
        clearButton.setMnemonic('C');
        findButton.setMnemonic('F');
        addButton.setMnemonic('A');
        deleteButton.setMnemonic('D');
        saveButton.setMnemonic('S');
        quitButton.setMnemonic('Q');
        appendIttiaButton.setMnemonic('I');

        panel.add(clearButton);
        panel.add(findButton);
        panel.add(addButton);
        panel.add(deleteButton);
        panel.add(saveButton);
        panel.add(quitButton);
        panel.add(appendIttiaButton);

        // Initially disable Save and Delete buttons
        saveButton.setEnabled(false);
        deleteButton.setEnabled(false);
    }

    public JPanel getPanel() {
        return panel;
    }

    public JButton getClearButton() {
        return clearButton;
    }

    public JButton getFindButton() {
        return findButton;
    }

    public JButton getAddButton() {
        return addButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public JButton getQuitButton() {
        return quitButton;
    }

    public JButton getAppendIttiaButton() {
        return appendIttiaButton;
    }

    public void setButtonState(boolean rowSelected) {
        saveButton.setEnabled(rowSelected);
        deleteButton.setEnabled(rowSelected);
    }
}