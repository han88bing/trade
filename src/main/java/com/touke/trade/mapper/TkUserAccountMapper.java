package com.touke.trade.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.touke.trade.pojo.TkUserAccount;

public interface TkUserAccountMapper {
	int insert(TkUserAccount record);

    int insertSelective(TkUserAccount record);

    TkUserAccount selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TkUserAccount record);

    int updateByPrimaryKey(TkUserAccount record);
    
    /**
     * 获取账户列表
     * @param userId 用户ID
     * @return
     */
    List<TkUserAccount> listByUserId(Integer userId);
    
    /**
     * 获取对象
     * @param code  证券代码
     * @param userId  用户id
     * @return
     */
    TkUserAccount findEntityByCodeUserId(@Param("code")String code,
    		@Param("userId")Integer userId);
    
    /**
     * 查询数量
     * @param code
     * @param userId  用户id
     * @return
     */
    int countByCodeUserId(@Param("code")String code,@Param("userId")Integer userId);
    
    /**
     * 账户变更 --购买
     * @param id
     * @param quantity
     * @param updateTime
     * @return
     */
    int updateAccountBuy(@Param("id")Integer id,@Param("quantity")Integer quantity,
    		@Param("updateTime")Date updateTime);
    
    /**
     * 账户变更 --卖出
     * @param code
     * @param quantity
     * @param updateTime
     * @return
     */
    int updateAccountSell(@Param("id")Integer id,@Param("quantity")Integer quantity,
    		@Param("updateTime")Date updateTime);
}