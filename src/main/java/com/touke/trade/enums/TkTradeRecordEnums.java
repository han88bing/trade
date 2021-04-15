package com.touke.trade.enums;

/**
 * 交易记录
 * @author allah
 *
 */
public interface TkTradeRecordEnums {

	/**操作类型*/
	enum operationType{
		
		INSERT("INSERT","新增"),
		UPDATE("UPDATE","更新"),
		CANCEL("CANCEL","关闭");
		
		String code;
	    String desc;
	    
		private operationType(String code, String desc) {
			this.code = code;
			this.desc = desc;
		}

		public String getCode() {
			return code;
		}

		public String getDesc() {
			return desc;
		}
	    
	}
	
	/**订单类型*/
	enum tradeType{
		
		BUY("Buy","购买"),
		SELL("Sell","卖出");
		
		String code;
	    String desc;
	    
		private tradeType(String code, String desc) {
			this.code = code;
			this.desc = desc;
		}

		public String getCode() {
			return code;
		}

		public String getDesc() {
			return desc;
		}
	    
	}
}
