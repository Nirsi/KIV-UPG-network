import java.awt.*;
import java.awt.geom.AffineTransform;

import static java.awt.Color.BLACK;
import static java.awt.Color.BLUE;

public class DrawReservoir
{
    private Graphics2D g;

    private int height = 180;
    private int width = 180;



    private int glyphSize = 200;



    public DrawReservoir(Graphics2D g)
    {
        this.g = g;

    }

    public void draw(Reservoir reservoir, int wWidth, int wHeight)
    {
        wWidth -= width;
        wHeight -= height;

        AffineTransform baseTransform = g.getTransform();

        g.translate(reservoir.position.getX() / 100 * wWidth, reservoir.position.getY() / 100 * wHeight);

        //Woda
        g.rotate(Math.toRadians(180), width / 2, height / 2);
        g.setColor(BLUE);
        g.fillRect(0,0,width,(int)(reservoir.content / reservoir.capacity * height));

        //Obal
        g.setColor(BLACK);
        g.drawRect(0,0,width,height);

        g.setTransform(baseTransform);

        //Pipes






    }
}
