package pe.edu.utp.blackdog.service;

import java.util.ResourceBundle;

public class AppConfig {
    static ResourceBundle rb = ResourceBundle.getBundle("app");

    public static String getConnectionStringCFN(){
        String os = System.getProperty("os.name");
        if (os.toLowerCase().contains("windows")) {
            return rb.getString("cnxStringW");

        }else{
            return rb.getString("cnxStringF");
        }
    }
    public static String getImagePath(){
        String os = System.getProperty("os.name");
        if (os.toLowerCase().contains("windows")) {
            return (System.getProperty("user.dir")+ rb.getString("pathW"));
        }else{
            return rb.getString("pathF");
        }
    }
    public static String getLogPath(){
        return rb.getString("logback");
    }
}
