import java.awt.*;
import java.awt.geom.AffineTransform;

public class DrawReservoir {
    private Graphics2D g;

    private int glyphSize = 200;

    public DrawReservoir(Graphics2D g) {
        this.g = g;
    }

//

    public void draw(Reservoir reservoir, int width, int height) {
        AffineTransform at = g.getTransform();

        g.translate(
                Translator.getInstance().getRealX(reservoir.position.getX()),
                Translator.getInstance().getRealY(reservoir.position.getY())
        );

        // Draw water
        g.rotate(Math.toRadians(180), width / 2, height / 2);

        // Set reservoir background
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, width, height);

        g.setColor(new Color(64, 164, 223)); // Blue
        g.fillRect(0, 0, width, (int) (reservoir.content / reservoir.capacity * height));

        // Draw border
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(2));
        g.drawRect(0, 0, width, height);

        g.setTransform(at);
    }
}
