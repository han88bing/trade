package com.touke.trade;

import javax.validation.Validator;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import com.touke.trade.util.context.SpringContextHolder;




@MapperScan("com.touke.trade.mapper") 
@SpringBootApplication
public class TradeApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(TradeApplication.class, args);
		printGods();
	}
	
	   /**
     * 改配置启用后，则方法的参数或返回值可以用注解校验规则
     */
    @Bean
    public static MethodValidationPostProcessor methodValidationPostProcessor(Validator validator) {
        MethodValidationPostProcessor processor = new MethodValidationPostProcessor();
        processor.setValidator(validator);
        return processor;
    }
    

	@Bean
	public SpringContextHolder springContextHolder() {
		return new SpringContextHolder();
	}
    
    private static void printGods() {
        System.out.println(
                "--------------- 佛祖保佑 神兽护体 女神助攻 流量冲天 ---------------\n" +
                        "                             _ooOoo_                                 \n" +
                        "                            o8888888o                                \n" +
                        "                            88\" . \"88                              \n" +
                        "                            (| ^_^ |)                                \n" +
                        "                            O\\  =  /O                               \n" +
                        "                         ____/`---'\\____                            \n" +
                        "                       .'  \\\\|     |//  `.                         \n" +
                        "                      /   \\|||  :  |||//  \\                        \n" +
                        "                     /  _||||| -:- |||||-  \\                        \n" +
                        "                     |   | \\\\\\  -  /// |   |                      \n" +
                        "                     | \\_|  ''\\---/''  |   |                       \n" +
                        "                     \\  .-\\__  `-`  ___/-. /                       \n" +
                        "                   ___`. .'  /--.--\\  `. . ___                      \n" +
                        "                 .\"\" '<  `.___\\_<|>_/___.'  >'\"\".               \n" +
                        "               | | :  `- \\`.;`\\ _ /`;.`/ - ` : | |                 \n" +
                        "               \\  \\ `-.   \\_ __\\ /__ _/   .-` /  /               \n" +
                        "         ========`-.____`-.___\\_____/___.-`____.-'========          \n" +
                        "                              `=---='                                \n" +
                        "         ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^          \n" +
                        "            佛祖保佑       永不宕机     永无BUG    流量冲天             \n" +
                        "");
    }
	
		
}
