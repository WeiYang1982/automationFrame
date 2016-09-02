package util;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.Reporter;

import java.io.File;

/**
 * Created by A on 2014/8/14.
 */
public class LogWriter {
    public static Logger log ;
    private String CaseFileName;
    Reporter reporter = new Reporter();

    public void writeFileAndReport(String message){
        reporter.log(message);
        log.error(message);
    }

    public Logger setLoggerWriter(String LogConfigFilePath){
        try {
            CaseFileName = Thread.currentThread().getStackTrace()[6].getFileName();
        }catch (Throwable throwable){
            if (throwable.getClass().equals(ArrayIndexOutOfBoundsException.class)){
                CaseFileName = Thread.currentThread().getStackTrace()[1].getFileName();
            }
        }
        log = Logger.getLogger(CaseFileName) ;
        try{
            File file = new File(LogConfigFilePath);
            if(file ==null && !file.exists()){
                file.mkdirs();
            }
            PropertyConfigurator.configure(LogConfigFilePath + File.separator + "log4j.properties");
            return log;
        }  catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


}
