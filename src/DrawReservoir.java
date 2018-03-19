import java.awt.*;
import java.awt.geom.AffineTransform;

public class DrawReservoir {
    private Graphics2D g;

    private int glyphSize = 200;

    public DrawReservoir(Graphics2D g) {
        this.g = g;
    }

//    public void draw(Reservoir reservoir, int wWidth, int wHeight)
//    {
//        wWidth -= width;
//        wHeight -= height;
//
//        AffineTransform baseTransform = g.getTransform();
//
//        g.translate(reservoir.position.getX() / 100 * wWidth, reservoir.position.getY() / 100 * wHeight);
//
//        //Woda
//        g.rotate(Math.toRadians(180), width / 2, height / 2);
//        g.setColor(BLUE);
//        g.fillRect(0,0,width,(int)(reservoir.content / reservoir.capacity * height));
//
//        //Obal
//        g.setColor(BLACK);
//        g.drawRect(0,0,width,height);
//
//        g.setTransform(baseTransform);
//    }

    public void draw(Reservoir reservoir, int width, int height) {
        AffineTransform at = g.getTransform();
        int widthA = width;
        int heightA = height;

        if (reservoir.position.getX() == 0)
            widthA = 0;
        if (reservoir.position.getY() == 0)
            heightA = 0;

//        System.out.println("width / widthA: " + width + "/" + widthA);
//        System.out.println("X: "+ (reservoir.position.getX() - widthA) + " Y: " + (reservoir.position.getY() - heightA));

        g.translate(
                Translator.getInstance().getRealX(reservoir.position.getX())- widthA,
                Translator.getInstance().getRealY(reservoir.position.getY())- heightA
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
