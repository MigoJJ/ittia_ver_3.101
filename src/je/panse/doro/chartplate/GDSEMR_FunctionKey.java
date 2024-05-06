package je.panse.doro.chartplate;

public class GDSEMR_FunctionKey {

    // Static method to handle function key action
    public static void handleFunctionKeyAction(int textAreaIndex, String functionKeyMessage) {
        // Print the function key action to the console
        System.out.println(functionKeyMessage);

        // Set the text in the specified text area of GDSEMR_frame
        je.panse.doro.GDSEMR_frame.setTextAreaText(textAreaIndex, functionKeyMessage);
    }
}
