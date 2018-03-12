import java.awt.*;
import java.awt.geom.*;

public class DrawReservoir
{
    private Graphics2D g;

    private int heigh = 100;
    private int width = 100;

    private int WWidth;
    private int Wheigh;

    public DrawReservoir(Graphics2D g)
    {
        this.g = g;
    }

    public void draw(Reservoir reservoir, int wWidth, int wHeigh)
    {
//        Point2D augPosition = new Point2D.Double(reservoir.position.getX() * width / 100, reservoir.position.getY() * heigh / 100);
//
//        if (augPosition.getX() * width > wWidth)
//            augPosition = new Point2D.Double(augPosition.getX() - width, augPosition.getY());
//
//        if (augPosition.getY() * heigh > wHeigh)
//            augPosition = new Point2D.Double(augPosition.getX(), augPosition.getY() - heigh);

        g.drawRect((int)reservoir.position.getX(), (int)reservoir.position.getY(),width,heigh);






    }




}
