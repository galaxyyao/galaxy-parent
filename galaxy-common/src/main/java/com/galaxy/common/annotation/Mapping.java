package com.galaxy.common.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @author: 姚皓
 * @date: 2019/7/16 11:38
 * @description:
 */
@Target({ METHOD, FIELD })
@Retention(RUNTIME)
public @interface Mapping {

	String name() default "";

}