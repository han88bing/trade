package com.touke.trade.aspect.xianliu;
import java.lang.annotation.*;

/**
 * Created by robbyzhan on 2020/7/11.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Bucket {

}