package je.panse.doro.listner.buttons.EMR_B_Excute;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Timer;
import java.util.TimerTask;

import je.panse.doro.entry.EntryDir;

public class EMR_B_FileListFrame extends JFrame {
    private JList<String> fileList;
    private DefaultListModel<String> listModel;
    private static final String DIRECTORY_PATH = EntryDir.homeDir + "/tripikata/rescue/rescuefolder";

    public EMR_B_FileListFrame() {
        setTitle("File List");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        listModel = new DefaultListModel<>();
        fileList = new JList<>(listModel);
        populateFileList();

        fileList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    int index = fileList.locationToIndex(evt.getPoint());
                    String selectedItem = listModel.getElementAt(index);
                    File selectedFile = new File(DIRECTORY_PATH, selectedItem);
                    
                    EditorFrame editorFrame = new EditorFrame(selectedFile);
                    editorFrame.setVisible(true);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(fileList);
        add(scrollPane, BorderLayout.CENTER);

        // Set location to the upper right corner
        setLocationRelativeTo(null);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Point centerPoint = ge.getCenterPoint();
        Rectangle bounds = ge.getMaximumWindowBounds();
        int x = (int) bounds.getMaxX() - getWidth();
        int y = 0;
        setLocation(x, y);

        // Close the frame after 5 seconds
        new Timer().schedule(new TimerTask() {
            public void run() {
                dispose();
            }
        }, 10000);
    }

    private void populateFileList() {
        File dir = new File(DIRECTORY_PATH);
        File[] files = dir.listFiles();

        if (files != null) {
            for (File file : files) {
                listModel.addElement(file.getName());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new EMR_B_FileListFrame().setVisible(true);
        });
    }

    class EditorFrame extends JFrame {
        private JTextArea textArea;

        public EditorFrame(File file) {
            setTitle("Editor: " + file.getName());
            setSize(500, 800);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLayout(new BorderLayout());

            textArea = new JTextArea();
            JScrollPane scrollPane = new JScrollPane(textArea);
            add(scrollPane, BorderLayout.CENTER);

            loadFileContent(file);
            setLocationRelativeTo(null);

            // Close the frame after 15 seconds
            new Timer().schedule(new TimerTask() {
                public void run() {
                    dispose();
                }
            }, 15000);  // 15000 milliseconds = 15 seconds
        }

        private void loadFileContent(File file) {
            try {
                byte[] bytes = Files.readAllBytes(file.toPath());
                String content = new String(bytes, StandardCharsets.UTF_8);
                textArea.setText(content);
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Failed to load file content", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

}
