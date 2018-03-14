import javafx.geometry.Point3D;

import java.awt.*;
import java.awt.geom.Point2D;

public class DrawPipes
{
    Graphics2D g;

    private double windowWidth;
    private double windowHeight;


    public DrawPipes(Graphics2D g)
    {
        this.g = g;
    }

    public void draw(Pipe pipe)
    {
        g.drawLine((int) getTruePosition(pipe.start.position).getX(), (int) getTruePosition(pipe.start.position).getY(),(int) getTruePosition(pipe.end.position).getX(),(int) getTruePosition(pipe.end.position).getY());
    }
    private Point2D getTruePosition(Point2D position)
    {
        return new Point2D.Double(position.getX() / 100 * windowWidth, position.getY() / 100 * windowHeight);
    }


}
