package UITestData;

import java.io.FileInputStream;
import java.util.Properties;

public class GetData {

    protected static Properties DATA;
    protected static FileInputStream fileInputStream;

    static {
        try {
            fileInputStream = new FileInputStream("src/test/resources/data");
            DATA = new Properties();
            DATA.load(fileInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getData(String key) {
        return DATA.getProperty(key);
    }
}
