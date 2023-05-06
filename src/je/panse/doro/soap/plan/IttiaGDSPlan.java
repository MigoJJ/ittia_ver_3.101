package je.panse.doro.soap.plan;
import javax.swing.*;	
import java.awt.*;

public class IttiaGDSPlan extends JFrame {

    public IttiaGDSPlan() {
        setTitle("My Frame");
        setSize(1200, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel centerPanel = new JPanel(new GridLayout(1, 3));

        IttiaGDSPlanPanel_1 panel1 = new IttiaGDSPlanPanel_1(this);
        centerPanel.add(panel1);
        IttiaGDSPlanPanel_2 panel2 = new IttiaGDSPlanPanel_2(this);
        centerPanel.add(panel2);
        IttiaGDSPlanPanel_3 panel3 = new IttiaGDSPlanPanel_3(this);
        centerPanel.add(panel3);
        
        JButton clearButton = new JButton("Clear");
        JButton saveButton = new JButton("Save");
        JButton quitButton = new JButton("Quit");

        JPanel southPanel = new JPanel();
        southPanel.add(clearButton);
        southPanel.add(saveButton);
        southPanel.add(quitButton);

        add(centerPanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        IttiaGDSPlan frame = new IttiaGDSPlan();
        frame.setVisible(true);
    }
}
