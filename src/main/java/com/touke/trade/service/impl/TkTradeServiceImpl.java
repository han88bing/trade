package com.touke.trade.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.touke.trade.constants.TkContants;
import com.touke.trade.enums.ErrorCodeEnum;
import com.touke.trade.enums.TkSecurityInfoEnums;
import com.touke.trade.enums.TkTradeEnums;
import com.touke.trade.enums.TkTradeRecordEnums;
import com.touke.trade.mapper.TkSecurityInfoMapper;
import com.touke.trade.mapper.TkTradeMapper;
import com.touke.trade.mapper.TkTradeRecordMapper;
import com.touke.trade.mapper.TkUserAccountMapper;
import com.touke.trade.pojo.TkSecurityInfo;
import com.touke.trade.pojo.TkTrade;
import com.touke.trade.pojo.TkTradeRecord;
import com.touke.trade.pojo.TkUserAccount;
import com.touke.trade.service.TkTradeService;
import com.touke.trade.util.CommonUtil;
import com.touke.trade.util.DateUtil;
import com.touke.trade.util.context.SpringContextHolder;
import com.touke.trade.util.context.UserContext;
import com.touke.trade.vo.request.TradeBuyRequest;
import com.touke.trade.vo.request.TradeCallBackRequest;
import com.touke.trade.vo.request.TradeRecordPageRequest;
import com.touke.trade.vo.request.TradeSellRequest;
import com.touke.trade.vo.response.CommResult;
import com.touke.trade.vo.response.TradeInfoResponse;
import com.touke.trade.vo.response.TradeRecordResponse;

@Service
public class TkTradeServiceImpl implements TkTradeService {

	
	@Autowired
	private TkTradeMapper tkTradeMapper;
	
	@Autowired
	private TkTradeRecordMapper tkTradeRecordMapper;
	
	@Autowired
	private TkSecurityInfoMapper tkSecurityInfoMapper;
	
	@Autowired
	private TkUserAccountMapper tkUserAccountMapper;
	
