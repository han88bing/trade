package com.touke.trade.controller;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.touke.trade.enums.TkTradeRecordEnums;
import com.touke.trade.service.TkTradeService;
import com.touke.trade.vo.request.TradeBuyRequest;
import com.touke.trade.vo.request.TradeRecordPageRequest;
import com.touke.trade.vo.request.TradeSellRequest;
import com.touke.trade.vo.response.CommResult;
import com.touke.trade.vo.response.TradeInfoResponse;
import com.touke.trade.vo.response.TradeRecordResponse;


/**
 * 交易相关
 * @author allah
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TkTradeControllerTest {

	@Autowired
	TkTradeService tkTradeService;
	
	
	/**购买*/
	@Test
	public void buy() {
		TradeBuyRequest request = TradeBuyRequest.builder()
				.quantity(10)
				.securityCode("REL")
				.build();
		
		CommResult<Long> result = tkTradeService.buy(request);
		Assert.assertTrue(result.isOk());
		System.out.println(JSON.toJSONString(result));
	}
	
	/** 卖出*/
	@Test
	public void sell() {
		TradeSellRequest request = TradeSellRequest.builder()
				.quantity(10)
				.securityCode("REL")
				.build();
		CommResult<Long> result = tkTradeService.sell(request);
		Assert.assertTrue(result.isOk());
		System.out.println(JSON.toJSONString(result));
	}
	
	
	/**订单详情*/
	@Test
	public void detail() {
		CommResult<TradeInfoResponse> result =  tkTradeService.detail(6745845845784L);
		Assert.assertTrue(result.isOk());
		System.out.println(JSON.toJSONString(result));
	}
	
	
	/**订单详情*/
	@Test
	public void record() {
		
		TradeRecordPageRequest request = TradeRecordPageRequest.builder()
				.pageNum(1)
				.pageSize(20)
				.type(TkTradeRecordEnums.tradeType.BUY.getCode())
				.userId(1).build();
		CommResult<List<TradeRecordResponse>> result = tkTradeService.record(request);	
		Assert.assertTrue(result.isOk());
		System.out.println(JSON.toJSONString(result));
	}
	
	/**订单详情*/
	@Test
	public void recordDetail() {
		CommResult<TradeRecordResponse> result =  tkTradeService.recordDetail(1);
		Assert.assertTrue(result.isOk());
		System.out.println(JSON.toJSONString(result));
	}
	
}
