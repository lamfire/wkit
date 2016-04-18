package com.lamfire.wkit;

/**
 * Created with IntelliJ IDEA.
 * User: linfan
 * Date: 16-4-18
 * Time: 下午9:06
 * To change this template use File | Settings | File Templates.
 */
public class ExceptionPageTemplate {
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
}