	@Transactional
	@Override
	public CommResult<Long> buy(TradeBuyRequest request) {
		
		Integer userId = UserContext.getUserId();
		
		// 证券校验  
		TkSecurityInfo tkSecurityInfo = tkSecurityInfoMapper.findEntityByCode(request.getSecurityCode(),
				TkSecurityInfoEnums.status.NORMAL.getCode());
		if(null == tkSecurityInfo) {
			return CommResult.fail(ErrorCodeEnum.TK_SECURITY_NOFIND);
		}
		
		// 生成购买订单
		Date date =new Date();
		TkTrade tkTrade =new TkTrade();
		tkTrade.setCreateTime(date);
		tkTrade.setUpdateTime(date);
		tkTrade.setOrderNo(CommonUtil.getOrder(TkContants.BUY_PREFIX+"", 3));
		tkTrade.setQuantity(request.getQuantity());
		tkTrade.setSecurityCode(request.getSecurityCode());
		tkTrade.setSecurityName(tkSecurityInfo.getName());
		tkTrade.setStatus(TkTradeEnums.status.INIT.getCode());
		tkTrade.setType(TkTradeEnums.type.BUY.getCode());
		tkTrade.setUserId(userId);
		tkTrade.setUserName(UserContext.getUserSession().getName());
		tkTradeMapper.insertSelective(tkTrade);
		
		//生成交易记录
		TkTradeRecord tkTradeRecord = new TkTradeRecord();
		tkTradeRecord.setCreateTime(date);
		tkTradeRecord.setOrderNo(tkTrade.getOrderNo());
		tkTradeRecord.setQuantity(tkTrade.getQuantity());
		tkTradeRecord.setSecurityCode(tkTrade.getSecurityCode());
		tkTradeRecord.setSecurityName(tkTrade.getSecurityName());
		tkTradeRecord.setUserId(tkTrade.getUserId());
		tkTradeRecord.setUserName(tkTrade.getUserName());
		tkTradeRecord.setVersion(TkContants.DEFAULT_VERSION);
		tkTradeRecord.setOperationType(TkTradeRecordEnums.operationType.INSERT.getCode());
		tkTradeRecord.setTradeType(TkTradeRecordEnums.tradeType.BUY.getCode());
		tkTradeRecordMapper.insertSelective(tkTradeRecord);
		
		//返回信息
		return CommResult.ok(tkTrade.getOrderNo());
	}
	
	
	@Transactional
	@Override
	public CommResult<Void> buyResult(TkTrade tkTrade, int result) {
		
		//获取版本号
		int version = tkTradeRecordMapper.findVersionByOrderNo(tkTrade.getOrderNo()) ;
		
		//业务判断
		Date date = new Date();
		
		if(TkTradeEnums.result.FAILED.getCode() == result) {
			//购买失败
			TkTrade record = new TkTrade();
			record.setId(tkTrade.getId());
			record.setUpdateTime(date);
			record.setStatus(TkTradeEnums.status.CLOSED.getCode());
			tkTradeMapper.updateByPrimaryKeySelective(record);
			
			
			//新增交易记录
			TkTradeRecord tkTradeRecord = new TkTradeRecord();
			tkTradeRecord.setCreateTime(date);
			tkTradeRecord.setOrderNo(tkTrade.getOrderNo());
			tkTradeRecord.setQuantity(tkTrade.getQuantity());
			tkTradeRecord.setSecurityCode(tkTrade.getSecurityCode());
			tkTradeRecord.setSecurityName(tkTrade.getSecurityName());
			tkTradeRecord.setUserId(tkTrade.getUserId());
			tkTradeRecord.setUserName(tkTrade.getUserName());
			tkTradeRecord.setVersion(version + 1);
			tkTradeRecord.setTradeType(TkTradeRecordEnums.tradeType.BUY.getCode());
			tkTradeRecord.setOperationType(TkTradeRecordEnums.operationType.CANCEL.getCode());
			tkTradeRecordMapper.insertSelective(tkTradeRecord);
			return CommResult.ok();
		}
		
		//购买成功
		TkTrade record = new TkTrade();
		record.setId(tkTrade.getId());
		record.setUpdateTime(date);
		record.setStatus(TkTradeEnums.status.FINISHED.getCode());
		tkTradeMapper.updateByPrimaryKeySelective(record);
		// 新增持有信息
		TkUserAccount tkUserAccount = tkUserAccountMapper.findEntityByCodeUserId(tkTrade.getSecurityCode(),
				tkTrade.getUserId());
		if(null == tkUserAccount) {
			// 不存在 则新增
			tkUserAccount = new TkUserAccount();
			tkUserAccount.setCreateTime(date);
			tkUserAccount.setName(tkTrade.getUserName());
			tkUserAccount.setQuantity(tkTrade.getQuantity());
			tkUserAccount.setSecurityCode(tkTrade.getSecurityCode());
			tkUserAccount.setSecurityName(tkTrade.getSecurityName());
			tkUserAccount.setUpdateTime(date);
			tkUserAccount.setUserId(tkTrade.getUserId());
			tkUserAccountMapper.insertSelective(tkUserAccount);
		}else {
			// 已存在 则修改
			tkUserAccountMapper.updateAccountBuy(tkUserAccount.getId(), tkTrade.getQuantity(), date);
		}
		
		//生成交易记录
		TkTradeRecord tkTradeRecord = new TkTradeRecord();
		tkTradeRecord.setCreateTime(date);
		tkTradeRecord.setOrderNo(tkTrade.getOrderNo());
		tkTradeRecord.setQuantity(tkTrade.getQuantity());
		tkTradeRecord.setSecurityCode(tkTrade.getSecurityCode());
		tkTradeRecord.setSecurityName(tkTrade.getSecurityName());
		tkTradeRecord.setUserId(tkTrade.getUserId());
		tkTradeRecord.setUserName(tkTrade.getUserName());
		tkTradeRecord.setVersion(version + 1);
		tkTradeRecord.setTradeType(TkTradeRecordEnums.tradeType.BUY.getCode());
		tkTradeRecord.setOperationType(TkTradeRecordEnums.operationType.UPDATE.getCode());
		tkTradeRecordMapper.insertSelective(tkTradeRecord);
		
		return CommResult.ok();
	}


