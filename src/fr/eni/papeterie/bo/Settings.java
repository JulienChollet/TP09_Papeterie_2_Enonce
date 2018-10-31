package fr.eni.papeterie.bo;

import java.io.IOException;
import java.util.Properties;

public class Settings {

    private static Properties properties;
        static{
            System.out.println("exec on call");
            properties = new Properties();
            try {
                properties.load(Settings.class.getResourceAsStream("settings.properties"));
            } catch (IOException e) {
                e.printStackTrace(); //TODO set on logger api
            }
        }


    /**
     * get a single property on file
     *
     * @param key search key on file
     * @return key
     */
    public static String getProperty(String key){
        return properties.getProperty(key);
    }
}
