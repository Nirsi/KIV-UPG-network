import java.awt.*;

public class DrawPipes {
    Graphics2D g;

    public DrawPipes(Graphics2D g) {
        this.g = g;
    }

    public void draw(Pipe pipe, int width, int height) {
        //drawMetal(pipe);
        //drawWater(pipe);

        //Kow
        drawPipe(pipe, new Color(50, 50, 50), 15, width, height);
        //Woda
        drawPipe(pipe, new Color(64, 164, 223), 12, width, height);


    }

//    private Point2D getTruePosition(Point2D position)
//    {
////        System.out.println("X: " + position.getX() + " Y: " + position.getY());
////        System.out.println("real X: " + position.getX() * windowWidth / 100 + " real Y: " + position.getY() * windowHeight/ 100 );
//        return new Point2D.Double((position.getX()) / 100.0 * windowWidth, (position.getY()) / 100.0 * windowHeight);
//    }

    private void drawPipe(Pipe pipe, Color color, int strokeSize, int width, int height) {
        g.setStroke(new BasicStroke(strokeSize));
        g.setColor(color);

//        - pipe.start.position.getY() * height / 100) + height/2
//        System.out.println("1: " + Translator.getInstance().getRealX(pipe.start.position.getX()) + " 2: " + Translator.getInstance().getRealX(pipe.start.position.getX()) + " 1-2: " + (int) (Translator.getInstance().getRealX(pipe.start.position.getX()) - Translator.getInstance().getRealX(pipe.start.position.getX())));
        g.drawLine(
                (int) (Translator.getInstance().getRealX(pipe.start.position.getX()) + width / 2),
                (int) (Translator.getInstance().getRealY(pipe.start.position.getY()) + height / 2),
                (int) (Translator.getInstance().getRealX(pipe.end.position.getX()) + width / 2),
                (int) (Translator.getInstance().getRealY(pipe.end.position.getY()) + height / 2)
        );
    }
}
