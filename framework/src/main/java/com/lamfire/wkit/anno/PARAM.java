package com.lamfire.wkit.anno;

import java.lang.annotation.*;

/**
 * Action Method Parameter Mapping
 * User: linfan
 * Date: 16-4-18
 * Time: 下午5:07
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.PARAMETER })
public @interface PARAM {
    public abstract String value();
}

