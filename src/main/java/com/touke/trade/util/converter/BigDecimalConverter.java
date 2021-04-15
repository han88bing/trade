package com.touke.trade.util.converter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

import java.math.BigDecimal;

/**
 * int 类型转换
 * 
 *
 */
public class BigDecimalConverter implements Converter<String, BigDecimal> {

	@Override
	public BigDecimal convert(String source) {
		if(StringUtils.isBlank(source)){
			return null;
		}
		return new BigDecimal(source);
	}
}
