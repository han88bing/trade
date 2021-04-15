package com.touke.trade.enums;

/**
 * 证券信息
 * @author allah
 *
 */
public interface TkSecurityInfoEnums {

	
	/**证券状态*/
	enum status{
		
		NORMAL(1,"正常"),
		CLOSED(2,"关闭");
		
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
	    
	}
}
