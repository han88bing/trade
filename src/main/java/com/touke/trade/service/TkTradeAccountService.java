package com.touke.trade.service;

import java.util.List;

import com.touke.trade.pojo.TkUserAccount;
import com.touke.trade.vo.response.CommResult;
import com.touke.trade.vo.response.TradeAccountResponse;

/**
 * 个人账户信息
 * @author allah
 *
 */
public interface TkTradeAccountService {

	/**
	 * 账户列表
	 * @param userId 用户ID
	 * @return
	 */
	CommResult<List<TradeAccountResponse>> detail(Integer userId);
	
	
	/**
	 * 账户交易信息
	 * @param id   账户ID
	 * @return
	 */
	TkUserAccount accountFlow(Integer id);
	
}
