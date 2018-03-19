import java.awt.*;
import java.awt.geom.Point2D;

public class Translator
{
    private static Translator Instance;

    private double realWidth;
    private double realHeight;

    private double virtualWidth;
    private double virtualHeight;


    public static Translator getInstance()
    {
        if (Instance == null)
            Instance = new Translator();
        return Instance;
    }

    public double getRealX(double x)
    {
        return realWidth / virtualWidth * x;
    }

    public double getRealY(double y)
    {
        return realHeight / virtualHeight * y;
    }

    @Deprecated
    public Point2D getRealCoords(Point2D virtualPosition)
    {
        return new Point2D.Double(realWidth / virtualWidth * virtualPosition.getX(), realWidth / virtualWidth * virtualPosition.getY());
    }


    public void setRealWidth(double realWidth) {
        this.realWidth = realWidth;
    }

    public void setRealHeight(double realHeight) {
        this.realHeight = realHeight;
    }

    public void setVirtualWidth(double virtualWidth) {
        this.virtualWidth = virtualWidth;
    }

    public void setVirtualHeight(double virtualHeight) {
        this.virtualHeight = virtualHeight;
    }

    @Override
    public String toString() {
        return "realWidth: " + realWidth + " realHeight: " + realHeight + " virtualWidth: " + virtualWidth + "virtualHeight: " + virtualHeight;
    }
}
