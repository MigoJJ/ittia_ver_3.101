package je.panse.doro.listner.functionkey;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class FunctionKeyPress extends KeyAdapter {
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_F1:
                // Action for F1 key
                performF1Action();
                break;
            case KeyEvent.VK_F2:
                // Action for F2 key
                performF2Action();
                break;
            // Add more cases as needed for other function keys
            default:
                break;
        }
    }

    private void performF1Action() {
        // Example action for F1 key
        System.out.println("F1 key pressed - Action executed.");
    }

    private void performF2Action() {
        // Example action for F2 key
        System.out.println("F2 key pressed - Action executed.");
    }
}

