package global;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by mrunal on 7/15/2017.
 */
public class Configuration {
    public String flipkarturl, shopclues, snapdealurl;
    public int maxtimeout, pollinginterval;
    private Properties properties = new Properties();

    public Configuration() throws IOException {
        loadProperties();
    }

    private void loadProperties() throws IOException {
        properties.load(Configuration.class.getResourceAsStream("/conf/environment.properties"));
        flipkarturl = properties.getProperty("flipkart.url");
        shopclues = properties.getProperty("shopclues.url");
        snapdealurl = properties.getProperty("snapdeal.url");
        maxtimeout = Integer.parseInt(getProperty("maxtimeout"));
        pollinginterval = Integer.parseInt(getProperty("pollinginterval"));
    }

    public String getProperty(String propertyName) {
        return properties.getProperty(propertyName);
    }
}
