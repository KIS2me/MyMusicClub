package music.config;

import music.intercept.AdminLoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.util.*;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private AdminLoginInterceptor adminLoginInterceptor;

    /**
     * 解决前后端跨域问题
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                //设置允许跨域请求的域名
                .allowedOriginPatterns("*")
                //放行全部请求类型
                .allowedMethods("*")
                //是否发送Cookie
                .allowCredentials(true)
                //暴露哪些头部信息
                .exposedHeaders("*");
    }

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        //放行路径
//        List<String> patterns = new ArrayList();
//        patterns.add("/admin/login");
//        patterns.add("/admin/logout");
//
//        //添加一个拦截器，拦截以/为前缀的url路径
//        registry.addInterceptor(adminLoginInterceptor).addPathPatterns("/**").excludePathPatterns(patterns);
//    }
}
