package je.panse.doro.fourgate;

import java.util.Random;

import je.panse.doro.GDSEMR_frame;

public class EMR_Comments {
    
    public static String getRandomRecommendation(String dcode) {
        String[] recommendationsDM = {
                "Eat a healthy diet. This means eating plenty of fruits, vegetables, and whole grains. It also means limiting sugary drinks, processed foods, and unhealthy fats.",
                "Maintain a healthy weight. If you are overweight or obese, losing even a small amount of weight can help improve your blood sugar control.",
                "Exercise regularly. Aim for at least 30 minutes of moderate-intensity exercise most days of the week.",
                "Take your medication as prescribed. If you are taking medication for diabetes, it is important to take it as prescribed by your doctor.",
                "Monitor your blood sugar regularly. This will help you track your blood sugar levels and make sure they are in a healthy range.",
                "Get regular eye, foot, and dental exams. People with diabetes are at increased risk for certain health problems, so it is important to get regular checkups to catch any problems early."
        };
        
        String[] recommendationsBP = {
        	    "Eat a healthy diet. This means eating plenty of fruits, vegetables, and whole grains. It also means limiting sugary drinks, processed foods, and unhealthy fats.",
        	    "Maintain a healthy weight. If you are overweight or obese, losing even a small amount of weight can help improve your blood pressure.",
        	    "Exercise regularly. Aim for at least 30 minutes of moderate-intensity exercise most days of the week.",
        	    "Limit your sodium intake. The recommended daily sodium intake is less than 2,300 milligrams.",
        	    "Increase your potassium intake. Potassium helps to lower blood pressure. Good sources of potassium include fruits, vegetables, and whole grains.",
        	    "Limit your alcohol intake. Drinking too much alcohol can raise blood pressure.",
        	    "Quit smoking. Smoking can raise blood pressure and increase your risk of heart disease and stroke.",
        	    "Manage stress. Stress can raise blood pressure. Find healthy ways to manage stress, such as exercise, yoga, or meditation."
        };
        
        String[] recommendationsCholesterol = {
        	    "Eat a healthy diet. This means eating plenty of fruits, vegetables, and whole grains. It also means limiting sugary drinks, processed foods, and unhealthy fats.",
        	    "Maintain a healthy weight. If you are overweight or obese, losing even a small amount of weight can help lower your cholesterol levels.",
        	    "Exercise regularly. Aim for at least 30 minutes of moderate-intensity exercise most days of the week.",
        	    "Limit your saturated fat intake. Saturated fat is found in animal products such as meat, dairy, and eggs. The recommended daily intake of saturated fat is less than 16 grams for men and less than 12 grams for women.",
        	    "Limit your trans fat intake. Trans fat is a type of unhealthy fat that is found in processed foods. The recommended daily intake of trans fat is zero grams.",
        	    "Choose lean protein sources. When choosing protein sources, such as meat, poultry, or fish, opt for lean cuts that are low in saturated fat.",
        	    "Eat plenty of fiber. Fiber helps to lower cholesterol levels. Good sources of fiber include fruits, vegetables, and whole grains.",
        	    "Nuts and seeds. Nuts and seeds are a good source of healthy fats and fiber. They can help to lower cholesterol levels.",
        	    "Soy products. Soy products, such as tofu and tempeh, are a good source of protein and fiber. They can also help to lower cholesterol levels.",
        	    "Garlic. Garlic has been shown to lower cholesterol levels. You can add garlic to your diet in a variety of ways, such as cooking with it, eating it raw, or taking garlic supplements."
        };
        
        Random rand = new Random();
        int randomIndexDM = rand.nextInt(recommendationsDM.length);
        int randomIndexBP = rand.nextInt(recommendationsBP.length);
        int randomIndexCholesterol = rand.nextInt(recommendationsCholesterol.length);
        
        if (dcode == "DM") {
        	return recommendationsDM[randomIndexDM]; 
        } else if (dcode == "BP") {
        	return recommendationsBP[randomIndexBP];
        } else if (dcode == "Chol") {
        	return recommendationsCholesterol[randomIndexCholesterol];
        } else {
        	return  "\n";
        }
    }
    
    public static void main(String args) {
        String rec = getRandomRecommendation(args);
        System.out.println(rec);
        GDSEMR_frame.setTextAreaText(9,rec);
    }
}