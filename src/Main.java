import javax.swing.*;
import java.awt.*;

public class Main
{
    public static void main(String[] args) throws InterruptedException {
        WaterNetwork waterNetwork = new WaterNetwork(3);

        JFrame frame = new JFrame();
        DrawComponents dc = new DrawComponents(waterNetwork);
        dc.setPreferredSize(new Dimension(800,800));

        frame.add(dc);

        frame.setTitle("UPG - Semestrální vykreslení");
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        //runSimulation(waterNetwork);
    }
}
