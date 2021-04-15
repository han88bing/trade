package com.touke.trade.service;

import java.util.List;

import com.touke.trade.pojo.TkMenu;

/**
 * 菜单权限
 * @author allah
 *
 */
public interface TkMenuService {

	/**
	 * 获取权限信息
	 * @param userId
	 * @return
	 */
	List<TkMenu> getMenusByUserId(int userId);
}
