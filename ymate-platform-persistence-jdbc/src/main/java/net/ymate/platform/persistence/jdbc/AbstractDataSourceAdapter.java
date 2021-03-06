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
package net.ymate.platform.persistence.jdbc;

import net.ymate.platform.core.util.ClassUtils;
import net.ymate.platform.persistence.jdbc.dialect.IDialect;
import org.apache.commons.lang.StringUtils;

/**
 * 抽象数据源适配器
 *
 * @author 刘镇 (suninformation@163.com) on 2013年8月1日 下午8:30:34
 * @version 1.0
 */
public abstract class AbstractDataSourceAdapter implements IDataSourceAdapter {

    protected DataSourceCfgMeta __cfgMeta;

    protected IDialect __dialect;

    protected boolean __inited;

    public void initialize(DataSourceCfgMeta cfgMeta) throws Exception {
        if (!__inited) {
            this.__cfgMeta = cfgMeta;
            //
            if (StringUtils.isNotBlank(cfgMeta.getDialectClass())) {
                __dialect = ClassUtils.impl(__cfgMeta.getDialectClass(), IDialect.class, this.getClass());
            } else {
                __dialect = JDBC.DB_DIALECTS.get(__cfgMeta.getType()).newInstance();
            }
            //
            __doInit();
            //
            __inited = true;
        }
    }

    public DataSourceCfgMeta getDataSourceCfgMeta() {
        return __cfgMeta;
    }

    protected abstract void __doInit() throws Exception;

    public IDialect getDialect() {
        return __dialect;
    }

    public void destroy() {
        if (__inited) {
            __inited = false;
            //
            __cfgMeta = null;
            __dialect = null;
        }
    }
}
