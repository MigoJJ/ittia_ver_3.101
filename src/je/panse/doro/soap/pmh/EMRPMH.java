package je.panse.doro.soap.pmh;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class EMRPMH extends JFrame {
    public EMRPMH() {
        setTitle("EMR PMH");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EMRPMH frame = new EMRPMH();
            frame.setVisible(true);
        });
    }
}
