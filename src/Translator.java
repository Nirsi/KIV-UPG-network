import java.awt.*;
import java.awt.geom.Point2D;

public class Translator
{
    private static Translator Instance;

    private double realWidth;
    private double realHeight;

    private double virtualWidth;
    private double virtualHeight;


    /**
     * Returns instance of singleton
     * @return new Translator
     */
    public static Translator getInstance()
    {
        if (Instance == null)
            Instance = new Translator();
        return Instance;
    }

    /**
     * Returns real X for drawing
     * @param x
     * @return double
     */
    public double getRealX(double x)
    {
        return realWidth / virtualWidth * x;
    }

    /**
     * Returns real Y for drawing
     * @param y
     * @return
     */
    public double getRealY(double y)
    {
        return realHeight / virtualHeight * y;
    }

    /**
     * Returns Point2D of real X and Y for drawing
     * @param virtualPosition
     * @return
     */
    @Deprecated
    public Point2D getRealCoords(Point2D virtualPosition)
    {
        return new Point2D.Double(realWidth / virtualWidth * virtualPosition.getX(), realWidth / virtualWidth * virtualPosition.getY());
    }

    /**
     * Sets width of real window
     * @param realWidth
     */
    public void setRealWidth(double realWidth) {
        this.realWidth = realWidth;
    }

    /**
     * Sets height of real window
     * @param realHeight
     */
    public void setRealHeight(double realHeight) {
        this.realHeight = realHeight;
    }

    /**
     * Sets width of virtual window
     * @param virtualWidth
     */
    public void setVirtualWidth(double virtualWidth) {
        this.virtualWidth = virtualWidth;
    }

    /**
     * Sets ¨height of virtual window
     * @param virtualHeight
     */
    public void setVirtualHeight(double virtualHeight) {
        this.virtualHeight = virtualHeight;
    }

    @Override
    public String toString() {
        return "realWidth: " + realWidth + " realHeight: " + realHeight + " virtualWidth: " + virtualWidth + "virtualHeight: " + virtualHeight;
    }
}
