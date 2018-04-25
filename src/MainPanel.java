import com.sun.org.apache.xml.internal.resolver.Catalog;
import javafx.scene.shape.Ellipse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.util.HashMap;

public class MainPanel extends JPanel {
    private WaterNetwork wn;
    private int reservoirWidth = 150;
    private int reservoirHeight = 150;

    /**
     * Constructor for passing watter network
     * @param wn
     */
    public MainPanel(WaterNetwork wn, int glyphSize)
    {
        this.wn = wn;
        this.reservoirWidth = glyphSize;
        this.reservoirHeight = glyphSize;

        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                for ( Object key: ((HashMap)ComponentCatalog.getSingleton().get("pipes")).keySet())
                {

                    Pipe pipeKey = (Pipe) key;
                    if (((Ellipse2D.Double)ComponentCatalog.getSingleton().nestInto("pipes").nestInto(pipeKey).nestInto("valve").get("object")).contains(e.getPoint()))
                    {
                        ComponentCatalog.getSingleton().nestInto("pipes").nestInto(pipeKey).nestInto("valve").put("selected", true);
                        System.out.println("Selected added");
                    }
                }
            }

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
        });

    }




    /**
     * Override paintComponent
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;


        (new HashMap<>()).keySet();






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
            drawNetworkComponents.drawPipes(p,reservoirWidth,reservoirHeight);
            drawNetworkComponents.drawValves(p, reservoirWidth, reservoirHeight);
            drawNetworkComponents.drawArrows(p, reservoirWidth, reservoirHeight);
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
