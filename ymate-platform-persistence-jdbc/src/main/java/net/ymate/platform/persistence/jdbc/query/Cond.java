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
package net.ymate.platform.persistence.jdbc.query;

import net.ymate.platform.persistence.Params;
import org.apache.commons.lang.StringUtils;

/**
 * 条件对象
 *
 * @author 刘镇 (suninformation@163.com) on 15/5/9 下午8:12
 * @version 1.0
 */
public final class Cond {

    public enum OPT {
        EQ("="),
        NOT_EQ("!="),
        LT("<"),
        GT(">"),
        LT_EQ("<="),
        GT_EQ(">="),
        LIKE("LIKE");

        private String __opt;

        OPT(String opt) {
            this.__opt = opt;
        }

        @Override
        public String toString() {
            return __opt;
        }
    }

    private StringBuilder __condSB;

    /**
     * SQL参数集合
     */
    private Params __params;

    public static Cond create() {
        return new Cond();
    }

    private Cond() {
        __condSB = new StringBuilder();
        __params = Params.create();
    }

    public Params params() {
        return this.__params;
    }

    public Cond param(Object param) {
        this.__params.add(param);
        return this;
    }

    public Cond param(Params params) {
        this.__params.add(params);
        return this;
    }

    public Cond cond(String cond) {
        __condSB.append(" ").append(cond).append(" ");
        return this;
    }

    public Cond cond(Cond cond) {
        __condSB.append(cond.toString());
        __params.add(cond.params());
        return this;
    }

    public Cond opt(String prefixA, String fieldA, OPT opt, String prefixB, String fieldB) {
        if (StringUtils.isNotBlank(prefixA)) {
            fieldA = prefixA.concat(".").concat(fieldA);
        }
        if (StringUtils.isNotBlank(prefixB)) {
            fieldB = prefixB.concat(".").concat(fieldB);
        }
        return opt(fieldA, opt, fieldB);
    }

    public Cond opt(String fieldA, OPT opt, String fieldB) {
        __condSB.append(fieldA).append(" ").append(opt).append(" ").append(fieldB);
        return this;
    }

    public Cond opt(String prefix, String field, OPT opt) {
        if (StringUtils.isNotBlank(prefix)) {
            field = prefix.concat(".").concat(field);
        }
        return opt(field, opt);
    }

    public Cond opt(String field, OPT opt) {
        __condSB.append(field).append(" ").append(opt).append(" ?");
        return this;
    }

    public Cond eq(String prefix, String field) {
        if (StringUtils.isNotBlank(prefix)) {
            field = prefix.concat(".").concat(field);
        }
        return eq(field);
    }

    public Cond eq(String field) {
        return opt(field, OPT.EQ);
    }

    public Cond notEq(String prefix, String field) {
        if (StringUtils.isNotBlank(prefix)) {
            field = prefix.concat(".").concat(field);
        }
        return notEq(field);
    }

    public Cond notEq(String field) {
        return opt(field, OPT.NOT_EQ);
    }

    public Cond gtEq(String prefix, String field) {
        if (StringUtils.isNotBlank(prefix)) {
            field = prefix.concat(".").concat(field);
        }
        return gtEq(field);
    }

    public Cond gtEq(String field) {
        return opt(field, OPT.GT_EQ);
    }

    public Cond gt(String prefix, String field) {
        if (StringUtils.isNotBlank(prefix)) {
            field = prefix.concat(".").concat(field);
        }
        return gt(field);
    }

    public Cond gt(String field) {
        return opt(field, OPT.GT);
    }

    public Cond ltEq(String prefix, String field) {
        if (StringUtils.isNotBlank(prefix)) {
            field = prefix.concat(".").concat(field);
        }
        return ltEq(field);
    }

    public Cond ltEq(String field) {
        return opt(field, OPT.LT_EQ);
    }

    public Cond lt(String prefix, String field) {
        if (StringUtils.isNotBlank(prefix)) {
            field = prefix.concat(".").concat(field);
        }
        return lt(field);
    }

    public Cond lt(String field) {
        return opt(field, OPT.LT);
    }

    public Cond like(String prefix, String field) {
        if (StringUtils.isNotBlank(prefix)) {
            field = prefix.concat(".").concat(field);
        }
        return like(field);
    }

    public Cond like(String field) {
        return opt(field, OPT.LIKE);
    }

    public Cond between(String prefix, String field, Object valueOne, Object valueTwo) {
        if (StringUtils.isNotBlank(prefix)) {
            field = prefix.concat(".").concat(field);
        }
        return between(field, valueOne, valueTwo);
    }

    public Cond between(String field, Object valueOne, Object valueTwo) {
        __condSB.append(field).append(" BETWEEN ? AND ?");
        __params.add(valueOne).add(valueTwo);
        return this;
    }

    public Cond isNull(String prefix, String field) {
        if (StringUtils.isNotBlank(prefix)) {
            field = prefix.concat(".").concat(field);
        }
        return isNull(field);
    }

    public Cond isNull(String field) {
        __condSB.append(field).append(" IS NULL");
        return this;
    }

    public Cond isNotNull(String prefix, String field) {
        if (StringUtils.isNotBlank(prefix)) {
            field = prefix.concat(".").concat(field);
        }
        return isNotNull(field);
    }

    public Cond isNotNull(String field) {
        __condSB.append(field).append(" IS NOT NULL");
        return this;
    }

    public Cond and() {
        return cond("AND");
    }

    public Cond or() {
        return cond("OR");
    }

    public Cond not() {
        return cond("NOT");
    }

    public Cond bracketBegin() {
        return cond("(");
    }

    public Cond bracketEnd() {
        return cond(")");
    }

    public Cond exists(SQL subSql) {
        __condSB.append(" EXISTS (").append(subSql.getSQL()).append(")");
        __params.add(subSql.params());
        return this;
    }

    public Cond exists(Select subSql) {
        __condSB.append(" EXISTS (").append(subSql.toString()).append(")");
        __params.add(subSql.getParams());
        return this;
    }

    public Cond in(String prefix, String field, SQL subSql) {
        if (StringUtils.isNotBlank(prefix)) {
            field = prefix.concat(".").concat(field);
        }
        return in(field, subSql);
    }

    public Cond in(String field, SQL subSql) {
        __condSB.append(field).append(" IN (").append(subSql.getSQL()).append(")");
        __params.add(subSql.params());
        return this;
    }

    public Cond in(String prefix, String field, Select subSql) {
        if (StringUtils.isNotBlank(prefix)) {
            field = prefix.concat(".").concat(field);
        }
        return in(field, subSql);
    }

    public Cond in(String field, Select subSql) {
        __condSB.append(field).append(" IN (").append(subSql.toString()).append(")");
        __params.add(subSql.getParams());
        return this;
    }

    public Cond in(String prefix, String field, Params params) {
        if (StringUtils.isNotBlank(prefix)) {
            field = prefix.concat(".").concat(field);
        }
        return in(field, params);
    }

    public Cond in(String field, Params params) {
        __condSB.append(field).append(" IN (").append(StringUtils.repeat("?", ", ", params.params().size())).append(")");
        __params.add(params);
        return this;
    }

    @Override
    public String toString() {
        return __condSB.toString();
    }
}
