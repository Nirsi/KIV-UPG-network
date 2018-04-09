import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class DrawNetworkComponents {

    Graphics2D g;

    public DrawNetworkComponents(Graphics2D g)
    {
        this.g = g;
    }

    /**
     * Drawing method
     *
     * @param reservoir
     * @param width
     * @param height
     */
    public void drawReservoirs(Reservoir reservoir, int width, int height) {

        AffineTransform baseTransform = g.getTransform();

        g.translate(
                Translator.getInstance().getTranslatedX(reservoir.position.getX()),
                Translator.getInstance().getTranslatedY(reservoir.position.getY())
        );

        //Water
        g.rotate(Math.toRadians(180), width / 2, height / 2);
        g.setColor(Color.BLUE);
        g.fillRect(0, 0, width, (int) (reservoir.content / reservoir.capacity * height));

        // Case of Reservoir
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(3));
        g.drawRect(0, 0, width, height);

        g.setTransform(baseTransform);
    }


    /**
     *
     * @param pipe
     * @param width
     * @param height
     */
    public void drawPipes(Pipe pipe, int width, int height) {
        //Kow
        drawPipe(pipe, new Color(50, 50, 50), 15, width, height);
        //Woda
        drawPipe(pipe, Color.BLUE, 12, width, height);
    }

    /**
     * Drawing of Pipes
     * @param pipe
     * @param color
     * @param strokeSize
     * @param width
     * @param height
     */
    private void drawPipe(Pipe pipe, Color color, int strokeSize, int width, int height) {
        g.setStroke(new BasicStroke(strokeSize));
        g.setColor(color);

        g.drawLine(
                (int) (Translator.getInstance().getTranslatedX(pipe.start.position.getX()) + width / 2),
                (int) (Translator.getInstance().getTranslatedY(pipe.start.position.getY()) + height / 2),
                (int) (Translator.getInstance().getTranslatedX(pipe.end.position.getX()) + width / 2),
                (int) (Translator.getInstance().getTranslatedY(pipe.end.position.getY()) + height / 2)
        );
    }

    /**
     *
     * @param node
     * @param width
     * @param height
     */
    public void drawNodes(NetworkNode node, int width, int height)
    {
        g.setColor(new Color(50, 50, 50));
        g.fillOval(
                (int) Translator.getInstance().getTranslatedX(node.position.getX()) + width,
                (int) Translator.getInstance().getTranslatedY(node.position.getY()) + width,
                width,
                height
        );
    }

    /**
     * Drawing method
     * @param pipe
     * @param reservoirWidth
     * @param reservoirHeight
     */
    public void drawValves(Pipe pipe, int reservoirWidth, int reservoirHeight) {
        Point2D vector = computeVector(pipe);

        int valveWidth = 50;
        int valveHeight = 50;

        g.setColor(new Color(255, 215, 0));
        g.fillOval(
                (int) (Translator.getInstance().getTranslatedX(pipe.start.position.getX() + vector.getX()) - valveWidth / 2 + reservoirWidth / 2),
                (int) (Translator.getInstance().getTranslatedY(pipe.start.position.getY() + vector.getY()) - valveHeight / 2 + reservoirHeight / 2),
                valveWidth,
                valveHeight);

        Shape c = g.getClip();

        g.setClip(
                (int) (Translator.getInstance().getTranslatedX(pipe.start.position.getX() + vector.getX()) - valveWidth / 2 + reservoirWidth / 2),
                (int) ((Translator.getInstance().getTranslatedY(pipe.start.position.getY() + vector.getY()) - valveHeight / 2 + reservoirHeight / 2) + (valveWidth * pipe.open)),
                valveWidth,
                valveHeight);

        g.setColor(Color.BLACK);
        g.fillOval(
                (int) (Translator.getInstance().getTranslatedX(pipe.start.position.getX() + vector.getX()) - valveWidth / 2 + reservoirWidth / 2),
                (int) (Translator.getInstance().getTranslatedY(pipe.start.position.getY() + vector.getY()) - valveHeight / 2 + reservoirHeight / 2),
                valveWidth,
                valveHeight);

        g.setColor(Color.BLACK);
        g.setClip(c);
    }

    /**
     * Computing vector
     * @param pipe
     */
    private Point2D computeVector(Pipe pipe) {
        Point2D vector = new Point2D.Double(
                pipe.end.position.getX() - pipe.start.position.getX(),
                pipe.end.position.getY() - pipe.start.position.getY()
        );
        return new Point2D.Double(vector.getX() * 0.4, vector.getY() * 0.4);
    }
}
