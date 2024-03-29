import java.awt.*;
import java.awt.font.TextAttribute;
import java.awt.geom.*;
import java.text.AttributedString;

public class DrawNetworkComponents {

    Graphics2D g;
    Color waterColor = new Color(27, 133, 255);

    public DrawNetworkComponents(Graphics2D g) {
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
        g.setColor(waterColor);
        g.fillRect(0, 0, width, (int) (reservoir.content / reservoir.capacity * height));

        // Case of Reservoir
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(3));
        g.drawRect(0,0,width,height);

        g.setTransform(baseTransform);
        Rectangle2D translatedRectangle = new Rectangle2D.Double(Translator.getInstance().getTranslatedX(reservoir.position.getX()),Translator.getInstance().getTranslatedY(reservoir.position.getY()),width,height);

        ComponentCatalog.reservoirsDetection.put(reservoir, translatedRectangle);




    }


    /**
     * @param pipe
     * @param width
     * @param height
     */
    public void drawPipes(Pipe pipe, int width, int height) {
        //Kow
        drawPipe(pipe, new Color(50, 50, 50), 15, width, height);
        //Woda
        drawPipe(pipe, waterColor, 12, width, height);


    }

    /**
     * Drawing of Pipes
     *
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
     * @param node
     * @param width
     * @param height
     */
    public void drawNodes(NetworkNode node, int width, int height) {
        g.setColor(waterColor);
        g.fillOval(
                (int) Translator.getInstance().getTranslatedX(node.position.getX()) + width,
                (int) Translator.getInstance().getTranslatedY(node.position.getY()) + width,
                width,
                height
        );
    }

    /**
     * Drawing method
     *
     * @param pipe
     * @param reservoirWidth
     * @param reservoirHeight
     */
    public void drawValves(Pipe pipe, int reservoirWidth, int reservoirHeight) {
        Point2D vector = computeVector(pipe);

        int valveWidth = 50;
        int valveHeight = 50;

        g.setColor(new Color(142, 201, 255));


//        if (pipe == Main.currentlySelectedValve)
//            g.setColor(new Color(255, 20, 200));

        Ellipse2D valve = new Ellipse2D.Double(
                (int) (Translator.getInstance().getTranslatedX(pipe.start.position.getX() + vector.getX()) - valveWidth / 2 + reservoirWidth / 2),
                (int) (Translator.getInstance().getTranslatedY(pipe.start.position.getY() + vector.getY()) - valveHeight / 2 + reservoirHeight / 2),
                valveWidth,
                valveHeight);

        if ((ComponentCatalog.valves.get(pipe)) != null){
            if(Main.currentlySelectedValve == pipe)
            {
                Ellipse2D selectionCircle = new Ellipse2D.Double(valve.getX() - 5, valve.getY() - 5, valve.getWidth() + 10, valve.getHeight() + 10);
                g.setColor(new Color(255, 34, 0));
                g.fill(selectionCircle);
                g.setColor(new Color(142, 201, 255));
            }


        }

        g.fill(valve);

        //addin' valve to catalog
        ComponentCatalog.valves.put(pipe, valve);

        Shape c = g.getClip();

        g.setClip(
                (int) (Translator.getInstance().getTranslatedX(pipe.start.position.getX() + vector.getX()) - valveWidth / 2 + reservoirWidth / 2),
                (int) ((Translator.getInstance().getTranslatedY(pipe.start.position.getY() + vector.getY()) - valveHeight / 2 + reservoirHeight / 2) + (valveWidth * pipe.open)),
                valveWidth,
                valveHeight);

        g.setColor(Color.BLACK);
        g.fill(valve);

        g.setColor(Color.BLACK);
        g.setClip(c);
    }

    /**
     * Computing vector
     *
     * @param pipe
     */
    private Point2D computeVector(Pipe pipe) {
        Point2D vector = new Point2D.Double(
                pipe.end.position.getX() - pipe.start.position.getX(),
                pipe.end.position.getY() - pipe.start.position.getY()
        );
        return new Point2D.Double(vector.getX() * 0.4, vector.getY() * 0.4);
    }

    /**
     * Drawing of Pipes -> computing vectors for drawing
     *
     * @param pipe
     * @param reservoirWidth
     * @param reservoirHeight
     */
    public void drawArrows(Pipe pipe, int reservoirWidth, int reservoirHeight)
    {
        Point2D vector;
        Point2D arrowStart;
        Point2D arrowEnd;
        Point2D middle;
        int circleSize;

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
        middle = new Point2D.Double(
                pipe.start.position.getX() + vector.getX() * 0.675,
                pipe.start.position.getY() + vector.getY() * 0.675
        );

        arrowStart = new Point2D.Double(
                Translator.getInstance().getTranslatedX(arrowStart.getX()) + reservoirWidth / 2,
                Translator.getInstance().getTranslatedY(arrowStart.getY()) + reservoirHeight / 2
        );
        arrowEnd = new Point2D.Double(
                Translator.getInstance().getTranslatedX(arrowEnd.getX()) + reservoirWidth / 2,
                Translator.getInstance().getTranslatedY(arrowEnd.getY()) + reservoirHeight / 2
        );
        middle = new Point2D.Double(
                Translator.getInstance().getTranslatedX(middle.getX()) + reservoirWidth / 2,
                Translator.getInstance().getTranslatedY(middle.getY()) + reservoirHeight / 2
        );

        drawTexts(pipe, arrowStart, arrowEnd);
        if (pipe.flow >= 0) {
            drawArrow((int) arrowStart.getX(), (int) arrowEnd.getX(), (int) arrowStart.getY(), (int) arrowEnd.getY(), 15);
        } else {
            drawArrow((int) arrowEnd.getX(), (int) arrowStart.getX(), (int) arrowEnd.getY(), (int) arrowStart.getY(), 15);
        }

        //Computing detection circle size

        double vx = arrowStart.getX() - arrowEnd.getX();
        double vy = arrowStart.getY() - arrowEnd.getY();

        circleSize = ((int) Math.sqrt(vx * vx + vy * vy)) + 20;

        Ellipse2D detectionCircle = new Ellipse2D.Double((int) middle.getX(),(int) middle.getY(),circleSize,circleSize);
        detectionCircle = new Ellipse2D.Double(detectionCircle.getX() - circleSize/2, detectionCircle.getY() - circleSize/2, circleSize,circleSize);
        ComponentCatalog.arrows.put(pipe, detectionCircle);

    }

    /**
     * Drawing respective arrows from precomputed coordinates.
     *
     * @param x1
     * @param x2
     * @param y1
     * @param y2
     * @param arrowLength
     */
    private void drawArrow(int x1, int x2, int y1, int y2, int arrowLength) {
        double vx = x2 - x1;
        double vy = y2 - y1;

        double vLength = Math.sqrt(vx * vx + vy * vy);

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

    /**
     * Drawing method
     *
     * @param pipe
     * @param arrowStart
     * @param arrowEnd
     */
    private void drawTexts(Pipe pipe, Point2D arrowStart, Point2D arrowEnd) {

        double vx = arrowEnd.getX() - arrowStart.getX();
        double vy = arrowEnd.getY() - arrowStart.getY();
        double vLength = Math.sqrt((vx * vx) + (vy * vy));
//        System.out.println("1: " + vx + " " + vy);
        vx = vx / vLength;
        vy = vy / vLength;
//        System.out.println("2: " + vx + " " + vy);

        vx = vx * 40;
        vy = vy * 60;
//        System.out.println("3: " + vx + " " + vy);

        Point2D normalVector = new Point2D.Double(
                -vy + arrowStart.getX(),
                vx + arrowStart.getY()
        );

        String temp = String.valueOf(Math.abs(Math.round(pipe.flow * 100.0) / 100.0));
        AttributedString pipeFlow = new AttributedString(temp + " m3/s");
        pipeFlow.addAttribute(TextAttribute.SUPERSCRIPT, TextAttribute.SUPERSCRIPT_SUPER, temp.length() + 2, temp.length() + 3);

        g.drawString(pipeFlow.getIterator(), (int) normalVector.getX(), (int) normalVector.getY());
    }
}
