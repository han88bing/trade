package com.touke.trade.service;

import java.util.List;

import com.touke.trade.pojo.TkTrade;
import com.touke.trade.vo.request.TradeBuyRequest;
import com.touke.trade.vo.request.TradeCallBackRequest;
import com.touke.trade.vo.request.TradeRecordPageRequest;
import com.touke.trade.vo.request.TradeSellRequest;
import com.touke.trade.vo.response.CommResult;
import com.touke.trade.vo.response.TradeInfoResponse;
import com.touke.trade.vo.response.TradeRecordResponse;

/**
 * 交易类接口
 * @author allah
 *
 */
public interface TkTradeService {

	/**
	 * 股票购买
	 * @param request
	 * @return
	 */
	CommResult<Long> buy(TradeBuyRequest request);
	
	/**
	 * 股票交易结果
	 * @param request
	 * @return
	 */
	CommResult<Void> tradeCallback(TradeCallBackRequest request);
	
	/**
	 * 股票交易 -购买处理
	 * @param tkTrade
	 * @param result
	 * @return
	 */
	CommResult<Void> buyResult(TkTrade tkTrade,int result);
	
	/**
	 * 股票卖出
	 * @param request
	 * @return
	 */
	CommResult<Long> sell(TradeSellRequest request);
	
	/**
	 * 股票交易 -卖出处理
	 * @param tkTrade
	 * @param result
	 * @return
	 */
	CommResult<Void> sellResult(TkTrade tkTrade,int result);
	
	/**
	 * 订单信息
	 * @param orderNo
	 * @return
	 */
	CommResult<TradeInfoResponse> detail(Long orderNo);
	/**
	 * 交易记录列表
	 * @param request
	 * @return
	 */
	CommResult<List<TradeRecordResponse>> record(TradeRecordPageRequest request);
	
	
	/**
	 * 交易记录明细
	 * @param id
	 * @return
	 */
	CommResult<TradeRecordResponse> recordDetail(Integer id);
	
}
