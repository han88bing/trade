package com.touke.trade.vo.request;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@ApiModel(value="交易记录查询条件1",description="交易记录查询条件1")
@Builder
@AllArgsConstructor
public class TradeRecordPageRequest {

    //分页信息
    @ApiModelProperty(name = "pageNum",value = "pagenum",dataType = "int",required=false)
    private Integer pageNum;


    @ApiModelProperty(name = "pageSize",value = "pageSize",dataType = "int",required=false)
    private Integer pageSize;

    @ApiModelProperty(name = "type",value = "交易类型",dataType = "string",required=false)
    private String type;

    @ApiModelProperty(name = "userId",value = "用户Id",dataType = "int",required = false,hidden=true)
    private Integer userId;

    @ApiModelProperty(name = "securityCode",value = "证券代码",dataType = "string",required = false)
    private String securityCode;


    public Integer getPageNum() {
        if (null == pageNum || pageNum.intValue() <= 0){
            return 1;
        }
        return pageNum;
    }

    public Integer getPageSize() {
        if (null == pageSize || pageSize.intValue() <= 0 || pageSize.intValue()  >100 ){
            return 20;
        }
        return pageSize;
    }

    
}
