import java.awt.*;
import java.awt.geom.AffineTransform;

public class DrawReservoir {
    private Graphics2D g;

    /**
     * Constructor for passing of Graphics instance.
     *
     * @param g
     */
    public DrawReservoir(Graphics2D g) {
        this.g = g;
    }


    /**
     * Drawing method
     *
     * @param reservoir
     * @param width
     * @param height
     */
    public void draw(Reservoir reservoir, int width, int height) {

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
}
