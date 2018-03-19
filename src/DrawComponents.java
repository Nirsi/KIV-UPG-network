import javax.swing.*;
import java.awt.*;

public class DrawComponents extends JPanel {
    private WaterNetwork wn;
    private int reservoirWidth = 150;
    private int reservoirHeight = 150;

    public DrawComponents(WaterNetwork wn) {
        this.wn = wn;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        Translator.getInstance().setRealWidth(getWidth() - reservoirWidth);
        Translator.getInstance().setRealHeight(getHeight() - reservoirWidth);


        DrawReservoir dr = new DrawReservoir(g2);
        DrawPipes dp = new DrawPipes(g2);
        DrawNodes dn = new DrawNodes(g2);
        DrawValves dv = new DrawValves(g2);
        DrawArrow da = new DrawArrow(g2);

        double maxX = 0, maxY = 0;
        for (NetworkNode nn : wn.getAllNetworkNodes()) {
            maxX = Math.max(maxX, nn.position.getX());
            maxY = Math.max(maxY, nn.position.getY());

            //System.out.println("Xnode: " + nn.position.getX() + " Ynode: " + nn.position.getY());
        }

        if (maxX == 0) {
            maxX = getWidth();
        }
        if (maxY == 0) {
            maxY = getHeight();
        }

        //System.out.println("maxX to setVirtual " + maxX);
        Translator.getInstance().setVirtualWidth(maxX);
        //System.out.println("maxX to setHeight " + maxY);
        Translator.getInstance().setVirtualHeight(maxY);

        //System.out.println(Translator.getInstance().toString());

        for (Pipe p : wn.getAllPipes()) {
            dp.draw(p, reservoirWidth, reservoirHeight);
            dv.draw(p, reservoirWidth, reservoirHeight);
            da.draw(p, reservoirWidth,reservoirHeight);
        }

        for (NetworkNode Nn : wn.getAllNetworkNodes()) {
            if (Nn instanceof Reservoir) {
                dr.draw((Reservoir) Nn, reservoirWidth, reservoirHeight);
            }
        }

        for (NetworkNode nn : wn.getAllNetworkNodes()) {
            if (!(nn instanceof Reservoir)) {
                dn.draw(nn, 50, 50);
            }
        }


    }


}
