import javax.sql.rowset.spi.TransactionalWriter;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class DrawArrow
{
    Graphics2D g;
    Point2D vector;
    Point2D arrowStart;
    Point2D arrowEnd;
    DrawTexts dt;

    public DrawArrow(Graphics2D g)
    {
        this.g = g;
        dt = new DrawTexts(g);
    }

    public void draw(Pipe pipe, int reservoirWidth, int reservoirHeight)
    {
        g.setStroke(new BasicStroke(2));
        vector = new Point2D.Double(
                (pipe.end.position.getX() - pipe.start.position.getX()),
                pipe.end.position.getY() - pipe.start.position.getY()
        );

        arrowStart = new Point2D.Double(
                pipe.start.position.getX() + vector.getX() * 0.60,
                pipe.start.position.getY() + vector.getY() * 0.60
                );

        arrowEnd = new Point2D.Double(
                pipe.start.position.getX() + vector.getX() * 0.75,
                pipe.start.position.getY() + vector.getY() * 0.75
        );
        arrowStart = new Point2D.Double(
                Translator.getInstance().getRealX(arrowStart.getX()) + reservoirWidth/2,
                Translator.getInstance().getRealY(arrowStart.getY()) + reservoirHeight /2
        );
        arrowEnd = new Point2D.Double(
                Translator.getInstance().getRealX(arrowEnd.getX()) + reservoirWidth/2,
                Translator.getInstance().getRealY(arrowEnd.getY()) + reservoirHeight /2
        );

        dt.draw(pipe,vector,arrowStart,arrowEnd);
        drawArrow((int) arrowStart.getX(),(int) arrowEnd.getX(),(int) arrowStart.getY(),(int) arrowEnd.getY(), 15);
    }

    private void drawArrow(int x1, int x2, int y1, int y2, int arrowLength)
    {
        double vx = x2 - x1;
        double vy = y2 - y1;

        double vLength = Math.sqrt(vx*vx + vy*vy);

        double vNormX = vx / vLength;
        double vNormY = vy / vLength;

        double vArrowX = vNormX * arrowLength;
        double vArrowY = vNormY * arrowLength;

        double kx = -vArrowY;
        double ky = vArrowX;

        kx *= 0.25;
        ky *= 0.25;

        g.draw(new Line2D.Double(x1, y1, x2, y2));

        g.draw(new Line2D.Double(x2, y2, x2 - vArrowX + kx, y2 - vArrowY + ky));
        g.draw(new Line2D.Double(x2, y2, x2 - vArrowX - kx, y2 - vArrowY - ky));
    }

    public Point2D getVector() {
        return vector;
    }

    public Point2D getArrowStart() {
        return arrowStart;
    }

    public Point2D getArrowEnd() {
        return arrowEnd;
    }
}
