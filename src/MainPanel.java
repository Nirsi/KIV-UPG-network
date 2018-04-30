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
                for (Object key : ObjectStack.reservoirsDetection.keySet()) {
                    Reservoir resKey = (Reservoir) key;
                    if (ObjectStack.reservoirsDetection.get(resKey).contains(e.getPoint())) {
                        System.out.println("reser DETECTED");
                        prepareChart(resKey, "res", "Voda v nádrži", "Voda", "čas");
                    }
                }

                for (Object key : ObjectStack.pipeGraphs.keySet()) {
                    Pipe pipeKey = (Pipe) key;

                    if(ObjectStack.arrows.get(pipeKey).contains(e.getPoint()))
                    {
                        System.out.println("arrow detected");
                        prepareChart(pipeKey, "pipe", "Průtok", "Woda", "čas");
                    }
                }


                for (Object key : ObjectStack.valves.keySet()) {
                    Pipe pipeKey = (Pipe) key;

                    if ((ObjectStack.valves.get(pipeKey)).contains(e.getPoint())) {
                        Main.currentlySelectedValve = pipeKey;
                        System.out.println("Valve detected");
                        Main.slider.setValue((int) (pipeKey.open * 100));
                    }
                }



//                for (Object key : ComponentCatalog.getInstance().getObjectsStartingWithKey("pipes")) {
//                    Pipe pipeKey = (Pipe) key;
//
//                    }
//                    if (((Ellipse2D.Double) ComponentCatalog.getInstance().nestInto("pipes").nestInto(pipeKey).nestInto("arrow").get("detection")).contains(e.getPoint())) {
//                        System.out.println("Arrow click detected");
//                        prepareChart(pipeKey, "pipe", "Průtok potrubím", "Průtok", "čas");
//                    }
//                }
            }

            //region Useless
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
            dataset.addSeries(ObjectStack.pipeGraphs.get(key));
        } else {
            dataset.addSeries(ObjectStack.reservoirsGraphs.get(key));
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

            if (ObjectStack.pipeGraphs.get(p) == null) {
                ObjectStack.pipeGraphs.put(p, new XYSeries("flow"));
            }
            (ObjectStack.pipeGraphs.get(p)).add(wn.currentSimulationTime(), Math.abs(p.flow));
        }

        for (NetworkNode Nn : wn.getAllNetworkNodes()) {
            if (Nn instanceof Reservoir) {
                drawNetworkComponents.drawReservoirs((Reservoir) Nn, reservoirWidth, reservoirHeight);

                if (ObjectStack.reservoirsGraphs.get(Nn) == null) {
                    ObjectStack.reservoirsGraphs.put((Reservoir) Nn, new XYSeries("flow"));
                }
                ObjectStack.reservoirsGraphs.get(Nn).add(wn.currentSimulationTime(), Math.abs(((Reservoir) Nn).content));

            }
        }

        for (NetworkNode nn : wn.getAllNetworkNodes()) {
            if (!(nn instanceof Reservoir)) {
                drawNetworkComponents.drawNodes(nn, 50, 50);
            }
        }
    }

    public void drawWith(Graphics2D g2) {
        paintComponent(g2);
    }

}
