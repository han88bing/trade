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
@ApiModel(value="股票购买结果",description="股票购买结果")
@Builder
@AllArgsConstructor
public class TradeCallBackRequest {

	@Min(1)
	@NotNull(message="id is required")
	@ApiModelProperty(name = "id",value = "订单ID",dataType = "int",required=true)
	private Integer id;
	
	
	@NotNull(message="result is required")
	@ApiModelProperty(name = "result",value = "订单结果 2-失败 1成功",dataType = "int",required=true)
	private Integer result;
	
}
