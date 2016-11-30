package demo;

import com.lamfire.wkit.Version;

/**
 * Created by lamfire on 16/11/30.
 */
public class Test {

    public static void main(String[] args) {
        System.out.println(Version.getInstance().isJavaVersionAndLater("1.8.1_65"));
        System.out.println(Version.getInstance().isJavaVersionAndLater(1,8,0,65));
    }
}
