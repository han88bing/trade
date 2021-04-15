package com.touke.trade.util.converter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

/**
 * int 类型转换
 * 
 *
 */
public class IntegerConverter implements Converter<String, Integer> {

	@Override
	public Integer convert(String source) {
		if(StringUtils.isBlank(source)){
			return null;
		}
		return Integer.parseInt(source);
	}
}
