import java.awt.*;
import java.awt.geom.Point2D;

public class DrawValves {
    Point2D vector;
    private Graphics2D g;

    public DrawValves(Graphics2D g)
    {
        this.g = g;
    }

    public void draw(Pipe pipe)
    {
        vector = new Point2D.Double((pipe.end.position.getX() - pipe.start.position.getX()), pipe.end.position.getX() - pipe.start.position.getX());
        vector = new Point2D.Double(vector.getX() * 0.4, vector.getY() * 0.4);


    }

}
