package com.touke.trade.mapper;

import com.touke.trade.pojo.TkRole;

public interface TkRoleMapper {
    int insert(TkRole record);

    int insertSelective(TkRole record);
}