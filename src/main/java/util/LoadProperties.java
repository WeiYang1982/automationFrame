package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by yangwei on 2015/4/23.
 */
public class LoadProperties {

    private static Properties properties = new Properties();

    private static void loadProperties(String FilePath,String FileName){
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(FilePath + File.separator + FileName));
            properties.load(fileInputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getPropertiesValue(String FilePath, String FileName, String key){
        if (properties.isEmpty()) {
            loadProperties(FilePath,FileName);
        }
        String prop = (String)properties.getProperty(key);
        if (prop != null) {
            prop = prop.trim();
        }else {
            prop = "";
        }
        return prop;
    }




}
