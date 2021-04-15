package com.touke.trade.controller;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.touke.trade.pojo.TkUserAccount;
import com.touke.trade.service.TkTradeAccountService;
import com.touke.trade.service.TkTradeService;
import com.touke.trade.util.context.UserContext;
import com.touke.trade.vo.request.TradeRecordPageRequest;
import com.touke.trade.vo.response.CommResult;
import com.touke.trade.vo.response.TradeAccountFlowResponse;
import com.touke.trade.vo.response.TradeAccountResponse;

/**
 * 账户相关
 * @author allah
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TkTradeAccountControllerTest {

	
	@Autowired
	private TkTradeAccountService tkTradeAccountService;

	@Autowired
	private TkTradeService tkTradeService;
	
	/**个人账户列表*/
	@Test
	public void detail() {
		CommResult<List<TradeAccountResponse>> result = tkTradeAccountService.detail(UserContext.getUserId());
		Assert.assertTrue(result.isOk());
		System.out.println(JSON.toJSONString(result));
	}
		
	
	
	
	
	/**股票账户流水信息*/
	@Test
	public void accountFlow() {
		TkUserAccount tkUserAccount = tkTradeAccountService.accountFlow(1);
		if(null == tkUserAccount) {
			return ;
		}
		
		//数据聚合
		TradeAccountFlowResponse response =new TradeAccountFlowResponse();
		BeanUtils.copyProperties(tkUserAccount, response);
		TradeRecordPageRequest request = TradeRecordPageRequest.builder()
				.securityCode(tkUserAccount.getSecurityCode())
				.userId(tkUserAccount.getUserId())
				.build();
		response.setFlowRecord(tkTradeService.record(request).getData());
		Assert.assertTrue(null == response);
		System.out.println(JSON.toJSONString(response));
	}
	
}
