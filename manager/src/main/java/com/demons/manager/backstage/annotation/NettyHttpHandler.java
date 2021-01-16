package com.demons.manager.backstage.annotation;

import java.lang.annotation.*;

/**
 * @author Outro
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NettyHttpHandler {
    /**
     * 请求路径
     * @return
     */
    String path() default "";

    /**
     * 支持的提交方式
     * @return
     */
    String method() default "POST";

    /**
     * path和请求路径是否需要完全匹配。 如果是PathVariable传参数，设置为false
     * @return 结果
     */
    boolean equals() default true;
}
