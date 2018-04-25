import javax.swing.*;
import java.awt.*;

public class Main
{
    /**
     * Entry point of program
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        WaterNetwork waterNetwork = new WaterNetwork(0);

        JFrame frame = new JFrame();
        MainPanel dc = new MainPanel(waterNetwork, Integer.parseInt("150"));
        dc.setPreferredSize(new Dimension(800,800));

        frame.add(dc);

        frame.setTitle("UPG - water network visualisation");
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        for(;;)
        {
            waterNetwork.updateState();
            dc.repaint();
            Thread.sleep(20);
        }
    }
}
