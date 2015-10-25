/*
 * Copyright 2007-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.ymate.platform.webmvc.annotation;

import java.lang.annotation.*;

/**
 * 绑定请求头中的参数变量
 *
 * @author 刘镇 (suninformation@163.com) on 2012-12-20 下午5:04:03
 * @version 1.0
 */
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestHeader {

    /**
     * @return 绑定的参数名称
     */
    String value() default "";

    /**
     * @return 绑定的参数名称前缀
     */
    String prefix() default "";

    /**
     * @return 默认值
     */
    String defaultValue() default "";
}
