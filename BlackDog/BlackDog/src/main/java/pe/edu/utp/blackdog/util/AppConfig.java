package pe.edu.utp.blackdog.util;

import java.util.ResourceBundle;

public class AppConfig {
    static ResourceBundle rb = ResourceBundle.getBundle("config");

    public static String getConnectionStringCFN(){
        return rb.getString("conenctionString");
    }
    public static String getErrorLog(){ return rb.getString("errorLog"); }

    public static String separator(){
        return System.getProperty("file.separator");
    }

    public static String getTemplateDir(){
        String os = System.getProperty("os.name");
        if (os.toLowerCase().contains("windows")) {
            return rb.getString("template_dir");
        }else{
            return rb.getString("template_dir_unix");
        }
    }

    public static String getDocumentRoot(){
        String os = System.getProperty("os.name");
        if (os.toLowerCase().contains("windows")) {
            return rb.getString("doc_root");
        }else{
            return rb.getString("doc_root_unix");
        }
    }

    public static String imgDir(){
        String os = System.getProperty("os.name");
        if (os.toLowerCase().contains("windows")) {
            return rb.getString("img");
        }else{
            return rb.getString("img_unix");
        }
    }
/*
    public static String getGrafoTemplate() { return getTemplateDir() + separator() + "GrafoClientesDeEmprendedor.html";}
    public static String getPortalCliente() { return getDocumentRoot() + separator() + "portalClient.html";}
*/
}
