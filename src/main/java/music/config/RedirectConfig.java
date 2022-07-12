package music.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 重定向配置方法
 */
@Configuration
public class RedirectConfig implements WebMvcConfigurer {
    /**
     * 将静态资源(图片、js、css文件)从特定位置重定向至web配置地址
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //对歌手图片的重定向
        registry.addResourceHandler("/img/singerPic/**").addResourceLocations(
                "file:" + System.getProperty("user.dir") + System.getProperty("file.separator")
                        + "img" + System.getProperty("file.separator")
                        + "singerPic" + System.getProperty("file.separator")
        );

        //对歌单图片的重定向
        registry.addResourceHandler("/img/songListPic/**").addResourceLocations(
                "file:" + System.getProperty("user.dir") + System.getProperty("file.separator")
                        + "img" + System.getProperty("file.separator")
                        + "songListPic" + System.getProperty("file.separator")
        );

        //对歌曲图片的重定向
        registry.addResourceHandler("/img/songPic/**").addResourceLocations(
                "file:" + System.getProperty("user.dir") + System.getProperty("file.separator")
                        + "img" + System.getProperty("file.separator")
                        + "songPic" + System.getProperty("file.separator")
        );

        //对歌曲文件的重定向
        registry.addResourceHandler("/song/**").addResourceLocations(
                "file:" + System.getProperty("user.dir") + System.getProperty("file.separator")
                        + "song" + System.getProperty("file.separator")
        );

        //对前台用户头像的重定向
        registry.addResourceHandler("/img/consumerPic/**").addResourceLocations(
                "file:" + System.getProperty("user.dir") + System.getProperty("file.separator")
                        + "img" + System.getProperty("file.separator")
                        + "consumerPic" + System.getProperty("file.separator")
        );
    }
}
