import java.awt.*;
import java.awt.geom.Point2D;

public class DrawPipes {
    Graphics2D g;

    private double windowWidth;
    private double windowHeight;


    public DrawPipes(Graphics2D g) {
        this.g = g;
    }

    public void draw(Pipe pipe, double windowWidth, double windowHeight) {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        g.setStroke(new BasicStroke(5));


        g.drawLine(
                (int) (getTruePosition(pipe.start.position).getX() - pipe.start.position.getX() * 180 /100) + 180 / 2,
                (int) (getTruePosition(pipe.start.position).getY() - pipe.start.position.getY() * 180 /100) + 180 / 2,
                (int) getTruePosition(pipe.end.position).getX(),
                (int) getTruePosition(pipe.end.position).getY());

    }

    private Point2D getTruePosition(Point2D position) {
//        System.out.println("X: " + position.getX() + " Y: " + position.getY());
//        System.out.println("real X: " + position.getX() * windowWidth / 100 + " real Y: " + position.getY() * windowHeight/ 100 );
        return new Point2D.Double((position.getX()) / 100.0 * windowWidth, (position.getY()) / 100.0 * windowHeight);
    }
}
