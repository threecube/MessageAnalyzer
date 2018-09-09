/**
 * 
 */
package com.threecube.prod.analzyer.service;

import java.io.Serializable;

import com.threecube.prod.analzyer.model.DetectResultModel;

/**
 * 
 * @author dingwenbin
 *
 */
public interface GwDetectorInterface<T> extends Serializable{

	/**
	 * detect 
	 * @param dataModel
	 */
	public DetectResultModel detect(T statDataDO);
}
