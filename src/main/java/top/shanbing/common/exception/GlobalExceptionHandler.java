package top.shanbing.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import top.shanbing.common.SpringApplicationContext;
import top.shanbing.domain.enums.ErrorCodeEnum;
import top.shanbing.domain.model.result.JsonResult;
import top.shanbing.domain.model.result.ResultUtil;

/**
 * 全局Controller 层处理异常拦截类
 */
@ControllerAdvice
public class GlobalExceptionHandler {
	private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public JsonResult handler(Exception e) {
    	logger.debug(e.getMessage(),e);

        if(e instanceof BizException ){
            BizException bizException = (BizException) e;
            logger.info(bizException.toString());
            return ResultUtil.error(bizException.errorCodeEnum);
        }

        logger.info(e.getMessage());
        if (e instanceof BindException) {
            //这里捕获实体校验的相关异常
            BindException bindException = (BindException) e;
            BindingResult bindingResult = bindException.getBindingResult();
            //获取所有错误信息拼接成的字符串
            String errorMessages = "";
            for(ObjectError error : bindingResult.getAllErrors()){
                errorMessages += "[" + error.getDefaultMessage() + "]";
            }
            return ResultUtil.error(ErrorCodeEnum.PARAM_VALID_ERROR.getCode(), errorMessages);
        }

		if(e instanceof IllegalArgumentException){
			return ResultUtil.error(ErrorCodeEnum.BIZ_ERROR.getCode(), e.toString());
		}

        //未知异常
        logger.error(e.getMessage(),e);
        if(SpringApplicationContext.isProd()){
            return ResultUtil.error(ErrorCodeEnum.BUSY_ERROR);
        }else{
            return ResultUtil.error(ErrorCodeEnum.UNKNOWN_ERROR.getCode(), e.toString());
        }
    }
}
