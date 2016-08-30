package util;

import java.io.File;

/**
 * Created by yangwei on 2015/5/14.
 */
public class BasicPath {

    private String ErrorImgFilePath;
    private String LogFilePath;
    private String ConfigFilePath;
    private String TestDataFilePath;

    public String getErrorImgFilePath() {
        return ErrorImgFilePath;
    }

    public String getLogFilePath() {
        return LogFilePath;
    }

    public String getConfigFilePath() {
        return ConfigFilePath;
    }

    public String getTestDataFilePath() {
        return TestDataFilePath;
    }

    public void setFilePath(String errorImgFilePath,String logFilePath,String configFilePath,String testDataFilePath) {
        System.out.println(System.getProperty("user.dir") + File.separator + "error_img");
        System.out.println(System.getProperty("Browser"));
        ErrorImgFilePath = errorImgFilePath;
        LogFilePath =logFilePath;
        ConfigFilePath = configFilePath;
        TestDataFilePath = testDataFilePath;
    }
}
