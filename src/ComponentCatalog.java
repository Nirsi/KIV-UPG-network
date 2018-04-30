
import org.jfree.data.xy.XYSeries;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;

public class ComponentCatalog {

    //Detection field for respective network components
    public static HashMap<Reservoir, Rectangle2D> reservoirsDetection = new HashMap<>();
    public static HashMap<Pipe, Ellipse2D> valves = new HashMap<>();
    public static HashMap<Pipe, Ellipse2D> arrows = new HashMap<>();

    //XYSeries for graphs of respective network components
    public static HashMap<Reservoir, XYSeries>  reservoirsGraphs = new HashMap<>();
    public static HashMap<Pipe, XYSeries> pipeGraphs = new HashMap<>();

}
