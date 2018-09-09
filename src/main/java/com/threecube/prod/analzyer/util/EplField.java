/**
 * 
 */
package com.threecube.prod.analzyer.util;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to mark properties which will be used as partition fields in epl 
 * 
 * @author dingwenbin
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface EplField {
	
	/**
	 * id, please set id from 0 by order
	 * 
	 * @return
	 */
	int id();
	
	/**
	 * field name used by epl
	 * @return
	 */
	String name();
}
