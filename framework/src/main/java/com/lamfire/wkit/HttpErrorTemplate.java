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
    public static final String TEMPLATE_EXCEPTION = "<html>\n" +
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
            "<h1>Internal server error : %s</h1>\n" +
            "<hr />\n" +
            "<pre>\n" +
            "%s\n" +
            "</pre>\n" +
            "<hr />\n" +
            "<small>\n" +
            "Wkit/%s\n" +
            "</small>\n" +
            "</body></html>";

    public static final String TEMPLATE_PERMISSION_DENIED = "<html>\n" +
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
            "<h1>Permission Denied : %s</h1>\n" +
            "<hr />\n" +
            "<pre>\n" +
            "Need Permissions : %s\n" +
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
            String str = writer.toString();
            result = String.format(TEMPLATE_EXCEPTION, servlet,str, Version.VERSION);
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
            String str = StringUtils.join(permissions,',');
            result = String.format(TEMPLATE_PERMISSION_DENIED, servlet,str, Version.VERSION);
            return result;
        }catch(Exception ex){

        }
        return result;
    }
}
