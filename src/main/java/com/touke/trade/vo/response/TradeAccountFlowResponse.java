package com.touke.trade.vo.response;


import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="交易详情",description="交易详情")
public class TradeAccountFlowResponse {


    @ApiModelProperty(name = "id",value = "主键ID", dataType = "int",required=true)
    private Integer id;

    @ApiModelProperty(name = "securityCode",value = "证券代码", dataType = "string",required=true)
    private String securityCode;

    @ApiModelProperty(name = "quantity",value = "交易数量", dataType = "int",required=true)
    private Integer quantity;

    @ApiModelProperty(name = "securityName",value = "证券名称", dataType = "string",required=true)
    private String securityName;
    
    @ApiModelProperty(name = "flowRecord",value = "交易记录", dataType = "string",required=true)
    private List<TradeRecordResponse> flowRecord;
 
}