	@Override
	public CommResult<Void> tradeCallback(TradeCallBackRequest request) {
		//1交易信息
		TkTrade tkTrade = tkTradeMapper.selectByPrimaryKey(request.getId());
		if(null == tkTrade) {
			return CommResult.fail("没有相关信息");
		}
		//2 校验订单状态
		if(TkTradeEnums.status.INIT.getCode() != tkTrade.getStatus().intValue()) {
			return CommResult.ok();
		}
		
		//业务处理转发
		if(TkTradeEnums.type.BUY.getCode().equals(tkTrade.getType())) {
			 //购买处理
			 return SpringContextHolder.getBean(TkTradeService.class).buyResult(tkTrade,request.getResult());
		}
		// 卖出处理
		return SpringContextHolder.getBean(TkTradeService.class).sellResult(tkTrade,request.getResult());
	}


	@Transactional
	@Override
	public CommResult<Long> sell(TradeSellRequest request) {
		
		Integer userId = UserContext.getUserId();
		
		// 证券校验  
		TkSecurityInfo tkSecurityInfo = tkSecurityInfoMapper.findEntityByCode(request.getSecurityCode(),
				TkSecurityInfoEnums.status.NORMAL.getCode());
		if(null == tkSecurityInfo) {
			return CommResult.fail(ErrorCodeEnum.TK_SECURITY_NOFIND);
		}

		//持有校验   目前1 不做冻结业务  （账户冻结）  2 重复下单   3多笔未处理订单
		TkUserAccount tkUserAccount = tkUserAccountMapper.findEntityByCodeUserId(request.getSecurityCode(), userId);
		if(null == tkUserAccount) {
			return CommResult.fail(ErrorCodeEnum.TK_ACCOUNT_NOFIND);
		}
		if(request.getQuantity() > tkUserAccount.getQuantity().intValue()) {
			return CommResult.fail(ErrorCodeEnum.TK_ACCOUNT_INSUFFICIENT);
		}
		
		// 生成购买订单
		Date date =new Date();
		TkTrade tkTrade =new TkTrade();
		tkTrade.setCreateTime(date);
		tkTrade.setUpdateTime(date);
		tkTrade.setOrderNo(CommonUtil.getOrder(TkContants.SELL_PREFIX+"", 3));
		tkTrade.setQuantity(request.getQuantity());
		tkTrade.setSecurityCode(request.getSecurityCode());
		tkTrade.setSecurityName(tkSecurityInfo.getName());
		tkTrade.setStatus(TkTradeEnums.status.INIT.getCode());
		tkTrade.setType(TkTradeEnums.type.SELL.getCode());
		tkTrade.setUserId(userId);
		tkTrade.setUserName(UserContext.getUserSession().getName());
		tkTradeMapper.insertSelective(tkTrade);
		
		//生成交易记录
		TkTradeRecord tkTradeRecord = new TkTradeRecord();
		tkTradeRecord.setCreateTime(date);
		tkTradeRecord.setOrderNo(tkTrade.getOrderNo());
		tkTradeRecord.setQuantity(tkTrade.getQuantity());
		tkTradeRecord.setSecurityCode(tkTrade.getSecurityCode());
		tkTradeRecord.setSecurityName(tkTrade.getSecurityName());
		tkTradeRecord.setUserId(tkTrade.getUserId());
		tkTradeRecord.setUserName(tkTrade.getUserName());
		tkTradeRecord.setVersion(TkContants.DEFAULT_VERSION);
		tkTradeRecord.setTradeType(TkTradeRecordEnums.tradeType.SELL.getCode());
		tkTradeRecord.setOperationType(TkTradeRecordEnums.operationType.INSERT.getCode());
		tkTradeRecordMapper.insertSelective(tkTradeRecord);
		
		//返回信息
		return CommResult.ok(tkTrade.getOrderNo());
	}

