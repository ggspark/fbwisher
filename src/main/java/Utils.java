import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Gaurav on 18/06/20.
 */
public class Utils {

    static public String findFile(String filename) {
        String[] paths = {"", "bin/", "target/classes"}; // if you have chromedriver somewhere else on the path, then put it here.
        for (String path : paths) {
            if (new File(path + filename).exists())
                return path + filename;
        }
        return "";
    }

    public static String getIdFromUrl(Set<String> urls) {
        for (String s : urls) {
            Map<String, String> map = getQueryMap(s);
            String id = map.get("id");
            if (id != null && !id.isEmpty()) {
                return id;
            }
        }
        return null;
    }

    public static Map<String, String> getQueryMap(String query) {
        Map<String, String> map = new HashMap<String, String>();
        if (query == null || query.isEmpty()) {
            return map;
        }
        String[] params = query.split("&");
        for (String param : params) {
            String name = param.split("=")[0];
            String value = param.split("=")[1];
            map.put(name, value);
        }
        return map;
    }
}
