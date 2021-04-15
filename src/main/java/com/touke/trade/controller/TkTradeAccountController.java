package com.touke.trade.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.touke.trade.pojo.TkUserAccount;
import com.touke.trade.service.TkTradeAccountService;
import com.touke.trade.service.TkTradeService;
import com.touke.trade.util.context.UserContext;
import com.touke.trade.vo.request.TradeRecordPageRequest;
import com.touke.trade.vo.response.CommResult;
import com.touke.trade.vo.response.TradeAccountFlowResponse;
import com.touke.trade.vo.response.TradeAccountResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value="交易信息")
@RestController
@RequestMapping("/api/trade/account")
public class TkTradeAccountController {

	
	@Autowired
	private TkTradeAccountService tkTradeAccountService;

	@Autowired
	private TkTradeService tkTradeService;
	
	@ApiOperation(value="个人账户列表", notes="个人账户列表")
	@GetMapping("/my")
	public CommResult<List<TradeAccountResponse>> detail() {
		return tkTradeAccountService.detail(UserContext.getUserId());
	}
		
	
	
	
	
	@ApiOperation(value="股票账户流水信息", notes="股票账户流水信息")
	@GetMapping("/detail/{id}")
	public CommResult<TradeAccountFlowResponse> accountFlow(@PathVariable("id")Integer id) {
		TkUserAccount tkUserAccount = tkTradeAccountService.accountFlow(id);
		if(null == tkUserAccount) {
			return CommResult.ok();
		}
		
		//数据聚合
		TradeAccountFlowResponse response =new TradeAccountFlowResponse();
		BeanUtils.copyProperties(tkUserAccount, response);
		TradeRecordPageRequest request = TradeRecordPageRequest.builder()
				.securityCode(tkUserAccount.getSecurityCode())
				.userId(tkUserAccount.getUserId())
				.build();
		response.setFlowRecord(tkTradeService.record(request).getData());
        return CommResult.ok(response);		
	}
	
}
