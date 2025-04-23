package je.panse.doro.support.sqlite3_manager.icd10.editor10;

import java.awt.Font;
import javax.swing.UIManager;

public class FontUtil {
    public static void setUIFont(javax.swing.plaf.FontUIResource f) {
        java.util.Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof javax.swing.plaf.FontUIResource)
                UIManager.put(key, f);
        }
    }

    public static void applyKoreanFont() {
        setUIFont(new javax.swing.plaf.FontUIResource("Malgun Gothic", Font.PLAIN, 14));
    }
}
