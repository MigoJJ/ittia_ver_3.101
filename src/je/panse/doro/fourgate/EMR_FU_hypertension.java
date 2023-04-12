package je.panse.doro.fourgate;

public class EMR_FU_hypertension {
    public static String getString(int i) {
        String[] arr = {"A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9", "A10"};

        String result = "";
        for (int j = 0; j < arr.length; j++) {
            if (i == j + 1) {
                result = arr[j];
                break;
            }
        }
        System.out.println(result);
        return result;
    }
}
