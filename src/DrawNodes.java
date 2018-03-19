import java.awt.*;

public class DrawNodes
{
    private Graphics2D g;

    public DrawNodes(Graphics2D g)
    {
        this.g = g;
    }

    public void draw(NetworkNode node, int width, int height)
    {
        drawNode(node, new Color(50, 50, 50),width,height);
        drawNode(node, new Color(64, 164, 223),width,height);
    }
    private void drawNode(NetworkNode node, Color color, int width, int height)
    {
        g.setColor(color);

        g.fillOval(
                (int) Translator.getInstance().getRealX(node.position.getX()) + width,
                (int) Translator.getInstance().getRealY(node.position.getY()) + width,
                width,
                height
        );
    }
}
