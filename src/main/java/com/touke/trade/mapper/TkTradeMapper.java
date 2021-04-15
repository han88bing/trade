package com.touke.trade.mapper;

import com.touke.trade.pojo.TkTrade;

public interface TkTradeMapper {
	
    int insert(TkTrade record);

    int insertSelective(TkTrade record);

    TkTrade selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TkTrade record);

    int updateByPrimaryKey(TkTrade record);
    
    /**
     * 订单号查询订单信息
     * @param orderNo
     * @return
     */
    TkTrade findEntityByOrderNo(Long orderNo);
    
}