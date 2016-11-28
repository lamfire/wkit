package com.lamfire.wkit;

import com.lamfire.utils.IOUtils;
import org.apache.commons.lang.StringUtils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Set;

/**
 * HttpErrorTemplate
 * User: linfan
 * Date: 16-4-18
 * Time: 下午9:06
 * To change this template use File | Settings | File Templates.
 */
public class HttpErrorTemplate {
    public static final String TEMPLATE = "<html>\n" +
            "<head><title>Request Error report</title>\n" +
            "<style>\n" +
            "<!--\n" +
            "H1 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:22px;height:48px;line-height:48px;} \n" +
            "BODY {font-family:Tahoma,Arial,sans-serif;color:black;background-color:white;} \n" +
            "HR {color : #525D76;}\n" +
            "-->\n" +
            "</style>\n" +
            "</head>\n" +
            "<body>\n" +
            "<h1>$s : %s</h1>\n" +
            "<hr />\n" +
            "<pre>\n" +
            "%s\n" +
            "</pre>\n" +
            "<hr />\n" +
            "<small>\n" +
            "Wkit/%s\n" +
            "</small>\n" +
            "</body></html>";


    public static String getExceptionTemplate(String servlet,Exception e){
        String result = null;
        StringWriter writer = new StringWriter();
        PrintWriter print  = new PrintWriter(writer, true);
        try{
            e.printStackTrace(print);
            String body = writer.toString();
            result = getTemplate("Http Server Internal Error",servlet,body);
            return result;
        }catch(Exception ex){

        }finally{
            IOUtils.closeQuietly(print);
            IOUtils.closeQuietly(writer);
        }
        return result;
    }

    public static String getPermissionDeniedTemplate(String servlet,Set<String> permissions){
        String result = null;
        try{
            String body = "Need Permissions : " + StringUtils.join(permissions,',');
            result = getTemplate("Permission Denied",servlet,body);
            return result;
        }catch(Exception ex){

        }
        return result;
    }

    public static String getNotAuthrizedTemplate(String servlet){
        String result = null;
        try{
            String body = "This request requires authorization to be accessed.";
            result = getTemplate("Not Authrized",servlet,body);
            return result;
        }catch(Exception ex){

        }
        return result;
    }

    public static String getTemplate(String title,String url,String body){
        return String.format(TEMPLATE,title,url,body,Version.VERSION);
    }
}
