package com.tutorial.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.FIELD, ElementType.METHOD })
public @interface AnnoDemo {
	public final static Logger logger = LogManager.getLogger(AnnoDemo.class.getName());

	int intValue();

	String strValue();
}