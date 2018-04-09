import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {
    private WaterNetwork wn;
    private int reservoirWidth = 150;
    private int reservoirHeight = 150;

    /**
     * Constructor for passing watter network
     * @param wn
     */
    public MainPanel(WaterNetwork wn) {
        this.wn = wn;
    }

    /**
     * Override paintComponent
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        Translator.getInstance().setTranslatedWidth(getWidth() - reservoirWidth);
        Translator.getInstance().setTranslatedHeight(getHeight() - reservoirWidth);


        DrawReservoir dr = new DrawReservoir(g2);
        DrawPipes dp = new DrawPipes(g2);
        DrawNodes dn = new DrawNodes(g2);
        DrawValves dv = new DrawValves(g2);
        DrawArrow da = new DrawArrow(g2);
        DrawNetworkComponents dnc = new DrawNetworkComponents(g2);

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
            dnc.drawPipes(p,reservoirWidth,reservoirHeight);
            //dp.draw(p, reservoirWidth, reservoirHeight);
            dnc.drawValves(p, reservoirWidth, reservoirHeight);
            //da.draw(p, reservoirWidth,reservoirHeight);
            dnc.drawArrows(p, reservoirWidth, reservoirHeight);
        }

        for (NetworkNode Nn : wn.getAllNetworkNodes()) {
            if (Nn instanceof Reservoir) {
                //dr.draw((Reservoir) Nn, reservoirWidth, reservoirHeight);
                dnc.drawReservoirs((Reservoir) Nn, reservoirWidth, reservoirHeight);

            }
        }

        for (NetworkNode nn : wn.getAllNetworkNodes()) {
            if (!(nn instanceof Reservoir)) {
                //dn.draw(nn, 50, 50);
                dnc.drawNodes(nn,50,50);
            }
        }


    }


}
