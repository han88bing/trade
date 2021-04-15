package com.touke.trade.mapper;

import com.touke.trade.pojo.TkMenu;

public interface TkMenuMapper {
    int insert(TkMenu record);

    int insertSelective(TkMenu record);
}