
import java.util.ArrayList;

/**
 * Created by mehdi on 4/25/17.
 */
public class Messag {
    private static String name;
    private static String type;
    public ArrayList<ArrayList<ArrayList<String>>> msg = new ArrayList<>();

    public Messag(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public static String getName() {
        return name;
    }

    public static String getType() {
        return type;
    }

    public ArrayList<ArrayList<ArrayList<String>>> getMsg() {
        return msg;
    }

    public void setMsg(ArrayList<ArrayList<ArrayList<String>>> msg) {
        this.msg = msg;
    }
}
