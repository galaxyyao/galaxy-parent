package com.galaxy.common.annotation;

import com.galaxy.common.enums.SensitiveFieldEnum;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author: 何民明
 * @date: 2019/7/16 11:38
 * @description:
 * 根据相应权限判断加了该注释的字段是否需要掩码
 */
@Target(FIELD)
@Retention(RUNTIME)
public @interface SensitiveField {

//    有些字段的掩码需要特殊处理，例如手机号码，只需要中间几位数掩码；默认情况下是全部掩码。目前SensitiveFieldEnum只有两种需要特殊处理的字段类型，若要添加，还需在SensitiveFieldUtil特殊处理
    SensitiveFieldEnum type() default SensitiveFieldEnum.DEFAULT;
}
