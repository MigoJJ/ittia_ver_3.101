package je.panse.doro.listner.functionkey;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class FunctionKeyPress extends KeyAdapter {
    private Map<Integer, Consumer<Void>> actionMap;

    public FunctionKeyPress() {
        initializeActionMap();
    }

    private void initializeActionMap() {
        actionMap = new HashMap<>();
        actionMap.put(KeyEvent.VK_F1, this::performF1Action);
        actionMap.put(KeyEvent.VK_F2, this::performF2Action);
        // Continue adding more function key actions as needed
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        // Execute the action if it exists in the map
        if (actionMap.containsKey(keyCode)) {
            actionMap.get(keyCode).accept(null);
        }
    }

    private void performF1Action(Void v) {
        System.out.println("F1 key pressed - Action executed.");
    }

    private void performF2Action(Void v) {
        System.out.println("F2 key pressed - Action executed.");
    }

    // Add more methods for other function keys as needed
}
