package com.touke.trade.vo.request;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@ApiModel(value="股票购买",description="股票购买")
@Builder
@AllArgsConstructor
public class TradeBuyRequest {

	@Min(1)
	@NotNull(message="quantity is required")
	@ApiModelProperty(name = "quantity",value = "数量",dataType = "int",required=true)
	private Integer quantity;
	
	
	@NotBlank(message="securityCode is required")
	@ApiModelProperty(name = "securityCode",value = "证券代码",dataType = "string",required=true)
	private String securityCode;
	
}
