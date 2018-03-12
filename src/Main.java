public class Main
{
    public static void main(String[] args) {
        WaterNetwork waterNetwork = new WaterNetwork();

        DrawComponents dc = new DrawComponents(waterNetwork);
        dc.paintComponent();
    }
}
