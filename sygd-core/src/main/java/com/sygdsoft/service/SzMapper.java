package com.sygdsoft.service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by 舒展 on 2016/6/16 0016.
 * 用于写在类前边标注跟该类有关的mapper
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public   @interface  SzMapper
{
    String id() default "";
}