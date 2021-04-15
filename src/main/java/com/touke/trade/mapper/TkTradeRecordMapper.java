package com.touke.trade.mapper;

import java.util.List;

import com.touke.trade.pojo.TkTradeRecord;
import com.touke.trade.vo.request.TradeRecordPageRequest;

public interface TkTradeRecordMapper {
   


	int insert(TkTradeRecord record);

	int insertSelective(TkTradeRecord record);

	TkTradeRecord selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(TkTradeRecord record);

	int updateByPrimaryKey(TkTradeRecord record);
    

    /**
     * 根据订单号 获取最大版本号
     * @param orderNo
     * @return
     */
    int findVersionByOrderNo(Long orderNo);
    
    /**
     * 获取交易记录
     * @param request
     * @return
     */
    List<TkTradeRecord> listResponseByPage(TradeRecordPageRequest request);
}