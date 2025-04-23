package je.panse.doro.support.sqlite3_manager.icd10.editor10;

import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

public class ButtonPanel extends JPanel {

    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton clearButton;
    private JButton loadButton; // Renamed from refresh
    private JButton findButton;

    public ButtonPanel() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Center buttons with gaps

        loadButton = new JButton("Load Data");
        addButton = new JButton("Add New");
        updateButton = new JButton("Update Selected");
        deleteButton = new JButton("Delete Selected");
        findButton = new JButton("Find ICD Code");
        clearButton = new JButton("Clear Fields");

        // Set initial state (Update/Delete disabled until row selected)
        updateButton.setEnabled(false);
        deleteButton.setEnabled(false);

        add(loadButton);
        add(addButton);
        add(updateButton);
        add(deleteButton);
        add(clearButton);
        add(findButton);
    }

    // Methods to add ActionListeners (to be called from the main frame)
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

    public void addFindListener(ActionListener listener) {  // ✅ renamed from addfindListener
        findButton.addActionListener(listener);
    }

    public void addClearListener(ActionListener listener) {
        clearButton.addActionListener(listener);
    }

    // Methods to control button states
    public void setEditDeleteEnabled(boolean enabled) {
        updateButton.setEnabled(enabled);
        deleteButton.setEnabled(enabled);
    }
}