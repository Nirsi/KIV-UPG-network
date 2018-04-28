import javax.swing.*;
import javax.swing.plaf.synth.Region;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.util.HashMap;

import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.*;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

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

    private void addMouseListener()
    {
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                for (Object key : ((HashMap) ComponentCatalog.getSingleton().get("pipes")).keySet()) {
                    Pipe pipeKey = (Pipe) key;


                    if (((Ellipse2D.Double) ComponentCatalog.getSingleton().nestInto("pipes").nestInto(pipeKey).nestInto("valve").get("object")).contains(e.getPoint())) {
                        if (Main.currentlySelectedValve != null) {
                            ComponentCatalog.getSingleton().nestInto("pipes").nestInto(Main.currentlySelectedValve).nestInto("valve").put("selected", false);
                        }
                        ComponentCatalog.getSingleton().nestInto("pipes").nestInto(pipeKey).nestInto("valve").put("selected", true);
                        Main.currentlySelectedValve = pipeKey;
                        Main.slider.setValue((int)(pipeKey.open * 100));
                    }
                    if (((Ellipse2D.Double)ComponentCatalog.getSingleton().nestInto("pipes").nestInto(pipeKey).nestInto("arrow").get("detection")).contains(e.getPoint()))
                    {
                        System.out.println("Arrow click detected");
                        prepareChart(pipeKey);
                    }
                }
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
    private void prepareChart(Pipe pipeKey)
    {
        JFreeChart xylineChart = ChartFactory.createXYLineChart(
                "Průtok potrubím" ,
                "čas" ,
                "zaplnění" ,
                createDataset(pipeKey) ,
                PlotOrientation.VERTICAL ,
                true , true , false);

        ChartPanel chartPanel = new ChartPanel( xylineChart );
        chartPanel.setPreferredSize( new Dimension( 560 , 367 ));

        Main.graphWindow = new JDialog();
        Main.graphWindow.add(chartPanel);
        Main.graphWindow.setSize(new Dimension(600,400));
        Main.graphWindow.setVisible(true);
    }
    private XYDataset createDataset( Pipe pipeKey) {
        final XYSeriesCollection dataset = new XYSeriesCollection( );
        dataset.addSeries( (XYSeries)ComponentCatalog.getSingleton().nestInto("pipes").nestInto(pipeKey).nestInto("flow").get("series"));
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

            if (ComponentCatalog.getSingleton().nestInto("pipes").nestInto(p).nestInto("flow").get("series") == null)
                ComponentCatalog.getSingleton().nestInto("pipes").nestInto(p).nestInto("flow").put("series",new XYSeries("flow"));
            ((XYSeries)ComponentCatalog.getSingleton().nestInto("pipes").nestInto(p).nestInto("flow").get("series")).add(wn.currentSimulationTime(),Math.abs(p.flow));
        }

        for (NetworkNode Nn : wn.getAllNetworkNodes()) {
            if (Nn instanceof Reservoir) {
                drawNetworkComponents.drawReservoirs((Reservoir) Nn, reservoirWidth, reservoirHeight);

            }
        }

        for (NetworkNode nn : wn.getAllNetworkNodes()) {
            if (!(nn instanceof Reservoir)) {
                drawNetworkComponents.drawNodes(nn, 50, 50);
            }
        }




    }


}
