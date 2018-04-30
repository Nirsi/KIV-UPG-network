import java.util.ArrayList;
import java.util.HashMap;

public class ComponentCatalog {
    private static ComponentCatalog singleton;
    private HashMap<String, Object> components = new HashMap<>();
    //    private HashMap currentNest = components;
    private String currentNest = "";

    private ComponentCatalog() {

    }

    public static ComponentCatalog getInstance() {
        if (singleton == null) {
            singleton = new ComponentCatalog();
        }

        return singleton;
    }

    public void put(Object key, Object value) {
        String dot = "";

        if (currentNest.length() != 0) {
            dot = ".";
        }

        components.put(currentNest + key.hashCode(), value);
        resetNest();
    }

    public Object get(Object key) {
        String dot = "";

        if (currentNest.length() != 0) {
            dot = ".";
        }

        Object res = components.get(currentNest + key.hashCode());
        resetNest();

        return res;
    }

    public ComponentCatalog nestInto(Object key) {
//        if (currentNest.get(key) == null) {
//            currentNest.put(key, new HashMap<>());
//        }
//
//        currentNest = (HashMap) currentNest.get(key);

        boolean addDot = false;
        if (currentNest.length() != 0) {
            addDot = true;
        }

        currentNest += key.hashCode();

//        if (addDot) {
        currentNest += ".";
//        }

        return this;
    }

    public void resetNest() {
        currentNest = "";
    }

    public ArrayList<Object> getObjectsStartingWithKey(String prefix) {
        ArrayList<Object> keys = new ArrayList<>();

        for (String key : components.keySet()) {
            if (String.valueOf(prefix.hashCode()).equals(key.substring(0, prefix.length() + 1))) {
                keys.add(components.get(key));
            }

        }

        return keys;
    }
}