import java.awt.*;
import java.awt.geom.AffineTransform;

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
}
