package com.touke.trade.vo.response;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="订单交易",description="订单交易")
public class TradeInfoResponse {


    @ApiModelProperty(name = "orderNo",value = "订单号", dataType = "string",required=true)
    private Long orderNo;

    @ApiModelProperty(name = "securityCode",value = "证券代码", dataType = "string",required=true)
    private String securityCode;
    
    @ApiModelProperty(name = "securityName",value = "证券名称", dataType = "string",required=true)
    private String securityName;
    
    @ApiModelProperty(name = "userName",value = "交易员名称", dataType = "string",required=true)
    private String userName;

    @ApiModelProperty(name = "quantity",value = "交易数量", dataType = "int",required=true)
    private Integer quantity;

    @ApiModelProperty(name = "tradeTime",value = "交易时间", dataType = "string",required=true)
    private String tradeTime;

    @ApiModelProperty(name = "statusMes",value = "订单状态", dataType = "string",required=true)
    private String statusMes;
    
    @ApiModelProperty(name = "type",value = "交易类型", dataType = "string",required=true)
    private String type;
}
