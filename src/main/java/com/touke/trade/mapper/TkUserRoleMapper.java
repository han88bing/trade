package com.touke.trade.mapper;

import com.touke.trade.pojo.TkUserRole;

public interface TkUserRoleMapper {
    int insert(TkUserRole record);

    int insertSelective(TkUserRole record);
}