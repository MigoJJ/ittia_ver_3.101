package je.panse.doro.samsara.comm;


import java.awt.Desktop;
import java.net.URI;

public class OpenOneNotePage {

    public static void main(String[] args) {
        String oneNotePageLink = "paste_the_link_to_your_page_here";
        try {
            Desktop.getDesktop().browse(new URI(oneNotePageLink));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
