import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Gaurav on 18/06/20.
 */
public class Utils {

    /**
     * Get parameter "id" from Set of urls
     *
     * @param urls Set of urls that might contain id param
     * @return id value if found; null otherwise
     */
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

    /**
     * Get map of query parameters from url
     *
     * @param url url with query params
     * @return map of query params
     */
    public static Map<String, String> getQueryMap(String url) {
        Map<String, String> map = new HashMap<>();
        if (url == null || url.isEmpty()) {
            return map;
        }
        String[] params = url.split("&");
        for (String param : params) {
            String name = param.split("=")[0];
            String value = param.split("=")[1];
            map.put(name, value);
        }
        return map;
    }

    /**
     * Sleep thread for {@code seconds}
     *
     * @param seconds number of seconds to delay
     */
    public static void delay(int seconds) {
        try {
            Thread.sleep(seconds * 1000); // sleep for 1 second.
        } catch (Exception x) {
            x.printStackTrace();
        }
    }
}
