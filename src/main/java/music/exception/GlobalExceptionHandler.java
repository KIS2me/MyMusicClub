package music.exception;

import com.alibaba.fastjson.JSONObject;
import music.utils.Constants;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理
 */
@ControllerAdvice(annotations = {RestController.class}) //用于确定需要处理加了哪些注解的类抛出的异常
@ResponseBody
public class GlobalExceptionHandler {
    /**
     * 异常处理方法
     * @return
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class) //用于确定方法是处理哪类异常的处理器，参数为异常类型的class文件
    public Object exceptionHandler1(SQLIntegrityConstraintViolationException ex) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Constants.CODE, 0);
        String msg = "";

        if(ex.getMessage().contains("Duplicate entry")) {
            //错误为已存在同名歌手
            String[] split = ex.getMessage().split(" ");
            msg = split[2] + "已存在";
        }else {
            msg = "未知错误";
        }
        jsonObject.put(Constants.MSG, msg);

        return jsonObject ;
    }

    @ExceptionHandler(IOException.class)
    public Object exceptionHandler1(IOException ex) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Constants.CODE, 0);

        //错误为文件上传错误
        String msg = "文件上传错误" + ex.getMessage();
        jsonObject.put(Constants.MSG, msg);

        return jsonObject ;
    }
}
