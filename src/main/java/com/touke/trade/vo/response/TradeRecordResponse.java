package com.touke.trade.vo.response;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="交易记录",description="交易记录")
public class TradeRecordResponse {

	@ApiModelProperty(name = "id",value = "主键ID", dataType = "string",required=true)
	private Integer id;
	
    @ApiModelProperty(name = "orderNo",value = "订单号", dataType = "string",required=true)
    private Long orderNo;

    @ApiModelProperty(name = "securityCode",value = "证券代码", dataType = "string",required=true)
    private String securityCode;

    @ApiModelProperty(name = "quantity",value = "交易数量", dataType = "int",required=true)
    private Integer quantity;

    @ApiModelProperty(name = "tradeTime",value = "交易时间", dataType = "string",required=true)
    private String tradeTime;

    @ApiModelProperty(name = "version",value = "版本号", dataType = "int",required=true)
    private Integer version;
    
    @ApiModelProperty(name = "tradeType",value = "交易类型", dataType = "string",required=true)
    private String tradeType;
}
