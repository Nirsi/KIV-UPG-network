import sun.plugin.dom.css.RGBColor;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

import org.jfree.chart.*;

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
        frame.setPreferredSize(new Dimension(1100,800));
        frame.setLayout(new BorderLayout());

        MainPanel dc = new MainPanel(waterNetwork, Integer.parseInt("150"));
        //dc.setPreferredSize(new Dimension(800,800));
        dc.setMinimumSize(new Dimension(800,800));

        JPanel toolPanel = new JPanel();
        toolPanel.setPreferredSize(new Dimension(200, 800));
        toolPanel.setBackground(new Color(178, 178, 178));

        slider = new JSlider(JSlider.HORIZONTAL, 0,100,50);

        slider.addChangeListener(e -> currentlySelectedValve.open = (((JSlider)e.getSource()).getValue() / 100.0));

        toolPanel.add(new Label("Valve"));
        toolPanel.add(slider);




        frame.add(dc, BorderLayout.CENTER);
        frame.add(toolPanel,BorderLayout.EAST);




        frame.setTitle("UPG - water network visualisation");
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


        graphWindow = new JDialog(frame, "Title");
        graphWindow.setSize(600,500);
        graphWindow.setLocationByPlatform(true);



        for(;;)
        {
            waterNetwork.updateState();
            dc.repaint();
            Thread.sleep(20);
        }
    }

    public static Pipe currentlySelectedValve;
    public static JSlider slider;
    public static JDialog graphWindow;
}
