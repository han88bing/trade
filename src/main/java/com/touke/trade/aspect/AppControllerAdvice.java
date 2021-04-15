package com.touke.trade.aspect;


import lombok.extern.slf4j.Slf4j;

import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindException;

import com.touke.trade.enums.ErrorCodeEnum;
import com.touke.trade.util.converter.DateConverter;
import com.touke.trade.util.converter.DoubleConverter;
import com.touke.trade.util.converter.IntegerConverter;
import com.touke.trade.util.converter.LongConverter;
import com.touke.trade.vo.response.CommResult;
import com.touke.trade.util.converter.BigDecimalConverter;
import com.touke.trade.exception.BusinessException;
import java.nio.file.AccessDeniedException;

/**
 * response 异常捕获 通用格式返回
 */
@ControllerAdvice
@Slf4j
public class AppControllerAdvice {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public CommResult<String> handleException(Exception e) {
        log.error("系统异常:", e);
        return CommResult.fail(ErrorCodeEnum.SYSTEM_ERROR);
    }

    /**
     * 捕获@Valid校验抛出的异常
     */
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public CommResult<String> handleException(BindException e) {
        String msg = e.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        log.error("缺少参数:{}", msg);
        return CommResult.fail(ErrorCodeEnum.PARAM_ERROR.getCode(), msg);
    }


    /**
     * 捕获@Valid校验抛出的异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public CommResult<String> handleException(MethodArgumentNotValidException e) {
        String msg = e.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        log.error("缺少参数2:{}", msg);
        return CommResult.fail(ErrorCodeEnum.PARAM_ERROR.getCode(), msg);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public CommResult<String> handleException(MissingServletRequestParameterException e) {
        String msg = e.getParameterName();
        log.error("缺少参数Param:{}", msg);
        return CommResult.fail(ErrorCodeEnum.PARAM_ERROR.getCode(), msg);
    }


    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public CommResult<String> handleException(BusinessException be) {
        log.error("业务异常:{}", be.getMessage());
        return CommResult.fail(be.getRspCode(), be.getMessage(), be.getData());
    }


    /**
     * 无权限
     * @author caochangshan
     * @date 2020/4/22
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public CommResult<String> handleException(AccessDeniedException be) {
        log.error("无权限访问{}", be.getMessage());
        return CommResult.fail(ErrorCodeEnum.ACCESS_DENIED);
    }


    @InitBinder
    public void initBinder(WebDataBinder binder) {
        GenericConversionService genericConversionService = (GenericConversionService) binder.getConversionService();
        if (genericConversionService != null) {
            genericConversionService.addConverter(new DateConverter());
            genericConversionService.addConverter(new IntegerConverter());
            genericConversionService.addConverter(new DoubleConverter());
            genericConversionService.addConverter(new LongConverter());
            genericConversionService.addConverter(new BigDecimalConverter());
        }
    }
}
