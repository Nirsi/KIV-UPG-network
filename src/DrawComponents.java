import javax.swing.*;
import java.awt.*;

public class DrawComponents extends JPanel
{
    private WaterNetwork wn;
    public DrawComponents(WaterNetwork wn)
    {
        this.wn = wn;
    }



    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;


        DrawReservoir dr = new DrawReservoir(g2);
        DrawPipes dp = new DrawPipes(g2);



        for (NetworkNode Nn : wn.getAllNetworkNodes())
        {
            if (Nn instanceof Reservoir)
                dr.draw((Reservoir) Nn, getWidth(), getHeight());

        }


        for (Pipe p : wn.getAllPipes())
        {
            dp.draw(p, getWidth(), getHeight());
        }



    }



}
