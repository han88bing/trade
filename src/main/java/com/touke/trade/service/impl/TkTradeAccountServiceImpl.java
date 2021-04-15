package com.touke.trade.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.touke.trade.mapper.TkTradeRecordMapper;
import com.touke.trade.mapper.TkUserAccountMapper;
import com.touke.trade.pojo.TkUserAccount;
import com.touke.trade.service.TkTradeAccountService;
import com.touke.trade.vo.response.CommResult;
import com.touke.trade.vo.response.TradeAccountResponse;

@Service
public class TkTradeAccountServiceImpl implements TkTradeAccountService {

	@Autowired
	private TkUserAccountMapper tkUserAccountMapper;
	
	
	@Override
	public CommResult<List<TradeAccountResponse>> detail(Integer userId) {
		List<TkUserAccount> accountList = tkUserAccountMapper.listByUserId(userId);
		List<TradeAccountResponse> accountResponse = accountList.stream().map(item->{
			TradeAccountResponse reponse = new TradeAccountResponse();
			BeanUtils.copyProperties(item, reponse);
			return reponse;
		}).collect(Collectors.toList());
		 
		return CommResult.ok(accountResponse);
	}

	@Override
	public TkUserAccount accountFlow(Integer id) {
		return tkUserAccountMapper.selectByPrimaryKey(id);
	}

}
