package com.lamfire.wkit.anno;

import java.lang.annotation.*;

/**
 * Action anno
 * User: linfan
 * Date: 16-4-18
 * Time: 下午5:07
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.METHOD,ElementType.TYPE })
public @interface MAPPING {
    public abstract String path();
    public abstract String permissions() default "";
}

