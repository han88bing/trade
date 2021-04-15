package com.touke.trade.util.converter;


import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

/**
 * double类型转换
 * 
 *
 */
public class DoubleConverter implements Converter<String, Double> {

	@Override
	public Double convert(String source) {
		if(StringUtils.isBlank(source)){
			return null;
		}
		return Double.parseDouble(source);
	}
}
