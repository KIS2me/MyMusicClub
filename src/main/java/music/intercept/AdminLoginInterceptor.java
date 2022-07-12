package music.intercept;

import music.exception.MyLoginException;
import music.utils.AxiosStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AdminLoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        if(request.getSession().getAttribute("admin") == null) {
            System.out.println("有错误");
            throw new MyLoginException(AxiosStatus.NOT_LOGIN);
        }
        return true;
    }
}
