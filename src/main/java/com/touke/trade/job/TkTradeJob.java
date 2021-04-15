package com.touke.trade.job;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.touke.trade.service.TkTradeService;
import com.touke.trade.vo.request.TradeCallBackRequest;
import com.touke.trade.vo.response.CommResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value="订单回调")
@RestController
@RequestMapping("/api/callback/trade")
public class TkTradeJob {
	
	
	@Autowired
	private TkTradeService tkTradeService;
	
	@ApiOperation(value="股票交易结果", notes="股票交易结果")
	@PostMapping("/result")
	public CommResult<Void> result(@Valid @RequestBody TradeCallBackRequest request) {
		return tkTradeService.tradeCallback(request);
	}

}
