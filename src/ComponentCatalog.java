import java.util.HashMap;

public class ComponentCatalog {
    private static ComponentCatalog singleton;
    private HashMap<Object, Object> components = new HashMap<>();
    private HashMap currentNest = components;

    private ComponentCatalog() {

    }

    public static ComponentCatalog getInstance() {
        if (singleton == null) {
            singleton = new ComponentCatalog();
        }

        return singleton;
    }

    public void put(Object key, Object value) {
        currentNest.put(key, value);
        resetNest();
    }

    public Object get(Object key) {
        Object res = currentNest.get(key);
        resetNest();

        return res;
    }

    public ComponentCatalog nestInto(Object key) {
        if (currentNest.get(key) == null) {
            currentNest.put(key, new HashMap<>());
        }

        currentNest = (HashMap) currentNest.get(key);

        return this;
    }

    public void resetNest() {
        currentNest = components;
    }
}