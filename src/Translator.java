public class Translator {
    private static Translator Instance;

    private double translatedWidth;
    private double translatedHeight;

    private double virtualWidth;
    private double virtualHeight;

    private double X;
    private double Y;

    private double maxX;
    private double maxY;


    /**
     * Returns instance of singleton
     *
     * @return new Translator
     */
    public static Translator getInstance() {
        if (Instance == null) {
            Instance = new Translator();
        }
        return Instance;
    }

    /**
     * Returns real X for drawing
     *
     * @param x
     * @return double
     */
    public double getTranslatedX(double x) {
        X = translatedWidth / virtualWidth * x;

        return X;
    }

    /**
     * Returns real Y for drawing
     *
     * @param y
     * @return
     */
    public double getTranslatedY(double y) {
        Y = translatedHeight / virtualHeight * y;

        return Y;
    }

    /**
     * Sets width of real window
     *
     * @param translatedWidth
     */
    public void setTranslatedWidth(double translatedWidth) {
        this.translatedWidth = translatedWidth;
    }

    /**
     * Sets height of real window
     *
     * @param translatedHeight
     */
    public void setTranslatedHeight(double translatedHeight) {
        this.translatedHeight = translatedHeight;
    }

    /**
     * Sets width of virtual window
     *
     * @param virtualWidth
     */
    public void setVirtualWidth(double virtualWidth) {
        this.virtualWidth = virtualWidth;
    }

    /**
     * Sets ¨height of virtual window
     *
     * @param virtualHeight
     */
    public void setVirtualHeight(double virtualHeight) {
        this.virtualHeight = virtualHeight;
    }

    @Override
    public String toString() {
        return "translatedWidth: " + translatedWidth + " translatedHeight: " + translatedHeight + " virtualWidth: " + virtualWidth + "virtualHeight: " + virtualHeight;
    }
}
