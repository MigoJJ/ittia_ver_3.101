package je.panse.doro.soap.assessment.icd_10;

import javax.swing.*;
import java.awt.*;

public class ButtonPanel {
    private JPanel panel;
    private JButton clearButton, findButton, editButton, addButton, deleteButton, saveButton, quitButton;

    public ButtonPanel() {
        panel = new JPanel(new FlowLayout());
        clearButton = new JButton("Clear");
        findButton = new JButton("Find");
        editButton = new JButton("Edit");
        addButton = new JButton("Add");
        deleteButton = new JButton("Delete");
        saveButton = new JButton("Save");
        quitButton = new JButton("Quit");

        panel.add(clearButton);
        panel.add(findButton);
        panel.add(editButton);
        panel.add(addButton);
        panel.add(deleteButton);
        panel.add(saveButton);
        panel.add(quitButton);
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

    public JButton getEditButton() {
        return editButton;
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
}