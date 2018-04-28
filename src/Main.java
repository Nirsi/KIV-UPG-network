import sun.plugin.dom.css.RGBColor;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import org.jfree.chart.*;

public class Main
{
   private static WaterNetwork waterNetwork;
    /**
     * Entry point of program
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        waterNetwork = new WaterNetwork(3);

        JFrame frame = new JFrame();
        frame.setPreferredSize(new Dimension(1100,800));
        frame.setLayout(new BorderLayout());

        MainPanel dc = new MainPanel(waterNetwork, Integer.parseInt("150"));
        //dc.setPreferredSize(new Dimension(800,800));
        dc.setMinimumSize(new Dimension(800,800));




        slider = new JSlider(JSlider.HORIZONTAL, 0,100,50);

        slider.addChangeListener(e -> currentlySelectedValve.open = (((JSlider)e.getSource()).getValue() / 100.0));


        graphWindow = new JDialog(frame, "Title");
        graphWindow.setSize(600,500);
        graphWindow.setLocationByPlatform(true);

        JPanel toolPanel = new JPanel();
        initToolPanel(toolPanel);


        frame.add(dc, BorderLayout.CENTER);
        frame.add(toolPanel,BorderLayout.EAST);

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

    private static void initToolPanel(JPanel toolPanel)
    {
        toolPanel.setPreferredSize(new Dimension(200, 800));
        toolPanel.setBackground(new Color(178, 178, 178));

        JButton btSlow = new JButton("NORMAL");
        JButton btFast = new JButton("FAST AF BOI!");


        toolPanel.add(btFast);
        toolPanel.add(btSlow);
        toolPanel.add(new Label("Valve"));
        toolPanel.add(slider);

        btFast.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                waterNetwork.runFast();
            }
            //region USELESS METHODS HERE
            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
            //endregion
        });

        btSlow.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                waterNetwork.runNormal();
            }
            //region USELESS METHODS HERE


            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }

            //endregion
        });


    }

    public static Pipe currentlySelectedValve;
    public static JSlider slider;
    public static JDialog graphWindow;
}
