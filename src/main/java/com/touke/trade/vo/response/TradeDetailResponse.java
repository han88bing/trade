package com.touke.trade.vo.response;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="交易详情",description="交易详情")
public class TradeDetailResponse {


    @ApiModelProperty(name = "orderNo",value = "订单号", dataType = "string",required=true)
    private Long orderNo;

    @ApiModelProperty(name = "securityCode",value = "证券代码", dataType = "string",required=true)
    private String securityCode;

    @ApiModelProperty(name = "quantity",value = "交易数量", dataType = "int",required=true)
    private Integer quantity;

    @ApiModelProperty(name = "finishTime",value = "交易时间", dataType = "string",required=false)
    private String finishTime;

    @ApiModelProperty(name = "version",value = "版本号", dataType = "string",required=true)
    private String version;
    
    @ApiModelProperty(name = "type",value = "交易类型", dataType = "string",required=true)
    private String type;
    
    
    @ApiModelProperty(name = "statusMes",value = "交易状态", dataType = "string",required=true)
    private String statusMes;
}
