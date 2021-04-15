package com.touke.trade.util.converter;


import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

/**
 * long类型转换
 * 
 *
 */
public class LongConverter implements Converter<String, Long> {


	@Override
	public Long convert(String source) {
		if(StringUtils.isBlank(source)){
			return null;
		}
		return Long.parseLong(source);
	}
}
