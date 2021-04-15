package com.touke.trade.mapper;

import com.touke.trade.pojo.TkUser;

public interface TkUserMapper {
   

	int insert(TkUser record);

    int insertSelective(TkUser record);

    TkUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TkUser record);

    int updateByPrimaryKey(TkUser record);
    
    
    TkUser selectByUsername(String username);
}