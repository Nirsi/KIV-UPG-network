import javafx.scene.shape.SVGPath;
import org.jfree.graphics2d.svg.SVGGraphics2D;
import sun.plugin.dom.css.RGBColor;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.Buffer;

import org.jfree.chart.*;

public class Main {

    private static MainPanel mainPanel;
    private static JPanel toolPanel;
    private static JFrame frame;
    private static WaterNetwork waterNetwork;
    public static Pipe currentlySelectedValve;
    public static JSlider slider;
    public static JDialog graphWindow;

    /**
     * Entry point of program
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        waterNetwork = new WaterNetwork(3);

        frame = new JFrame();
        frame.setPreferredSize(new Dimension(1100, 800));
        frame.setLayout(new BorderLayout());

        mainPanel = new MainPanel(waterNetwork, Integer.parseInt("150"));
        //mainPanel.setPreferredSize(new Dimension(800,800));
        mainPanel.setMinimumSize(new Dimension(800, 800));


        slider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);

        slider.addChangeListener(e -> currentlySelectedValve.open = (((JSlider) e.getSource()).getValue() / 100.0));


        graphWindow = new JDialog(frame, "Title");
        graphWindow.setSize(600, 500);
        graphWindow.setLocationByPlatform(true);

        toolPanel = new JPanel();
        initToolPanel(toolPanel);


        frame.add(mainPanel, BorderLayout.CENTER);
        frame.add(toolPanel, BorderLayout.EAST);

        frame.setTitle("UPG - water network visualisation");
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        for (; ; ) {
            waterNetwork.updateState();
            mainPanel.repaint();
            Thread.sleep(20);
        }
    }

    private static void initToolPanel(JPanel toolPanel) {
        toolPanel.setPreferredSize(new Dimension(200, 800));
        toolPanel.setBackground(new Color(178, 178, 178));


        JButton btSlow = new JButton("NORMAL");
        JButton btFast = new JButton("FAST AF BOI!");
        JButton btSvgExport = new JButton("Export to SVG");
        JButton btPngExport = new JButton("Export to PNG");


        toolPanel.add(btFast);
        toolPanel.add(btSlow);
        toolPanel.add(btSvgExport);
        toolPanel.add(btPngExport);
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

        btSvgExport.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ExportAsSvg();

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

        btPngExport.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ExportAsPng();

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

    private static void ExportAsPng()
    {
        BufferedImage bufferedImage = new BufferedImage(mainPanel.getWidth(),mainPanel.getHeight(),BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = bufferedImage.createGraphics();
        mainPanel.drawWith(g2);

        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {

            File file = fileChooser.getSelectedFile();
            try {
                if (ImageIO.write(bufferedImage, "png", file))
                {
                    System.out.println("File saved");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private static void ExportAsSvg()
    {
        SVGGraphics2D svgGraphics2D = new SVGGraphics2D(mainPanel.getWidth(), mainPanel.getHeight());
        mainPanel.drawWith(svgGraphics2D);
        String input = svgGraphics2D.getSVGElement();
        System.out.println(input);

        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION)
        {
            File file = fileChooser.getSelectedFile();

            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                writer.write(input);
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
