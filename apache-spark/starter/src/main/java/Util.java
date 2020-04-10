import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Util {

    private static Set<String> borings = new HashSet<>();
    private static Map<String, String> corrections = new HashMap<>();

    static {
        InputStream is = Main.class.getResourceAsStream("/boringwords.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        br.lines().forEach(it -> borings.add(it));

        corrections.put("jav", "java");
    }

    public static boolean isBoring(String word) {
        return borings.contains(word);
    }

    public static boolean isNotBoring(String word) {
        return !isBoring(word);
    }
}
