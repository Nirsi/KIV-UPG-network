import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

public class DrawNodes
{
    private Graphics2D g;
    private double windowWidth;
    private double windowHeight;


    public DrawNodes(Graphics2D g)
    {
        this.g = g;
    }

    public void draw(NetworkNode node, double windowWidth, double windowHeight)
    {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;

        System.out.println(getTruePosition(node.position).getX());
        Ellipse2D nodeCircle = new Ellipse2D.Double(getTruePosition(node.position).getX(), getTruePosition(node.position).getY(),50,50);
        g.translate(-nodeCircle.getWidth()/2, -nodeCircle.getHeight()/2);
        g.draw(nodeCircle);
    }

    private Point2D getTruePosition(Point2D position)
    {
        return new Point2D.Double((position.getX()) / 100.0 * windowWidth, (position.getY()) / 100.0 * windowHeight);
    }


}
