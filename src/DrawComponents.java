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
    public void paintComponent(Graphics gOld)
    {
        super.paintComponent(gOld);
        Graphics2D g = (Graphics2D)gOld;


        DrawReservoir dr = new DrawReservoir(g);


        for (NetworkNode Nn : wn.getAllNetworkNodes())
        {
            if (Nn instanceof Reservoir)
                dr.draw((Reservoir) Nn, getWidth(), getHeight());

        }

    }



}
