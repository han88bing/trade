package com.touke.trade.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.touke.trade.service.TkTradeService;
import com.touke.trade.vo.request.TradeBuyRequest;
import com.touke.trade.vo.request.TradeRecordPageRequest;
import com.touke.trade.vo.request.TradeSellRequest;
import com.touke.trade.vo.response.CommResult;
import com.touke.trade.vo.response.TradeInfoResponse;
import com.touke.trade.vo.response.TradeRecordResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value="交易信息")
@RestController
@RequestMapping("/api/trade")
public class TkTradeController {

	
	@Autowired
	private TkTradeService tkTradeService;
	
	@ApiOperation(value="股票购买buy", notes="股票购买buy")
	@PostMapping("/buy")
	public CommResult<Long> buy(@Valid @RequestBody TradeBuyRequest request) {
		return tkTradeService.buy(request);
	}
	
	@ApiOperation(value="股票交易sell", notes="股票交易sell")
	@PostMapping("/sell")
	public CommResult<Long> sell(@Valid @RequestBody TradeSellRequest request) {
		return tkTradeService.sell(request);
	}
	
	@ApiOperation(value="股票交易详情", notes="股票交易详情")
	@GetMapping("/detail/{orderNo}")
	public CommResult<TradeInfoResponse> detail(@PathVariable("orderNo")Long orderNo) {
		return tkTradeService.detail(orderNo);
	}
		
	
	@ApiOperation(value="股票交易记录", notes="股票交易记录")
	@GetMapping("/record")
	public CommResult<List<TradeRecordResponse>> record(@Valid TradeRecordPageRequest request) {
		return tkTradeService.record(request);
	}
	
	
	@ApiOperation(value="股票交易记录详情", notes="股票交易记录详情")
	@GetMapping("/record/detail/{id}")
	public CommResult<TradeRecordResponse> recordDetail(@PathVariable("id")Integer id) {
		return tkTradeService.recordDetail(id);
	}
}
