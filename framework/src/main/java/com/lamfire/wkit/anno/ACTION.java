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
@Target( { java.lang.annotation.ElementType.TYPE })
public @interface ACTION {
    public abstract String path() default "";
    public abstract String permissions() default "";
    public abstract boolean singleton() default false;
}

