package com.touke.trade.enums;

/**
 * 订单枚举
 * @author allah
 *
 */
public interface TkTradeEnums {

	/**订单状态*/
	enum status{
		
		INIT(1,"初始化"),
		FINISHED(2,"成功"),
		CLOSED(3,"关闭");
		
		int code;
	    String desc;
	    
		private status(int code, String desc) {
			this.code = code;
			this.desc = desc;
		}

		public int getCode() {
			return code;
		}

		public String getDesc() {
			return desc;
		}
		
		public static String getDesc(int code) {
			for (status st : status.values()) {
				if (code == st.getCode()) {
					return st.getDesc();
				}
			}
			return "";
		}
	}
	
	/**订单结果*/
	enum result{
		
		SUCCESS(1,"成功"),
		FAILED(2,"失败");
		
		int code;
	    String desc;
	    
		private result(int code, String desc) {
			this.code = code;
			this.desc = desc;
		}

		public int getCode() {
			return code;
		}

		public String getDesc() {
			return desc;
		}
	    
	}
	
	/**订单状态*/
	enum type{
		
		BUY("Buy","购买"),
		SELL("Sell","卖出");
		
		String code;
	    String desc;
	    
		private type(String code, String desc) {
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
