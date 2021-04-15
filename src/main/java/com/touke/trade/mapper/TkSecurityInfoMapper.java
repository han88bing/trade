package com.touke.trade.mapper;

import org.apache.ibatis.annotations.Param;

import com.touke.trade.pojo.TkSecurityInfo;

public interface TkSecurityInfoMapper {
    int insert(TkSecurityInfo record);

    int insertSelective(TkSecurityInfo record);

    TkSecurityInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TkSecurityInfo record);

    int updateByPrimaryKey(TkSecurityInfo record);
    
    /**
     * 获取对象
     * @param code  证券代码
     * @param status 证券状态
     * @return
     */
    TkSecurityInfo findEntityByCode(@Param("code")String code,@Param("status")Integer status);
    
    
  
    
}