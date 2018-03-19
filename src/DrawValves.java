import java.awt.*;
import java.awt.geom.Point2D;

public class DrawValves {
    Point2D vector;
    private Graphics2D g;

    private int valveWidth = 50;
    private int valveHeight = 50;

    public DrawValves(Graphics2D g) {
        this.g = g;
    }

    public void draw(Pipe pipe, int reservoirWidth, int reservoirHeight) {
        vector = new Point2D.Double((pipe.end.position.getX() - pipe.start.position.getX()), pipe.end.position.getY() - pipe.start.position.getY());
        vector = new Point2D.Double(vector.getX() * 0.4, vector.getY() * 0.4);

        g.setColor(new Color(255,215,0));
        g.fillOval(
                (int) (Translator.getInstance().getRealX(pipe.start.position.getX() + vector.getX()) - valveWidth / 2 + reservoirWidth / 2),
                (int) (Translator.getInstance().getRealY(pipe.start.position.getY() + vector.getY()) - valveHeight / 2 + reservoirHeight / 2),
                valveWidth,
                valveHeight);

        Shape c = g.getClip();

        g.setClip(
                (int) (Translator.getInstance().getRealX(pipe.start.position.getX() + vector.getX()) - valveWidth / 2 + reservoirWidth / 2),
                (int) ((Translator.getInstance().getRealY(pipe.start.position.getY() + vector.getY()) - valveHeight / 2 + reservoirHeight / 2) + (valveWidth * pipe.open)),
                valveWidth,
                valveHeight);

        g.setColor(Color.PINK);
        g.fillOval(
                (int) (Translator.getInstance().getRealX(pipe.start.position.getX() + vector.getX()) - valveWidth / 2 + reservoirWidth / 2),
                (int) (Translator.getInstance().getRealY(pipe.start.position.getY() + vector.getY()) - valveHeight / 2 + reservoirHeight / 2),
                valveWidth,
                valveHeight);

        g.setColor(Color.BLACK);

        g.setClip(c);
    }
}
