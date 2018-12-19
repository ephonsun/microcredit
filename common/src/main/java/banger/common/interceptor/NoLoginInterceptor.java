package banger.common.interceptor;

import java.lang.annotation.*;

/**
 * 类解释
 *
 * @Version 1.0 2017/3/30
 * @Author Merlin
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
    public @interface NoLoginInterceptor {
}