	@Transactional
	@Override
	public CommResult<Void> sellResult(TkTrade tkTrade, int result) {
		//获取版本号
		int version = tkTradeRecordMapper.findVersionByOrderNo(tkTrade.getOrderNo()) ;
		
		//业务判断
		Date date = new Date();
		
		if(TkTradeEnums.result.FAILED.getCode() == result) {
			//卖出失败
			TkTrade record = new TkTrade();
			record.setId(tkTrade.getId());
			record.setUpdateTime(date);
			record.setStatus(TkTradeEnums.status.CLOSED.getCode());
			tkTradeMapper.updateByPrimaryKeySelective(record);
			
			
			//新增交易记录
			TkTradeRecord tkTradeRecord = new TkTradeRecord();
			tkTradeRecord.setCreateTime(date);
			tkTradeRecord.setOrderNo(tkTrade.getOrderNo());
			tkTradeRecord.setQuantity(tkTrade.getQuantity());
			tkTradeRecord.setSecurityCode(tkTrade.getSecurityCode());
			tkTradeRecord.setSecurityName(tkTrade.getSecurityName());
			tkTradeRecord.setUserId(tkTrade.getUserId());
			tkTradeRecord.setUserName(tkTrade.getUserName());
			tkTradeRecord.setVersion(version + 1);
			tkTradeRecord.setTradeType(TkTradeRecordEnums.tradeType.SELL.getCode());
			tkTradeRecord.setOperationType(TkTradeRecordEnums.operationType.CANCEL.getCode());
			tkTradeRecordMapper.insertSelective(tkTradeRecord);
			return CommResult.ok();
		}
		
		//交易成功
		TkTrade record = new TkTrade();
		record.setId(tkTrade.getId());
		record.setUpdateTime(date);
		record.setStatus(TkTradeEnums.status.FINISHED.getCode());
		tkTradeMapper.updateByPrimaryKeySelective(record);
		// 新增持有信息
		TkUserAccount tkUserAccount = tkUserAccountMapper.findEntityByCodeUserId(tkTrade.getSecurityCode(),
				tkTrade.getUserId());
	
		// 已存在 则修改
		tkUserAccountMapper.updateAccountSell(tkUserAccount.getId(), tkTrade.getQuantity(), date);
	
		//生成交易记录
		TkTradeRecord tkTradeRecord = new TkTradeRecord();
		tkTradeRecord.setCreateTime(date);
		tkTradeRecord.setOrderNo(tkTrade.getOrderNo());
		tkTradeRecord.setQuantity(tkTrade.getQuantity());
		tkTradeRecord.setSecurityCode(tkTrade.getSecurityCode());
		tkTradeRecord.setSecurityName(tkTrade.getSecurityName());
		tkTradeRecord.setUserId(tkTrade.getUserId());
		tkTradeRecord.setUserName(tkTrade.getUserName());
		tkTradeRecord.setVersion(version + 1);
		tkTradeRecord.setTradeType(TkTradeRecordEnums.tradeType.SELL.getCode());
		tkTradeRecord.setOperationType(TkTradeRecordEnums.operationType.UPDATE.getCode());
		tkTradeRecordMapper.insertSelective(tkTradeRecord);
		
		return CommResult.ok();
	}
	
	
	
	
	@Override
	public CommResult<TradeInfoResponse> detail(Long orderNo) {
		TkTrade tkTrade = tkTradeMapper.findEntityByOrderNo(orderNo);
		if(null == tkTrade) {
			CommResult.ok();
		}
		TradeInfoResponse response = new TradeInfoResponse();
        BeanUtils.copyProperties(tkTrade,response);
        response.setStatusMes(TkTradeEnums.status.getDesc(tkTrade.getStatus()));
        response.setTradeTime(DateUtil.dateToString(tkTrade.getCreateTime(), DateUtil.DATE_FORMAT_FULL));
        return  CommResult.ok(response);
	}


	@Override
	public CommResult<List<TradeRecordResponse>> record(TradeRecordPageRequest request) {
		PageHelper.startPage(request.getPageNum(), request.getPageSize());
		List<TkTradeRecord> recordList = tkTradeRecordMapper.listResponseByPage(request);
		
		//类型转化
	    List<TradeRecordResponse> responses = recordList.stream().map(item ->{
	    	TradeRecordResponse response = new TradeRecordResponse();
            BeanUtils.copyProperties(item,response);
            response.setTradeTime(DateUtil.dateToString(item.getCreateTime(), DateUtil.DATE_FORMAT_FULL));
            return response;
        }).collect(Collectors.toList());
		
		return CommResult.ok(responses);
	}

	@Override
	public CommResult<TradeRecordResponse> recordDetail(Integer id) {
		TkTradeRecord tkTradeRecord = tkTradeRecordMapper.selectByPrimaryKey(id);
		if(null == tkTradeRecord) {
			CommResult.ok();
		}
		TradeRecordResponse response = new TradeRecordResponse();
        BeanUtils.copyProperties(tkTradeRecord,response);
        response.setTradeTime(DateUtil.dateToString(tkTradeRecord.getCreateTime(), DateUtil.DATE_FORMAT_FULL));
        return  CommResult.ok(response);
	}

}
