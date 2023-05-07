package je.panse.doro.soap.ros;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import je.panse.doro.GDSEMR_frame;

public class EMR_ROS_ButtonActions  extends JFrame implements ActionListener {
    private JTable dataTable;
    private JTextArea outputArea;

    public EMR_ROS_ButtonActions(JTable dataTable, JTextArea outputArea) {
        this.dataTable = dataTable;
        this.outputArea = outputArea;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
        case "Clear":
        	outputArea.setText("");
            break;
        // handle other button actions here
        case "Save":
            GDSEMR_frame.setTextAreaText(2, outputArea.getText());
            EMR_ROS.disposemain(null);
            	
//                try {
//                    FileWriter writer = new FileWriter("/home/woonjungkoh/git/ittia_Version_2.1/src/je/panse/doro/tripikata/backup");
//                    for (int i = 0; i < dataTable.getRowCount(); i++) {
//                        for (int j = 0; j < dataTable.getColumnCount(); j++) {
//                            writer.write(dataTable.getValueAt(i, j).toString() + ",");
//                        }
//                        writer.write("\n");
//                    }
//                    writer.close();
//                    outputArea.append("Data saved to file: data.txt\n");
//                } catch (IOException ex) {
//                    outputArea.append("Error saving data to file: " + ex.getMessage() + "\n");
//                }

                break;
            case "Quit":
            	System.out.println(" case quit:");
            	outputArea.setText("");
            	EMR_ROS.disposemain(null);
                break;
        }
    }
    
}
