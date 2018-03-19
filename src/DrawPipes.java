import java.awt.*;

public class DrawPipes {
    Graphics2D g;

    /**
     * Constructor for passing Graphics instance.
     * @param g
     */
    public DrawPipes(Graphics2D g) {
        this.g = g;
    }

    public void draw(Pipe pipe, int width, int height) {

        //Kow
        drawPipe(pipe, new Color(50, 50, 50), 15, width, height);
        //Woda
        drawPipe(pipe, new Color(64, 164, 223), 12, width, height);


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
