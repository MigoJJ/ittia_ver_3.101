package je.panse.doro.listner;

import java.awt.Component;	
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import je.panse.doro.chartplate.EMR_Write_To_Chartplate;

public class TextAreaChangeListener implements DocumentListener {
    private JTextArea textArea;
    private JTextArea tempOutputArea;
    private boolean updatingTextArea = false;

    public TextAreaChangeListener(JTextArea textArea, JTextArea tempOutputArea) {
        this.textArea = textArea;
        this.tempOutputArea = tempOutputArea;
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        if (!updatingTextArea) {
            updateTempOutputArea();
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
            updateTempOutputArea();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
            updateTempOutputArea();
    }

    public void updateTempOutputArea() {
        String inputText = textArea.getText();
        tempOutputArea.setText(inputText);
        // Append the text from all text areas to the output area
        StringBuilder appendedTextBuilder = new StringBuilder();
        Component[] components = textArea.getParent().getComponents();
        for (int i = 0; i < components.length; i++) {
            if (components[i] instanceof JTextArea) {
                JTextArea textArea = (JTextArea) components[i];
                String text = textArea.getText();
                appendedTextBuilder.append(" ").append(text).append("\n");
            }
        }
        tempOutputArea.setText(appendedTextBuilder.toString());

        // Call the method to append the output text to the chartplate
        EMR_Write_To_Chartplate.textAreaAppend(tempOutputArea);
    }
    
}
