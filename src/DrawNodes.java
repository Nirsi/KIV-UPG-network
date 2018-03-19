import java.awt.*;

public class DrawNodes
{
    private Graphics2D g;

    /**
     * Constructor for passing Graphics instance.
     * @param g
     */
    public DrawNodes(Graphics2D g)
    {
        this.g = g;
    }

    /**
     * Drawing of nodes
     * @param node
     * @param width
     * @param height
     */
    public void draw(NetworkNode node, int width, int height)
    {
        drawNode(node, new Color(50, 50, 50),width,height);
        drawNode(node, Color.BLUE,width,height);
    }

    /**
     * Drawing respective Node
     * @param node
     * @param color
     * @param width
     * @param height
     */
    private void drawNode(NetworkNode node, Color color, int width, int height)
    {
        g.setColor(color);

        g.fillOval(
                (int) Translator.getInstance().getTranslatedX(node.position.getX()) + width,
                (int) Translator.getInstance().getTranslatedY(node.position.getY()) + width,
                width,
                height
        );
    }
}
