package com.touke.trade.job;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.touke.trade.enums.TkTradeEnums;
import com.touke.trade.service.TkTradeService;
import com.touke.trade.vo.request.TradeCallBackRequest;
import com.touke.trade.vo.response.CommResult;


/**
 * 交易相关
 * @author allah
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TkTradeJobTest {

	@Autowired
	TkTradeService tkTradeService;
	
	
	/**交易结果*/
	@Test
	public void buy() {
		TradeCallBackRequest request = TradeCallBackRequest.builder()
				.id(1)
				.result(TkTradeEnums.result.SUCCESS.getCode())
				.build();
		
		CommResult<Void> result = tkTradeService.tradeCallback(request);
		Assert.assertTrue(result.isOk());
		System.out.println(JSON.toJSONString(result));
	}
	
	
	
}
