package je.panse.doro.support.sqlite3_manager.icd10.editor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ButtonPanel extends JPanel {
    private JButton loadButton;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton clearButton;
    private JButton findButton;

    public ButtonPanel() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));

        loadButton = new JButton("Load");
        addButton = new JButton("Add");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");
        clearButton = new JButton("Clear");
        findButton = new JButton("Find");

        add(loadButton);
        add(addButton);
        add(updateButton);
        add(deleteButton);
        add(clearButton);
        add(findButton);

        setEditDeleteEnabled(false); // Initially disable Update and Delete
    }

    public void setEditDeleteEnabled(boolean enabled) {
        updateButton.setEnabled(enabled);
        deleteButton.setEnabled(enabled);
    }

    public void addLoadListener(ActionListener listener) {
        loadButton.addActionListener(listener);
    }

    public void addAddListener(ActionListener listener) {
        addButton.addActionListener(listener);
    }

    public void addUpdateListener(ActionListener listener) {
        updateButton.addActionListener(listener);
    }

    public void addDeleteListener(ActionListener listener) {
        deleteButton.addActionListener(listener);
    }

    public void addClearListener(ActionListener listener) {
        clearButton.addActionListener(listener);
    }

    public void addFindListener(ActionListener listener) {
        findButton.addActionListener(listener);
    }
}