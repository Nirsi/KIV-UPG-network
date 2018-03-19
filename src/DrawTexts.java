import java.awt.*;
import java.awt.font.TextAttribute;
import java.awt.geom.Point2D;
import java.text.AttributedString;

public class DrawTexts {

    Graphics2D g;

    Point2D vector;
    Point2D arrowStart;
    Point2D arrowEnd;
    Point2D normalVector;
    Point2D normalVectorStart;
    Point2D normalVectorEnd;

    public DrawTexts(Graphics2D g) {
        this.g = g;
    }

    public void draw(Pipe pipe, Point2D vector, Point2D arrowStart, Point2D arrowEnd) {
        this.vector = vector;
        this.arrowStart = arrowStart;
        this.arrowEnd = arrowEnd;

        int vx = (int) (arrowEnd.getX() - arrowStart.getX());
        int vy = (int) (arrowEnd.getY() - arrowStart.getY());
        double vectorLenght = Math.sqrt((vx * vx) + (vy * vy));

        normalVector = new Point2D.Double(
                -vy + arrowStart.getX(),
                vx + arrowStart.getY()
        );

//        g.drawLine(
//                (int) (arrowStart.getX()),
//                (int) (arrowStart.getY()),
//                (int) (normalVector.getX()),
//                (int) (normalVector.getY())
//        )

        String temp = String.valueOf(Math.round(pipe.flow * 100.0) / 100.0);
        AttributedString pipeFlow = new AttributedString(temp + " m3/s");
        pipeFlow.addAttribute(TextAttribute.SUPERSCRIPT, TextAttribute.SUPERSCRIPT_SUPER, temp.length() + 2, temp.length() + 3);


        g.drawString(pipeFlow.getIterator(), (int) normalVector.getX(), (int) normalVector.getY());
    }
}