import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MainPanel extends JPanel {
    private WaterNetwork wn;
    private int reservoirWidth = 150;
    private int reservoirHeight = 150;
    private JFreeChart lineChart;

    //private Pipe currentlySelected;

    /**
     * Constructor for passing watter network
     *
     * @param wn
     */
    public MainPanel(WaterNetwork wn, int glyphSize) {
        this.wn = wn;
        this.reservoirWidth = glyphSize;
        this.reservoirHeight = glyphSize;

        addMouseListener();

    }

    private void addMouseListener() {
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                for (Object key : ComponentCatalog.reservoirsDetection.keySet()) {
                    Reservoir resKey = (Reservoir) key;
                    if (ComponentCatalog.reservoirsDetection.get(resKey).contains(e.getPoint())) {
                        System.out.println("reser DETECTED");
                        prepareChart(resKey, "res", "Voda v nádrži", "Voda", "čas");
                    }
                }

                for (Object key : ComponentCatalog.pipeGraphs.keySet()) {
                    Pipe pipeKey = (Pipe) key;

                    if(ComponentCatalog.arrows.get(pipeKey).contains(e.getPoint()))
                    {
                        System.out.println("arrow detected");
                        prepareChart(pipeKey, "pipe", "Průtok potrubím", "Průtok v čase", "čas");
                    }
                }


                for (Object key : ComponentCatalog.valves.keySet()) {
                    Pipe pipeKey = (Pipe) key;

                    if ((ComponentCatalog.valves.get(pipeKey)).contains(e.getPoint())) {
                        Main.currentlySelectedValve = pipeKey;
                        System.out.println("Valve detected");
                        Main.slider.setValue((int) (pipeKey.open * 100));
                    }
                }
            }

            //region Unused methods
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

    private void prepareChart(Object key, String type, String title, String XAxis, String YAxis) {
        JFreeChart xylineChart = ChartFactory.createXYLineChart(
                title,
                YAxis,
                XAxis,
                createDataset(key, type),
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel chartPanel = new ChartPanel(xylineChart);
        chartPanel.setPreferredSize(new Dimension(560, 367));

        Main.graphWindow = new JDialog();
        Main.graphWindow.add(chartPanel);
        Main.graphWindow.setSize(new Dimension(600, 400));
        Main.graphWindow.setVisible(true);
    }

    private XYDataset createDataset(Object key, String type) {
        final XYSeriesCollection dataset = new XYSeriesCollection();
        if (type.equals("pipe")) {
            dataset.addSeries(ComponentCatalog.pipeGraphs.get(key));
        } else {
            dataset.addSeries(ComponentCatalog.reservoirsGraphs.get(key));
        }
        return dataset;
    }


    /**
     * Override paintComponent
     *
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        Translator.getInstance().setTranslatedWidth(getWidth() - reservoirWidth);
        Translator.getInstance().setTranslatedHeight(getHeight() - reservoirWidth);

        DrawNetworkComponents drawNetworkComponents = new DrawNetworkComponents(g2);

        double maxX = 0, maxY = 0;
        for (NetworkNode nn : wn.getAllNetworkNodes()) {
            maxX = Math.max(maxX, nn.position.getX());
            maxY = Math.max(maxY, nn.position.getY());
        }

        if (maxX == 0) {
            maxX = getWidth();
        }
        if (maxY == 0) {
            maxY = getHeight();
        }

        Translator.getInstance().setVirtualWidth(maxX);
        Translator.getInstance().setVirtualHeight(maxY);


        for (Pipe p : wn.getAllPipes()) {
            drawNetworkComponents.drawPipes(p, reservoirWidth, reservoirHeight);
            drawNetworkComponents.drawValves(p, reservoirWidth, reservoirHeight);
            drawNetworkComponents.drawArrows(p, reservoirWidth, reservoirHeight);

            if (ComponentCatalog.pipeGraphs.get(p) == null) {
                ComponentCatalog.pipeGraphs.put(p, new XYSeries("flow"));
            }
            (ComponentCatalog.pipeGraphs.get(p)).add(wn.currentSimulationTime(), Math.abs(p.flow));
        }

        for (NetworkNode Nn : wn.getAllNetworkNodes()) {
            if (Nn instanceof Reservoir) {
                drawNetworkComponents.drawReservoirs((Reservoir) Nn, reservoirWidth, reservoirHeight);

                if (ComponentCatalog.reservoirsGraphs.get(Nn) == null) {
                    ComponentCatalog.reservoirsGraphs.put((Reservoir) Nn, new XYSeries("flow"));
                }
                ComponentCatalog.reservoirsGraphs.get(Nn).add(wn.currentSimulationTime(), Math.abs(((Reservoir) Nn).content));

            }
        }

        for (NetworkNode nn : wn.getAllNetworkNodes()) {
            if (!(nn instanceof Reservoir)) {

                //"oprava špatného vykreslení při změně GlyphSize"
                Translator.getInstance().setTranslatedWidth(getWidth() - 150);
                Translator.getInstance().setTranslatedHeight(getHeight() - 150);
                drawNetworkComponents.drawNodes(nn, 50, 50);
                Translator.getInstance().setTranslatedWidth(getWidth() - reservoirWidth);
                Translator.getInstance().setTranslatedHeight(getHeight() - reservoirWidth);
            }
        }
    }

    public void drawWith(Graphics2D g2) {
        paintComponent(g2);
    }

}
